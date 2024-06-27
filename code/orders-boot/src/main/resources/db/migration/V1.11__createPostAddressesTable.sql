CREATE TABLE IF NOT EXISTS "post_addresses"
(
    id                  UUID NOT NULL ,
    delivery_method     VARCHAR(100) NOT NULL,
    city                VARCHAR(255) NOT NULL,
    department          VARCHAR(255) NOT NULL,
    CONSTRAINT PK_post_addresses PRIMARY KEY (id),
    CONSTRAINT FK_post_addresses_id FOREIGN KEY (id) REFERENCES orders(id)
);