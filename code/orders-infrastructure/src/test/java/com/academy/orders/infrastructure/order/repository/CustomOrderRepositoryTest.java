package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.infrastructure.ModelUtils;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaJoin;
import org.hibernate.query.criteria.JpaPath;
import org.hibernate.query.criteria.JpaRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomOrderRepositoryTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private CriteriaQuery<OrderEntity> criteriaQuery;

	@Mock
	private JpaCriteriaQuery<UUID> countCriteriaQuery;

	@Mock
	private Root<OrderEntity> root;

	@Mock
	private Join<Object, Object> orderItemJoin;

	@Mock
	private Join<Object, Object> postAddressJoin;

	@Mock
	private JpaJoin<Object, Object> jpaOrderItemJoin;

	@Mock
	private JpaJoin<Object, Object> jpaPostAddressJoin;

	@Mock
	private Join<Object, Object> accountJoin;

	@Mock
	private TypedQuery<OrderEntity> typedQuery;

	@Mock
	private JpaCriteriaQuery<UUID> countTypedQuery;

	@Mock
	private Predicate predicate;

	@Mock
	private Path<Object> path;

	@Mock
	private Fetch<Object, Object> fetch;

	@Mock
	private Expression<Long> expression;

	@Mock
	private JpaCriteriaQuery<Long> jpaCriteriaQueryLong;

	@Mock
	private TypedQuery<Long> typedQueryLong;

	@Mock
	private JpaRoot<OrderEntity> jpaRoot;

	@Mock
	private JpaPath jpaPath;

	@InjectMocks
	private CustomOrderRepository customOrderRepository;

	@BeforeEach
	public void setUp() {
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
		customOrderRepository = new CustomOrderRepository(entityManager);
	}

	@Test
	void testFindAllByFilterParameters() {
		// Given
		OrdersFilterParametersDto filterParametersDto = ModelUtils.getOrdersFilterParametersDto();
		Pageable pageable = PageRequest.of(1, 2, Sort.by("total"));
		OrderEntity orderEntity = ModelUtils.getOrderEntity();
		List<OrderEntity> orderEntities = Collections.singletonList(orderEntity);
		PageImpl<OrderEntity> expected = new PageImpl<>(orderEntities, pageable, 1L);

		when(criteriaBuilder.createQuery(OrderEntity.class)).thenReturn(criteriaQuery);
		when(criteriaQuery.from(OrderEntity.class)).thenReturn(root);
		when(root.join("orderItems", JoinType.LEFT)).thenReturn(orderItemJoin);
		when(orderItemJoin.get(anyString())).thenReturn(path);
		when(root.join("postAddress", JoinType.LEFT)).thenReturn(postAddressJoin);
		when(root.join("account", JoinType.LEFT)).thenReturn(accountJoin);
		when(accountJoin.get("id")).thenReturn(path);
		when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
		when(typedQuery.setMaxResults(pageable.getPageSize())).thenReturn(typedQuery);
		when(typedQuery.setFirstResult((int) pageable.getOffset())).thenReturn(typedQuery);
		when(root.get(anyString())).thenReturn(path);
		when(criteriaBuilder.equal(any(Path.class), any(Boolean.class))).thenReturn(predicate);
		when(postAddressJoin.get(anyString())).thenReturn(path);
		when(path.in(ArgumentMatchers.<Object>any())).thenReturn(predicate);
		when(criteriaBuilder.lessThanOrEqualTo(any(), any(LocalDateTime.class))).thenReturn(predicate);
		when(criteriaBuilder.greaterThanOrEqualTo(any(), any(LocalDateTime.class))).thenReturn(predicate);
		when(criteriaQuery.orderBy(any(List.class))).thenReturn(criteriaQuery);
		when(criteriaQuery.groupBy(path, path, path)).thenReturn(criteriaQuery);
		when(criteriaQuery.where(any(Predicate[].class))).thenReturn(criteriaQuery);
		when(root.fetch(anyString(), any(JoinType.class))).thenReturn(fetch);
		when(criteriaBuilder.sum(any(Path.class))).thenReturn(expression);
		when(criteriaBuilder.asc(expression)).thenReturn(mock(Order.class));

		when(criteriaBuilder.createQuery(UUID.class)).thenReturn(countCriteriaQuery);
		when(countCriteriaQuery.from(OrderEntity.class)).thenReturn(jpaRoot);
		when(jpaRoot.get(anyString())).thenReturn(jpaPath);
		when(jpaRoot.join("orderItems", JoinType.LEFT)).thenReturn(jpaOrderItemJoin);
		when(jpaRoot.join("postAddress", JoinType.LEFT)).thenReturn(jpaPostAddressJoin);
		when(countCriteriaQuery.createCountQuery()).thenReturn(jpaCriteriaQueryLong);
		when(jpaOrderItemJoin.get(anyString())).thenReturn(jpaPath);
		when(jpaPostAddressJoin.get(anyString())).thenReturn(jpaPath);
		when(typedQueryLong.getSingleResult()).thenReturn(1L);
		when(entityManager.createQuery(jpaCriteriaQueryLong)).thenReturn(typedQueryLong);
		when(countCriteriaQuery.groupBy(any(Path.class))).thenReturn(countCriteriaQuery);
		when(countCriteriaQuery.where(any(Predicate[].class))).thenReturn(countCriteriaQuery);
		when(countCriteriaQuery.select(any(Expression.class))).thenReturn(countCriteriaQuery);
		when(typedQuery.getResultList()).thenReturn(orderEntities);

		// When
		PageImpl<OrderEntity> result = customOrderRepository.findAllByFilterParameters(filterParametersDto, pageable);

		// Then
		assertEquals(expected, result);
	}
}
