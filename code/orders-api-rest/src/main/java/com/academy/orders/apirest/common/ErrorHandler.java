package com.academy.orders.apirest.common;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.exception.AccountAlreadyExistsException;
import com.academy.orders.domain.exception.AlreadyExistsException;
import com.academy.orders.domain.exception.NotFoundException;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders_api_rest.generated.model.ErrorObjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorObjectDTO handleOrderNotFoundException(final NotFoundException ex) {
		log.warn("Can't find entity", ex);
		return new ErrorObjectDTO().status(HttpStatus.BAD_REQUEST.value())
				.title(HttpStatus.BAD_REQUEST.getReasonPhrase()).detail(ex.getMessage());
	}

	@ExceptionHandler(AccountAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ErrorObjectDTO handleOrderNotFoundException(final AccountAlreadyExistsException ex) {
		log.warn("Account already exists ", ex);
		return new ErrorObjectDTO()
            .status(HttpStatus.CONFLICT.value())
            .title(HttpStatus.CONFLICT.getReasonPhrase())
            .detail(ex.getMessage());
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorObjectDTO handleUnauthorizedException(final AuthenticationException ex) {
		return new ErrorObjectDTO().status(HttpStatus.UNAUTHORIZED.value())
				.title(HttpStatus.UNAUTHORIZED.getReasonPhrase()).detail(ex.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ErrorObjectDTO handleAccessDeniedException(final AccessDeniedException ex) {
		return new ErrorObjectDTO().status(HttpStatus.FORBIDDEN.value()).title(HttpStatus.FORBIDDEN.getReasonPhrase())
				.detail(ex.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorObjectDTO handleInternalError(final Throwable ex) {
		log.warn("Internal error", ex);
		return new ErrorObjectDTO().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).detail(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorObjectDTO handleBadRequestException(final MethodArgumentTypeMismatchException error) {
		log.warn("Bad request, param: {}", error.getPropertyName(), error);
		return new ErrorObjectDTO().status(HttpStatus.BAD_REQUEST.value())
				.title(HttpStatus.BAD_REQUEST.getReasonPhrase()).detail(error.getMessage());
	}

}
