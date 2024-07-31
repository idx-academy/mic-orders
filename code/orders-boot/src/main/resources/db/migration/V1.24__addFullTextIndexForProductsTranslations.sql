CREATE EXTENSION pg_trgm;
CREATE EXTENSION btree_gin;

CREATE INDEX ix_products_translations_name ON products_translations
USING gin (name);