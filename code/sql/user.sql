DROP DATABASE IF EXISTS book;

CREATE DATABASE book;

USE book;
CREATE TABLE t_user(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`username` VARCHAR(20) NOT n`book`ull UNIQUE,
`password` VARCHAR(32) NOT NULL,
`email` VARCHAR(200)
);

INSERT INTO t_user(`username`,`password`,`email`) VALUES('admin','admin','admin@atguigu.com');

SELECT * FROM t_user;