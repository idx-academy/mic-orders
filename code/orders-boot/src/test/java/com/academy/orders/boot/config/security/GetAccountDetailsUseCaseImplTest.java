package com.academy.orders.boot.config.security;

import com.academy.orders.domain.account.entity.AccountDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAccountDetailsUseCaseImplTest {
	@InjectMocks
	private GetUserDetailsUseCaseImpl getUserDetailsUseCase;

	@Mock
	private SecurityUser securityUser;

	@Test
	void getUserDetailsFromUserDetailsTest() {
		long id = 1L;
		String firstName = "John";
		String lastName = "Doe";

		when(securityUser.getId()).thenReturn(id);
		when(securityUser.getFirstName()).thenReturn(firstName);
		when(securityUser.getLastName()).thenReturn(lastName);

		AccountDetails accountDetails = getUserDetailsUseCase.getUserDetailsFromUserDetails(securityUser);

		assertEquals(id, accountDetails.id());
		assertEquals(firstName, accountDetails.firstName());
		assertEquals(lastName, accountDetails.lastName());
	}
}
