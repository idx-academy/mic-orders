package com.academy.orders.infrastructure;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import java.time.LocalDateTime;
import java.util.List;

public class ModelUtils {
	private ModelUtils() {
	}

	public static AccountEntity getAccountEntity() {
		return AccountEntity.builder().id(1L).password("$2a$12$5ZEfkhNQUREmioQ54TaFaOEM7h/QBgASIeqZceFGKPT80aTfYdvV.")
				.email("mock@mail.com").firstName("MockFirst").lastName("MockLast").role(Role.ROLE_ADMIN)
				.status(UserStatus.ACTIVE).createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).build();
	}

	public static Account getAccount() {
		return Account.builder().id(1L).password("$2a$12$5ZEfkhNQUREmioQ54TaFaOEM7h/QBgASIeqZceFGKPT80aTfYdvV.")
				.email("mock@mail.com").firstName("MockFirst").lastName("MockLast").role(Role.ROLE_ADMIN)
				.status(UserStatus.ACTIVE).createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).build();
	}

	public static CreateAccountDTO getCreateAccountDTO() {
		return CreateAccountDTO.builder().password("$2a$12$5ZEfkhNQUREmioQ54TaFaOEM7h/QBgASIeqZceFGKPT80aTfYdvV.")
				.email("mock@mail.com").firstName("MockFirst").lastName("MockLast").build();
	}

	public static Pageable getPageable() {
		return Pageable.builder().page(0).size(8).sort(List.of("test")).build();
	}
}