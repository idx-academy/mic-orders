package com.academy.orders.domain.language.repository.exception;

import com.academy.orders.domain.exception.NotFoundException;
import lombok.Getter;

@Getter
public class LanguageNotFoundException extends NotFoundException {
	private final String code;

	public LanguageNotFoundException(String code) {
		super(String.format("Language %s is not found", code));
		this.code = code;
	}
}
