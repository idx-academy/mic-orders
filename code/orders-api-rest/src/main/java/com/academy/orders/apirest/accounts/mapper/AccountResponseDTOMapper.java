package com.academy.orders.apirest.accounts.mapper;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.common.Page;
import com.academy.orders_api_rest.generated.model.AccountResponseManagementDTO;
import com.academy.orders_api_rest.generated.model.PageAccountsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface AccountResponseDTOMapper {

	@Mapping(target = "content", source = "content")
	PageAccountsDTO toResponse(Page<Account> accountPage);

	@Mapping(target = "role", source = "role")
	@Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapLocalDateTimeToOffsetDateTime")
	AccountResponseManagementDTO toResponse(Account account);

	@ValueMapping(source = "ROLE_USER", target = "USER")
	@ValueMapping(source = "ROLE_ADMIN", target = "ADMIN")
	@ValueMapping(source = "ROLE_MANAGER", target = "MANAGER")
	AccountResponseManagementDTO.RoleEnum mapRole(Role role);

	@Named("mapLocalDateTimeToOffsetDateTime")
	default OffsetDateTime mapLocalDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
		return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.UTC);
	}
}
