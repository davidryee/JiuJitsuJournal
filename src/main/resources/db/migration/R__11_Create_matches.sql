CREATE TABLE IF NOT EXISTS `matches` (
`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
`match_date` DATE,
`user_id` int,
`opponent_id` int,
`description` VARCHAR(3000),
FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ,
FOREIGN KEY (`opponent_id`) REFERENCES `opponents`(`id`)
)