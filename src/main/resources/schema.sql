# 当运行h2的时候不能建库
# DROP DATABASE IF EXISTS `ebookstore`; # 每次重新运行都删库重建，DEBUG only
# CREATE DATABASE IF NOT EXISTS `ebookstore` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
# USE `ebookstore`;
#  用mysql的时候取消注释
# TODO: 奇怪的现象, springboot里面无法正确执行DROP

DROP TABLE IF EXISTS `OrderItems`;
DROP TABLE IF EXISTS `Orders`;
DROP TABLE IF EXISTS `CartItems`;
DROP TABLE IF EXISTS `Carts`;
DROP TABLE IF EXISTS `Books`;
DROP TABLE IF EXISTS `UserPrivacys`;
DROP TABLE IF EXISTS `UserPublics`;

# 注意外键约束
#ON UPDATE RESTRICT 如果尝试更新引用的主键值，那么将禁止这个更新操作。换句话说，如果有其他表的外键引用了这个主键，那么不能修改这个主键的值。
# ON DELETE CASCADE 这个约束表示，如果删除了引用的主键值，那么将删除所有引用该主键的外键记录。换句话说，如果你删除了一个表的记录，那么所有引用这个记录的主键的其他表的记录也将被删除。
CREATE TABLE IF NOT EXISTS Books
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255) NOT NULL,
    price       INT          NOT NULL,
    isbn        CHAR(17)     NOT NULL, # 13位isbn 5部分 == 4 '-'
    description TEXT         NOT NULL,
    sales       INT          NOT NULL DEFAULT 0,
    cover       VARCHAR(255) NOT NULL
);



CREATE TABLE IF NOT EXISTS UserPublics
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(255),
    lastName  VARCHAR(255),
    address   VARCHAR(255),
    city      VARCHAR(255),
    state     VARCHAR(255),
    phone     CHAR(20),
    email     VARCHAR(255),
    headImg   VARCHAR(255)              DEFAULT 'defaultUser.jpg',
    username  VARCHAR(255) NOT NULL UNIQUE,
    balance   INT                       DEFAULT 0,
    status    ENUM ('normal', 'banned') DEFAULT 'normal',
    role      ENUM ('user', 'admin')    DEFAULT 'user'
);

CREATE TABLE IF NOT EXISTS UserPrivacys
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    userpublic_id INT UNIQUE   NOT NULL,
    CONSTRAINT fk_userprivacy_userpublic
        FOREIGN KEY (userpublic_id) REFERENCES UserPublics (id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT
);

CREATE TABLE IF NOT EXISTS Carts
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    CONSTRAINT fk_cart_user_id
        FOREIGN KEY (user_id) REFERENCES UserPublics (id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT
);


CREATE TABLE IF NOT EXISTS `Orders`
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    user_id   INT,
    receiver  VARCHAR(255),
    createdAt TIMESTAMP                                      DEFAULT CURRENT_TIMESTAMP,
    tel       CHAR(20),
    address   VARCHAR(255),
    status    ENUM ('unpaid', 'paid', 'shipped', 'received') DEFAULT 'unpaid',
    CONSTRAINT fk_order_user_id
        FOREIGN KEY (user_id) REFERENCES UserPublics (id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT
);
CREATE TABLE IF NOT EXISTS OrderItems
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    book_id  INT,
    number   INT,
    order_id INT,
    CONSTRAINT fk_orderItem_book_id
        FOREIGN KEY (book_id) REFERENCES books (id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT,
    CONSTRAINT fk_orderItem_order_id
        FOREIGN KEY (order_id) REFERENCES `Orders` (id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT
);
# 分离cartItem和orderItem以减轻orderItem子表压力
CREATE TABLE IF NOT EXISTS CartItems
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    number  INT,
    cart_id INT,
    CONSTRAINT fk_cartItem_cart_id
        FOREIGN KEY (cart_id) REFERENCES Carts (id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT,
    CONSTRAINT fk_cartItem_book_id
        FOREIGN KEY (book_id) REFERENCES books (id)
            ON DELETE CASCADE
            ON UPDATE RESTRICT
);

