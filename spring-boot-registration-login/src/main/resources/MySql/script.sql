show databases;
create database cloud1;
use cloud1;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL, 
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    birthday DATE,
    phone_number VARCHAR(15),
    fathers_name VARCHAR(45),
    is_admin BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE users ADD COLUMN role ENUM('USER', 'ADMIN') DEFAULT 'USER' NOT NULL;

ALTER TABLE users ADD COLUMN profile_picture VARCHAR(255);




