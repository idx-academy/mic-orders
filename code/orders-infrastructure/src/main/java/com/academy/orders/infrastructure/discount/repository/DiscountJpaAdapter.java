package com.academy.orders.infrastructure.discount.repository;

import com.academy.orders.infrastructure.discount.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscountJpaAdapter extends JpaRepository<DiscountEntity, UUID> {
}
