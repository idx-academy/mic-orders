package com.academy.orders.boot.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	private final UserDetailsService userDetailsService;
	private final String[] allowedOrigins;

	public SecurityConfig(UserDetailsService userDetailsService,
			@Value("${auth.allowed-origins}") String[] allowedOrigins) {
		this.userDetailsService = userDetailsService;
		this.allowedOrigins = allowedOrigins;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authManager(PasswordEncoder passwordEncoder) {
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(authProvider);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerExceptionResolver handlerExceptionResolver)
			throws Exception {
		return http.cors(cors -> cors.configurationSource(configSource -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(List.of(allowedOrigins));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
					"X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
			config.setAllowCredentials(true);
			config.setMaxAge(3600L);
			return config;
		})).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> {
			auth.requestMatchers("/auth/sign-in", "/auth/sign-up", "/swagger-ui/**", "/v3/api-docs/**", "/v1/products")
					.permitAll();
			auth.anyRequest().authenticated();
		}).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())
						.authenticationEntryPoint(authenticationEntryPoint(handlerExceptionResolver)))
				.build();
	}

	public AuthenticationEntryPoint authenticationEntryPoint(HandlerExceptionResolver resolver) {
		return (request, response, exception) -> resolver.resolveException(request, response, null, exception);
	}

	@Bean
	public JwtDecoder jwtDecoder(@Qualifier("jwtKeyPairProvider") Supplier<KeyPair> keyPairProvider) {
		return NimbusJwtDecoder.withPublicKey((RSAPublicKey) keyPairProvider.get().getPublic()).build();
	}

	@Bean
	public JwtEncoder jwtEncoder(@Qualifier("jwtKeyPairProvider") Supplier<KeyPair> keyPairProvider) {
		var keyPair = keyPairProvider.get();
		var jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey(keyPair.getPrivate()).build();
		var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthorityPrefix(""); // Remove the SCOPE_ prefix

		var authConverter = new JwtAuthenticationConverter();
		authConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return authConverter;
	}

	@Bean("jwtKeyPairProvider")
	// @Profile("local") TODO in case of env need to load from base64 keys/files
	public Supplier<KeyPair> generatedJwtKeyPairProvider() throws NoSuchAlgorithmException {
		var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		var keyPair = keyPairGenerator.generateKeyPair();

		return () -> keyPair;
	}
}
