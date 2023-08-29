INSERT INTO users (user_id, name, email, username, password) VALUES (1, 'John Doe', 'johndoe@mail.com','johndoe', '$2a$10$Pp9PfeJ8QqdXvZZhf7vbxuZUAWQF8m.HbjzB7MWUvu4bo.E.JGfdK'); --pwd = 12345
INSERT INTO users (user_id, name, email, username, password) VALUES (2, 'Jane Doe', 'janedoe@mail.com','janedoe', '$2a$10$Pp9PfeJ8QqdXvZZhf7vbxuZUAWQF8m.HbjzB7MWUvu4bo.E.JGfdK'); --pwd = 12345

INSERT INTO roles (id, user_id, name) VALUES (1,1,'ROLE_USER');
INSERT INTO roles (id, user_id, name) VALUES (2,2,'ROLE_USER');

INSERT INTO tasks (task_id, name, created_at, status, user_id, active) VALUES (1, 'task test joe', '2007-12-03T10:15:30', 0, 1, true);
INSERT INTO tasks (task_id, name, created_at, status, user_id, active) VALUES (2, 'task test jane', '2007-12-03T10:15:30', 0, 2, true);