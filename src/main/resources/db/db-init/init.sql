-- Ensure the schema exists before using it
CREATE SCHEMA IF NOT EXISTS "food-product";

-- Create users table
CREATE TABLE IF NOT EXISTS "food-product".users (
    account_id SERIAL PRIMARY KEY,
    dob VARCHAR(255),
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    phone_number VARCHAR(255),
    cart_id INTEGER
);

-- Create user_schedule table
CREATE TABLE IF NOT EXISTS "food-product".user_schedule (
    account_id INTEGER,
    schedule_id INTEGER,
    PRIMARY KEY (account_id, schedule_id)
);

-- Create user_reviews table
CREATE TABLE IF NOT EXISTS "food-product".user_reviews (
    account_id INTEGER,
    review_id INTEGER,
    PRIMARY KEY (account_id, review_id)
);

-- Create schedules table
CREATE TABLE IF NOT EXISTS "food-product".schedules (
    schedule_id SERIAL PRIMARY KEY,
    day_of_week VARCHAR(255),
    schedule_time TIME,
    name VARCHAR(255),
    category VARCHAR(255),
    is_passed BOOLEAN,
    account_ids INTEGER[],
    product_id UUID[]
);

-- Create user_schedule table
CREATE TABLE IF NOT EXISTS "food-product".user_schedule (
    schedule_id INTEGER,
    account_id INTEGER,
    PRIMARY KEY (schedule_id, account_id)
);

-- Create product_schedule table
CREATE TABLE IF NOT EXISTS "food-product".product_schedule (
    schedule_id INTEGER,
    cart_id UUID,
    PRIMARY KEY (schedule_id, cart_id)
);

-- Create product_cart table
CREATE TABLE IF NOT EXISTS "food-product".product_cart (
    cart_id UUID PRIMARY KEY,
    account_id INTEGER NOT NULL,
    product_id UUID[]
);
