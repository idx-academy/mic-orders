CREATE TABLE IF NOT EXISTS "products_translations" (
    product_id UUID,
    language_id BIGINT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (language_id) REFERENCES languages(id),
    PRIMARY KEY (product_id, language_id)
);
