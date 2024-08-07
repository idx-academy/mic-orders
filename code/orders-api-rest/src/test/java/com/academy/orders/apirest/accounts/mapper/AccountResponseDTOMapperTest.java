package com.academy.orders.apirest.accounts.mapper;

import com.academy.orders.apirest.ModelUtils;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.common.Page;
import com.academy.orders_api_rest.generated.model.AccountResponseManagementDTO;
import com.academy.orders_api_rest.generated.model.PageAccountsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class AccountResponseDTOMapperTest {
	private AccountResponseDTOMapper accountResponseDTOMapper;

	@BeforeEach
	void setUp() {
		accountResponseDTOMapper = Mappers.getMapper(AccountResponseDTOMapper.class);
	}

	@Test
	void toResponseAccountTest() {
		Account account = ModelUtils.getAccount();

		AccountResponseManagementDTO result = accountResponseDTOMapper.toResponse(account);

		assertThat(result).isNotNull();
		assertThat(result.getFirstName()).isEqualTo(account.firstName());
		assertThat(result.getLastName()).isEqualTo(account.lastName());
		assertThat(result.getEmail()).isEqualTo(account.email());
		assertThat(result.getRole()).isEqualTo(AccountResponseManagementDTO.RoleEnum.USER);
		assertThat(result.getCreatedAt()).isNotNull();
	}

	@Test
	void toResponsePageTest() {
		Account account = ModelUtils.getAccount();
		Page<Account> accountPage = ModelUtils.getAccountPage();
		PageAccountsDTO result = accountResponseDTOMapper.toResponse(accountPage);

		assertThat(result).isNotNull();
		assertThat(result.getContent()).hasSize(1);
		assertThat(result.getContent().get(0).getFirstName()).isEqualTo(account.firstName());
	}

	@Test
	void mapLocalDateTimeToOffsetDateTimeTest() {
		LocalDateTime localDateTime = LocalDateTime.of(2024, 8, 4, 10, 30, 0);
		OffsetDateTime expectedOffsetDateTime = localDateTime.atOffset(ZoneOffset.UTC);
		OffsetDateTime result = accountResponseDTOMapper.mapLocalDateTimeToOffsetDateTime(localDateTime);
		assertThat(result).isEqualTo(expectedOffsetDateTime);
	}

	@Test
	void mapLocalDateTimeToOffsetDateTimeWithNullInputTest() {
		OffsetDateTime result = accountResponseDTOMapper.mapLocalDateTimeToOffsetDateTime(null);
		assertThat(result).isNull();
	}
}
