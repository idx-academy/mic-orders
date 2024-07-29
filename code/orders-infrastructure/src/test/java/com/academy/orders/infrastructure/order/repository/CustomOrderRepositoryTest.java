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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
	private CriteriaQuery<Long> countCriteriaQuery;

	@Mock
	private Root<OrderEntity> root;

	@Mock
	private Join<Object, Object> orderItemJoin;

	@Mock
	private Join<Object, Object> postAddressJoin;

	@Mock
	private Join<Object, Object> accountJoin;

	@Mock
	private TypedQuery<OrderEntity> typedQuery;

	@Mock
	private TypedQuery<Long> countTypedQuery;

	@Mock
	private Predicate predicate;

	@Mock
	private Path<Object> path;

	@Mock
	private Fetch<Object, Object> fetch;

	@Mock
	private Expression<Long> expression;

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

		when(criteriaBuilder.createQuery(Long.class)).thenReturn(countCriteriaQuery);
		when(criteriaBuilder.count(any(Path.class))).thenReturn(expression);
		when(countCriteriaQuery.from(OrderEntity.class)).thenReturn(root);
		when(entityManager.createQuery(countCriteriaQuery)).thenReturn(countTypedQuery);
		when(countCriteriaQuery.groupBy(any(Path.class))).thenReturn(countCriteriaQuery);
		when(countCriteriaQuery.where(any(Predicate[].class))).thenReturn(countCriteriaQuery);
		when(countCriteriaQuery.select(any(Expression.class))).thenReturn(countCriteriaQuery);
		when(typedQuery.getResultList()).thenReturn(orderEntities);
		when(countTypedQuery.setMaxResults(1)).thenReturn(countTypedQuery);
		when(countTypedQuery.getSingleResult()).thenReturn(1L);

		// When
		PageImpl<OrderEntity> result = customOrderRepository.findAllByFilterParameters(filterParametersDto, pageable);

		// Then
		assertEquals(expected, result);

		verify(criteriaBuilder, times(2)).createQuery(OrderEntity.class);
		verify(criteriaQuery, times(2)).from(OrderEntity.class);
		verify(root, times(2)).join("orderItems", JoinType.LEFT);
		verify(orderItemJoin, times(5)).get(anyString());
		verify(root, times(2)).join("postAddress", JoinType.LEFT);
		verify(entityManager, times(2)).createQuery(criteriaQuery);
		verify(root, times(12)).get(anyString());
		verify(criteriaBuilder, times(2)).equal(any(Path.class), any(Boolean.class));
		verify(postAddressJoin, times(3)).get(anyString());
		verify(path, times(5)).in(ArgumentMatchers.<Object>any());
		verify(criteriaBuilder, times(2)).lessThanOrEqualTo(any(), any(LocalDateTime.class));
		verify(criteriaBuilder, times(2)).greaterThanOrEqualTo(any(), any(LocalDateTime.class));
		verify(criteriaQuery).orderBy(any(List.class));
		verify(criteriaBuilder, times(5)).sum(any(Path.class));
		verify(criteriaBuilder).asc(expression);

		verify(criteriaBuilder).createQuery(Long.class);
		verify(criteriaBuilder).count(any(Path.class));
		verify(countCriteriaQuery).from(OrderEntity.class);
		verify(entityManager).createQuery(countCriteriaQuery);
		verify(typedQuery, times(2)).getResultList();
		verify(countTypedQuery).setMaxResults(1);
		verify(countTypedQuery).getSingleResult();
	}
}
