package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
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
	public static final String ORDER_ITEMS = "orderItems";
	public static final String POST_ADDRESS = "postAddress";
	public static final String PRICE = "price";
	private final EntityManager em;
	private final CriteriaBuilder cb;

	@Autowired
	public CustomOrderRepository(EntityManager em) {
		this.em = em;
		cb = em.getCriteriaBuilder();
	}

	/**
	 * Retrieves a paginated list of order entities based on the specified filter
	 * parameters.
	 *
	 * @param filterParametersDto
	 *            the filter parameters to apply when retrieving the order entities
	 * @param pageable
	 *            the pagination information
	 * @return a paginated list of order entities that match the filter parameters
	 */
	public PageImpl<OrderEntity> findAllByFilterParameters(OrdersFilterParametersDto filterParametersDto,
			Pageable pageable) {
		var mainQuery = cb.createQuery(OrderEntity.class);
		var mainRoot = mainQuery.from(OrderEntity.class);

		Join<OrderEntity, OrderItemEntity> oij = mainRoot.join(ORDER_ITEMS, JoinType.LEFT);
		Join<OrderEntity, PostAddressEntity> paj = mainRoot.join(POST_ADDRESS, JoinType.LEFT);

		List<Order> order = getOrder(pageable, mainRoot, oij);
		List<Predicate> predicates = getAllPredicates(mainRoot, paj, filterParametersDto);
		List<Predicate> totalPredicates = getTotalPredicates(oij, filterParametersDto);

		mainQuery.where(predicates.toArray(new Predicate[0])).groupBy(mainRoot.get("id")).orderBy(order);

		if (!totalPredicates.isEmpty()) {
			mainQuery.having(totalPredicates.toArray(new Predicate[0]));
		}

		TypedQuery<OrderEntity> countTotalTypedQuery = em.createQuery(mainQuery);
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

		var orderItemFetch = root.fetch(ORDER_ITEMS, JoinType.LEFT);
		orderItemFetch.fetch("product", JoinType.LEFT);
		root.fetch(POST_ADDRESS, JoinType.LEFT);
		root.fetch("account", JoinType.LEFT);

		query.where(root.get("id").in(ids.toArray()));

		em.createQuery(query).getResultList();
	}

	private List<Order> getOrder(Pageable pageable, Root<OrderEntity> countTotalRoot,
			Join<OrderEntity, OrderItemEntity> orderItemJoin) {
		Sort pageableSort = pageable.getSort();
		List<Order> orders = new LinkedList<>();

		pageableSort.forEach(order -> {
			if (order.getProperty().equals("total")) {
				var totalPriceExpression = cb.sum(orderItemJoin.get(PRICE));
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

	private Long getTotalCount(OrdersFilterParametersDto filterParametersDto) {
		var countQuery = cb.createQuery(Long.class);
		var countRoot = countQuery.from(OrderEntity.class);

		Join<OrderEntity, OrderItemEntity> oij = countRoot.join(ORDER_ITEMS, JoinType.LEFT);
		Join<OrderEntity, PostAddressEntity> paj = countRoot.join(POST_ADDRESS, JoinType.LEFT);

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
			OrdersFilterParametersDto filterParametersDto) {
		List<Predicate> predicates = new LinkedList<>();
		if (filterParametersDto.totalLess() != null) {
			predicates.add(cb.le(cb.sum(oij.get(PRICE)), filterParametersDto.totalLess()));
		}
		if (filterParametersDto.totalMore() != null) {
			predicates.add(cb.ge(cb.sum(oij.get(PRICE)), filterParametersDto.totalMore()));
		}
		return predicates;
	}

	private List<Predicate> getAllPredicates(Root<OrderEntity> root, Join<OrderEntity, PostAddressEntity> paj,
			OrdersFilterParametersDto filterParametersDto) {
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
