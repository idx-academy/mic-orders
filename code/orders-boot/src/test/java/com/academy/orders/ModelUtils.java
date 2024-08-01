package com.academy.orders;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.academy.orders.domain.order.entity.enumerated.DeliveryMethod.NOVA;

public class ModelUtils {

	public static final LocalDateTime DATE_TIME = LocalDateTime.of(1, 1, 1, 1, 1);

	public static Account getAccount() {
		return Account.builder().id(1L).email("user@mail.com").firstName("first").lastName("last")
				.password("$2a$12$j6tAmpJpMhU6ATtgRIS0puHsPVxs2upwoBUbTtakSt9tlZ6uZ04IC").role(Role.ROLE_ADMIN)
				.status(UserStatus.ACTIVE).createdAt(DATE_TIME).build();
	}

	public static Order getOrderWithoutId() {
		return Order.builder().isPaid(false).orderStatus(OrderStatus.IN_PROGRESS)
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(getOrderReceiver()).orderItems(List.of(getOrderItem())).build();
	}

	public static OrderItem getOrderItem() {
		return OrderItem.builder().product(getProduct()).quantity(3).price(BigDecimal.valueOf(200)).build();
	}

	public static Product getProduct() {
		return Product.builder().id(UUID.fromString("93063b3f-7c61-47ee-8612-4bfae9f3ff0f"))
				.status(ProductStatus.VISIBLE).image("some_image").createdAt(DATE_TIME).quantity(100)
				.price(BigDecimal.valueOf(100.00)).build();
	}

	public static OrderReceiver getOrderReceiver() {
		return OrderReceiver.builder().email("mock@mail.com").firstName("MockFirst").lastName("MockLast").build();
	}
}
