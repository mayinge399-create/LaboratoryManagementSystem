-- device borrow table
DROP TABLE IF EXISTS `shiyanshebei_borrow`;
CREATE TABLE IF NOT EXISTS `shiyanshebei_borrow` (
  `id` bigint NOT NULL COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `shiyanshebei_id` bigint DEFAULT NULL COMMENT '设备ID',
  `shebeibianhao` varchar(200) DEFAULT NULL COMMENT '设备编号',
  `shebeimingcheng` varchar(200) DEFAULT NULL COMMENT '设备名称',
  `yuyuefangshi` varchar(200) DEFAULT NULL COMMENT '预约方式',
  `yuyueren` varchar(200) DEFAULT NULL COMMENT '预约人',
  `yuyueshijian` varchar(200) DEFAULT NULL COMMENT '预约时间',
  `guihaishijian` varchar(200) DEFAULT NULL COMMENT '归还时间',
  `yuyuezhuangtai` varchar(200) DEFAULT '借出中' COMMENT '预约状态',
  `xuehao` varchar(200) DEFAULT NULL COMMENT '学号',
  `xueshengxingming` varchar(200) DEFAULT NULL COMMENT '学生姓名',
  `banji` varchar(200) DEFAULT NULL COMMENT '班级',
  `shouji` varchar(200) DEFAULT NULL COMMENT '手机',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='实验设备借阅';
