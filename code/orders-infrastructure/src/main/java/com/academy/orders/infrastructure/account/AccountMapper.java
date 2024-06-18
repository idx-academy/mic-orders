package com.academy.orders.infrastructure.account;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	Account fromEntity(AccountEntity accountEntity);

	AccountEntity toEntity(CreateAccountDTO account);
}
