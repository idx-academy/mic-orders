package com.academy.orders.apirest.auth;

import com.academy.orders_api_rest.generated.api.SecurityApi;
import com.academy.orders_api_rest.generated.model.SignInRequestDTO;
import java.time.Instant;
import java.util.stream.Collectors;
import com.academy.orders_api_rest.generated.model.AuthTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthTokenController implements SecurityApi {

	private final JwtEncoder encoder;

	private final AuthenticationManager authenticationManager;

	@Override
	public AuthTokenResponseDTO signIn(SignInRequestDTO credentials) {
		var authentication = authenticate(credentials);
		var claims = buildClaims(authentication);
		var token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

		return new AuthTokenResponseDTO(token);
	}

	private Authentication authenticate(SignInRequestDTO credentials) {

		return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
	}

	private JwtClaimsSet buildClaims(Authentication authentication) {
		Instant now = Instant.now();
		long expiry = 3600L;
		String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		return JwtClaimsSet.builder().issuer("self").issuedAt(now).expiresAt(now.plusSeconds(expiry))
				.subject(authentication.getName()).claim("scope", scope).build();
	}

}
