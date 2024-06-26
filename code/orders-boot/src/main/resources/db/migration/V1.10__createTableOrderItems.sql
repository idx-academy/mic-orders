CREATE TABLE IF NOT EXISTS "order_items"
(
    order_id       UUID NOT NULL ,
    product_id     UUID NOT NULL ,
    is_paid        BOOLEAN NOT NULL,
    price          DECIMAL(10,2) NOT NULL,
    quantity       INT NOT NULL,
    CONSTRAINT PK_order_items PRIMARY KEY (order_id,product_id),
    CONSTRAINT FK_order_items_order_id FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT FK_order_items_product_id FOREIGN KEY (product_id) REFERENCES products(id)
);