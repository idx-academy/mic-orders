CREATE TABLE IF NOT EXISTS "tags"
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO "tags" (name) VALUES ('category:mobile'), ('category:tablet'), ('category:computer');
