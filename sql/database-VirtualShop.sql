DROP DATABASE virtualshop;
create database virtualshop default char set utf8;
use virtualshop;

DROP TABLE `products`;
CREATE TABLE `products`
(
    `id`             INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`           VARCHAR(255)    NOT NULL,
    `price`          FLOAT           NOT NULL,
    `stock_quantity` FLOAT           NOT NULL
);

DROP TABLE `users`;
CREATE TABLE `users`
(
    `id`           INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `login`        VARCHAR(255)    NOT NULL,
    `password`     VARCHAR(255)    NOT NULL,
    `amount_money` FLOAT           NOT NULL
);

-- CREATE TABLE `order`
-- (
--     `id`           INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
--     `user_id`      INT             NOT NULL,
--     `product_id`   VARCHAR(255)    NOT NULL,
--     `password`     VARCHAR(255)    NOT NULL,
--     `amount_money` FLOAT           NOT NULL
-- );
