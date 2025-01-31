CREATE TABLE IF NOT EXISTS "discounts" (
    id                UUID CONSTRAINT PK_discounts PRIMARY KEY,
    amount            INTEGER NOT NULL,
    start_date        TIMESTAMP NOT NULL,
    end_date          TIMESTAMP NOT NULL
);