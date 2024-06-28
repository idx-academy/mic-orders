package com.academy.orders.domain.account.usecase;

import com.academy.orders.domain.account.entity.AccountDetails;

public interface GetUserDetailsUseCase {
	AccountDetails getUserDetailsFromUserDetails(Object principal);
}
