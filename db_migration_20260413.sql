ALTER TABLE `shiyanshebei`
ADD COLUMN `category` VARCHAR(200) DEFAULT '其他' COMMENT '分类' AFTER `danwei`;

UPDATE `shiyanshebei`
SET `category` = '其他'
WHERE `category` IS NULL OR `category` = '';

DROP TABLE IF EXISTS `caigoujilu`;
