package com.academy.orders.infrastructure;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemId;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import com.academy.orders.infrastructure.order.entity.OrderItemEntity;
import com.academy.orders.infrastructure.order.entity.OrderReceiverVO;
import com.academy.orders.infrastructure.order.entity.PostAddressEntity;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ModelUtils {
	private static final LocalDateTime DATE_TIME = LocalDateTime.of(1, 1, 1, 1, 1);

	private ModelUtils() {
	}

	public static AccountEntity getAccountEntity() {
		return AccountEntity.builder().id(1L).password("$2a$12$5ZEfkhNQUREmioQ54TaFaOEM7h/QBgASIeqZceFGKPT80aTfYdvV.")
				.email("mock@mail.com").firstName("MockFirst").lastName("MockLast").role(Role.ROLE_ADMIN)
				.status(UserStatus.ACTIVE).createdAt(DATE_TIME).build();
	}

	public static Account getAccount() {
		return Account.builder().id(1L).password("$2a$12$5ZEfkhNQUREmioQ54TaFaOEM7h/QBgASIeqZceFGKPT80aTfYdvV.")
				.email("mock@mail.com").firstName("MockFirst").lastName("MockLast").role(Role.ROLE_ADMIN)
				.status(UserStatus.ACTIVE).createdAt(DATE_TIME).build();
	}

	public static CreateAccountDTO getCreateAccountDTO() {
		return CreateAccountDTO.builder().password("$2a$12$5ZEfkhNQUREmioQ54TaFaOEM7h/QBgASIeqZceFGKPT80aTfYdvV.")
				.email("mock@mail.com").firstName("MockFirst").lastName("MockLast").build();
	}

	public static ProductEntity getProductEntity() {
		return ProductEntity.builder().id(UUID.fromString("c39314ce-b659-4776-86b9-8201b05bb339"))
				.status(ProductStatus.AVAILABLE).image("http://localhost:8080/image-1").createdAt(DATE_TIME)
				.quantity(100).price(BigDecimal.valueOf(100.00)).build();
	}

	public static Product getProduct() {
		return Product.builder().id(UUID.fromString("c39314ce-b659-4776-86b9-8201b05bb339"))
				.status(ProductStatus.AVAILABLE).image("http://localhost:8080/image-1").createdAt(DATE_TIME)
				.quantity(100).price(BigDecimal.valueOf(100.00)).build();
	}

	public static Pageable getPageable() {
		return Pageable.builder().page(0).size(8).sort(List.of("test")).build();
	}

	public static OrderEntity getOrderEntity() {
		return OrderEntity.builder().id(UUID.fromString("4602edda-6e9f-4a35-a472-2f6eac06e203")).createdAt(DATE_TIME)
				.editedAt(DATE_TIME).isPaid(false).orderStatus(OrderStatus.IN_PROGRESS).receiver(getOrderReceiverVO())
				.build();
	}

	public static OrderReceiverVO getOrderReceiverVO() {
		return OrderReceiverVO.builder().email("mock@mail.com").firstName("MockFirst").lastName("MockLast").build();
	}

	public static PostAddressEntity getPostAddressEntity() {
		return PostAddressEntity.builder().id(UUID.fromString("4602edda-6e9f-4a35-a472-2f6eac06e203"))
				.city("Mocked city").department("Mocked department").deliveryMethod(DeliveryMethod.NOVA).build();
	}

	public static OrderItemEntity getOrderItemEntity() {
		return OrderItemEntity.builder().price(BigDecimal.valueOf(100.00)).quantity(1).build();
	}

	public static Order getOrder() {
		return Order.builder().id(UUID.fromString("4602edda-6e9f-4a35-a472-2f6eac06e203")).createdAt(DATE_TIME)
				.isPaid(false).orderStatus(OrderStatus.IN_PROGRESS).receiver(getOrderReceiver()).build();
	}

	public static OrderReceiver getOrderReceiver() {
		return OrderReceiver.builder().email("mock@mail.com").firstName("MockFirst").lastName("MockLast").build();
	}

	public static CartItemEntity getCartItemEntity() {
		var productEntity = getProductEntity();
		var accountEntity = getAccountEntity();
		return CartItemEntity.builder().cartItemId(new CartItemId(productEntity.getId(), accountEntity.getId()))
				.account(accountEntity).product(productEntity).quantity(1).build();
	}

	public static CartItem getCartItem() {
		return CartItem.builder().product(getProduct()).quantity(1).build();
	}
}