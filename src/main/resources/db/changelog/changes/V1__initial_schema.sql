CREATE SCHEMA IF NOT EXISTS business;

CREATE TABLE IF NOT EXISTS business.users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(500),
    date_of_birth DATE,
    password VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS business.account (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE,
    initial_balance DECIMAL,
    balance DECIMAL,

    FOREIGN KEY (user_id) REFERENCES business.users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS business.email_data (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    email VARCHAR(200) UNIQUE,

    FOREIGN KEY (user_id) REFERENCES business.users(id)
);

CREATE TABLE IF NOT EXISTS business.phone_data (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    phone VARCHAR(13) UNIQUE,

    FOREIGN KEY (user_id) REFERENCES business.users(id)
);