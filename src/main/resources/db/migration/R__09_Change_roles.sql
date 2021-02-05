DELETE FROM `roles`
WHERE id = 1 OR id = 2;


INSERT INTO `roles` (name)
VALUES
        ("ROLE_USER"),
        ("ROLE_ADMIN")