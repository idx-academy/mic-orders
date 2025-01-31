INSERT INTO "discounts" (id, amount, start_date, end_date) VALUES
('2deb0bfa-6303-4ffa-b666-c5bec805eb53', 10, '2025-01-01 00:00:00+00', '2025-01-31 23:59:59+00'),
('2191fbb6-8c66-40c1-92f5-424cb2ad374b', 15, '2025-02-01 00:00:00+00', '2025-02-28 23:59:59+00'),
('d8a0573e-b4db-4322-acb8-bbb4f57ce9fe', 20, '2025-03-01 00:00:00+00', '2025-03-31 23:59:59+00'),
('faf4aea8-ea7d-41a4-b8f3-3bfceacd7e5e', 25, '2025-04-01 00:00:00+00', '2025-04-30 23:59:59+00'),
('505523c1-5801-4ea0-acc2-c51975361836', 30, '2025-05-01 00:00:00+00', '2025-05-31 23:59:59+00'),
('5d35f441-fc93-4ca2-8328-659653063dc0', 35, '2025-06-01 00:00:00+00', '2025-06-30 23:59:59+00'),
('6c136dcd-5c82-49c1-91e1-3e0c7bbd7fd8', 40, '2025-07-01 00:00:00+00', '2025-07-31 23:59:59+00'),
('22a8204b-0c33-40c3-b250-7074ad5c9e2e', 45, '2025-08-01 00:00:00+00', '2025-08-31 23:59:59+00'),
('efa322d3-cca1-4cb7-b070-e0336fb08df9', 50, '2025-09-01 00:00:00+00', '2025-09-30 23:59:59+00'),
('00a573cc-8ac0-4bbe-a28e-d8a98343a997', 25, '2025-04-01 00:00:00+00', '2025-04-30 23:59:59+00'),
('6b3ecc06-8ea6-47fa-b4c8-8590552bfbc2', 30, '2025-05-01 00:00:00+00', '2025-05-31 23:59:59+00'),
('99e1d8a5-c4c3-4573-9b3a-3bd7ff58f6d6', 35, '2025-06-01 00:00:00+00', '2025-06-30 23:59:59+00'),
('f965eb6b-f238-46f1-a20b-42e91f005e48', 40, '2025-07-01 00:00:00+00', '2025-07-31 23:59:59+00'),
('98bb7688-3f11-4421-83b6-bc25b95b5b75', 45, '2025-08-01 00:00:00+00', '2025-08-31 23:59:59+00'),
('3a4fe8c4-ce9c-4340-aec7-0cb87b4f9613', 50, '2025-09-01 00:00:00+00', '2025-09-30 23:59:59+00');

INSERT INTO "products" (id, status, image_link, created_at, quantity, price, discount_id) VALUES
('b8be6872-b25e-4c1a-b845-df2f0150b4f2', 'VISIBLE', 'phone_2-tTDYhyoyqsEkwPzySFdXflYCe7TkUb.jpg', NOW(), 15, 500.00, '2deb0bfa-6303-4ffa-b666-c5bec805eb53'),
('33f6b0ad-0786-4882-9870-eccf088fc5c2', 'VISIBLE', 'phone_2-tTDYhyoyqsEkwPzySFdXflYCe7TkUb.jpg', NOW(), 15, 999.00, '2191fbb6-8c66-40c1-92f5-424cb2ad374b'),
('ee9c5b72-dfeb-43f0-8fc7-24961c572a0f', 'VISIBLE', 'phone_2-tTDYhyoyqsEkwPzySFdXflYCe7TkUb.jpg', NOW(), 15, 799.00, 'd8a0573e-b4db-4322-acb8-bbb4f57ce9fe'),
('9be62d75-ef7f-4b6a-bfa1-9b94a12c8c4f', 'VISIBLE', 'phone_2-tTDYhyoyqsEkwPzySFdXflYCe7TkUb.jpg', NOW(), 15, 1199.00, 'faf4aea8-ea7d-41a4-b8f3-3bfceacd7e5e'),
('f38d74c3-dce9-4609-940d-d0a2a5109d10', 'VISIBLE', 'phone_2-tTDYhyoyqsEkwPzySFdXflYCe7TkUb.jpg', NOW(), 15, 1999.00, '505523c1-5801-4ea0-acc2-c51975361836'),
('8f9edb19-372d-4521-9bb5-72b5b081285f', 'VISIBLE', 'tablet_1-PpRl76SFgEv3Zig14ygkiiabH9f8qS.png', NOW(), 15, 1399.00, '5d35f441-fc93-4ca2-8328-659653063dc0'),
('251ff570-bd59-49f4-83f3-bd0bfb0a93a2', 'VISIBLE', 'tablet_1-PpRl76SFgEv3Zig14ygkiiabH9f8qS.png', NOW(), 15, 799.00, '6c136dcd-5c82-49c1-91e1-3e0c7bbd7fd8'),
('35b755f4-5f31-4862-9930-5574e8fbc35f', 'VISIBLE', 'tablet_1-PpRl76SFgEv3Zig14ygkiiabH9f8qS.png', NOW(), 15, 1799.00, '22a8204b-0c33-40c3-b250-7074ad5c9e2e'),
('c8e5107c-f59a-4c2b-b1b5-f7cfdbe0a9a6', 'VISIBLE', 'tablet_1-PpRl76SFgEv3Zig14ygkiiabH9f8qS.png', NOW(), 15, 1299.00, 'efa322d3-cca1-4cb7-b070-e0336fb08df9'),
('e1cfa0e1-2a5d-4b43-9475-4caea0be4a95', 'VISIBLE', 'tablet_1-PpRl76SFgEv3Zig14ygkiiabH9f8qS.png', NOW(), 15, 1499.00, '00a573cc-8ac0-4bbe-a28e-d8a98343a997'),
('a9da953e-2be1-42d0-81e4-9a379263c276', 'VISIBLE', 'computer_1-J0a7bI2jB5NozuSaXnzyMtxHyijWoD.jpg', NOW(), 15, 699.00, '6b3ecc06-8ea6-47fa-b4c8-8590552bfbc2'),
('b2d7d8ff-4a60-4779-8302-c51b95e44006', 'VISIBLE', 'computer_1-J0a7bI2jB5NozuSaXnzyMtxHyijWoD.jpg', NOW(), 15, 999.00, '99e1d8a5-c4c3-4573-9b3a-3bd7ff58f6d6'),
('c865773b-222a-4d68-bfc7-5cba80c379be', 'VISIBLE', 'computer_1-J0a7bI2jB5NozuSaXnzyMtxHyijWoD.jpg', NOW(), 15, 599.00, 'f965eb6b-f238-46f1-a20b-42e91f005e48'),
('ce994ba5-974d-4c73-b68f-cfdafbc8d999', 'VISIBLE', 'computer_1-J0a7bI2jB5NozuSaXnzyMtxHyijWoD.jpg', NOW(), 15, 2499.00, '98bb7688-3f11-4421-83b6-bc25b95b5b75'),
('31ab09eb-76f2-4e7e-bde5-93d6283923b2', 'VISIBLE', 'computer_1-J0a7bI2jB5NozuSaXnzyMtxHyijWoD.jpg', NOW(), 15, 2499.00, '3a4fe8c4-ce9c-4340-aec7-0cb87b4f9613');

INSERT INTO "products_tags" (product_id, tag_id) VALUES
('b8be6872-b25e-4c1a-b845-df2f0150b4f2', 1),
('33f6b0ad-0786-4882-9870-eccf088fc5c2', 1),
('ee9c5b72-dfeb-43f0-8fc7-24961c572a0f', 1),
('9be62d75-ef7f-4b6a-bfa1-9b94a12c8c4f', 1),
('f38d74c3-dce9-4609-940d-d0a2a5109d10', 1),
('8f9edb19-372d-4521-9bb5-72b5b081285f', 2),
('251ff570-bd59-49f4-83f3-bd0bfb0a93a2', 2),
('35b755f4-5f31-4862-9930-5574e8fbc35f', 2),
('c8e5107c-f59a-4c2b-b1b5-f7cfdbe0a9a6', 2),
('e1cfa0e1-2a5d-4b43-9475-4caea0be4a95', 3),
('a9da953e-2be1-42d0-81e4-9a379263c276', 3),
('b2d7d8ff-4a60-4779-8302-c51b95e44006', 3),
('c865773b-222a-4d68-bfc7-5cba80c379be', 3),
('ce994ba5-974d-4c73-b68f-cfdafbc8d999', 3);

INSERT INTO "products_translations" (product_id, language_id, name, description) VALUES
('b8be6872-b25e-4c1a-b845-df2f0150b4f2', 1, 'Samsung Galaxy A55 5G 8/256GB Mobile Phone Lavender', 'Display: 6.6 / Super AMOLED, 2340x1080 / Samsung Exynos 1480 (4 x 2.75 GHz + 4 x 2.0 GHz) / Main triple camera: 50 MP + 12 MP + 5 MP, Front camera: 32 MP / RAM 8 GB / 256 GB internal memory + microSD (up to 1 TB) / 3G / LTE / 5G / GPS / A-GPS / GLONASS / BDS / Dual SIM support (Nano-SIM) / Android 14 / 5000 mAh'),
('33f6b0ad-0786-4882-9870-eccf088fc5c2', 1, 'Apple iPhone 14 Pro 128GB Space Gray Mobile Phone', 'Display: 6.1 / Super Retina XDR, 2532x1170 / A16 Bionic chip / Main triple camera: 48 MP + 12 MP + 12 MP, Front camera: 12 MP / RAM 6 GB / 128 GB internal memory / 3G / LTE / 5G / GPS / GLONASS / Dual SIM support (Nano-SIM and eSIM) / iOS 16 / 3200 mAh'),
('ee9c5b72-dfeb-43f0-8fc7-24961c572a0f', 1, 'Apple iPhone 13 128GB Red Mobile Phone', 'Display: 6.1 / Super Retina XDR, 2532x1170 / A15 Bionic chip / Main dual camera: 12 MP + 12 MP, Front camera: 12 MP / RAM 4 GB / 128 GB internal memory / 3G / LTE / 5G / GPS / GLONASS / Dual SIM support (Nano-SIM and eSIM) / iOS 15 / 3240 mAh'),
('9be62d75-ef7f-4b6a-bfa1-9b94a12c8c4f', 1, 'Samsung Galaxy S21 128GB Mobile Phone Black', 'Display: 6.2 / Dynamic AMOLED 2X, 2400x1080 / Exynos 2100 / Main camera: 12 MP + 64 MP + 12 MP, Front camera: 10 MP / RAM 8 GB / 128 GB internal memory / 3G / LTE / 5G / GPS / A-GPS / GLONASS / BDS / Dual SIM support (Nano-SIM) / Android 12 / 4000 mAh'),
('f38d74c3-dce9-4609-940d-d0a2a5109d10', 1, 'Apple iPhone 12 64GB White Mobile Phone', 'Display: 6.1 / Super Retina XDR, 2532x1170 / A14 Bionic chip / Main dual camera: 12 MP + 12 MP, Front camera: 12 MP / RAM 4 GB / 64 GB internal memory / 3G / LTE / 5G / GPS / GLONASS / Dual SIM support (Nano-SIM and eSIM) / iOS 14 / 2815 mAh');

INSERT INTO "products_translations" (product_id, language_id, name, description) VALUES
('8f9edb19-372d-4521-9bb5-72b5b081285f', 1, 'Apple iPad Pro 11 (2022) 128GB Wi-Fi Space Gray Tablet', 'Display: 11 / Liquid Retina, 2388x1668 / M2 chip / Main camera: 12 MP, Front camera: 12 MP / RAM 8 GB / 128 GB internal memory / Wi-Fi / Bluetooth 5.3 / iPadOS 16 / 7538 mAh'),
('251ff570-bd59-49f4-83f3-bd0bfb0a93a2', 1, 'Samsung Galaxy Tab S8+ 256GB Wi-Fi Silver Tablet', 'Display: 12.4 / Super AMOLED, 2800x1752 / Snapdragon 8 Gen 1 / Main camera: 13 MP, Front camera: 12 MP / RAM 8 GB / 256 GB internal memory + microSD (up to 1 TB) / Wi-Fi / Bluetooth 5.2 / Android 12 / 10090 mAh'),
('35b755f4-5f31-4862-9930-5574e8fbc35f', 1, 'Apple iPad Air 2022 64GB Wi-Fi Pink Tablet', 'Display: 10.9 / Liquid Retina, 2360x1640 / M1 chip / Main camera: 12 MP, Front camera: 12 MP / RAM 4 GB / 64 GB internal memory / Wi-Fi / Bluetooth 5.0 / iPadOS 15 / 7606 mAh'),
('c8e5107c-f59a-4c2b-b1b5-f7cfdbe0a9a6', 1, 'Lenovo Tab P11 Pro 128GB Wi-Fi Gray Tablet', 'Display: 11.5 / OLED, 2560x1600 / Snapdragon 730G / Main camera: 13 MP, Front camera: 8 MP / RAM 4 GB / 128 GB internal memory / Wi-Fi / Bluetooth 5.0 / Android 11 / 8600 mAh'),
('e1cfa0e1-2a5d-4b43-9475-4caea0be4a95', 1, 'Huawei MatePad Pro 10.8 256GB Wi-Fi Black Tablet', 'Display: 10.8 / IPS, 2560x1600 / Kirin 990 / Main camera: 13 MP, Front camera: 8 MP / RAM 6 GB / 256 GB internal memory / Wi-Fi / Bluetooth 5.1 / EMUI 11 / 7250 mAh');

INSERT INTO "products_translations" (product_id, language_id, name, description) VALUES
('a9da953e-2be1-42d0-81e4-9a379263c276', 1, 'HP Spectre x360 14 1TB Black Night Laptop', 'Display: 13.5 / OLED, 3000x2000 / Intel Core i7-1165G7 / RAM 16 GB / 1 TB SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.0 / Windows 10 Home / 66 Wh battery'),
('b2d7d8ff-4a60-4779-8302-c51b95e44006', 1, 'Apple MacBook Pro 16 (2023) M2 1TB Space Gray Laptop', 'Display: 16 / Liquid Retina XDR, 3456x2234 / M2 chip / RAM 16 GB / 1 TB SSD / Wi-Fi 6E / Bluetooth 5.3 / macOS Ventura / 100 Wh battery'),
('c865773b-222a-4d68-bfc7-5cba80c379be', 1, 'Lenovo ThinkPad X1 Carbon Gen 9 512GB Black Laptop', 'Display: 14 / FHD+, 1920x1200 / Intel Core i7-1165G7 / RAM 16 GB / 512 GB SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.2 / Windows 10 Pro / 57 Wh battery'),
('ce994ba5-974d-4c73-b68f-cfdafbc8d999', 1, 'ASUS ROG Zephyrus G14 512GB Black Laptop', 'Display: 14 / QHD, 2560x1600 / AMD Ryzen 9 5900HS / RAM 16 GB / 512 GB SSD / NVIDIA GeForce RTX 3060 / Wi-Fi 6 / Bluetooth 5.1 / Windows 10 Home / 76 Wh battery'),
('31ab09eb-76f2-4e7e-bde5-93d6283923b2', 1, 'Dell XPS 15 1TB Silver Laptop', 'Display: 15.6 / 4K, 3840x2160 / Intel Core i9-11900H / RAM 32 GB / 1 TB SSD / NVIDIA GeForce RTX 3070 / Wi-Fi 6 / Bluetooth 5.0 / Windows 10 Pro / 86 Wh battery');

INSERT INTO "products_translations" (product_id, language_id, name, description) VALUES
('b8be6872-b25e-4c1a-b845-df2f0150b4f2', 2, 'Мобільний телефон Samsung Galaxy A55 5G 8/256GB Ліловий', 'Екран: 6.6 / Super AMOLED, 2340x1080 / Samsung Exynos 1480 (4 x 2.75 ГГц + 4 x 2.0 ГГц) / Основна потрійна камера: 50 МП + 12 МП + 5 МП, Фронтальна камера: 32 МП / ОЗП 8 ГБ / 256 ГБ вбудованої пам`яті + microSD (до 1 ТБ) / 3G / LTE / 5G / GPS / A-GPS / GLONASS / BDS / Підтримка Dual SIM (Nano-SIM) / Android 14 / 5000 мАг'),
('33f6b0ad-0786-4882-9870-eccf088fc5c2', 2, 'Мобільний телефон Apple iPhone 14 Pro 128GB Сірий космос', 'Екран: 6.1 / Super Retina XDR, 2532x1170 / Чіп A16 Bionic / Основна потрійна камера: 48 МП + 12 МП + 12 МП, Фронтальна камера: 12 МП / ОЗП 6 ГБ / 128 ГБ вбудованої пам`яті / 3G / LTE / 5G / GPS / GLONASS / Підтримка Dual SIM (Nano-SIM та eSIM) / iOS 16 / 3200 мАг'),
('ee9c5b72-dfeb-43f0-8fc7-24961c572a0f', 2, 'Мобільний телефон Apple iPhone 13 128GB Червоний', 'Екран: 6.1 / Super Retina XDR, 2532x1170 / Чіп A15 Bionic / Основна подвійна камера: 12 МП + 12 МП, Фронтальна камера: 12 МП / ОЗП 4 ГБ / 128 ГБ вбудованої пам`яті / 3G / LTE / 5G / GPS / GLONASS / Підтримка Dual SIM (Nano-SIM та eSIM) / iOS 15 / 3240 мАг'),
('9be62d75-ef7f-4b6a-bfa1-9b94a12c8c4f', 2, 'Мобільний телефон Samsung Galaxy S21 128GB Чорний', 'Екран: 6.2 / Dynamic AMOLED 2X, 2400x1080 / Exynos 2100 / Основна камера: 12 МП + 64 МП + 12 МП, Фронтальна камера: 10 МП / ОЗП 8 ГБ / 128 ГБ вбудованої пам`яті / 3G / LTE / 5G / GPS / A-GPS / GLONASS / BDS / Підтримка Dual SIM (Nano-SIM) / Android 12 / 4000 мАг'),
('f38d74c3-dce9-4609-940d-d0a2a5109d10', 2, 'Мобільний телефон Apple iPhone 12 64GB Білий', 'Екран: 6.1 / Super Retina XDR, 2532x1170 / Чіп A14 Bionic / Основна подвійна камера: 12 МП + 12 МП, Фронтальна камера: 12 МП / ОЗП 4 ГБ / 64 ГБ вбудованої пам`яті / 3G / LTE / 5G / GPS / GLONASS / Підтримка Dual SIM (Nano-SIM та eSIM) / iOS 14 / 2815 мАг');

INSERT INTO "products_translations" (product_id, language_id, name, description) VALUES
('8f9edb19-372d-4521-9bb5-72b5b081285f', 2, 'Планшет Apple iPad Pro 11 (2022) 128GB Wi-Fi Сірий космос', 'Екран: 11 / Liquid Retina, 2388x1668 / Чіп M2 / Основна камера: 12 МП, Фронтальна камера: 12 МП / ОЗП 8 ГБ / 128 ГБ вбудованої пам`яті / Wi-Fi / Bluetooth 5.3 / iPadOS 16 / 7538 мАг'),
('251ff570-bd59-49f4-83f3-bd0bfb0a93a2', 2, 'Планшет Samsung Galaxy Tab S8+ 256GB Wi-Fi Срібний', 'Екран: 12.4 / Super AMOLED, 2800x1752 / Snapdragon 8 Gen 1 / Основна камера: 13 МП, Фронтальна камера: 12 МП / ОЗП 8 ГБ / 256 ГБ вбудованої пам`яті + microSD (до 1 ТБ) / Wi-Fi / Bluetooth 5.2 / Android 12 / 10090 мАг'),
('35b755f4-5f31-4862-9930-5574e8fbc35f', 2, 'Планшет Apple iPad Air 2022 64GB Wi-Fi Рожевий', 'Екран: 10.9 / Liquid Retina, 2360x1640 / Чіп M1 / Основна камера: 12 МП, Фронтальна камера: 12 МП / ОЗП 4 ГБ / 64 ГБ вбудованої пам`яті / Wi-Fi / Bluetooth 5.0 / iPadOS 15 / 7606 мАг'),
('c8e5107c-f59a-4c2b-b1b5-f7cfdbe0a9a6', 2, 'Планшет Lenovo Tab P11 Pro 128GB Wi-Fi Сірий', 'Екран: 11.5 / OLED, 2560x1600 / Snapdragon 730G / Основна камера: 13 МП, Фронтальна камера: 8 МП / ОЗП 4 ГБ / 128 ГБ вбудованої пам`яті / Wi-Fi / Bluetooth 5.0 / Android 11 / 8600 мАг'),
('e1cfa0e1-2a5d-4b43-9475-4caea0be4a95', 2, 'Планшет Huawei MatePad Pro 10.8 256GB Wi-Fi Чорний', 'Екран: 10.8 / IPS, 2560x1600 / Kirin 990 / Основна камера: 13 МП, Фронтальна камера: 8 МП / ОЗП 6 ГБ / 256 ГБ вбудованої пам`яті / Wi-Fi / Bluetooth 5.1 / EMUI 11 / 7250 мАг');

INSERT INTO "products_translations" (product_id, language_id, name, description) VALUES
('a9da953e-2be1-42d0-81e4-9a379263c276', 2, 'Ноутбук HP Spectre x360 14 1TB Чорний нічний', 'Екран: 13.5 / OLED, 3000x2000 / Intel Core i7-1165G7 / ОЗП 16 ГБ / 1 ТБ SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.0 / Windows 10 Home / Акумулятор 66 мАг'),
('b2d7d8ff-4a60-4779-8302-c51b95e44006', 2, 'Ноутбук Apple MacBook Pro 16 (2023) M2 1TB Сірий космос', 'Екран: 16 / Liquid Retina XDR, 3456x2234 / Чіп M2 / ОЗП 16 ГБ / 1 ТБ SSD / Wi-Fi 6E / Bluetooth 5.3 / macOS Ventura / Акумулятор 100 мАг'),
('c865773b-222a-4d68-bfc7-5cba80c379be', 2, 'Ноутбук Lenovo ThinkPad X1 Carbon Gen 9 512GB Чорний', 'Екран: 14 / FHD+, 1920x1200 / Intel Core i7-1165G7 / ОЗП 16 ГБ / 512 ГБ SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.2 / Windows 10 Pro / Акумулятор 57 мАг'),
('ce994ba5-974d-4c73-b68f-cfdafbc8d999', 2, 'Ноутбук ASUS ROG Zephyrus G14 512GB Чорний', 'Екран: 14 / QHD, 2560x1600 / AMD Ryzen 9 5900HS / ОЗП 16 ГБ / 512 ГБ SSD / NVIDIA GeForce RTX 3060 / Wi-Fi 6 / Bluetooth 5.1 / Windows 10 Home / Акумулятор 76 мАг'),
('31ab09eb-76f2-4e7e-bde5-93d6283923b2', 2, 'Ноутбук Dell XPS 15 1TB Срібний', 'Екран: 15.6 / 4K, 3840x2160 / Intel Core i9-11900H / ОЗП 32 ГБ / 1 ТБ SSD / NVIDIA GeForce RTX 3070 / Wi-Fi 6 / Bluetooth 5.0 / Windows 10 Pro / Акумулятор 86 мАг');