INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('title', 'author', 10.00, 'isbn', 'description', 0, 'cover.jpg');
INSERT INTO Userprivacys (username, password, role)
VALUES ('root', 'password', 'admin');
INSERT INTO Userpublics (username, userprivacy_id)
VALUES ('root', (SELECT id FROM UserPrivacys WHERE username = 'root'));

-- 插入到cart表
INSERT INTO Carts (user_id, book_id, number)
VALUES ((SELECT id FROM UserPublics WHERE username = 'root'), (SELECT id FROM books WHERE title = 'title'), 1);



-- 插入到order表
INSERT INTO `Orders` (user_id, receiver, tel, address, status)
VALUES ((SELECT id FROM UserPublics WHERE username = 'root'), 'Receiver Name', '1234567890', 'Address', 'paid');
 (SELECT id FROM Books WHERE title = 'title')));

-- 插入到orderItem表
INSERT INTO OrderItems (book_id, number, order_id)
VALUES ((SELECT id FROM books WHERE title = 'title'), 1, 1);

-- 更多的书籍
INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('To Kill a Mockingbird', 'Harper Lee', 10.00, '978-0446310789', 'A novel about the roots of human behavior - innocence and experience, kindness and cruelty, love and hatred, humor and pathos.', 0, 'tokillamockingbird.jpg');

INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('1984', 'George Orwell', 15.00, '978-0451524935', 'A dystopian novel set in Airstrip One, formerly Great Britain, a province of the superstate Oceania.', 0, '1984.jpg');

INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', 20.00, '978-0743273565', 'The story of the mysteriously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan.', 0, 'thegreatgatsby.jpg');

INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('Moby Dick', 'Herman Melville', 25.00, '978-1503280786', 'The sailor Ishmael narrates the obsessive quest of Ahab, captain of the whaling ship Pequod, for revenge on Moby Dick.', 0, 'mobydick.jpg');

INSERT INTO Books (title, author, price, isbn, description, sales, cover)
VALUES ('War and Peace', 'Leo Tolstoy', 30.00, '978-1400079988', 'A novel by the Russian author Leo Tolstoy, which is regarded as a central work of world literature.', 0, 'warandpeace.jpg');