CREATE TABLE IF NOT EXISTS "products_tags"
(
    product_id      UUID NOT NULL,
    tag_id          BIGINT NOT NULL,
    CONSTRAINT      PK_products_tags PRIMARY KEY (product_id, tag_id),
    CONSTRAINT      FK_products_tags_product_id FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT      FK_products_tags_tag_id FOREIGN KEY (tag_id) REFERENCES tags(id)
);

INSERT INTO "products_tags" (product_id, tag_id) VALUES ('123e4567-e89b-12d3-a456-426614174000', 1);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('123e4567-e89b-12d3-a456-426614174000', 2);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('123e4567-e89b-12d3-a456-426614174000', 3);
-- For second product
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('123e4567-e89b-12d3-a457-426614174000', 1);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('123e4567-e89b-12d3-a457-426614174000', 2);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('123e4567-e89b-12d3-a457-426614174000', 3);
