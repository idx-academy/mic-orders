package com.academy.orders.apirest.accounts.mapper;

import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders_api_rest.generated.model.AccountStatusDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountDTOMapper {
	UserStatus mapToUserStatus(AccountStatusDTO accountStatusDTO);
}
