-- Ensure the schema exists before using it
CREATE SCHEMA IF NOT EXISTS "food-product";

-- Drop problematic tables if they exist
DROP TABLE IF EXISTS "food-product".product_cart CASCADE;
DROP TABLE IF EXISTS "food-product".users CASCADE;
DROP TABLE IF EXISTS "food-product".admins CASCADE;
DROP TABLE IF EXISTS "food-product".carts CASCADE;
DROP TABLE IF EXISTS "food-product".products CASCADE;
DROP TABLE IF EXISTS "food-product".reviews CASCADE;
DROP TABLE IF EXISTS "food-product".movies CASCADE;
DROP TABLE IF EXISTS "food-product".games CASCADE;
DROP TABLE IF EXISTS "food-product".foods CASCADE;
DROP TABLE IF EXISTS "food-product".schedules CASCADE;
DROP TABLE IF EXISTS "food-product".user_schedule CASCADE;
DROP TABLE IF EXISTS "food-product".product_schedule CASCADE;
DROP TABLE IF EXISTS "food-product".movie_types CASCADE;
DROP TABLE IF EXISTS "food-product".game_types CASCADE;
DROP TABLE IF EXISTS "food-product".food_types CASCADE;

-- Create users table
CREATE TABLE IF NOT EXISTS "food-product".users (
    account_id SERIAL PRIMARY KEY,
    dob VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    phone_number VARCHAR(255),
    image_url TEXT,
    cart_id INTEGER
);

-- Create admins table
CREATE TABLE IF NOT EXISTS "food-product".admins (
    account_id SERIAL PRIMARY KEY,
    dob VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    image_url TEXT,
    phone_number VARCHAR(255)
);

-- Create cart table with `cart_id` as SERIAL
CREATE SEQUENCE IF NOT EXISTS "food-product".cart_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS "food-product".carts (
    cart_id INTEGER PRIMARY KEY DEFAULT nextval('"food-product".cart_seq'),
    price DOUBLE PRECISION,
    account_id INTEGER NOT NULL REFERENCES "food-product".users(account_id) ON DELETE CASCADE
);

-- Create product table with `UUID` as primary key
CREATE TABLE IF NOT EXISTS "food-product".products (
    product_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    price DOUBLE PRECISION,
    description TEXT,
    image_url TEXT
);

-- Ensure `product_id` is UUID in product_cart
CREATE TABLE IF NOT EXISTS "food-product".product_cart (
    cart_id INTEGER NOT NULL,
    account_id INTEGER NOT NULL,
    product_id UUID NOT NULL,
    PRIMARY KEY (cart_id, account_id, product_id),
    CONSTRAINT FK_product_cart_cart FOREIGN KEY (cart_id) REFERENCES "food-product".carts(cart_id) ON DELETE CASCADE,
    CONSTRAINT FK_product_cart_user FOREIGN KEY (account_id) REFERENCES "food-product".users(account_id) ON DELETE CASCADE,
    CONSTRAINT FK_product_cart_product FOREIGN KEY (product_id) REFERENCES "food-product".products(product_id) ON DELETE CASCADE
);

-- Create schedules table
CREATE SEQUENCE IF NOT EXISTS "food-product".schedule_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS "food-product".schedules (
    schedule_id INTEGER PRIMARY KEY DEFAULT nextval('"food-product".schedule_seq'),
    day_of_week VARCHAR(20),
    schedule_time TIME,
    name VARCHAR(255),
    category VARCHAR(255),
    is_passed BOOLEAN DEFAULT FALSE
);

-- Create user_schedule table
CREATE TABLE IF NOT EXISTS "food-product".user_schedule (
    account_id INTEGER NOT NULL REFERENCES "food-product".users(account_id) ON DELETE CASCADE,
    schedule_id INTEGER NOT NULL REFERENCES "food-product".schedules(schedule_id) ON DELETE CASCADE,
    PRIMARY KEY (account_id, schedule_id)
);

-- Create product_schedule table
CREATE TABLE IF NOT EXISTS "food-product".product_schedule (
    schedule_id INTEGER NOT NULL REFERENCES "food-product".schedules(schedule_id) ON DELETE CASCADE,
    cart_id UUID NOT NULL,
    PRIMARY KEY (schedule_id, cart_id)
);

-- Create foods table
CREATE TABLE IF NOT EXISTS "food-product".foods (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ingredients TEXT,
    product_id UUID REFERENCES "food-product".products(product_id) ON DELETE CASCADE
);

-- Create games table
CREATE TABLE IF NOT EXISTS "food-product".games (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID REFERENCES "food-product".products(product_id) ON DELETE CASCADE
);

-- Create movies table
CREATE TABLE IF NOT EXISTS "food-product".movies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    platform VARCHAR(255),
    product_id UUID REFERENCES "food-product".products(product_id) ON DELETE CASCADE
);

-- Create reviews table
CREATE TABLE IF NOT EXISTS "food-product".reviews (
    review_id SERIAL PRIMARY KEY,
    comment TEXT,
    rating DOUBLE PRECISION,
    account_id INTEGER REFERENCES "food-product".users(account_id) ON DELETE CASCADE,
    movie_id UUID REFERENCES "food-product".movies(id) ON DELETE CASCADE
);

-- Create movie_types table
CREATE TABLE IF NOT EXISTS "food-product".movie_types (
    movie_type_id SERIAL PRIMARY KEY,
    movie_type VARCHAR(255),
    movie_id UUID REFERENCES "food-product".movies(id) ON DELETE CASCADE
);

-- Create game_types table
CREATE TABLE IF NOT EXISTS "food-product".game_types (
    game_type_id SERIAL PRIMARY KEY,
    game_type VARCHAR(255),
    game_id UUID REFERENCES "food-product".games(id) ON DELETE CASCADE
);

-- Create food_types table
CREATE TABLE IF NOT EXISTS "food-product".food_types (
    food_type_id SERIAL PRIMARY KEY,
    food_type VARCHAR(255),
    food_id UUID REFERENCES "food-product".foods(id) ON DELETE CASCADE
);
