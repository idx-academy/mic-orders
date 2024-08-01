package com.academy.orders.infrastructure.account;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface AccountPageMapper {
	com.academy.orders.domain.common.Page<Account> toDomain(Page<AccountEntity> page);
}
