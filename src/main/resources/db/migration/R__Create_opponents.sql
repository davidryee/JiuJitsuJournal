CREATE TABLE IF NOT EXISTS `opponents` (
`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` varchar(50),
`heightInInches` int,
`weightInLbs` int,
`beltRankId` int
)