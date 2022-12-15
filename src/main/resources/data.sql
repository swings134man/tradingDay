INSERT INTO role (role_id, role_name, role_number) values (1, 'USER', '1');
INSERT INTO role (role_id, role_name, role_number) values (2, 'MANAGER', '2');
INSERT INTO role (role_id, role_name, role_number) values (3, 'ADMIN', '3');

INSERT INTO member (member_no, address, email, member_id, name, pwd, tel_no) values(1, '서울 중구 1길 123', 'iu1234@naver.com', 'iu1234', '아이유','dkdldb1234@', '010-1234-5678');
INSERT INTO member (member_no, address, email, member_id, name, pwd, tel_no) values(2, '서울 중구 3길 123', 'xodlf5363@asd.com', 'xodlf5363', '쿠쿠루삥뽕','xodlf5363@', '010-4321-8756');

INSERT INTO user_role (user_role_id, member_no, role_id) values (1, 1, 3);
INSERT INTO user_role (user_role_id, member_no, role_id) values (2, 2, 1);