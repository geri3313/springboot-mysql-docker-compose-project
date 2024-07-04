CREATE DATABASE IF NOT EXISTS cloud1;

USE cloud1;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL, -- Assuming BCrypt encoded password length
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    birthday DATE,
    phone_number VARCHAR(15),
    fathers_name VARCHAR(45),
    is_admin BOOLEAN NOT NULL DEFAULT FALSE, -- New column to indicate admin status
    role ENUM('USER', 'ADMIN') DEFAULT 'USER' NOT NULL,
    profile_picture VARCHAR(255)
);