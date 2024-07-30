CREATE INDEX ix_products_translations_search ON products_translations
USING gin (to_tsvector('simple', name));