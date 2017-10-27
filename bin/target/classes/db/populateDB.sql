--DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 1000;

-- user
INSERT INTO users (name, surname, email, password, role)
VALUES ('User', 'Useres', 'user@gmail.com', 'user', 'ROLE_USER');

-- admin
INSERT INTO users (name, surname, email, password, role)
VALUES ('Admin', 'Adminers', 'admin@gmail.com', 'admin', 'ROLE_ADMIN');

--INSERT INTO user_roles (role, user_id) VALUES
--  ('ROLE_USER', 1000),
--  ('ROLE_ADMIN', 1001);