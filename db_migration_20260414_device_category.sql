CREATE TABLE IF NOT EXISTS `shiyanshebei_category` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `shiyanshebei_id` bigint(20) NOT NULL COMMENT '设备ID',
  `category` varchar(200) NOT NULL COMMENT '分类名称',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_shiyanshebei_id` (`shiyanshebei_id`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备-分类关联表';

INSERT INTO `shiyanshebei_category` (`id`, `shiyanshebei_id`, `category`, `addtime`)
SELECT
  CAST(CONCAT(s.id, '01') AS UNSIGNED) AS id,
  s.id,
  CASE
    WHEN s.category IS NULL OR s.category = '' THEN '地质与资源类'
    ELSE SUBSTRING_INDEX(s.category, ',', 1)
  END AS category,
  NOW()
FROM `shiyanshebei` s
WHERE NOT EXISTS (
  SELECT 1 FROM `shiyanshebei_category` c WHERE c.shiyanshebei_id = s.id
);
