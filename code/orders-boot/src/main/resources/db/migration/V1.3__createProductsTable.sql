CREATE TABLE IF NOT EXISTS "products"
(
    id                  UUID CONSTRAINT PK_products PRIMARY KEY,
    status              VARCHAR(20),
    image_link          VARCHAR(255),
    created_at          TIMESTAMP,
    quantity            INT,
    price               DECIMAL(10,2)
);

INSERT INTO "products" (id, status, image_link, created_at, quantity, price) VALUES
('123e4567-e89b-12d3-a456-426614174000', 'AVAILABLE', 'https://example.com/image.jpg', NOW(), 10, 999.99),
('123e4567-e89b-12d3-a457-426614174000', 'AVAILABLE', 'https://example.com/image.jpg', NOW(), 10, 888.88);
