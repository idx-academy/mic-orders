CREATE TABLE IF NOT EXISTS "products_tags"
(
    product_id      VARCHAR(255) NOT NULL,
    tag_id          BIGINT NOT NULL,
    CONSTRAINT      PK_products_tags PRIMARY KEY (product_id, tag_id),
    CONSTRAINT      FK_products_tags_product_id FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT      FK_products_tags_tag_id FOREIGN KEY (tag_id) REFERENCES tags(id)
);