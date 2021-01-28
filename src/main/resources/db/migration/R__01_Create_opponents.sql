CREATE TABLE IF NOT EXISTS `opponents` (
`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` varchar(50),
`heightInInches` int,
`weightInLbs` int,
`beltRankId` int
)