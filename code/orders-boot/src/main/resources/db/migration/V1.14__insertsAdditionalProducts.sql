INSERT INTO "products" (id, status, image_link, created_at, quantity, price) VALUES
('84b7e491-0dcf-44c3-beb6-7496dc6ef3b1', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/phone_2-tTDYhyoyqsEkwPzySFdXflYCe7TkUb.jpg', NOW(), 10, 559.00),
('04f7a6b5-e55e-4f68-9f68-9636e1b2e257', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/phone_1-QodrkqNjm6MWrKqg9ixBBMMfFU40X7.jpg', NOW(), 10, 1199.00),
('93063b3f-7c61-47ee-8612-4bfae9f3ff0b', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/tablet_1-PpRl76SFgEv3Zig14ygkiiabH9f8qS.png', NOW(), 10, 899.00),
('ae459db4-9b15-4d6b-9e63-2d4d5f7cb0b1', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/tablet_2-ayF4QQ9ilJtRKlpBLCvwlJkBYddhPO.png', NOW(), 10, 1099.00),
('52d4f45c-70a8-4f36-99d0-d645c5f704b2', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/computer_1-J0a7bI2jB5NozuSaXnzyMtxHyijWoD.jpg', NOW(), 10, 1099.00),
('f3786b85-0d0e-4bff-92f8-6f9d0b3dc6f6', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/computer_2-KjjXijfL9U0rd3P4Jfk06KwUHkSRRl.webp', NOW(), 10, 1499.00),
('0a2db6de-d14b-448e-bb74-993fa64f3ae8', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/phone_1-QodrkqNjm6MWrKqg9ixBBMMfFU40X7.jpg', NOW(), 10, 799.00),
('c2567b36-3ad4-4a79-978f-2f1dcd86e2b5', 'AVAILABLE', 'https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/computer_1-J0a7bI2jB5NozuSaXnzyMtxHyijWoD.jpg', NOW(), 10, 1899.00);

INSERT INTO "products_translations" (product_id, language_id, name, description) VALUES
('84b7e491-0dcf-44c3-beb6-7496dc6ef3b1', 1, 'Mobile Phone Samsung Galaxy A55 5G 8/256GB Lilac', 'Screen: 6.6\" Super AMOLED, 2340x1080 / Samsung Exynos 1480 (4 x 2.75 GHz + 4 x 2.0 GHz) / Main Triple Camera: 50 MP + 12 MP + 5 MP, Front Camera: 32 MP / RAM 8 GB / 256 GB internal storage + microSD (up to 1 TB) / 3G / LTE / 5G / GPS / A-GPS / GLONASS / BDS / Dual SIM support (Nano-SIM) / Android 14 / 5000 mAh'),
('04f7a6b5-e55e-4f68-9f68-9636e1b2e257', 1, 'Mobile Phone Apple iPhone 14 Pro 128GB Space Gray', 'Screen: 6.1\" Super Retina XDR, 2532x1170 / A16 Bionic chip / Main Triple Camera: 48 MP + 12 MP + 12 MP, Front Camera: 12 MP / RAM 6 GB / 128 GB internal storage / 3G / LTE / 5G / GPS / GLONASS / Dual SIM support (Nano-SIM and eSIM) / iOS 16 / 3200 mAh'),
('93063b3f-7c61-47ee-8612-4bfae9f3ff0b', 1, 'Tablet Apple iPad Pro 11\" (2022) 128GB Wi-Fi Space Gray', 'Screen: 11\" Liquid Retina, 2388x1668 / M2 chip / Main Camera: 12 MP, Front Camera: 12 MP / RAM 8 GB / 128 GB internal storage / Wi-Fi / Bluetooth 5.3 / iPadOS 16 / 7538 mAh'),
('ae459db4-9b15-4d6b-9e63-2d4d5f7cb0b1', 1, 'Tablet Samsung Galaxy Tab S8+ 256GB Wi-Fi Silver', 'Screen: 12.4\" Super AMOLED, 2800x1752 / Snapdragon 8 Gen 1 / Main Camera: 13 MP, Front Camera: 12 MP / RAM 8 GB / 256 GB internal storage + microSD (up to 1 TB) / Wi-Fi / Bluetooth 5.2 / Android 12 / 10090 mAh'),
('52d4f45c-70a8-4f36-99d0-d645c5f704b2', 1, 'Laptop Apple MacBook Pro 14\" (2023) M2 Pro 512GB Space Gray', 'Screen: 14.2\" Liquid Retina XDR, 3024x1964 / M2 Pro chip / RAM 16 GB / 512 GB SSD / Wi-Fi 6E / Bluetooth 5.3 / macOS Ventura / 70 Wh battery'),
('f3786b85-0d0e-4bff-92f8-6f9d0b3dc6f6', 1, 'Laptop Dell XPS 13 9310 512GB Platinum Silver', 'Screen: 13.4\" FHD+, 1920x1200 / Intel Core i7-1185G7 / RAM 16 GB / 512 GB SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.1 / Windows 10 Home / 52 Wh battery'),
('0a2db6de-d14b-448e-bb74-993fa64f3ae8', 1, 'Mobile Phone Apple iPhone 13 128GB Red', 'Screen: 6.1\" Super Retina XDR, 2532x1170 / A15 Bionic chip / Main Dual Camera: 12 MP + 12 MP, Front Camera: 12 MP / RAM 4 GB / 128 GB internal storage / 3G / LTE / 5G / GPS / GLONASS / Dual SIM support (Nano-SIM and eSIM) / iOS 15 / 3240 mAh'),
('c2567b36-3ad4-4a79-978f-2f1dcd86e2b5', 1, 'Laptop HP Spectre x360 14 1TB Nightfall Black', 'Screen: 13.5\" OLED, 3000x2000 / Intel Core i7-1165G7 / RAM 16 GB / 1 TB SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.0 / Windows 10 Home / 66 Wh battery');

INSERT INTO "products_translations" (product_id, language_id, name, description) VALUES
('84b7e491-0dcf-44c3-beb6-7496dc6ef3b1', 2, 'Мобільний телефон Samsung Galaxy A55 5G 8/256GB Ліловий', 'Екран: 6.6 / Super AMOLED, 2340x1080 / Samsung Exynos 1480 (4 x 2.75 ГГц + 4 x 2.0 ГГц) / Основна потрійна камера: 50 МП + 12 МП + 5 МП, Фронтальна камера: 32 МП / ОЗП 8 ГБ / 256 ГБ вбудованої пам`яті + microSD (до 1 ТБ) / 3G / LTE / 5G / GPS / A-GPS / GLONASS / BDS / Підтримка Dual SIM (Nano-SIM) / Android 14 / 5000 мАг'),
('04f7a6b5-e55e-4f68-9f68-9636e1b2e257', 2, 'Мобільний телефон Apple iPhone 14 Pro 128GB Сірий космос', 'Екран: 6.1 / Super Retina XDR, 2532x1170 / Чіп A16 Bionic / Основна потрійна камера: 48 МП + 12 МП + 12 МП, Фронтальна камера: 12 МП / ОЗП 6 ГБ / 128 ГБ вбудованої пам`яті / 3G / LTE / 5G / GPS / GLONASS / Підтримка Dual SIM (Nano-SIM та eSIM) / iOS 16 / 3200 мАг'),
('93063b3f-7c61-47ee-8612-4bfae9f3ff0b', 2, 'Планшет Apple iPad Pro 11 (2022) 128GB Wi-Fi Сірий космос', 'Екран: 11 / Liquid Retina, 2388x1668 / Чіп M2 / Основна камера: 12 МП, Фронтальна камера: 12 МП / ОЗП 8 ГБ / 128 ГБ вбудованої пам`яті / Wi-Fi / Bluetooth 5.3 / iPadOS 16 / 7538 мАг'),
('ae459db4-9b15-4d6b-9e63-2d4d5f7cb0b1', 2, 'Планшет Samsung Galaxy Tab S8+ 256GB Wi-Fi Срібний', 'Екран: 12.4 / Super AMOLED, 2800x1752 / Snapdragon 8 Gen 1 / Основна камера: 13 МП, Фронтальна камера: 12 МП / ОЗП 8 ГБ / 256 ГБ вбудованої пам`яті + microSD (до 1 ТБ) / Wi-Fi / Bluetooth 5.2 / Android 12 / 10090 мАг'),
('52d4f45c-70a8-4f36-99d0-d645c5f704b2', 2, 'Ноутбук Apple MacBook Pro 14 (2023) M2 Pro 512GB Сірий космос', 'Екран: 14.2 / Liquid Retina XDR, 3024x1964 / Чіп M2 Pro / ОЗП 16 ГБ / 512 ГБ SSD / Wi-Fi 6E / Bluetooth 5.3 / macOS Ventura / Акумулятор 70 мАг'),
('f3786b85-0d0e-4bff-92f8-6f9d0b3dc6f6', 2, 'Ноутбук Dell XPS 13 9310 512GB Платиновий срібний', 'Екран: 13.4 / FHD+, 1920x1200 / Intel Core i7-1185G7 / ОЗП 16 ГБ / 512 ГБ SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.1 / Windows 10 Home / Акумулятор 52 мАг'),
('0a2db6de-d14b-448e-bb74-993fa64f3ae8', 2, 'Мобільний телефон Apple iPhone 13 128GB Червоний', 'Екран: 6.1 / Super Retina XDR, 2532x1170 / Чіп A15 Bionic / Основна подвійна камера: 12 МП + 12 МП, Фронтальна камера: 12 МП / ОЗП 4 ГБ / 128 ГБ вбудованої пам`яті / 3G / LTE / 5G / GPS / GLONASS / Підтримка Dual SIM (Nano-SIM та eSIM) / iOS 15 / 3240 мАг'),
('c2567b36-3ad4-4a79-978f-2f1dcd86e2b5', 2, 'Ноутбук HP Spectre x360 14 1TB Чорний нічний', 'Екран: 13.5 / OLED, 3000x2000 / Intel Core i7-1165G7 / ОЗП 16 ГБ / 1 ТБ SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.0 / Windows 10 Home / Акумулятор 66 мАг');

INSERT INTO "products_tags" (product_id, tag_id) VALUES ('84b7e491-0dcf-44c3-beb6-7496dc6ef3b1', 1);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('04f7a6b5-e55e-4f68-9f68-9636e1b2e257', 1);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('93063b3f-7c61-47ee-8612-4bfae9f3ff0b', 2);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('ae459db4-9b15-4d6b-9e63-2d4d5f7cb0b1', 2);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('52d4f45c-70a8-4f36-99d0-d645c5f704b2', 3);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('f3786b85-0d0e-4bff-92f8-6f9d0b3dc6f6', 3);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('0a2db6de-d14b-448e-bb74-993fa64f3ae8', 1);
INSERT INTO "products_tags" (product_id, tag_id) VALUES ('c2567b36-3ad4-4a79-978f-2f1dcd86e2b5', 3);



