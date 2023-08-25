-- id: 1
INSERT INTO author(id, name, hashed_password) VALUES(nextval('author_SEQ'), 'admin', '$2a$10$oTooQAl8NKbwmUPqk46xeORRg6xNAoU/CGaN6XKj1RG3TP9N1o/eK');
-- id: 51
INSERT INTO author(id, name, hashed_password) VALUES(nextval('author_SEQ'), 'user1', '$2a$10$y19Nll.gLaZap5oPxpX7ouDONK501R8M42rkHR3filUbuX.Ut00Py');
-- id: 101
INSERT INTO author(id, name, hashed_password) VALUES(nextval('author_SEQ'), 'user2', '$2a$10$XS/ama2MvTY2/wJmHWkw4.gVh.DffDxbju4v7RhcrZptME6YmGfcG');

INSERT INTO comment(id, user_id, comment) VALUES(1, 51, 'This is the first comment.');
INSERT INTO comment(id, user_id, comment) VALUES(2, 51, 'This is the second comment.');
INSERT INTO comment(id, user_id, comment) VALUES(3, 51, 'This is the third comment.');
INSERT INTO comment(id, user_id, comment) VALUES(4, 101, 'This is the fourth comment.');
INSERT INTO comment(id, user_id, comment) VALUES(5, 101, 'This is the fifth comment.');
INSERT INTO comment(id, user_id, comment) VALUES(6, 101, 'This is the sixth comment.');

INSERT INTO user_role(id, user_id, role) VALUES(1, 1, 'ADMIN');
INSERT INTO user_role(id, user_id, role) VALUES(2, 51, 'USER');
INSERT INTO user_role(id, user_id, role) VALUES(3, 101, 'USER');
