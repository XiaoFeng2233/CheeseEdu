/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : cheesedu

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 07/01/2022 16:39:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `view_numbers` int(11) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------

-- ----------------------------
-- Table structure for banner_config
-- ----------------------------
DROP TABLE IF EXISTS `banner_config`;
CREATE TABLE `banner_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `image` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `platform` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sort` int(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of banner_config
-- ----------------------------

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '课程标题',
  `short_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '简短介绍',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '页面详细介绍',
  `type` tinyint(255) NOT NULL COMMENT '0 免费 1会员免费 2收费',
  `status` tinyint(255) NOT NULL COMMENT '0下架 1上架 2删除',
  `category_id` int(255) NOT NULL COMMENT '分类的id',
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `image` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '封面图片url',
  `deleted_time` datetime NULL DEFAULT NULL,
  `student_number` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `course_category`(`category_id`) USING BTREE,
  CONSTRAINT `course_category` FOREIGN KEY (`category_id`) REFERENCES `course_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------

-- ----------------------------
-- Table structure for course_category
-- ----------------------------
DROP TABLE IF EXISTS `course_category`;
CREATE TABLE `course_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `status` tinyint(255) NOT NULL COMMENT '0下架 1上架 2删除',
  `created_time` datetime NOT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  `delete_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 203 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course_category
-- ----------------------------

-- ----------------------------
-- Table structure for course_chapter
-- ----------------------------
DROP TABLE IF EXISTS `course_chapter`;
CREATE TABLE `course_chapter`  (
  `course_id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `index_number` int(255) NULL DEFAULT NULL,
  `is_delete` tinyint(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `chapter_course_id`(`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course_chapter
-- ----------------------------

-- ----------------------------
-- Table structure for course_comments
-- ----------------------------
DROP TABLE IF EXISTS `course_comments`;
CREATE TABLE `course_comments`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `deleted_time` datetime NULL DEFAULT NULL,
  `is_delete` tinyint(6) NOT NULL COMMENT '0正常  1 被删除',
  `star` float NOT NULL COMMENT '评分',
  `status` tinyint(255) NOT NULL COMMENT '1通过  0不正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comment_course_id`(`course_id`) USING BTREE,
  CONSTRAINT `comment_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course_comments
-- ----------------------------

-- ----------------------------
-- Table structure for course_records
-- ----------------------------
DROP TABLE IF EXISTS `course_records`;
CREATE TABLE `course_records`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `created_time` datetime NOT NULL,
  `video_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_records
-- ----------------------------

-- ----------------------------
-- Table structure for global_config
-- ----------------------------
DROP TABLE IF EXISTS `global_config`;
CREATE TABLE `global_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `site_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `site_agreement` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `site_footer` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `site_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `site_keyword` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `default_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `site_logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of global_config
-- ----------------------------
INSERT INTO `global_config` VALUES (1, '网站标题', '/image/ICO.png', '<h1 style=\"text-align:center\">待修改的用户协议</h1>', ' <div style=\"background-color: #2e2e2e;padding: 40px 0;color: white;\">\n        <div class=\"container\">\n          <div style=\"text-align: center;margin: 5px 0\">Powered By CheeseEdu</div>\n          <div style=\"text-align: center;margin: 5px 0\">课程内容版权均归 本网站 所有</div>\n          <div style=\"text-align: center;margin: 5px 0\">京ICP证XXXXXXXXX号</div>\n        </div>\n    </div>', '网页介绍', '网页关键词', '/image/ICO.png', '/image/LOGO.png');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `event` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `created_time` datetime NOT NULL,
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 307 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for payment_config
-- ----------------------------
DROP TABLE IF EXISTS `payment_config`;
CREATE TABLE `payment_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alipay_public_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `notify_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `merchant_private_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payment_config
-- ----------------------------
INSERT INTO `payment_config` VALUES (1, '', 'http://您的域名/pay/callback/alipay_callback.do', '', '');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `user_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `status` tinyint(4) NOT NULL COMMENT '0 已删除 1 正常',
  `images` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `created_time` datetime NOT NULL,
  `deleted_time` datetime NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `is_solved` tinyint(255) NOT NULL,
  `view_count` int(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `question_user_id`(`user_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `question_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question
-- ----------------------------

-- ----------------------------
-- Table structure for question_reply
-- ----------------------------
DROP TABLE IF EXISTS `question_reply`;
CREATE TABLE `question_reply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `images` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `created_time` datetime NOT NULL,
  `deleted_time` datetime NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `status` tinyint(255) NOT NULL COMMENT '0删除 1正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reply_user_id`(`user_id`) USING BTREE,
  INDEX `reply_question_id`(`question_id`) USING BTREE,
  CONSTRAINT `reply_question_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `reply_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_reply
-- ----------------------------

-- ----------------------------
-- Table structure for sms_config
-- ----------------------------
DROP TABLE IF EXISTS `sms_config`;
CREATE TABLE `sms_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sms_platform` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '短信平台',
  `aliyun_access_key_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '阿里云AccessKeyId',
  `aliyun_access_key_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '阿里云AccessKeySecret',
  `aliyun_sign_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '阿里云短信签名',
  `aliyun_reg_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '阿里云注册模板',
  `aliyun_login_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '阿里云登录模板',
  `aliyun_forget_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '阿里云忘记密码模板',
  `tencent_secret_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '腾讯云SecretId',
  `tencent_secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '腾讯云SecretKey',
  `tencent_sdk_app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '腾讯云SmsSdkAppId ',
  `tencent_reg_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '腾讯云注册模板',
  `tencent_login_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '腾讯云登录模板',
  `tencent_forget_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '腾讯云忘记密码模板',
  `tencent_sign_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '腾讯云短信签名',
  `tencent_reset_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '腾讯云重置手机号模板',
  `aliyun_reset_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '阿里云重置手机号模板',
  `tencent_region` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `duanxinbao_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `duanxinbao_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `duanxinbao_prefix` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_config
-- ----------------------------
INSERT INTO `sms_config` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for storage_config
-- ----------------------------
DROP TABLE IF EXISTS `storage_config`;
CREATE TABLE `storage_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `aliyun_access_key_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `aliyun_access_key_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `aliyun_bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `aliyun_endpoint` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `qcloud_secret_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `qcloud_secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `qcloud_cos_region` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `qcloud_bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of storage_config
-- ----------------------------
INSERT INTO `storage_config` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `vip_package_id` int(11) NOT NULL,
  `type` tinyint(255) NOT NULL COMMENT '1为课程  2为会员套餐  3为续费会员',
  `price` decimal(10, 2) NOT NULL,
  `is_free` tinyint(255) NOT NULL COMMENT '0为收费   1为免费',
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `is_pay_ok` tinyint(255) NOT NULL COMMENT '0代表未支付成功 1代表支付成功',
  `status` tinyint(255) NOT NULL COMMENT '0代表过期失效  1代表有效',
  `order_num` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `payment` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `payment_num` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `user_id` int(11) NOT NULL,
  `order_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `expire_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for user_course
-- ----------------------------
DROP TABLE IF EXISTS `user_course`;
CREATE TABLE `user_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `is_free` tinyint(255) NOT NULL COMMENT '0不免费  1免费',
  `created_time` datetime NOT NULL,
  `status` tinyint(255) NOT NULL COMMENT '0不可观看 1可观看',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_course
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `mobile_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `is_lock` tinyint(4) NOT NULL COMMENT '0 表示不封禁  1表示暂时封禁',
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `reg_area` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `reg_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `is_admin` tinyint(4) NOT NULL COMMENT '0 表示不是管理员  1表示是管理员',
  `status` tinyint(4) NOT NULL COMMENT '0表示删除   1表示正常',
  `is_vip` tinyint(4) NOT NULL,
  `vip_expired_time` datetime NULL DEFAULT NULL,
  `vip_package_id` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `mobile_number`(`mobile_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '694a77aaf6aea79ed390180833775a84', '超级管理员', 'derekderekderek', '13888888888', '/image/ICO.png', 0, '2022-01-07 02:29:09', '2022-01-07 02:29:12', '美国 马萨诸塞 斯普林菲尔德 Comcast', '98.56.32.12', 1, 1, 0, NULL, NULL);

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `chapter_id` int(11) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `is_delete` tinyint(255) NOT NULL,
  `object_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `video_duration` bigint(255) NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `deleted_time` datetime NULL DEFAULT NULL,
  `status` tinyint(255) NOT NULL COMMENT '0上传未完成  1上传完成',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `video_course`(`course_id`) USING BTREE,
  INDEX `video_chapter`(`chapter_id`) USING BTREE,
  CONSTRAINT `video_chapter` FOREIGN KEY (`chapter_id`) REFERENCES `course_chapter` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `video_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video
-- ----------------------------

-- ----------------------------
-- Table structure for vip_package
-- ----------------------------
DROP TABLE IF EXISTS `vip_package`;
CREATE TABLE `vip_package`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `duration` int(11) NOT NULL COMMENT '套餐时长 单位天',
  `price` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip_package
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
