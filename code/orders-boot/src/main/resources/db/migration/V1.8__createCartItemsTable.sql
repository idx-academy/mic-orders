CREATE TABLE IF NOT EXISTS "cart_items" (
    product_id  UUID,
    user_id     BIGINT,
    quantity    INT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES accounts(id),
    PRIMARY KEY (user_id, product_id)
);

