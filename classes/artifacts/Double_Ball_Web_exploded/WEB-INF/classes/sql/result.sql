/*
Navicat MySQL Data Transfer

Source Server         : 192.168.33.117_33307
Source Server Version : 50719
Source Host           : 192.168.33.117:33307
Source Database       : xytest

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-10-31 17:20:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for result
-- ----------------------------
DROP TABLE IF EXISTS `result`;
CREATE TABLE `result` (
  `number` varchar(255) NOT NULL,
  `href` varchar(255) DEFAULT NULL,
  `red1` varchar(255) DEFAULT NULL,
  `red2` varchar(255) DEFAULT NULL,
  `red3` varchar(255) DEFAULT NULL,
  `red4` varchar(255) DEFAULT NULL,
  `red5` varchar(255) DEFAULT NULL,
  `red6` varchar(255) DEFAULT NULL,
  `blue` varchar(255) DEFAULT NULL,
  `openDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deadlineDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `extend` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
