CREATE TABLE IF NOT EXISTS "products_tags"
(
    product_id      UUID NOT NULL,
    tag_id          BIGINT NOT NULL,
    CONSTRAINT      PK_products_tags PRIMARY KEY (product_id, tag_id),
    CONSTRAINT      FK_products_tags_product_id FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT      FK_products_tags_tag_id FOREIGN KEY (tag_id) REFERENCES tags(id)
);

INSERT INTO "products_tags" (product_id, tag_id) VALUES ('84b7e490-0dcf-44c3-beb6-7496dc6ef3b0', 1);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('04f7a6b4-e55e-4f68-9f68-9636e1b2e256', 1);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('93063b3f-7c61-47ee-8612-4bfae9f3ff0f', 2);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('ae459db4-9b15-4d6b-9e63-2d4d5f7cb0b0', 2);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('52d4f45c-70a8-4f36-99d0-d645c5f704b0', 3);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('f3786b85-0d0e-4bff-92f8-6f9d0b3dc6f5', 3);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('0a2db6de-d14b-448e-bb74-993fa64f3ae7', 1);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('c2567b36-3ad4-4a79-978f-2f1dcd86e2b3', 3);
