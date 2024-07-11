package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.order.dto.OrderFilterParametersDto;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import com.academy.orders.infrastructure.order.entity.OrderItemEntity;
import com.academy.orders.infrastructure.order.entity.PostAddressEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

@Repository
public class CustomOrderRepository {
	private final EntityManager em;
	private final CriteriaBuilder cb;

	@Autowired
	public CustomOrderRepository(EntityManager em) {
		this.em = em;
		cb = em.getCriteriaBuilder();
	}

	public PageImpl<OrderEntity> findAllByFilterParameters(OrderFilterParametersDto filterParametersDto,
			Pageable pageable) {
		var countTotalQuery = cb.createQuery(OrderEntity.class);
		var countTotalRoot = countTotalQuery.from(OrderEntity.class);

		Join<OrderEntity, OrderItemEntity> oij = countTotalRoot.join("orderItems", JoinType.LEFT);
		Join<OrderEntity, PostAddressEntity> paj = countTotalRoot.join("postAddress", JoinType.LEFT);

		List<Order> order = getOrder(pageable, countTotalRoot, oij);
		List<Predicate> predicates = getAllPredicates(countTotalRoot, paj, filterParametersDto);
		List<Predicate> totalPredicates = getTotalPredicates(oij, filterParametersDto);

		countTotalQuery.where(predicates.toArray(new Predicate[0])).groupBy(countTotalRoot.get("id")).orderBy(order);

		if (!totalPredicates.isEmpty()) {
			countTotalQuery.having(totalPredicates.toArray(new Predicate[0]));
		}

		TypedQuery<OrderEntity> countTotalTypedQuery = em.createQuery(countTotalQuery);
		List<OrderEntity> firstResults = countTotalTypedQuery.getResultList();

		if (firstResults.isEmpty()) {
			return new PageImpl<>(firstResults, pageable, 0L);
		}

		List<UUID> ids = firstResults.stream().map(OrderEntity::getId).toList();

		fetchDataByIds(ids);

		Long totalCount = getTotalCount(filterParametersDto);

		return new PageImpl<>(firstResults, pageable, totalCount);
	}

	private void fetchDataByIds(List<UUID> ids) {
		var query = cb.createQuery(OrderEntity.class);
		var root = query.from(OrderEntity.class);

		var orderItemJoin = root.fetch("orderItems", JoinType.LEFT);
		orderItemJoin.fetch("product", JoinType.LEFT);
		root.fetch("postAddress", JoinType.LEFT);
		root.fetch("account", JoinType.LEFT);

		query.select(root).where(root.get("id").in(ids.toArray()));

		em.createQuery(query).getResultList();
	}

	private List<Order> getOrder(Pageable pageable, Root<OrderEntity> countTotalRoot,
			Join<OrderEntity, OrderItemEntity> orderItemJoin) {
		Sort pageableSort = pageable.getSort();
		List<Order> orders = new LinkedList<>();

		pageableSort.forEach(order -> {
			if (order.getProperty().equals("total")) {
				var totalPriceExpression = cb.sum(orderItemJoin.get("price"));
				Order totalOrder = order.getDirection().isAscending()
						? cb.asc(totalPriceExpression)
						: cb.desc(totalPriceExpression);
				orders.add(totalOrder);
			} else {
				orders.addAll(QueryUtils.toOrders(Sort.by(order), countTotalRoot, cb));
			}
		});

		return orders;
	}

	private Long getTotalCount(OrderFilterParametersDto filterParametersDto) {
		var countQuery = cb.createQuery(Long.class);
		var countRoot = countQuery.from(OrderEntity.class);

		Join<OrderEntity, OrderItemEntity> oij = countRoot.join("orderItems", JoinType.LEFT);
		Join<OrderEntity, PostAddressEntity> paj = countRoot.join("postAddress", JoinType.LEFT);

		List<Predicate> predicatesCount = getAllPredicates(countRoot, paj, filterParametersDto);
		List<Predicate> totalCountPredicates = getTotalPredicates(oij, filterParametersDto);
		countQuery.select(cb.count(countRoot.get("id"))).where(predicatesCount.toArray(new Predicate[0]))
				.groupBy(countRoot.get("id"));

		if (!totalCountPredicates.isEmpty()) {
			countQuery.having(totalCountPredicates.toArray(new Predicate[0]));
		}
		TypedQuery<Long> countTypedQuery = em.createQuery(countQuery);

		return countTypedQuery.getSingleResult();
	}

	private List<Predicate> getTotalPredicates(Join<OrderEntity, OrderItemEntity> oij,
			OrderFilterParametersDto filterParametersDto) {
		List<Predicate> predicates = new LinkedList<>();
		if (filterParametersDto.totalLess() != null) {
			predicates.add(cb.le(cb.sum(oij.get("price")), filterParametersDto.totalLess()));
		}
		if (filterParametersDto.totalMore() != null) {
			predicates.add(cb.ge(cb.sum(oij.get("price")), filterParametersDto.totalMore()));
		}
		return predicates;
	}

	private List<Predicate> getAllPredicates(Root<OrderEntity> root, Join<OrderEntity, PostAddressEntity> paj,
			OrderFilterParametersDto filterParametersDto) {
		List<Predicate> predicates = new LinkedList<>();
		if (filterParametersDto.isPaid() != null) {
			predicates.add(cb.equal(root.get("isPaid"), filterParametersDto.isPaid()));
		}
		if (!filterParametersDto.deliveryMethods().isEmpty()) {
			predicates.add(paj.get("deliveryMethod").in(filterParametersDto.deliveryMethods().toArray()));
		}
		if (!filterParametersDto.statuses().isEmpty()) {
			predicates.add(root.get("orderStatus").in(filterParametersDto.statuses().toArray()));
		}
		if (filterParametersDto.createdBefore() != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filterParametersDto.createdBefore()));
		}
		if (filterParametersDto.createdAfter() != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filterParametersDto.createdAfter()));
		}
		return predicates;
	}
}
