CREATE TABLE IF NOT EXISTS "languages" (
    id SERIAL PRIMARY KEY,
    code VARCHAR(5) NOT NULL UNIQUE
);

INSERT INTO "languages" (code) VALUES ('en'), ('uk');
