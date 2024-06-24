CREATE TABLE IF NOT EXISTS "products_translations" (
    product_id UUID,
    language_id BIGINT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (language_id) REFERENCES languages(id),
    PRIMARY KEY (product_id, language_id)
);

INSERT INTO "products_translations" (product_id, language_id, name, description) VALUES
('84b7e490-0dcf-44c3-beb6-7496dc6ef3b0', 1, 'Mobile Phone Samsung Galaxy A55 5G 8/256GB Lilac', 'Screen: 6.6\" Super AMOLED, 2340x1080 / Samsung Exynos 1480 (4 x 2.75 GHz + 4 x 2.0 GHz) / Main Triple Camera: 50 MP + 12 MP + 5 MP, Front Camera: 32 MP / RAM 8 GB / 256 GB internal storage + microSD (up to 1 TB) / 3G / LTE / 5G / GPS / A-GPS / GLONASS / BDS / Dual SIM support (Nano-SIM) / Android 14 / 5000 mAh'),
('04f7a6b4-e55e-4f68-9f68-9636e1b2e256', 1, 'Mobile Phone Apple iPhone 14 Pro 128GB Space Gray', 'Screen: 6.1\" Super Retina XDR, 2532x1170 / A16 Bionic chip / Main Triple Camera: 48 MP + 12 MP + 12 MP, Front Camera: 12 MP / RAM 6 GB / 128 GB internal storage / 3G / LTE / 5G / GPS / GLONASS / Dual SIM support (Nano-SIM and eSIM) / iOS 16 / 3200 mAh'),
('93063b3f-7c61-47ee-8612-4bfae9f3ff0f', 1, 'Tablet Apple iPad Pro 11\" (2022) 128GB Wi-Fi Space Gray', 'Screen: 11\" Liquid Retina, 2388x1668 / M2 chip / Main Camera: 12 MP, Front Camera: 12 MP / RAM 8 GB / 128 GB internal storage / Wi-Fi / Bluetooth 5.3 / iPadOS 16 / 7538 mAh'),
('ae459db4-9b15-4d6b-9e63-2d4d5f7cb0b0', 1, 'Tablet Samsung Galaxy Tab S8+ 256GB Wi-Fi Silver', 'Screen: 12.4\" Super AMOLED, 2800x1752 / Snapdragon 8 Gen 1 / Main Camera: 13 MP, Front Camera: 12 MP / RAM 8 GB / 256 GB internal storage + microSD (up to 1 TB) / Wi-Fi / Bluetooth 5.2 / Android 12 / 10090 mAh'),
('52d4f45c-70a8-4f36-99d0-d645c5f704b0', 1, 'Laptop Apple MacBook Pro 14\" (2023) M2 Pro 512GB Space Gray', 'Screen: 14.2\" Liquid Retina XDR, 3024x1964 / M2 Pro chip / RAM 16 GB / 512 GB SSD / Wi-Fi 6E / Bluetooth 5.3 / macOS Ventura / 70 Wh battery'),
('f3786b85-0d0e-4bff-92f8-6f9d0b3dc6f5', 1, 'Laptop Dell XPS 13 9310 512GB Platinum Silver', 'Screen: 13.4\" FHD+, 1920x1200 / Intel Core i7-1185G7 / RAM 16 GB / 512 GB SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.1 / Windows 10 Home / 52 Wh battery'),
('0a2db6de-d14b-448e-bb74-993fa64f3ae7', 1, 'Mobile Phone Apple iPhone 13 128GB Red', 'Screen: 6.1\" Super Retina XDR, 2532x1170 / A15 Bionic chip / Main Dual Camera: 12 MP + 12 MP, Front Camera: 12 MP / RAM 4 GB / 128 GB internal storage / 3G / LTE / 5G / GPS / GLONASS / Dual SIM support (Nano-SIM and eSIM) / iOS 15 / 3240 mAh'),
('c2567b36-3ad4-4a79-978f-2f1dcd86e2b3', 1, 'Laptop HP Spectre x360 14 1TB Nightfall Black', 'Screen: 13.5\" OLED, 3000x2000 / Intel Core i7-1165G7 / RAM 16 GB / 1 TB SSD / Intel Iris Xe Graphics / Wi-Fi 6 / Bluetooth 5.0 / Windows 10 Home / 66 Wh battery');
