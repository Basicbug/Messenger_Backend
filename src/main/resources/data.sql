INSERT INTO USER (uid, name, provider) VALUES ('qwebnm7788', '최재원', 'naver');
INSERT INTO USER (uid, name, provider) VALUES ('bsgreentea', '김보성', 'naver');
INSERT INTO USER (uid, name, provider) VALUES ('ChoMk', '조명기', 'naver');

INSERT INTO FRIENDS (fromuid, touid) VALUES ('ChoMk', 'qwebnm7788');
INSERT INTO FRIENDS (fromuid, touid) VALUES ('ChoMk', 'bsgreentea');
INSERT INTO FRIENDS (fromuid, touid) VALUES ('bsgreentea', 'ChoMk');
INSERT INTO FRIENDS (fromuid, touid) VALUES ('bsgreentea', 'qwebnm7788');
INSERT INTO FRIENDS (fromuid, touid) VALUES ('qwebnm7788', 'bsgreentea');
