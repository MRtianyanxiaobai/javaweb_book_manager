USE book;
SELECT * FROM t_user;

CREATE TABLE t_book(
	`id` INT(11) PRIMARY KEY AUTO_INCREMENT, 	## 主键
	`name` VARCHAR(50) NOT NULL,				## 书名 
	`author` VARCHAR(50) NOT NULL,				## 作者
	`price` DECIMAL(11,2) NOT NULL,				## 价格
	`sales` INT(11) NOT NULL,					## 销量
	`stock` INT(11) NOT NULL,					## 库存
	`img_path` VARCHAR(200) NOT NULL			## 书的图片路径
)
