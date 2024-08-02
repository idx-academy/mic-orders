package com.academy.orders.infrastructure;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemId;
import com.academy.orders.infrastructure.language.entity.LanguageEntity;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import com.academy.orders.infrastructure.order.entity.OrderItemEntity;
import com.academy.orders.infrastructure.order.entity.OrderReceiverVO;
import com.academy.orders.infrastructure.order.entity.PostAddressEntity;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationId;
import com.academy.orders.infrastructure.tag.entity.TagEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.PageImpl;

import static com.academy.orders.domain.order.entity.enumerated.DeliveryMethod.NOVA;
import static com.academy.orders.infrastructure.TestConstants.LANGUAGE_EN;
import static com.academy.orders.infrastructure.TestConstants.TEST_ID;
import static com.academy.orders.infrastructure.TestConstants.TEST_UUID;

public class ModelUtils {
	private static final LocalDateTime DATE_TIME = LocalDateTime.of(1, 1, 1, 1, 1);
	private static final String TEST_IMAGE_NAME = "image-1";
	public static final String TEST_IMAGE_LINK = "http://localhost:8080/image-1";

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
				.status(ProductStatus.VISIBLE).image(TEST_IMAGE_NAME).createdAt(DATE_TIME).quantity(100)
				.price(BigDecimal.valueOf(100.00)).build();
	}

	public static ProductEntity getProductEntityWithTranslation() {
		return ProductEntity.builder().id(UUID.fromString("c39314ce-b659-4776-86b9-8201b05bb339"))
				.status(ProductStatus.VISIBLE).image(TEST_IMAGE_NAME).createdAt(DATE_TIME).quantity(100)
				.price(BigDecimal.valueOf(100.00)).productTranslations(Set.of(getProductTranslationEntity())).build();
	}

	public static Product getProduct() {
		return Product.builder().id(UUID.fromString("c39314ce-b659-4776-86b9-8201b05bb339"))
				.status(ProductStatus.VISIBLE).image(TEST_IMAGE_NAME).createdAt(DATE_TIME).quantity(100)
				.price(BigDecimal.valueOf(100.00)).build();
	}

	public static OrderEntity getOrderEntity() {
		return OrderEntity.builder().id(UUID.fromString(String.valueOf(TEST_UUID))).createdAt(DATE_TIME)
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

	public static <T> Page<T> getPage(List<T> content, long totalElements, int totalPages, int number, int size) {
		return Page.<T>builder().totalElements(totalElements).totalPages(totalPages).first(number == 0)
				.last(number == totalPages - 1).number(number).numberOfElements(content.size()).size(size)
				.empty(content.isEmpty()).content(content).build();
	}

	public static Pageable getPageable() {
		return getPageable(0, 8, List.of("id"));
	}

	public static Pageable getPageable(Integer page, Integer size, List<String> sort) {
		return Pageable.builder().page(page).size(size).sort(sort).build();
	}

	@SafeVarargs
	public static <T> Page<T> getPageOf(T... elements) {
		return Page.<T>builder().content(List.of(elements)).empty(false).first(true).last(false).number(1)
				.numberOfElements(10).totalPages(10).totalElements(100L).size(1).build();
	}

	public static Order getOrder() {
		return Order.builder().id(UUID.fromString("4602edda-6e9f-4a35-a472-2f6eac06e203"))
				.createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).isPaid(false).orderStatus(OrderStatus.IN_PROGRESS)
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(getOrderReceiver()).orderItems(List.of(getOrderItem())).build();
	}

	public static OrderItem getOrderItem() {
		return OrderItem.builder().product(getProduct()).quantity(3).price(BigDecimal.valueOf(200)).build();
	}

	public static OrdersFilterParametersDto getOrdersFilterParametersDto() {
		return OrdersFilterParametersDto.builder().deliveryMethods(List.of(DeliveryMethod.NOVA))
				.statuses(List.of(OrderStatus.IN_PROGRESS)).isPaid(false).createdBefore(DATE_TIME)
				.createdAfter(DATE_TIME).totalMore(BigDecimal.ZERO).totalLess(BigDecimal.TEN).build();
	}

	public static ProductManagementFilterDto getManagementFilterDto() {
		return ProductManagementFilterDto.builder().status(ProductStatus.VISIBLE).createdBefore(DATE_TIME)
				.createdAfter(DATE_TIME).priceMore(BigDecimal.ZERO).priceLess(BigDecimal.TEN).build();
	}

	@SafeVarargs
	public static <T> PageImpl<T> getPageImplOf(T... elements) {
		return new PageImpl<>(List.of(elements));
	}

	public static ProductTranslationEntity getProductTranslationEntity() {
		return ProductTranslationEntity.builder().productTranslationId(new ProductTranslationId(TEST_UUID, 1L))
				.name("Name").description("Description").product(getProductEntity()).language(getLanguageEntity())
				.build();
	}

	public static ProductTranslationManagement getProductTranslationManagement() {
		return ProductTranslationManagement.builder().productId(TEST_UUID).languageId(1L).name("Name")
				.description("Description").language(new Language(1L, LANGUAGE_EN)).build();
	}

	public static ProductManagement getProductManagement() {
		return ProductManagement.builder().id(TEST_UUID).status(ProductStatus.VISIBLE).createdAt(LocalDateTime.now())
				.quantity(10).price(BigDecimal.valueOf(100.00)).tags(Set.of(new Tag(1L, "tag")))
				.productTranslationManagement(Set.of(getProductTranslationManagement())).build();
	}

	public static TagEntity getTagEntity() {
		return TagEntity.builder().id(TEST_ID).name("category:mobile").build();
	}

	public static Tag getTag() {
		return Tag.builder().id(TEST_ID).name("category:mobile").build();
	}

	public static LanguageEntity getLanguageEntity() {
		return LanguageEntity.builder().id(1L).code("en").build();
	}

	public static Language getLanguage() {
		return Language.builder().id(1L).code("en").build();
	}
}
