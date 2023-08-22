INSERT INTO users (user_id, name, email, username, password) VALUES (1, 'Jonh Doe', 'johndoe@mail.com','johndoe', '$2a$10$Pp9PfeJ8QqdXvZZhf7vbxuZUAWQF8m.HbjzB7MWUvu4bo.E.JGfdK'); --pwd = 12345

INSERT INTO roles (id, user_id, name) VALUES (1,1,'ROLE_USER');

INSERT INTO tasks (task_id, name, created_at, status, user_id) VALUES (1, 'task test', '2007-12-03T10:15:30', 0, 1)