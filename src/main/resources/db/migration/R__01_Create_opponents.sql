CREATE TABLE IF NOT EXISTS `opponents` (
`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` varchar(50),
`height_in_inches` int,
`weight_in_lbs` int,
`belt_rank_id` int
)