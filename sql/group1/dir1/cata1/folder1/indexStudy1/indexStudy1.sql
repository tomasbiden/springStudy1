CREATE TABLE `user1` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                        `zipcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                        `birthdate` date NOT NULL,
                        PRIMARY KEY (`id`),
                        KEY `idx_username_birthdate` (`zipcode`,`birthdate`) ) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4;

# 查询 zipcode 为 431200 且生日在 3 月的用户
# birthdate 字段使用函数索引失效
explain
# SELECT * FROM user1 WHERE zipcode = '431200' AND MONTH(birthdate) = 3;

SELECT * FROM user1 WHERE zipcode = '431200'
AND birthdate = 1535265;