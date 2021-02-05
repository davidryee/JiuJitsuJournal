CREATE TABLE IF NOT EXISTS `users` (
`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
`username` varchar(20),
`email` varchar(50),
`password` varchar(120)
)