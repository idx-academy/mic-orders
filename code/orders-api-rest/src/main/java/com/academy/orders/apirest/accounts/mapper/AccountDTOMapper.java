package com.academy.orders.apirest.accounts.mapper;

import com.academy.orders.domain.account.dto.AccountManagementFilterDto;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders_api_rest.generated.model.AccountFilterDTO;
import com.academy.orders_api_rest.generated.model.AccountManagementRolesDTO;
import com.academy.orders_api_rest.generated.model.AccountStatusDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring")
public interface AccountDTOMapper {
	UserStatus mapToUserStatus(AccountStatusDTO accountStatusDTO);

	@Mapping(target = "status", source = "status")
	@Mapping(target = "role", source = "role")
	AccountManagementFilterDto toDomain(AccountFilterDTO accountFilterDTO);

	Pageable toDomain(PageableDTO pageableDTO);

	@ValueMapping(source = "USER", target = "ROLE_USER")
	@ValueMapping(source = "ADMIN", target = "ROLE_ADMIN")
	@ValueMapping(source = "MANAGER", target = "ROLE_MANAGER")
	Role mapRole(AccountManagementRolesDTO role);
}
