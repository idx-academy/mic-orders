package com.academy.orders.boot.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableMethodSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
	private final UserDetailsService userDetailsService;

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
		return http.cors(cors -> {
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(Arrays.asList("*"));
			config.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
			config.setAllowedHeaders(Arrays.asList("*"));
			source.registerCorsConfiguration("/**", config);
			cors.configurationSource(source);
		}).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterAfter(accountStatusFilter(), BearerTokenAuthenticationFilter.class)
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
	public OncePerRequestFilter accountStatusFilter() {
		return new OncePerRequestFilter() {
			@SneakyThrows
			@Override
			protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
					@NonNull FilterChain filterChain) {
				var header = request.getHeader("Authorization");
				if (!ObjectUtils.isEmpty(header) && header.startsWith("Bearer")) {
					var token = header.split(" ")[1].trim();
					var username = jwtDecoder(generatedJwtKeyPairProvider()).decode(token).getSubject();
					log.info("Account activity verification");
					userDetailsService.loadUserByUsername(username);
				}
				filterChain.doFilter(request, response);
			}
		};
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
