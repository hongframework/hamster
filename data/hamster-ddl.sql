

/*Table structure for table `cfg_broker` */



CREATE TABLE `cfg_broker` (
  `cfg_broker_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '消息队列id',
  `cfg_broker_name` varchar(128) DEFAULT NULL COMMENT '消息队列名称',
  `cfg_broker_code` varchar(64) DEFAULT NULL COMMENT '消息队列编码',
  `cfg_broker_type` tinyint(4) DEFAULT NULL COMMENT '消息队列类型',
  `addr_list` varchar(64) DEFAULT NULL COMMENT '地址列表',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `zk_addr_list` varchar(64) DEFAULT NULL COMMENT 'zk地址列表',
  PRIMARY KEY (`cfg_broker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='消息队列';



/*Table structure for table `cfg_datasource` */



CREATE TABLE `cfg_datasource` (
  `cfg_datasource_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '数据源id',
  `cfg_datasource_type` tinyint(4) DEFAULT NULL COMMENT '数据库类型',
  `url` varchar(64) DEFAULT NULL COMMENT 'URL',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `db` varchar(64) DEFAULT NULL COMMENT '数据库名称',
  PRIMARY KEY (`cfg_datasource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='数据源';



/*Table structure for table `cfg_datatable` */



CREATE TABLE `cfg_datatable` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `cfg_datasource_id` bigint(20) NOT NULL,
  `template_table_name` varchar(64) NOT NULL,
  `sub_table_name` varchar(64) NOT NULL,
  `create_sql` varchar(512) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='数据表';



/*Table structure for table `cfg_dataview` */



CREATE TABLE `cfg_dataview` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '数据视图id,ID',
  `view_name` varchar(128) NOT NULL COMMENT '视图名称',
  `cfg_datasource_id` bigint(12) NOT NULL COMMENT '数据源id,ID',
  `view_sql` varchar(1024) NOT NULL COMMENT '视图SQL',
  `main_tables` varchar(64) NOT NULL COMMENT '视图主表',
  `tables` varchar(64) NOT NULL COMMENT '视图关联表',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='数据视图';



/*Table structure for table `cfg_deployment` */



CREATE TABLE `cfg_deployment` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '发布id,ID',
  `code` varchar(64) NOT NULL COMMENT '发布编码,编码',
  `name` varchar(128) NOT NULL COMMENT '发布名称,名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '发布类型,类型',
  `datasource_id` bigint(12) NOT NULL COMMENT '数据源id,ID',
  `log_begin_position` varchar(128) DEFAULT NULL COMMENT '日志开始位置',
  `log_end_position` varchar(128) DEFAULT NULL COMMENT '日志结束位置',
  `log_begin_timestamp` datetime DEFAULT NULL COMMENT '日志开始时间',
  `log_end_timestamp` datetime DEFAULT NULL COMMENT '日志结束位置',
  `labels` varchar(128) NOT NULL COMMENT '主标签',
  `second_labels` varchar(128) DEFAULT NULL COMMENT '次标签',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deploy_json` varchar(512) DEFAULT NULL COMMENT '过滤JSON,',
  `alarm_strategy` varchar(512) DEFAULT NULL COMMENT '报警策略',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='发布';



/*Table structure for table `cfg_deployment_detail` */



CREATE TABLE `cfg_deployment_detail` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '发布明细id,ID',
  `job_template_id` bigint(12) NOT NULL COMMENT '任务模板定义id,ID',
  `job_id` bigint(12) NOT NULL COMMENT '任务定义id,ID',
  `node_template_id` bigint(12) DEFAULT NULL COMMENT '动态节点定义id,ID',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `deployment_id` bigint(12) NOT NULL COMMENT '发布id,ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='发布明细';



/*Table structure for table `cfg_job` */



CREATE TABLE `cfg_job` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务定义id,ID',
  `job_template_id` bigint(12) NOT NULL COMMENT '任务模板定义id,ID',
  `name` varchar(128) NOT NULL COMMENT '任务定义名称,名称',
  `code` varchar(64) NOT NULL COMMENT '任务定义编码,编码',
  `description` varchar(128) DEFAULT NULL COMMENT '任务定义描述,描述',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1137 DEFAULT CHARSET=utf8 COMMENT='任务定义';



/*Table structure for table `cfg_job_attr` */



CREATE TABLE `cfg_job_attr` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务属性定义id,ID',
  `job_id` bigint(12) NOT NULL COMMENT '任务定义id,ID',
  `attr_id` bigint(12) DEFAULT NULL COMMENT '动态节点属性定义id,ID',
  `attr_code` varchar(128) NOT NULL COMMENT '动态节点属性编码',
  `attr_val` varchar(1024) DEFAULT NULL COMMENT '属性值',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7370 DEFAULT CHARSET=utf8 COMMENT='任务属性定义';



/*Table structure for table `cfg_job_template_def` */



CREATE TABLE `cfg_job_template_def` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务模板定义id,ID',
  `code` varchar(64) NOT NULL COMMENT '任务模板定义编码,编码',
  `name` varchar(128) NOT NULL COMMENT '任务模板定义名称,名称',
  `description` varchar(128) DEFAULT NULL COMMENT '任务模板定义描述,描述',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='任务模板定义';



/*Table structure for table `cfg_job_template_node_def` */



CREATE TABLE `cfg_job_template_node_def` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务模板节点定义id,ID',
  `job_template_id` bigint(12) NOT NULL COMMENT '任务模板定义id,ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mutix` tinyint(4) NOT NULL COMMENT '是否多个',
  `node_id` bigint(12) NOT NULL COMMENT '动态节点定义id,ID',
  `execute_order` tinyint(4) DEFAULT NULL COMMENT '调度顺序',
  `pri` bigint(12) DEFAULT NULL COMMENT '优先级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='任务模板节点定义';



/*Table structure for table `cfg_label` */



CREATE TABLE `cfg_label` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '标签id,ID',
  `code` varchar(64) NOT NULL COMMENT '标签编码,编码',
  `description` varchar(128) DEFAULT NULL COMMENT '标签描述,描述',
  `type` tinyint(4) DEFAULT NULL COMMENT '标签类型,类型',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='标签';



/*Table structure for table `cfg_node` */



CREATE TABLE `cfg_node` (
  `cfg_node_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '节点id',
  `cfg_node_code` varchar(64) DEFAULT NULL COMMENT '节点编码',
  `cfg_node_name` varchar(128) DEFAULT NULL COMMENT '节点名称',
  `ip` varchar(64) DEFAULT NULL COMMENT '节点IP',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `labels` varchar(12) NOT NULL COMMENT '标签id,ID',
  PRIMARY KEY (`cfg_node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='节点';



/*Table structure for table `cfg_node_attr_def` */



CREATE TABLE `cfg_node_attr_def` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '动态节点属性定义id,ID',
  `node_id` bigint(12) NOT NULL COMMENT '动态节点定义id,ID',
  `code` varchar(64) NOT NULL COMMENT '动态节点属性定义编码,编码',
  `name` varchar(128) NOT NULL COMMENT '动态节点属性定义名称,名称',
  `description` varchar(512) NOT NULL COMMENT '动态节点属性定义描述,描述',
  `type` varchar(128) NOT NULL COMMENT '动态节点属性定义类型,类型',
  `necessary` tinyint(4) NOT NULL COMMENT '必须属性',
  `relat_attr` varchar(128) DEFAULT NULL COMMENT '关联属性',
  `relat_dict` varchar(128) DEFAULT NULL COMMENT '关联字典',
  `def_val` varchar(128) DEFAULT NULL COMMENT '默认值',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='动态节点属性定义';



/*Table structure for table `cfg_node_def` */



CREATE TABLE `cfg_node_def` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '动态节点定义id,ID',
  `type` varchar(10) NOT NULL COMMENT '动态节点定义类型,类型',
  `datasource_type` varchar(10) DEFAULT NULL COMMENT '数据源类型',
  `name` varchar(128) NOT NULL COMMENT '动态节点定义名称,名称',
  `code` varchar(64) NOT NULL COMMENT '动态节点定义编码,编码',
  `description` varchar(128) DEFAULT NULL COMMENT '动态节点定义描述,描述',
  `execute_method` varchar(10) NOT NULL COMMENT '运行方式',
  `execute_uri` varchar(512) NOT NULL COMMENT '运行类',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='动态节点定义';



/*Table structure for table `cfg_node_task_relat` */



CREATE TABLE `cfg_node_task_relat` (
  `cfg_node_task_relat_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务节点关系id',
  `cfg_node_id` bigint(12) DEFAULT NULL COMMENT '节点id',
  `cfg_task_inst_id` bigint(12) DEFAULT NULL COMMENT '任务实例id',
  `is_main_node` tinyint(4) DEFAULT NULL COMMENT '是否主机',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cfg_node_task_relat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8 COMMENT='任务节点部署';



/*Table structure for table `cfg_statistics` */



CREATE TABLE `cfg_statistics` (
  `cfg_statistics_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '流量统计id',
  `cfg_statistics_name` varchar(128) DEFAULT NULL COMMENT '流量统计名称',
  `cfg_datasource_id` bigint(12) DEFAULT NULL COMMENT '数据源id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `log_begin_file` varchar(64) DEFAULT NULL COMMENT '日志起始文件',
  `log_begin_position` bigint(20) DEFAULT NULL COMMENT '日志起始位置',
  `log_begin_timestamp` datetime DEFAULT NULL COMMENT '日志起始时间',
  `log_end_file` varchar(64) DEFAULT NULL COMMENT '日志终止文件',
  `log_end_position` bigint(20) DEFAULT NULL COMMENT '日志终止位置',
  `log_end_timestamp` datetime DEFAULT NULL COMMENT '日志终止时间',
  PRIMARY KEY (`cfg_statistics_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='流量统计';



/*Table structure for table `cfg_statistics_detail` */



CREATE TABLE `cfg_statistics_detail` (
  `cfg_statistics_detail_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '流量统计明细id',
  `cfg_statistics_id` bigint(12) DEFAULT NULL COMMENT '流量统计id',
  `db_object_name` varchar(64) DEFAULT NULL COMMENT '数据对象名称',
  `db_object_operate_type` tinyint(4) DEFAULT NULL COMMENT '数据操作类型',
  `data_filter_expression` varchar(512) DEFAULT NULL COMMENT '数据过滤表达式',
  `cfg_statistics_topic_id` bigint(12) DEFAULT NULL COMMENT '统计主题id',
  `statistics_view` varchar(64) DEFAULT NULL COMMENT '统计维度',
  `statistics_value` varchar(512) DEFAULT NULL COMMENT '统计值',
  `count` tinyint(4) DEFAULT NULL COMMENT '是否计数',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `statistics_script` text COMMENT '统计脚本',
  PRIMARY KEY (`cfg_statistics_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='流量统计明细';



/*Table structure for table `cfg_statistics_topic` */



CREATE TABLE `cfg_statistics_topic` (
  `cfg_statistics_topic_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '统计主题id',
  `cfg_statistics_topic_code` varchar(64) DEFAULT NULL COMMENT '统计主题编码',
  `cfg_statistics_topic_name` varchar(128) DEFAULT NULL COMMENT '统计主题名称',
  `cfg_statistics_topic_desc` varchar(128) DEFAULT NULL COMMENT '统计主题描述',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cfg_statistics_topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='统计主题';



/*Table structure for table `cfg_subscribe` */



CREATE TABLE `cfg_subscribe` (
  `cfg_subscribe_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '订阅id',
  `type` smallint(6) DEFAULT NULL COMMENT '订阅类型',
  `cfg_datasource_id` bigint(12) DEFAULT NULL COMMENT '数据源id',
  `db_object_name` varchar(64) DEFAULT NULL COMMENT '数据对象名称',
  `data_filter_expression` varchar(1000) DEFAULT NULL COMMENT '数据过滤表达式',
  `cfg_topic_id` bigint(12) DEFAULT NULL COMMENT '主题id',
  `partition_strategy` tinyint(4) DEFAULT NULL COMMENT '分区策略',
  `partition_key` varchar(64) DEFAULT NULL COMMENT '分区属性',
  `log_begin_position` bigint(20) DEFAULT NULL COMMENT '日志起始位置',
  `log_begin_timestamp` datetime DEFAULT NULL COMMENT '日志起始时间',
  `log_end_position` bigint(20) DEFAULT NULL COMMENT '日志终止位置',
  `log_end_timestamp` datetime DEFAULT NULL COMMENT '日志终止时间',
  `log_current_position` bigint(20) DEFAULT NULL COMMENT '当前日志执行位置',
  `cfg_subscribe_name` varchar(128) DEFAULT NULL COMMENT '订阅名称',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `log_begin_file` varchar(64) DEFAULT NULL COMMENT '日志起始文件',
  `log_end_file` varchar(64) DEFAULT NULL COMMENT '日志终止文件',
  `cfg_broker_id` bigint(12) DEFAULT NULL COMMENT '消息队列id',
  `db_object_operate_type` tinyint(4) DEFAULT NULL COMMENT '数据操作类型',
  PRIMARY KEY (`cfg_subscribe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='订阅';



/*Table structure for table `cfg_subscribe_data` */



CREATE TABLE `cfg_subscribe_data` (
  `cfg_subscribe_data_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '订阅数据id',
  `cfg_subscribe_data_type` tinyint(4) DEFAULT NULL COMMENT '订阅数据类型',
  `cfg_subscribe_data_code` varchar(64) DEFAULT NULL COMMENT '订阅数据编码',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `contain_change_before_value` tinyint(4) DEFAULT NULL COMMENT '订阅变更前数据',
  `cfg_subscribe_id` bigint(12) DEFAULT NULL COMMENT '订阅id',
  PRIMARY KEY (`cfg_subscribe_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=304 DEFAULT CHARSET=utf8 COMMENT='订阅数据';



/*Table structure for table `cfg_subscribe_detail` */



CREATE TABLE `cfg_subscribe_detail` (
  `cfg_subscribe_detail_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '数据订阅明细id',
  `cfg_subscribe_id` bigint(12) DEFAULT NULL COMMENT '订阅id',
  `db_object_name` varchar(64) DEFAULT NULL COMMENT '数据对象名称',
  `cfg_topic_id` bigint(12) DEFAULT NULL COMMENT '主题id',
  `db_object_datas` varchar(1024) DEFAULT NULL COMMENT '数据对象数据',
  `partition_strategy` tinyint(4) DEFAULT NULL COMMENT '分区策略',
  `partition_key` varchar(64) DEFAULT NULL COMMENT '分区属性',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `data_filter_expression` varchar(512) DEFAULT NULL COMMENT '数据过滤表达式',
  `db_object_operate_type` tinyint(4) DEFAULT NULL COMMENT '数据操作类型',
  PRIMARY KEY (`cfg_subscribe_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='数据订阅明细';



/*Table structure for table `cfg_task_def` */



CREATE TABLE `cfg_task_def` (
  `cfg_task_def_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务定义id',
  `cfg_task_def_code` varchar(64) DEFAULT NULL COMMENT '任务定义编码',
  `cfg_task_def_name` varchar(128) DEFAULT NULL COMMENT '任务定义名称',
  `cfg_task_def_type` tinyint(4) DEFAULT NULL COMMENT '任务定义类型',
  `cfg_task_instance_type` tinyint(4) DEFAULT NULL COMMENT '任务实例类型',
  `param_name_1` varchar(64) DEFAULT NULL COMMENT '任务参数名称',
  `param_code_1` varchar(64) DEFAULT NULL COMMENT '任务参数编码',
  `param_name_2` varchar(64) DEFAULT NULL COMMENT '任务参数名称2',
  `param_code_2` varchar(64) DEFAULT NULL COMMENT '任务参数编码2',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cfg_task_def_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='任务定义';



/*Table structure for table `cfg_task_inst` */



CREATE TABLE `cfg_task_inst` (
  `cfg_task_inst_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务实例id',
  `cfg_task_inst_desc` varchar(128) DEFAULT NULL COMMENT '任务实例描述',
  `cfg_task_def_id` bigint(12) DEFAULT NULL COMMENT '任务定义id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `param_value_1` varchar(128) DEFAULT NULL COMMENT '任务参数值',
  `param_value_remark_1` varchar(128) DEFAULT NULL COMMENT '任务参数值描述',
  `param_value_2` varchar(128) DEFAULT NULL COMMENT '任务参数值2',
  `param_value_remark_2` varchar(128) DEFAULT NULL COMMENT '任务参2数值描述2',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cfg_task_inst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='任务实例';



/*Table structure for table `cfg_task_node_def` */



CREATE TABLE `cfg_task_node_def` (
  `cfg_task_node_def_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务节点定义id',
  `cfg_task_node_def_name` varchar(128) DEFAULT NULL COMMENT '任务节点定义名称',
  `cfg_task_node_def_code` varchar(64) DEFAULT NULL COMMENT '任务节点定义编码',
  `java_class` varchar(128) DEFAULT NULL COMMENT '任务节点实现类',
  `cfg_task_def_id` bigint(12) DEFAULT NULL COMMENT '任务定义id',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cfg_task_node_def_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='任务子节点定义';



/*Table structure for table `cfg_topic` */



CREATE TABLE `cfg_topic` (
  `cfg_topic_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主题id',
  `cfg_topic_name` varchar(128) DEFAULT NULL COMMENT '主题名称',
  `cfg_topic_desc` varchar(128) DEFAULT NULL COMMENT '主题描述',
  `cfg_topic_type` tinyint(4) DEFAULT NULL COMMENT '主题类型',
  `cfg_topic_code` varchar(64) DEFAULT NULL COMMENT '主题编码',
  `PARTITIONS` tinyint(4) DEFAULT NULL COMMENT '分区数',
  `replicas` tinyint(4) DEFAULT NULL COMMENT '副本数',
  `serial_no` smallint(4) DEFAULT NULL COMMENT '消息序列生成规则',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `cfg_broker_id` bigint(12) DEFAULT NULL COMMENT '消息队列id',
  PRIMARY KEY (`cfg_topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='主题';



/*Table structure for table `hfmd_enum` */



CREATE TABLE `hfmd_enum` (
  `hfmd_enum_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典项ID',
  `hfmd_enum_value` varchar(128) DEFAULT NULL COMMENT '字典项值',
  `hfmd_enum_text` varchar(32) DEFAULT NULL COMMENT '字典项文本',
  `hfmd_enum_desc` varchar(128) DEFAULT NULL COMMENT '字典项描述',
  `is_default` int(2) DEFAULT NULL COMMENT '是否默认',
  `pri` decimal(4,2) DEFAULT NULL COMMENT '优先级',
  `ext1` varchar(128) DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(128) DEFAULT NULL COMMENT '扩展字段2',
  `hfmd_enum_class_id` bigint(20) DEFAULT NULL COMMENT '字典ID',
  `hfmd_enum_class_code` varchar(32) DEFAULT NULL,
  `op_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_op_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`hfmd_enum_id`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8 COMMENT='字典项';



/*Table structure for table `hfmd_enum_class` */



CREATE TABLE `hfmd_enum_class` (
  `hfmd_enum_class_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `hfmd_enum_class_name` varchar(32) DEFAULT NULL COMMENT '字典名称',
  `hfmd_enum_class_code` varchar(64) DEFAULT NULL COMMENT '字典编码',
  `hfmd_enum_class_desc` varchar(128) DEFAULT NULL COMMENT '字典描述',
  `ext1` varchar(128) DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(128) DEFAULT NULL COMMENT '扩展字段2',
  `op_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_op_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`hfmd_enum_class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8 COMMENT='字典';



/*Table structure for table `hfsec_menu` */



CREATE TABLE `hfsec_menu` (
  `hfsec_menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `hfsec_menu_code` varchar(64) DEFAULT NULL COMMENT '菜单编码',
  `hfsec_menu_name` varchar(128) DEFAULT NULL COMMENT '菜单名称',
  `hfsec_menu_desc` varchar(128) DEFAULT NULL COMMENT '菜单描述',
  `menu_level` int(2) DEFAULT NULL COMMENT '菜单级别',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `url` varchar(128) DEFAULT NULL COMMENT '地址',
  `parent_hfsec_menu_id` bigint(20) DEFAULT NULL COMMENT '父级菜单ID',
  `pri` decimal(4,2) DEFAULT NULL COMMENT '优先级',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`hfsec_menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='菜单';



/*Table structure for table `hfsec_organize` */



CREATE TABLE `hfsec_organize` (
  `hfsec_organize_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '组织id',
  `hfsec_organize_code` varchar(64) DEFAULT NULL COMMENT '组织编码',
  `hfsec_organize_name` varchar(128) DEFAULT NULL COMMENT '组织名称',
  `hfsec_organize_type` tinyint(4) DEFAULT NULL COMMENT '组织类型',
  `hfsec_organize_level` tinyint(4) DEFAULT NULL COMMENT '组织级别',
  `parent_hfsec_organize_id` bigint(12) DEFAULT NULL COMMENT '上级组织id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`hfsec_organize_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='组织';



/*Table structure for table `hfsec_role` */



CREATE TABLE `hfsec_role` (
  `hfsec_role_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `hfsec_role_code` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `hfsec_role_name` varchar(128) DEFAULT NULL COMMENT '角色名称',
  `hfsec_role_type` tinyint(4) DEFAULT NULL COMMENT '角色类型',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`hfsec_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色';



/*Table structure for table `hfsec_role_authorize` */



CREATE TABLE `hfsec_role_authorize` (
  `hfsec_role_authorize_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '角色授权id',
  `hfsec_role_authorize_type` tinyint(4) DEFAULT NULL COMMENT '角色授权类型',
  `hfsec_role_id` bigint(12) DEFAULT NULL COMMENT '角色id',
  `hfsec_menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`hfsec_role_authorize_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='角色授权';



/*Table structure for table `hfsec_user` */



CREATE TABLE `hfsec_user` (
  `hfsec_user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `hfsec_user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `account` varchar(64) DEFAULT NULL COMMENT '用户账号',
  `password` varchar(128) DEFAULT NULL COMMENT '用户密码',
  `gender` int(2) DEFAULT NULL COMMENT '性别',
  `mobile` varchar(6) DEFAULT NULL COMMENT '手机号',
  `email` int(2) DEFAULT NULL COMMENT '邮箱',
  `addr` int(2) DEFAULT NULL COMMENT '地址',
  `avatar` varchar(512) DEFAULT NULL COMMENT '头像',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `hfuc_org_id` bigint(20) DEFAULT NULL COMMENT '归属组织ID',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  `hfsec_organize_id` bigint(20) DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`hfsec_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户';



/*Table structure for table `hfsec_user_authorize` */



CREATE TABLE `hfsec_user_authorize` (
  `hfsec_user_authorize_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '用户授权id',
  `hfsec_user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `hfsec_organize_id` bigint(12) DEFAULT NULL COMMENT '组织id',
  `hfsec_role_id` bigint(12) DEFAULT NULL COMMENT '角色id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`hfsec_user_authorize_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户授权';



/*Table structure for table `job_exe_meta` */



CREATE TABLE `job_exe_meta` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务调度元数据id,ID',
  `deploy` varchar(32) NOT NULL COMMENT '发布信息,deploy',
  `datastore` varchar(64) NOT NULL COMMENT '数据源',
  `source_table` varchar(20) NOT NULL COMMENT '源表',
  `target_table` varchar(20) NOT NULL COMMENT '目标表',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `delay_time` bigint(20) NOT NULL COMMENT '任务延迟时长,任务延迟时间',
  `total_delay` bigint(20) DEFAULT NULL COMMENT '累计延迟时长,累计延迟',
  `execute_time` datetime NOT NULL COMMENT '累计执行时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_node` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_pair` (`deploy`,`datastore`,`source_table`,`target_table`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务调度元数据';



/*Table structure for table `log_deploy` */



CREATE TABLE `log_deploy` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '发布日志id,ID',
  `deploy_id` bigint(12) NOT NULL COMMENT '发布id,ID',
  `start_time` datetime DEFAULT NULL COMMENT '启动时间',
  `execute_time` datetime DEFAULT NULL COMMENT '执行时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `start_info` varchar(512) DEFAULT NULL COMMENT '启动信息',
  `end_info` varchar(512) DEFAULT NULL COMMENT '结束信息',
  `error_info` varchar(512) DEFAULT NULL COMMENT '异常信息',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `select_key` varchar(128) NOT NULL COMMENT '选择器',
  `deploy_code` varchar(128) NOT NULL COMMENT '发布编码,',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=472 DEFAULT CHARSET=utf8 COMMENT='发布日志';

