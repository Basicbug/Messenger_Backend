INSERT INTO USER (uid, name, provider, status) VALUES ('qwebnm7788', '최재원', 'naver', '열심히 살자');
INSERT INTO USER (uid, name, provider, status) VALUES ('bsgreentea', '김보성', 'naver', '배고프다..');
INSERT INTO USER (uid, name, provider) VALUES ('ChoMk', '조명기', 'naver');

INSERT INTO FRIENDS (uid, frienduid) VALUES ('ChoMk', 'qwebnm7788');
INSERT INTO FRIENDS (uid, frienduid) VALUES ('ChoMk', 'bsgreentea');
INSERT INTO FRIENDS (uid, frienduid) VALUES ('bsgreentea', 'ChoMk');
INSERT INTO FRIENDS (uid, frienduid) VALUES ('bsgreentea', 'qwebnm7788');
INSERT INTO FRIENDS (uid, frienduid) VALUES ('qwebnm7788', 'bsgreentea');
