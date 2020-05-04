INSERT INTO USER (uid, name, provider) VALUES ('qwebnm7788', '최재원', 'naver');
INSERT INTO USER (uid, name, provider) VALUES ('bsgreentea', '김보성', 'naver');
INSERT INTO USER (uid, name, provider) VALUES ('ChoMk', '조명기', 'naver');

INSERT INTO FRIENDS (uid, frienduid) VALUES ('ChoMk', 'qwebnm7788');
INSERT INTO FRIENDS (uid, frienduid) VALUES ('ChoMk', 'bsgreentea');
INSERT INTO FRIENDS (uid, frienduid) VALUES ('bsgreentea', 'ChoMk');
INSERT INTO FRIENDS (uid, frienduid) VALUES ('bsgreentea', 'qwebnm7788');
INSERT INTO FRIENDS (uid, frienduid) VALUES ('qwebnm7788', 'bsgreentea');
