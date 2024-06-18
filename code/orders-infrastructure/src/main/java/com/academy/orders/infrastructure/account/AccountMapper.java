package com.academy.orders.infrastructure.account;

import com.academy.orders.domain.entity.Account;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account fromEntity(AccountEntity account);
}
