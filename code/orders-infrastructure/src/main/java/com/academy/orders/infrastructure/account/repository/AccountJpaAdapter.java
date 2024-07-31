package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaAdapter extends JpaRepository<AccountEntity, Long> {
	Optional<AccountEntity> findByEmail(String email);

	Boolean existsByEmail(String email);

	@Query("SELECT a.role FROM AccountEntity a WHERE a.email = :email")
	Optional<Role> findRoleByEmail(String email);

	@Modifying
	@Query("UPDATE AccountEntity a SET a.status = :status WHERE a.id = :id")
	void updateStatus(Long id, UserStatus status);
}
