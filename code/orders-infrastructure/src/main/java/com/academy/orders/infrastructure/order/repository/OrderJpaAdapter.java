package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.infrastructure.order.entity.OrderEntity;
import java.util.UUID;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaAdapter extends CrudRepository<OrderEntity, UUID> {
	@Query("select o from OrderEntity o " + "left join fetch o.postAddress pa " + "left join fetch o.orderItems oa "
			+ "left join fetch oa.product p " + "where o.account.id = :accountId")
	PageImpl<OrderEntity> findAllByAccount_Id(Long accountId, Pageable pageable);
}
