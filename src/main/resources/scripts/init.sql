/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : study

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2021-02-24 14:57:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zk_study
-- ----------------------------
DROP TABLE IF EXISTS `zk_study`;
CREATE TABLE `zk_study` (
  `id` bigint(11) NOT NULL,
  `status` varchar(11) DEFAULT NULL,
  `active_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of zk_study
-- ----------------------------
INSERT INTO `zk_study` VALUES ('1', '-1', '2021-02-20 17:09:02', '李四');
INSERT INTO `zk_study` VALUES ('2', '-1', '2021-02-20 17:09:17', '王五');
INSERT INTO `zk_study` VALUES ('3', '-1', '2021-02-20 17:09:27', '张三');
