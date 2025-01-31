ALTER TABLE "products"
ADD COLUMN discount_id UUID unique;

ALTER TABLE "products"
ADD CONSTRAINT fk_discount
FOREIGN KEY (discount_id)
REFERENCES discounts(id);