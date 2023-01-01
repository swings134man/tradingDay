-- 권한, user, USER_ROLE

INSERT INTO role (role_id, role_name, role_number) values (1, 'ROLE_USER', '1');
INSERT INTO role (role_id, role_name, role_number) values (2, 'ROLE_MANAGER', '2');
INSERT INTO role (role_id, role_name, role_number) values (3, 'ROLE_ADMIN', '3');

-- INSERT INTO member (member_no,  email, address, member_id , pwd, tel_no, name, activated) values(1, 'admin@test.com', '서울 중구 1길 1', 'admin', '$2a$10$.jeb0/BDVYk6LyEQtnASheopM46DheYDXdZBH6suI0lmNJWbYC8RK', '010-1234-5678', '어드민', TRUE);
-- INSERT INTO member (member_no,  email, address, member_id , pwd, tel_no, name, activated) values(2, 'manager@test.com', '서울 중구 2길 2', 'manager', '$2a$10$3YurJkDY9VjrDWXpmBISEe2Aq27FciDdBGa9SdKE7315/djVLF.HG', '010-1234-6789', '매니저', TRUE);
-- INSERT INTO member (member_no,  email, address, member_id , pwd, tel_no, name, activated) values(3, 'user1@test.com', '서울 중구 3길 3', 'user1', '$2a$10$QtZBHxA.LVPJtP7ScjMad.5z3StuYDO5Ff9yu7zfXtWl7W3mJPrti', '010-1234-0987', '테스트유저', TRUE);
-- INSERT INTO member (member_no,  email, address, member_id , pwd, tel_no, name, activated) values(4, 'winter123@test.com', '서울 중구 4길 4', 'winter123', '$2a$10$tjagZ3HQqgOUU75UI.heQ.RAWHOClKl5C4014j3pA6GMqVIC91NJS', '010-1234-6455', '김윈터', TRUE);
-- INSERT INTO member (member_no,  email, address, member_id , pwd, tel_no, name, activated) values(5, 'xodlf5363@test.com', '서울 중구 5길 5', 'xodlf5363', '$2a$10$iwndSZG.Dd.QsEDH7iKSTOxapMrKRwp7sTLbU1cOzTko3S7g.FEnW', '010-4321-1234', '김테스트', TRUE);



-- INSERT INTO user_role (member_no, authority, created_date, modified_date) values (1, 'ROLE_ADMIN', '2023-01-01 15:12:03', '2023-01-01 15:12:03');
-- INSERT INTO user_role (member_no, authority, created_date, modified_date) values (2, 'ROLE_MANAGER', '2023-01-01 15:12:03', '2023-01-01 15:12:03');
-- INSERT INTO user_role (member_no, authority, created_date, modified_date) values (3, 'ROLE_USER', '2023-01-01 15:12:03', '2023-01-01 15:12:03');
-- INSERT INTO user_role (member_no, authority, created_date, modified_date) values (4, 'ROLE_USER', '2023-01-01 15:12:03', '2023-01-01 15:12:03');
-- INSERT INTO user_role (member_no, authority, created_date, modified_date) values (5, 'ROLE_USER', '2023-01-01 15:12:03', '2023-01-01 15:12:03');