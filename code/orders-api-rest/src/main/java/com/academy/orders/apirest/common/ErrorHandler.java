package com.academy.orders.apirest.common;

import com.academy.orders.domain.cart.exception.EmptyCartException;
import com.academy.orders.domain.cart.exception.ExceedsAvailableException;
import com.academy.orders.domain.exception.AlreadyExistsException;
import com.academy.orders.domain.exception.NotFoundException;
import com.academy.orders.domain.order.exception.InsufficientProductQuantityException;
import com.academy.orders.domain.order.exception.InvalidOrderStatusTransitionException;
import com.academy.orders_api_rest.generated.model.ErrorObjectDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static java.util.Objects.requireNonNull;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public final ErrorObjectDTO handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
		log.warn("Constraint violation ", ex);
		return new ErrorObjectDTO().status(HttpStatus.BAD_REQUEST.value())
				.title(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.detail(requireNonNull(ex.getFieldError()).getDefaultMessage());

	}

	@ExceptionHandler(value = NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorObjectDTO handleNotFoundException(final NotFoundException ex) {
		log.warn("Can't find entity", ex);
		return new ErrorObjectDTO().status(HttpStatus.NOT_FOUND.value()).title(HttpStatus.NOT_FOUND.getReasonPhrase())
				.detail(ex.getMessage());
	}

	@ExceptionHandler(AlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ErrorObjectDTO handleAccountAlreadyExistsException(final AlreadyExistsException ex) {
		log.warn("Account already exists ", ex);
		return new ErrorObjectDTO().status(HttpStatus.CONFLICT.value()).title(HttpStatus.CONFLICT.getReasonPhrase())
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

	@ExceptionHandler(EmptyCartException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorObjectDTO handleEmptyCartException(final EmptyCartException ex) {
		log.warn("Cart is empty.", ex);
		return new ErrorObjectDTO().status(HttpStatus.BAD_REQUEST.value())
				.title(HttpStatus.BAD_REQUEST.getReasonPhrase()).detail(ex.getMessage());
	}

	@ExceptionHandler(InsufficientProductQuantityException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ErrorObjectDTO handleInsufficientProductQuantityException(final InsufficientProductQuantityException ex) {
		log.warn("Ordered quantity exceeds available stock for product. ", ex);
		return new ErrorObjectDTO().status(HttpStatus.CONFLICT.value()).title(HttpStatus.CONFLICT.getReasonPhrase())
				.detail(ex.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorObjectDTO handleConstraintViolationException(final ConstraintViolationException error) {
		log.warn("Bad request, param: {}", error.getMessage(), error);
		return new ErrorObjectDTO().status(HttpStatus.BAD_REQUEST.value())
				.title(HttpStatus.BAD_REQUEST.getReasonPhrase()).detail(error.getMessage());
	}

	@ExceptionHandler(ExceedsAvailableException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ErrorObjectDTO handleExceedsAvailableException(final ExceedsAvailableException ex) {
		log.warn("Product quantity exceeds available stock", ex);
		return new ErrorObjectDTO().status(HttpStatus.CONFLICT.value()).title(HttpStatus.CONFLICT.getReasonPhrase())
				.detail(ex.getMessage());
	}

	@ExceptionHandler(InvalidOrderStatusTransitionException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorObjectDTO handleInvalidOrderStatusTransitionException(final InvalidOrderStatusTransitionException ex) {
		log.warn("Invalid Order Status Transition", ex);
		return new ErrorObjectDTO().status(HttpStatus.BAD_REQUEST.value())
				.title(HttpStatus.BAD_REQUEST.getReasonPhrase()).detail(ex.getMessage());
	}
}
