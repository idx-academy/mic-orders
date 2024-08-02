INSERT INTO orders (id, is_paid, order_status, created_at, edited_at, first_name, last_name, email, account_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440015', true, 'COMPLETED', '2020-06-25 12:44:44.518239', '2024-07-31 12:34:56', 'John', 'Doe', 'john.doe@example.com', 4),
    ('550e8400-e29b-41d4-a716-446655440016', false, 'IN_PROGRESS', '2024-06-25 12:44:44.518239', '2024-07-31 13:45:12', 'Jane', 'Smith', 'jane.smith@example.com', 4);

INSERT INTO order_items (order_id, product_id, price, quantity)
VALUES
    ('550e8400-e29b-41d4-a716-446655440015', '93063b3f-7c61-47ee-8612-4bfae9f3ff0f', 19.99, 2),
    ('550e8400-e29b-41d4-a716-446655440015', '0a2db6de-d14b-448e-bb74-993fa64f3ae7', 5.99, 1),
    ('550e8400-e29b-41d4-a716-446655440016', 'c2567b36-3ad4-4a79-978f-2f1dcd86e2b3', 49.99, 1),
    ('550e8400-e29b-41d4-a716-446655440016', 'ae459db4-9b15-4d6b-9e63-2d4d5f7cb0b0', 9.99, 3);

INSERT INTO post_addresses (id, delivery_method, city, department)
VALUES
    ('550e8400-e29b-41d4-a716-446655440015', 'NOVA', 'Kyiv', '4'),
    ('550e8400-e29b-41d4-a716-446655440016', 'UKRPOSHTA', 'Lviv', '2');