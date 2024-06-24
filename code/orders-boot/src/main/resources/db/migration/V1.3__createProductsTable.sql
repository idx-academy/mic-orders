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
('84b7e490-0dcf-44c3-beb6-7496dc6ef3b0', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/phone_2-tTDYhyoyqsEkwPzySFdXflYCe7TkUb.jpg', NOW(), 10, 500.00),
('04f7a6b4-e55e-4f68-9f68-9636e1b2e256', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/phone_1-QodrkqNjm6MWrKqg9ixBBMMfFU40X7.jpg', NOW(), 10, 999.00),
('93063b3f-7c61-47ee-8612-4bfae9f3ff0f', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/tablet_1-PpRl76SFgEv3Zig14ygkiiabH9f8qS.png', NOW(), 10, 799.00),
('ae459db4-9b15-4d6b-9e63-2d4d5f7cb0b0', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/tablet_2-ayF4QQ9ilJtRKlpBLCvwlJkBYddhPO.png', NOW(), 10, 999.00),
('52d4f45c-70a8-4f36-99d0-d645c5f704b0', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/computer_1-J0a7bI2jB5NozuSaXnzyMtxHyijWoD.jpg', NOW(), 10, 1999.00),
('f3786b85-0d0e-4bff-92f8-6f9d0b3dc6f5', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/computer_2-KjjXijfL9U0rd3P4Jfk06KwUHkSRRl.webp', NOW(), 10, 1399.00),
('0a2db6de-d14b-448e-bb74-993fa64f3ae7', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/phone_1-QodrkqNjm6MWrKqg9ixBBMMfFU40X7.jpg', NOW(), 10, 899.00),
('c2567b36-3ad4-4a79-978f-2f1dcd86e2b3', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/computer_1-J0a7bI2jB5NozuSaXnzyMtxHyijWoD.jpg', NOW(), 10, 1799.00);
