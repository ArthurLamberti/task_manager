INSERT INTO users (user_id, name, email, username, password) VALUES (1, 'Jonh Doe', 'johndoe@mail.com','johndoe', '$2a$10$Pp9PfeJ8QqdXvZZhf7vbxuZUAWQF8m.HbjzB7MWUvu4bo.E.JGfdK'); --pwd = 12345

INSERT INTO roles (id, user_id, name) VALUES (1,1,'ROLE_USER');