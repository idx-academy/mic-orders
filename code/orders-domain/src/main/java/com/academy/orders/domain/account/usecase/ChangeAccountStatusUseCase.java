package com.academy.orders.domain.account.usecase;

import com.academy.orders.domain.account.entity.enumerated.UserStatus;

public interface ChangeAccountStatusUseCase {

	void changeStatus(Long id, UserStatus status);
}
