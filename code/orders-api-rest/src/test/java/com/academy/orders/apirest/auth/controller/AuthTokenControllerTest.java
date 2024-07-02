package com.academy.orders.apirest.auth.controller;

import com.academy.orders.apirest.auth.mapper.SignUpRequestDTOMapperImpl;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.domain.account.usecase.GetAccountDetailsUseCase;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.usecase.CreateUserAccountUseCase;
import com.academy.orders_api_rest.generated.model.SignInRequestDTO;
import com.academy.orders_api_rest.generated.model.SignUpRequestDTO;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthTokenController.class)
@ContextConfiguration(classes = {AuthTokenController.class})
@Import(value = {SignUpRequestDTOMapperImpl.class, AopAutoConfiguration.class, TestSecurityConfig.class})
class AuthTokenControllerTest {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtEncoder encoder;
	@MockBean
	private PasswordEncoder passwordEncoder;
	@MockBean
	private AuthenticationManager authenticationManager;
	@MockBean
	private CreateUserAccountUseCase createUserAccountUseCase;
	@Mock
	private GetAccountDetailsUseCase getAccountDetailsUseCase;
	@Test
	void signInTest() throws Exception {
		var signInRequestDTO = new SignInRequestDTO("admin@mail.com", "Admin_1234");

		var authentication = new UsernamePasswordAuthenticationToken(getAccountDetailsUseCase,
				signInRequestDTO.getPassword(), List.of(() -> "ROLE_USER"));
		var jwt = mock(Jwt.class);
		var token = "token-value";

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authentication);
		when(getAccountDetailsUseCase.getId()).thenReturn(1L);
		when(getAccountDetailsUseCase.getFirstName()).thenReturn("TestName");
		when(getAccountDetailsUseCase.getLastName()).thenReturn("TestLastName");

		when(encoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);
		when(jwt.getTokenValue()).thenReturn(token);

		mockMvc.perform(post("/auth/sign-in").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signInRequestDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value(token));

		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(getAccountDetailsUseCase).getId();
		verify(getAccountDetailsUseCase).getFirstName();
		verify(getAccountDetailsUseCase).getLastName();
		verify(encoder).encode(any(JwtEncoderParameters.class));
	}

	@Test
	void signUpTest() throws Exception {
		var signUpRequest = new SignUpRequestDTO("newuser@mail.com", "Admin_1234", "John", "Doe");
		var signUpRequestEncoded = new SignUpRequestDTO(signUpRequest.getEmail(), "123456789",
				signUpRequest.getFirstName(), signUpRequest.getLastName());
		var signInRequestDTO = new SignInRequestDTO(signUpRequest.getEmail(), signUpRequest.getPassword());
		var createAccountDTO = CreateAccountDTO.builder().email(signUpRequestEncoded.getEmail())
				.password(signUpRequestEncoded.getPassword()).firstName(signUpRequestEncoded.getFirstName())
				.lastName(signUpRequestEncoded.getLastName()).build();

		var authentication = new UsernamePasswordAuthenticationToken(getAccountDetailsUseCase,
				signInRequestDTO.getPassword(), List.of(() -> "ROLE_USER"));
		var jwt = mock(Jwt.class);
		var token = "token-value";

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authentication);
		when(encoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);
		when(jwt.getTokenValue()).thenReturn(token);
		when(getAccountDetailsUseCase.getId()).thenReturn(1L);
		when(getAccountDetailsUseCase.getFirstName()).thenReturn("TestName");
		when(getAccountDetailsUseCase.getLastName()).thenReturn("TestLastName");

		when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn(signUpRequestEncoded.getPassword());

		mockMvc.perform(post("/auth/sign-up").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.token").value(token));

		verify(passwordEncoder).encode(signUpRequest.getPassword());
		verify(createUserAccountUseCase).create(createAccountDTO);
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(getAccountDetailsUseCase).getId();
		verify(getAccountDetailsUseCase).getFirstName();
		verify(getAccountDetailsUseCase).getLastName();
		verify(encoder).encode(any(JwtEncoderParameters.class));
	}

}
