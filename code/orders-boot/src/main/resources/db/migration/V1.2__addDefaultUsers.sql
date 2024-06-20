INSERT INTO accounts(password, email, first_name, last_name, role, status, created_at)
VALUES ('$2a$12$rdHudC1kdv6Kim/lDAc.ouYfoxrLobmNhIN9NggIQJxnVslS8LpBe','admin@mail.com','admin','admin','ROLE_ADMIN','ACTIVE',now());

INSERT INTO accounts(password, email, first_name, last_name, role, status, created_at)
VALUES ('$2a$12$aNl3loPbhTt3Bw5bKxl01uf2YfkXQ9XOI52vQIj6r7EK3DBnTescu','user@mail.com','user','user','ROLE_USER','ACTIVE',now());