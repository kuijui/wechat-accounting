-- 微信记账小程序数据库
CREATE DATABASE IF NOT EXISTS wechat_accounting DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE wechat_accounting;

-- 用户表
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` varchar(64) NOT NULL COMMENT '微信openid',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `gender` tinyint DEFAULT '0' COMMENT '性别：0-未知，1-男，2-女',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_openid` (`openid`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 分类表
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(20) NOT NULL COMMENT '分类名称',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `color` varchar(20) DEFAULT NULL COMMENT '颜色',
  `type` tinyint NOT NULL COMMENT '类型：1-收入，2-支出',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `is_system` tinyint DEFAULT '1' COMMENT '是否系统预设：0-用户自定义，1-系统预设',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID（用户自定义分类时不为空）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表';

-- 账单表
CREATE TABLE `bill` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '账单ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `type` tinyint NOT NULL COMMENT '类型：1-收入，2-支出',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `bill_date` date NOT NULL COMMENT '账单日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_bill_date` (`bill_date`),
  KEY `idx_type` (`type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账单表';

-- 预算表
CREATE TABLE `budget` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '预算ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `category_id` bigint DEFAULT NULL COMMENT '分类ID（为空表示总预算）',
  `amount` decimal(10,2) NOT NULL COMMENT '预算金额',
  `year` int NOT NULL COMMENT '年份',
  `month` int NOT NULL COMMENT '月份',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_category_month` (`user_id`,`category_id`,`year`,`month`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_year_month` (`year`,`month`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预算表';

-- 插入系统预设分类数据
INSERT INTO `category` (`name`, `icon`, `color`, `type`, `sort_order`, `is_system`) VALUES
-- 收入分类
('工资', 'salary', '#4CAF50', 1, 1, 1),
('奖金', 'bonus', '#8BC34A', 1, 2, 1),
('投资收益', 'investment', '#CDDC39', 1, 3, 1),
('兼职', 'part-time', '#FFEB3B', 1, 4, 1),
('其他收入', 'other-income', '#FFC107', 1, 5, 1),

-- 支出分类
('餐饮', 'food', '#FF5722', 2, 1, 1),
('交通', 'transport', '#FF9800', 2, 2, 1),
('购物', 'shopping', '#F44336', 2, 3, 1),
('娱乐', 'entertainment', '#E91E63', 2, 4, 1),
('医疗', 'medical', '#9C27B0', 2, 5, 1),
('教育', 'education', '#673AB7', 2, 6, 1),
('住房', 'housing', '#3F51B5', 2, 7, 1),
('通讯', 'communication', '#2196F3', 2, 8, 1),
('其他支出', 'other-expense', '#607D8B', 2, 9, 1);