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


-- Create product table
CREATE TABLE IF NOT EXISTS "food-product".products (
    product_id UUID PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE PRECISION,
    description TEXT,
    image_url TEXT
);

-- Create cart table
CREATE TABLE IF NOT EXISTS "food-product".carts (
    cart_id SERIAL PRIMARY KEY,
    price DOUBLE PRECISION,
    account_id INTEGER NOT NULL,
    product_id UUID[]
);

-- Then create product_cart
CREATE TABLE IF NOT EXISTS "food-product".product_cart (
    cart_id INTEGER PRIMARY KEY,
    account_id INTEGER NOT NULL,
    product_id UUID[],
    CONSTRAINT FK_product_cart FOREIGN KEY (cart_id) REFERENCES "food-product".carts(cart_id)
);

-- Create foods table
CREATE TABLE IF NOT EXISTS "food-product".foods (
    id UUID PRIMARY KEY,
    ingredients TEXT,
    product_id UUID REFERENCES "food-product".products(product_id)
);

-- Create games table
CREATE TABLE IF NOT EXISTS "food-product".games (
    id UUID PRIMARY KEY,
    product_id UUID REFERENCES "food-product".products(product_id)
);

-- Create movies table
CREATE TABLE IF NOT EXISTS "food-product".movies (
    id UUID PRIMARY KEY,
    platform VARCHAR(255),
    product_id UUID REFERENCES "food-product".products(product_id)
);

-- Create reviews table
CREATE TABLE IF NOT EXISTS "food-product".reviews (
    review_id SERIAL PRIMARY KEY,
    comment TEXT,
    rating DOUBLE PRECISION,
    account_id INTEGER,
    movie_id UUID
);

-- Create food_types table
CREATE TABLE IF NOT EXISTS "food-product".food_types (
    food_type_id SERIAL PRIMARY KEY,
    food_type VARCHAR(255)
);

-- Create game_types table
CREATE TABLE IF NOT EXISTS "food-product".game_types (
    game_type_id SERIAL PRIMARY KEY,
    game_type VARCHAR(255)
);

-- Create movie_types table
CREATE TABLE IF NOT EXISTS "food-product".movie_types (
    movie_type_id SERIAL PRIMARY KEY,
    movie_type VARCHAR(255)
);

ALTER TABLE "food-product".carts ALTER COLUMN account_id DROP NOT NULL;
