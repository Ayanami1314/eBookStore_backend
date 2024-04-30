
# 当运行h2的时候不能建库
CREATE DATABASE IF NOT EXISTS `ebookStore` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ebookStore`;
#  用mysql的时候取消注释


CREATE TABLE books (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       price DECIMAL(10, 2) NOT NULL,
                       isbn VARCHAR(13) NOT NULL,
                       description TEXT NOT NULL,
                       sales INT NOT NULL DEFAULT 0,
                       cover VARCHAR(255) NOT NULL
);
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,

                       email VARCHAR(255) NOT NULL,
                       role ENUM('admin', 'user') NOT NULL DEFAULT 'user'
);
CREATE TABLE userInfo(
    userId INT,
    FOREIGN KEY (userId) REFERENCES users(id),
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
)