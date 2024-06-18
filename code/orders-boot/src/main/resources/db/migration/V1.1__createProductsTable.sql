CREATE TABLE IF NOT EXISTS "products"
(
    id                  VARCHAR(255) CONSTRAINT PK_products PRIMARY KEY,
    status              VARCHAR(20),
    image_link          VARCHAR(255),
    created_at          TIMESTAMP,
    quantity            INT,
    price               DECIMAL(10,2)
);