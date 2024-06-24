package com.academy.orders.apirest.common;

import com.academy.orders.domain.account.exception.AccountAlreadyExistsException;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders_api_rest.generated.model.ErrorObjectDTO;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {
	private static final String DEFAULT_ERROR_MESSAGE = "Something went wrong";

	@InjectMocks
	private ErrorHandler errorHandler;

	@Test
	void handleConstraintViolationExceptionTest() {
		var ex = mock(MethodArgumentNotValidException.class);
		var fieldError = mock(FieldError.class);

		when(fieldError.getDefaultMessage()).thenReturn(DEFAULT_ERROR_MESSAGE);
		when(ex.getFieldError()).thenReturn(fieldError);

		assertEquals(buildErrorObjectDTO(BAD_REQUEST), errorHandler.handleConstraintViolationException(ex));
	}

	@Test
	void handleNotFoundExceptionTest() {
		var orderId = UUID.randomUUID();
		var message = String.format("Order %s is not found", orderId);
		var ex = new OrderNotFoundException(orderId);

		assertEquals(buildErrorObjectDTO(NOT_FOUND, message), errorHandler.handleNotFoundException(ex));
	}

	@Test
	void handleAccountAlreadyExistsExceptionTest() {
		var email = "test@mail.com";
		var message = String.format("Account with email %s already exists", email);
		var ex = new AccountAlreadyExistsException(email);

		assertEquals(buildErrorObjectDTO(CONFLICT, message), errorHandler.handleAccountAlreadyExistsException(ex));
	}

	@Test
	void handleAuthenticationExceptionTest() {
		var ex = mock(AuthenticationException.class);
		when(ex.getMessage()).thenReturn(DEFAULT_ERROR_MESSAGE);

		assertEquals(buildErrorObjectDTO(UNAUTHORIZED), errorHandler.handleUnauthorizedException(ex));
	}

	@Test
	void handleAccessDeniedExceptionTest() {
		var ex = new AccessDeniedException(DEFAULT_ERROR_MESSAGE);
		assertEquals(buildErrorObjectDTO(FORBIDDEN), errorHandler.handleAccessDeniedException(ex));
	}

	@Test
	void handleInternalErrorTest() {
		var ex = new Throwable(DEFAULT_ERROR_MESSAGE);
		assertEquals(buildErrorObjectDTO(INTERNAL_SERVER_ERROR), errorHandler.handleInternalError(ex));
	}

	@Test
	void handleBadRequestExceptionTest() {
		var ex = mock(MethodArgumentTypeMismatchException.class);

		when(ex.getMessage()).thenReturn(DEFAULT_ERROR_MESSAGE);
		assertEquals(buildErrorObjectDTO(BAD_REQUEST), errorHandler.handleBadRequestException(ex));
	}

	private ErrorObjectDTO buildErrorObjectDTO(HttpStatus status, String detail) {
		return new ErrorObjectDTO(status.value(), status.getReasonPhrase(), detail);
	}

	private ErrorObjectDTO buildErrorObjectDTO(HttpStatus status) {
		return new ErrorObjectDTO(status.value(), status.getReasonPhrase(), DEFAULT_ERROR_MESSAGE);
	}
}
