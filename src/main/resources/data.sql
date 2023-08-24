-- id: 1
INSERT INTO author(id, name, hashed_password) VALUES(nextval('author_SEQ'), 'admin', 'admin');
-- id: 51
INSERT INTO author(id, name, hashed_password) VALUES(nextval('author_SEQ'), 'user1', 'user1');
-- id: 101
INSERT INTO author(id, name, hashed_password) VALUES(nextval('author_SEQ'), 'user2', 'user2');

INSERT INTO comment(id, author_id, comment) VALUES(1, 51, 'This is the first comment.');
INSERT INTO comment(id, author_id, comment) VALUES(2, 51, 'This is the second comment.');
INSERT INTO comment(id, author_id, comment) VALUES(3, 51, 'This is the third comment.');
INSERT INTO comment(id, author_id, comment) VALUES(4, 101, 'This is the fourth comment.');
INSERT INTO comment(id, author_id, comment) VALUES(5, 101, 'This is the fifth comment.');
INSERT INTO comment(id, author_id, comment) VALUES(6, 101, 'This is the sixth comment.');
