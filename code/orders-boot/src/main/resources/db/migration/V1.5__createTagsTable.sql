CREATE TABLE IF NOT EXISTS "tags"
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO "tags" (name) VALUES ('SmartPhone'),('6 inch'),('Mobile');
