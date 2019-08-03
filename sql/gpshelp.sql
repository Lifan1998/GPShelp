/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : gpshelp

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 15/05/2019 20:13:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `unit_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `account_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `account_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`unit_id`, `id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for receiver
-- ----------------------------
DROP TABLE IF EXISTS `receiver`;
CREATE TABLE `receiver`  (
  `unit_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `account_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `account_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`unit_id`, `id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rescuer
-- ----------------------------
DROP TABLE IF EXISTS `rescuer`;
CREATE TABLE `rescuer`  (
  `unit_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `avater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`unit_id`, `id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rescuer
-- ----------------------------
INSERT INTO `rescuer` VALUES ('11', '11111', 'qqq', '男', '123456789', 'oAxVW4x5yAp-Aguzi3D5_Fi_-P2E', NULL, '在线', NULL);

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rescuer_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `chat_group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `receiver_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `event_location_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `event_location_coordinate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `aid_location_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `aid_location_coordinate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `aid_route` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('1557920823198', '已完结', '110', 'oAxVW42g8hRzfPymV5yt0hGbb4YY', 'oAxVW4x5yAp-Aguzi3D5_Fi_-P2E', '82336731430913', NULL, '江苏省南京市江宁区日晖路', '31.930362955729166,118.88203748914931', '南京市公安局警务工作服务站高新区站(江宁03)', '31.939486,118.877648', '[31.930451,118.882019,59,61,0,0,-490,740,-1780,2620,-1310,1900,-420,680,0,0,70,110,530,-70,710,20,370,70,290,80,480,200,230,120,360,220,500,340,1370,960,800,530,500,410,0,0,180,70,1280,-1532,1150,-1328,2010,-2280,1168,-1304,61,-51,471,-515,1130,-1340,580,-780,0,0,210,-170,130,-30,80,0,210,60,450,190,610,220,280,90,520,140,510,120,410,70,700,90,330,20,830,0,400,-10,440,-30,350,-30,850,-150,350,-80,660,-180,310,-100,930,-260,2240,-740,530,-240,690,-380,210,-130,510,-420,330,-340,473,-427,1527,-1473,170,-190,1120,-1040,1020,-990,391,-222,91,-73,99,-110,164,-238,390,-409,1735,-1618,434,-388,1276,-1212,300,-300,398,-429,662,-601,1680,-1170,585,-495,2705,-2215,580,-500,0,0,2070,3360,20,50,1250,1990,0,0,110,300,20,120,-50,180,-80,150,-30,20,-190,60,-100,-10,-100,-30,-90,-40,-100,-80,-700,-650,-410,-420,-110,-150,-50,-120,-70,-230,30,-340,110,-260,190,-200,670,-520,70,-100,0,0,3800,-2760,1530,-1190,2020,-1770,1450,-1380,2130,-2200,760,-870,680,-750,980,-1240,450,-550,2170,-2470,1850,-1940,1410,-1350,520,-480,1710,-1500,550,-413,400,-407,1150,-960,3690,-3130,230,-180,1920,-1610,700,-760,650,-770,250,-470,160,-350,60,-140,90,-270,110,-570,100,-750,30,-330,0,-340,-20,-320,-140,-1160,-70,-280,-240,-760,-260,-1640,-120,-970,0,0,-30,-170,-50,-540,-30,-520,30,-410,160,-990,0,0,170,-850,1130,-2750,270,-730,110,-400,230,-1130,140,-1200,380,-2970,190,-1250,130,-1290,230,-1910,70,-740,150,-1120,110,-1100,0,-610,-10,-200,-40,-360,-150,-850,-210,-1000,-160,-600,-60,-300,-80,-300,-160,-790,-120,-1230,-310,-2790,-70,-320,-150,-1070,0,0,30,-210,-90,-940,-150,-1360,0,0,60,-330,76,-274,14,-146,140,-370,140,-240,260,-330,190,-180,250,-170,340,-150,439,-69,557,-18,464,27,260,40,250,60,1040,400,70,10,80,-20,80,-60,0,0,910,310,5560,2050,550,190,440,130,4790,1190,0,0,2390,600,0,0,790,200,450,100,250,40,450,60,1810,170,320,50,1120,230,710,130,1030,220,900,170,1890,320,560,130,1470,430,1970,600,0,0,1090,330,770,210,550,100,760,110,1200,200,760,150,560,170,4630,1580,660,200,1180,310,1030,200,690,110,700,130,0,0,200,90,1120,170,360,40,800,50,1770,220,0,0,80,-140,-113,-704,-53,-465,6,-331,410,-3770,-20,-220,-40,-150,-90,-240,-70,-150,-60,-230,-10,-170,10,-350,80,-1160,-40,-160,50,-630,80,-570,133,-620,77,-220,60,-130,330,-1510,0,0,10,-80,180,20,0,0,0,-30]');
INSERT INTO `task` VALUES ('1557921655723', '已完结', '110', 'oAxVW42g8hRzfPymV5yt0hGbb4YY', 'oAxVW4x5yAp-Aguzi3D5_Fi_-P2E', '82337603846145', NULL, '江苏省南京市江宁区日晖路', '31.930362955729166,118.88203748914931', '南京市公安局警务工作服务站高新区站(江宁03)', '31.939486,118.877648', '[31.930451,118.882019,59,61,0,0,-490,740,-1780,2620,-820,1180,0,0,-153,-178,-1497,-1729,0,0,262,-542,87,-303,55,-240,14,-652,0,0,1331,4,681,40,795,19,471,-6,224,-23,140,-140,590,-880,0,0,-133,-8]');

-- ----------------------------
-- Table structure for unit
-- ----------------------------
DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `unit_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `coordinate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of unit
-- ----------------------------
INSERT INTO `unit` VALUES ('11', '11', '110', '1', '1', '1,2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `first_login` datetime(6) NOT NULL,
  `last_login` datetime(6) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('oAxVW42g8hRzfPymV5yt0hGbb4YY', '2019-05-15 19:46:31.048000', '2019-05-15 19:57:51.318000', NULL, NULL, NULL);
INSERT INTO `user` VALUES ('oAxVW4x5yAp-Aguzi3D5_Fi_-P2E', '2019-05-10 19:14:33.098000', '2019-05-15 19:56:06.779000', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
