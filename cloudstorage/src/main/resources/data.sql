INSERT INTO USERS (username, firstname, lastname, password, salt) VALUES ('user1', 'eu', 'la', 'XO5V1jcQhW1kgQpiNDZs6Q==', '1nV98i3GIwbdKS2Q070Pzg==')

INSERT INTO NOTES (notetitle, notedescription, userid) VALUES ('TEST-NOTE', 'this is a note1', 1)
INSERT INTO NOTES (notetitle, notedescription, userid) VALUES ('TEST-NOTE', 'this is a note2', 1)
INSERT INTO NOTES (notetitle, notedescription, userid) VALUES ('TEST-NOTE', 'this is a note3', 1)

INSERT INTO CREDENTIALS (userid, url, username, key, password) VALUES(1, 'www.eugland.com', 'eugland', 'M+KHGPUrF0+7kUJnirB6Tw==', 'ipEqXwxkAIpfrDGy7wiXJA==')

INSERT INTO FILES (userid, filename, contenttype, filesize, filedata) VALUES (1, 'testfile.txt', 'text/plain', '14', 5468697320697320612074657374)