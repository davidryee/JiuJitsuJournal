CREATE TABLE IF NOT EXISTS `user_roles` (
`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
`user_id` int NOT NULL,
`role_id` int NOT NULL,
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (role_id) REFERENCES roles(id)
)