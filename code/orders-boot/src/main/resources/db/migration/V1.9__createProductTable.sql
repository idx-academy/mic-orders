CREATE TABLE IF NOT EXISTS "orders"
(
    id             UUID CONSTRAINT PK_orders PRIMARY KEY,
    is_paid        BOOLEAN NOT NULL,
    order_status   VARCHAR(100) NOT NULL,
    created_at     TIMESTAMP NOT NULL,
    edited_at      TIMESTAMP NOT NULL,
    first_name     VARCHAR(100) NOT NULL,
    last_name      VARCHAR(100) NOT NULL,
    email          VARCHAR(100) NOT NULL,
    account_id     BIGINT  NOT NULL,
    CONSTRAINT FK_orders_accounts_id FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE INDEX IX_orders_accounts_id ON orders(account_id);