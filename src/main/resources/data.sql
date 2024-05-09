INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('title', 'author', 10.00, 'isbn', 'description', 0, 'https://img.tukuppt.com/png_preview/00/04/81/SYZxWQlAr9.jpg!/fw/780');
INSERT INTO Userprivacys (username, password, role)
VALUES ('root', 'password', 'admin');
INSERT INTO Userpublics (username, userprivacy_id)
VALUES ('root', (SELECT id FROM UserPrivacys WHERE username = 'root'));

-- 插入到cart表
INSERT INTO Carts (user_id, number)
VALUES ((SELECT id FROM UserPublics WHERE username = 'root'),  1);



-- 插入到order表
INSERT INTO `Orders` (user_id, receiver, tel, address, status)
VALUES ((SELECT id FROM UserPublics WHERE username = 'root'), 'Receiver Name', '1234567890', 'Address', 'paid');
 (SELECT id FROM Books WHERE title = 'title')));

-- 插入到orderItem表
INSERT INTO OrderItems (book_id, number, order_id, cart_id)
VALUES ((SELECT id FROM books WHERE title = 'title'), 1, NULL, 1);


-- 更多的书籍
INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('To Kill a Mockingbird', 'Harper Lee', 10.00, '978-0446310789', 'A novel about the roots of human behavior - innocence and experience, kindness and cruelty, love and hatred, humor and pathos.', 0, 'https://cdn2.penguin.com.au/covers/original/9781784870799.jpg');

INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('1984', 'George Orwell', 15.00, '978-0451524935', 'A dystopian novel set in Airstrip One, formerly Great Britain, a province of the superstate Oceania.', 0, 'https://s3.amazonaws.com/criterion-production/films/9b263f6358cf0bae18244c759d4592f0/BonyZ4taymxrfBiRqY6K42NBh0jsop_large.jpg');

INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', 20.00, '978-0743273565', 'The story of the mysteriously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan.', 0, 'https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781471173936/the-great-gatsby-9781471173936_lg.jpg');

INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('Moby Dick', 'Herman Melville', 25.00, '978-1503280786', 'The sailor Ishmael narrates the obsessive quest of Ahab, captain of the whaling ship Pequod, for revenge on Moby Dick.', 0, 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Moby-Dick_FE_title_page.jpg/330px-Moby-Dick_FE_title_page.jpg');

INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('War and Peace', 'Leo Tolstoy', 30.00, '978-1400079988', 'A novel by the Russian author Leo Tolstoy, which is regarded as a central work of world literature.', 0, 'https://images-na.ssl-images-amazon.com/images/I/A1aDb5U5myL.jpg');