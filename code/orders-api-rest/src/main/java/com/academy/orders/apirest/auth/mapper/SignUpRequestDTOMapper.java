package com.academy.orders.apirest.auth.mapper;

import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders_api_rest.generated.model.SignInRequestDTO;
import com.academy.orders_api_rest.generated.model.SignUpRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SignUpRequestDTOMapper {
    CreateAccountDTO fromDto(SignUpRequestDTO signUpRequestDTO);
    SignInRequestDTO toSignInRequestDto(SignUpRequestDTO signUpRequestDTO);
}
