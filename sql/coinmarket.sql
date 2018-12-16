/*
Navicat MySQL Data Transfer

Source Server         : 10.1.1.25
Source Server Version : 50722
Source Host           : 10.1.1.25:3306
Source Database       : coinmarket

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-11-30 16:30:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for capital_daily
-- ----------------------------
DROP TABLE IF EXISTS `capital_daily`;
CREATE TABLE `capital_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` decimal(16,2) DEFAULT NULL COMMENT '每日总资产',
  `floating_funds` decimal(16,2) NOT NULL COMMENT '流动资产',
  `lockrepo_funds` decimal(16,2) NOT NULL COMMENT '锁仓资产',
  `profits_funds` decimal(16,2) NOT NULL COMMENT '收益资产',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of capital_daily
-- ----------------------------

-- ----------------------------
-- Table structure for capital_total
-- ----------------------------
DROP TABLE IF EXISTS `capital_total`;
CREATE TABLE `capital_total` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` decimal(16,2) DEFAULT NULL COMMENT '总资产',
  `floating_funds` decimal(16,2) NOT NULL COMMENT '流动资产',
  `lockrepo_funds` decimal(16,2) NOT NULL COMMENT '锁仓资产',
  `profits_funds` decimal(16,2) NOT NULL COMMENT '收益资产',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of capital_total
-- ----------------------------

-- ----------------------------
-- Table structure for consumer
-- ----------------------------
DROP TABLE IF EXISTS `consumer`;
CREATE TABLE `consumer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `team_pos_code` varchar(4) DEFAULT NULL COMMENT '节点码',
  `account` varchar(32) DEFAULT NULL COMMENT '用户名',
  `pass_word` varchar(64) DEFAULT NULL COMMENT '密码',
  `phone_no` varchar(20) DEFAULT NULL COMMENT '电话',
  `referee` int(11) DEFAULT NULL COMMENT '推荐人',
  `email` varchar(34) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `left_code` varchar(4) DEFAULT NULL COMMENT '左节点码',
  `right_code` varchar(4) DEFAULT NULL COMMENT '右节点码',
  `full_path` text COMMENT '所在团队路径',
  `path_direction` text COMMENT '路径方向',
  `isleaf` varchar(2) DEFAULT NULL COMMENT '是否为叶子节点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_capital_account
-- ----------------------------
DROP TABLE IF EXISTS `consumer_capital_account`;
CREATE TABLE `consumer_capital_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consumer_id` int(11) NOT NULL COMMENT '用户ID',
  `team_pos_code` varchar(4) NOT NULL COMMENT '节点编码',
  `floating_funds` decimal(16,2) NOT NULL COMMENT '流动资产',
  `lockrepo_funds` decimal(16,2) NOT NULL COMMENT '锁仓资产',
  `profits_funds` decimal(16,2) NOT NULL COMMENT '收益资产',
  `accumulated_profits` decimal(16,2) DEFAULT NULL COMMENT '累计收益',
  `profits_today` decimal(16,2) DEFAULT NULL COMMENT '今日收益',
  `floating_address` varchar(64) NOT NULL COMMENT '流动地址',
  `lockrepo_address` varchar(64) NOT NULL COMMENT '锁仓地址',
  `profits_address` varchar(64) NOT NULL COMMENT '收益地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_capital_account
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_capital_daily
-- ----------------------------
DROP TABLE IF EXISTS `consumer_capital_daily`;
CREATE TABLE `consumer_capital_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consumer_id` int(11) NOT NULL COMMENT '用户ID',
  `account_id` int(11) NOT NULL COMMENT '账户ID',
  `trance_type` varchar(2) DEFAULT NULL COMMENT '交易类型',
  `source_type` varchar(2) DEFAULT NULL COMMENT '具体交易类型',
  `daily_funds` decimal(16,2) DEFAULT NULL COMMENT '日总资金',
  `floating_funds` decimal(16,2) NOT NULL COMMENT '日流动资产',
  `lockrepo_funds` decimal(16,2) NOT NULL COMMENT '日锁仓资产',
  `profits_funds` decimal(16,2) NOT NULL COMMENT '日收益资产',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_capital_daily
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_capital_total
-- ----------------------------
DROP TABLE IF EXISTS `consumer_capital_total`;
CREATE TABLE `consumer_capital_total` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consumer_id` int(11) NOT NULL COMMENT '用户ID',
  `account_id` int(11) NOT NULL COMMENT '账户ID',
  `trance_type` varchar(2) DEFAULT NULL COMMENT '交易类型',
  `source_type` varchar(2) DEFAULT NULL COMMENT '具体交易类型',
  `total_funds` decimal(16,2) DEFAULT NULL COMMENT '总资产',
  `floating_funds` decimal(16,2) NOT NULL COMMENT '流动资产',
  `lockrepo_funds` decimal(16,2) NOT NULL COMMENT '锁仓资产',
  `profits_funds` decimal(16,2) DEFAULT NULL COMMENT '收益资产',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_capital_total
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_check_code
-- ----------------------------
DROP TABLE IF EXISTS `consumer_check_code`;
CREATE TABLE `consumer_check_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_no` varchar(11) NOT NULL,
  `check_code` varchar(8) NOT NULL,
  `invalid_flag` varchar(2) NOT NULL,
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_check_code
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_count_daily
-- ----------------------------
DROP TABLE IF EXISTS `consumer_count_daily`;
CREATE TABLE `consumer_count_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL COMMENT '当天用户量',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_count_daily
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_count_total
-- ----------------------------
DROP TABLE IF EXISTS `consumer_count_total`;
CREATE TABLE `consumer_count_total` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL COMMENT '总用户量',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_count_total
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_login_history
-- ----------------------------
DROP TABLE IF EXISTS `consumer_login_history`;
CREATE TABLE `consumer_login_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consumer_id` int(11) NOT NULL COMMENT '用户ID',
  `ip_address` varchar(64) DEFAULT NULL COMMENT 'ip地址',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_login_history
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_profits_daily
-- ----------------------------
DROP TABLE IF EXISTS `consumer_profits_daily`;
CREATE TABLE `consumer_profits_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profits_team` decimal(16,2) NOT NULL COMMENT '团队收益',
  `profits_referee` decimal(16,2) NOT NULL COMMENT '推荐收益',
  `profits_lockrepo` decimal(16,2) NOT NULL COMMENT '锁仓收益',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `consumer_id` int(11) DEFAULT NULL COMMENT '用户id',
  `account_id` int(11) DEFAULT NULL COMMENT '账户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_profits_daily
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_profits_total
-- ----------------------------
DROP TABLE IF EXISTS `consumer_profits_total`;
CREATE TABLE `consumer_profits_total` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profits_team` decimal(16,2) NOT NULL COMMENT '团队收益',
  `profits_referee` decimal(16,2) NOT NULL COMMENT '推荐收益',
  `profits_lockrepo` decimal(16,2) NOT NULL COMMENT '锁仓收益',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `consumer_id` int(11) NOT NULL COMMENT '用户ID',
  `account_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_profits_total
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_settings
-- ----------------------------
DROP TABLE IF EXISTS `consumer_settings`;
CREATE TABLE `consumer_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consumer_id` int(11) NOT NULL COMMENT '用户ID',
  `head_portrait` binary(1) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `auto_transfer` varchar(2) DEFAULT NULL COMMENT '是否自动释放锁仓资产',
  `capital_password` varchar(64) DEFAULT NULL COMMENT '资金密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_settings
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_team
-- ----------------------------
DROP TABLE IF EXISTS `consumer_team`;
CREATE TABLE `consumer_team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consumer_id` int(11) NOT NULL COMMENT '用户ID',
  `team_pos_code` varchar(4) NOT NULL COMMENT '节点编码',
  `left_total_member` int(16) NOT NULL COMMENT '左码总人数',
  `right_total_member` int(16) NOT NULL COMMENT '右码总人数',
  `left_profits_total` decimal(16,2) NOT NULL COMMENT '左团队收益资产',
  `left_lockrepo_total` decimal(16,2) NOT NULL COMMENT '左团队锁仓资产',
  `left_floating_total` decimal(16,2) NOT NULL COMMENT '左团队流资产',
  `right_profits_total` decimal(16,2) NOT NULL COMMENT '右团队收益资产',
  `right_lockrepo_total` decimal(16,2) NOT NULL COMMENT '右团队锁仓资产',
  `right_floating_total` decimal(16,2) NOT NULL COMMENT '右团队流资产',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_team
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_two_dimension_code
-- ----------------------------
DROP TABLE IF EXISTS `consumer_two_dimension_code`;
CREATE TABLE `consumer_two_dimension_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consumer_id` int(11) NOT NULL COMMENT '用户ID',
  `left_dimension` binary(1) DEFAULT NULL,
  `right_dimension` binary(1) DEFAULT NULL,
  `trandfer_dimesion` binary(1) DEFAULT NULL,
  `left_dimesion_code` varchar(50) NOT NULL COMMENT '用户左码',
  `right_dimension_code` varchar(50) NOT NULL COMMENT '用户右码',
  `transfer_dimension_code` varchar(50) DEFAULT NULL COMMENT '交易方编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_two_dimension_code
-- ----------------------------

-- ----------------------------
-- Table structure for fee_daily
-- ----------------------------
DROP TABLE IF EXISTS `fee_daily`;
CREATE TABLE `fee_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` decimal(16,2) NOT NULL COMMENT '每日手续费总量',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fee_daily
-- ----------------------------

-- ----------------------------
-- Table structure for fee_total
-- ----------------------------
DROP TABLE IF EXISTS `fee_total`;
CREATE TABLE `fee_total` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` decimal(16,2) NOT NULL COMMENT '手续费总量',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fee_total
-- ----------------------------

-- ----------------------------
-- Table structure for lockrepo_destroy_daily
-- ----------------------------
DROP TABLE IF EXISTS `lockrepo_destroy_daily`;
CREATE TABLE `lockrepo_destroy_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `destory_capital` decimal(16,2) NOT NULL COMMENT '销毁锁仓资产',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lockrepo_destroy_daily
-- ----------------------------

-- ----------------------------
-- Table structure for lockrepo_destroy_total
-- ----------------------------
DROP TABLE IF EXISTS `lockrepo_destroy_total`;
CREATE TABLE `lockrepo_destroy_total` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `destory_capital` decimal(16,2) NOT NULL COMMENT '销毁资产',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lockrepo_destroy_total
-- ----------------------------

-- ----------------------------
-- Table structure for profits_daily
-- ----------------------------
DROP TABLE IF EXISTS `profits_daily`;
CREATE TABLE `profits_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profits_team` decimal(16,2) NOT NULL COMMENT '团队收益',
  `profits_referee` decimal(16,2) NOT NULL COMMENT '推荐收益',
  `profits_lockrepo` decimal(16,2) NOT NULL COMMENT '锁仓收益',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profits_daily
-- ----------------------------

-- ----------------------------
-- Table structure for profits_lockrepo_ratio
-- ----------------------------
DROP TABLE IF EXISTS `profits_lockrepo_ratio`;
CREATE TABLE `profits_lockrepo_ratio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profits_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '锁仓收益参数',
  `lower_limit` decimal(16,2) NOT NULL COMMENT '下限',
  `upper_limit` decimal(16,2) NOT NULL COMMENT '上限',
  `ratio` decimal(16,3) DEFAULT NULL COMMENT '比例',
  `cardinal_number` decimal(16,2) DEFAULT NULL COMMENT '计算基数',
  `updated_user` int(11) DEFAULT NULL COMMENT '操作员',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_user` int(11) NOT NULL COMMENT '操作员',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profits_lockrepo_ratio
-- ----------------------------
INSERT INTO `profits_lockrepo_ratio` VALUES ('3', '001', '0.00', '100.00', '0.000', '1000.00', '1', '2018-11-28 15:18:37', '1', '2018-11-24 22:58:26');
INSERT INTO `profits_lockrepo_ratio` VALUES ('4', '002', '100.00', '500.00', '0.002', '1000.00', null, null, '1', '2018-11-24 22:59:01');
INSERT INTO `profits_lockrepo_ratio` VALUES ('5', '003', '500.00', '10000.00', '0.005', '1000.00', null, null, '1', '2018-11-24 22:59:33');
INSERT INTO `profits_lockrepo_ratio` VALUES ('13', '009', '10000.00', '100000.00', '0.008', '1000.00', null, null, '1', '2018-11-27 16:19:32');
INSERT INTO `profits_lockrepo_ratio` VALUES ('14', '010', '100000.00', '200000.00', '0.010', '1000.00', null, null, '1', '2018-11-27 16:22:30');
INSERT INTO `profits_lockrepo_ratio` VALUES ('15', '011', '200000.00', '300000.00', '0.012', '1000.00', '1', '2018-11-27 16:40:30', '1', '2018-11-27 16:22:49');
INSERT INTO `profits_lockrepo_ratio` VALUES ('16', '012', '300000.00', '999999999.00', '0.015', '1000.00', '1', '2018-11-27 16:40:30', '1', '2018-11-27 16:22:49');

-- ----------------------------
-- Table structure for profits_team_ratio
-- ----------------------------
DROP TABLE IF EXISTS `profits_team_ratio`;
CREATE TABLE `profits_team_ratio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profits_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '团队收益参数',
  `lower_limit` decimal(16,2) NOT NULL COMMENT '下限',
  `upper_limit` decimal(16,2) NOT NULL COMMENT '上限',
  `ratio` decimal(16,3) NOT NULL COMMENT '比例',
  `cardinal_number` decimal(16,2) DEFAULT NULL COMMENT '计算基数',
  `updated_user` int(11) DEFAULT NULL COMMENT '操作员',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_user` int(11) NOT NULL COMMENT '操作员',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profits_team_ratio
-- ----------------------------
INSERT INTO `profits_team_ratio` VALUES ('2', '001', '0.00', '100.00', '0.001', '1000.00', null, null, '1', '2018-11-27 10:23:52');
INSERT INTO `profits_team_ratio` VALUES ('3', '002', '100.00', '1000.00', '0.003', '1000.00', null, null, '1', '2018-11-29 16:05:35');
INSERT INTO `profits_team_ratio` VALUES ('4', '003', '1000.00', '10000.00', '0.004', '1000.00', null, null, '1', '2018-11-29 16:06:09');
INSERT INTO `profits_team_ratio` VALUES ('5', '004', '10000.00', '100000.00', '0.005', '1000.00', null, null, '1', '2018-11-29 16:06:50');
INSERT INTO `profits_team_ratio` VALUES ('6', '005', '100000.00', '200000.00', '0.008', '1000.00', null, null, '1', '2018-11-29 16:07:30');
INSERT INTO `profits_team_ratio` VALUES ('7', '006', '200000.00', '300000.00', '0.010', '1000.00', null, null, '1', '2018-11-29 16:07:57');
INSERT INTO `profits_team_ratio` VALUES ('8', '007', '300000.00', '999999999.00', '0.020', '1000.00', null, null, '1', '2018-11-29 16:08:20');

-- ----------------------------
-- Table structure for profits_total
-- ----------------------------
DROP TABLE IF EXISTS `profits_total`;
CREATE TABLE `profits_total` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profits_team` decimal(16,2) NOT NULL COMMENT '团队收益',
  `profits_referee` decimal(16,2) NOT NULL COMMENT '推荐收益',
  `profits_lockrepo` decimal(16,2) NOT NULL COMMENT '锁仓收益',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profits_total
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典组名称',
  `group_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `dic_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典名称',
  `dic_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '数据字典编码',
  `dic_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('1', '交易分类', 'transType', '收入', 'income', '1');
INSERT INTO `sys_dictionary` VALUES ('2', '交易分类', 'transType', '支出', 'expense', '2');
INSERT INTO `sys_dictionary` VALUES ('3', '交易类型', 'sourceType', '交易转入', 'trans_in', '1');
INSERT INTO `sys_dictionary` VALUES ('4', '交易类型', 'sourceType', '锁仓收益', 'lockrepo_profits', '2');
INSERT INTO `sys_dictionary` VALUES ('5', '交易类型', 'sourceType', '团队收益', 'team_profits', '3');
INSERT INTO `sys_dictionary` VALUES ('6', '交易类型', 'sourceType', '推荐收益', 'referee_profits', '4');
INSERT INTO `sys_dictionary` VALUES ('7', '交易类型', 'sourceType', '锁仓资产释放', 'release_lockrepo', '5');
INSERT INTO `sys_dictionary` VALUES ('8', '交易类型', 'sourceType', '交易转出', 'trans_out', '6');
INSERT INTO `sys_dictionary` VALUES ('9', '交易类型', 'sourceType', '复投锁仓', 'reinvest', '7');
INSERT INTO `sys_dictionary` VALUES ('10', '交易类型', 'sourceType', '锁仓资金销毁', 'destroy_lockrepo', '8');
INSERT INTO `sys_dictionary` VALUES ('11', '交易类型', 'sourceType', '交易手续费', 'trans_fee', '9');
INSERT INTO `sys_dictionary` VALUES ('12', '系统上线日期', 'publishDate', '系统上线日期', 'publish_date', '2018-11-01');

-- ----------------------------
-- Table structure for sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_code` varchar(50) NOT NULL COMMENT '参数编码',
  `param_name` varchar(500) NOT NULL COMMENT '参数名称',
  `param_value` decimal(16,2) NOT NULL COMMENT '参数值',
  `updated_user` int(11) DEFAULT NULL COMMENT '操作员',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_user` int(11) NOT NULL COMMENT '操作员',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_parameter
-- ----------------------------
INSERT INTO `sys_parameter` VALUES ('1', 'referee_profits_ratio', '推荐收益计算比例', '0.01', '1', '2018-11-28 10:39:28', '1', '2018-11-28 10:39:20');
INSERT INTO `sys_parameter` VALUES ('2', 'release_lockrepo_ratio', '锁仓资产每日释放比例', '0.01', '1', '2018-11-28 10:40:34', '1', '2018-11-28 10:40:31');
INSERT INTO `sys_parameter` VALUES ('3', 'destroy_lockrepo_times', '收益清账倍数', '4.00', '1', '2018-11-28 10:41:54', '1', '2018-11-28 10:41:52');
INSERT INTO `sys_parameter` VALUES ('4', 'reinvest_lockrepo_limit', '自动复投锁仓资产资金', '1000.00', '1', '2018-11-28 10:43:10', '1', '2018-11-28 10:43:12');
INSERT INTO `sys_parameter` VALUES ('5', 'destroy_lockrepo_limit', '收益清账基数', '1000.00', '1', '2018-11-28 10:40:34', '1', '2018-11-28 10:40:31');
INSERT INTO `sys_parameter` VALUES ('6', 'referee_profits_ratio', '推荐收益收益率', '0.01', '1', '2018-11-29 11:12:01', '1', '2018-11-29 11:11:58');
INSERT INTO `sys_parameter` VALUES ('7', 'daily_profits_limit', '日收益上限', '1000.00', '1', '2018-11-30 10:13:38', '1', '2018-11-30 10:13:41');
INSERT INTO `sys_parameter` VALUES ('8', 'transaction_fee_rate', '转账手续费', '0.05', '1', '2018-11-29 09:33:56', '1', '2018-11-29 09:34:00');
INSERT INTO `sys_parameter` VALUES ('9', 'coin_price', '币价', '1.00', '1', '2018-11-29 09:34:59', '1', '2018-11-29 09:35:03');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `account` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '晓霞十', '103e7525684c0d092488093a9fc0e4defb5e612403a1932163a2cf71a26e1cbf', null, 'admin', null);

-- ----------------------------
-- Table structure for tb_sequence
-- ----------------------------
DROP TABLE IF EXISTS `tb_sequence`;
CREATE TABLE `tb_sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `_increment` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sequence
-- ----------------------------
