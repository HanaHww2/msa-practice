-- p_product
INSERT INTO p_products
    (stock, price, created_at, modified_at, id, name)
VALUES
    (10, 10000, now(), now(), '00000000-0000-0000-0000-000000000001'::uuid, 'test product1'),
    (20, 20000, now(), now(), '00000000-0000-0000-0000-000000000002'::uuid, 'test product2'),
    (30, 30000, now(), now(), '00000000-0000-0000-0000-000000000003'::uuid, 'test product3'),
    (40, 40000, now(), now(), '00000000-0000-0000-0000-000000000004'::uuid, 'test product4'),
    (50, 50000, now(), now(), '00000000-0000-0000-0000-000000000005'::uuid, 'test product5');