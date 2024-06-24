CREATE TABLE IF NOT EXISTS "accounts"
(
    id         BIGSERIAL CONSTRAINT PK_accounts PRIMARY KEY,
    password   varchar(255) NOT NULL,
    email      varchar(100) NOT NULL  CONSTRAINT UQ_accounts_email UNIQUE,
    first_name varchar(100) NOT NULL,
    last_name  varchar(100) NOT NULL,
    role       varchar(20)  NOT NULL,
    status     varchar(20)  NOT NULL,
    created_at timestamp
);