package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders_api_rest.generated.model.ProductDetailsResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.academy.orders.apirest.ModelUtils.*;
import static com.academy.orders.apirest.TestConstants.PRODUCT_DESCRIPTION;
import static com.academy.orders.apirest.TestConstants.PRODUCT_NAME;
import static com.academy.orders.apirest.TestConstants.TAG_NAME;

class ProductDetailsResponseDTOMapperTest {
    private ProductDetailsResponseDTOMapper productDetailsResponseDTOMapper;

    @BeforeEach
    void setUp() {
        productDetailsResponseDTOMapper = Mappers.getMapper(ProductDetailsResponseDTOMapper.class);
    }

    @Test
    void toDtoWithValidProductTest() {
        var product = getProduct();
        var dto = productDetailsResponseDTOMapper.toDTO(product);

        Assertions.assertEquals(PRODUCT_NAME, dto.getName());
        Assertions.assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
        Assertions.assertEquals(product.getImage(), dto.getImage());
        Assertions.assertEquals(1, dto.getTags().size());
        Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
        Assertions.assertEquals(product.getQuantity(), dto.getQuantity());
        Assertions.assertEquals(product.getPrice(), dto.getPrice());
        Assertions.assertNull(dto.getDiscount());
    }

    @Test
    void toDtoWithDiscountTest() {
        var product = getProductWithAppliedDiscount();
        var dto = productDetailsResponseDTOMapper.toDTO(product);

        Assertions.assertEquals(PRODUCT_NAME, dto.getName());
        Assertions.assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
        Assertions.assertEquals(product.getImage(), dto.getImage());
        Assertions.assertEquals(1, dto.getTags().size());
        Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
        Assertions.assertEquals(product.getQuantity(), dto.getQuantity());
        Assertions.assertEquals(product.getPrice(), dto.getPrice());
        Assertions.assertEquals(product.getPriceWithDiscount(), dto.getPriceWithDiscount());
        Assertions.assertEquals(product.getDiscount().getAmount(), dto.getDiscount());
    }

    @Test
    void toDtoWithEmptyTranslationsTest() {
        Product product = getProductWithEmptyTranslations();
        ProductDetailsResponseDTO dto = productDetailsResponseDTOMapper.toDTO(product);

        Assertions.assertNull(dto.getName());
        Assertions.assertNull(dto.getDescription());
        Assertions.assertEquals(product.getImage(), dto.getImage());
        Assertions.assertEquals(1, dto.getTags().size());
        Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
        Assertions.assertEquals(product.getQuantity(), dto.getQuantity());
        Assertions.assertEquals(product.getPrice(), dto.getPrice());
        Assertions.assertNull(dto.getDiscount());
        Assertions.assertNull(dto.getPriceWithDiscount());
    }

    @Test
    void toDtoWithEmptyTagsTest() {
        Product product = getProductWithEmptyTags();
        ProductDetailsResponseDTO dto = productDetailsResponseDTOMapper.toDTO(product);

        Assertions.assertEquals(PRODUCT_NAME, dto.getName());
        Assertions.assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
        Assertions.assertEquals(product.getImage(), dto.getImage());
        Assertions.assertEquals(0, dto.getTags().size());
        Assertions.assertEquals(product.getQuantity(), dto.getQuantity());
        Assertions.assertEquals(product.getPrice(), dto.getPrice());
        Assertions.assertNull(dto.getDiscount());
        Assertions.assertNull(dto.getPriceWithDiscount());
    }
}
