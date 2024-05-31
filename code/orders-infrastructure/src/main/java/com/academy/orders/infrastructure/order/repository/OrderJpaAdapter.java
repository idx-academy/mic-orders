package com.academy.orders.infrastructure.order.repository;

import java.util.UUID;

import com.academy.orders.infrastructure.order.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaAdapter extends CrudRepository<OrderEntity, UUID> {

}
