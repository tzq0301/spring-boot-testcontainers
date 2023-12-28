DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL DEFAULT '',
    `sex`  TINYINT      NOT NULL DEFAULT 0
);