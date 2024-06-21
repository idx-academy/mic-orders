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
('123e4567-e89b-12d3-a456-426614174000', 1, 'Apple iPhone', 'Some Apple product'),
('123e4567-e89b-12d3-a456-426614174000', 2, 'ЕПЛ айФон', 'Продукція Епл'),
('123e4567-e89b-12d3-a457-426614174000', 1, 'Samsung', 'Samsung Description'),
('123e4567-e89b-12d3-a457-426614174000', 2, 'Самсунг', 'Опис Самсунг');
