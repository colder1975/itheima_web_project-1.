-- 部门表
CREATE TABLE IF NOT EXISTS `dept` (
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        VARCHAR(50)  NOT NULL UNIQUE         COMMENT '部门名称',
    `create_time` DATETIME     NOT NULL                COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT NULL            COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- 员工表
CREATE TABLE IF NOT EXISTS `emp` (
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    VARCHAR(50)  NOT NULL UNIQUE         COMMENT '用户名',
    `password`    VARCHAR(50)  NOT NULL DEFAULT '123456' COMMENT '密码',
    `name`        VARCHAR(50)  NOT NULL                COMMENT '姓名',
    `gender`      TINYINT(1)   DEFAULT NULL            COMMENT '性别（0:女, 1:男）',
    `image`       VARCHAR(200) DEFAULT NULL            COMMENT '头像URL',
    `job`         INT          NOT NULL                COMMENT '职位（1:班主任, 2:讲师, 3:学工主管, 4:教研主管, 5:咨询师）',
    `entrydate`   DATE         NOT NULL                COMMENT '入职日期',
    `dept_id`     INT          NOT NULL                COMMENT '部门ID',
    `create_time` DATETIME     NOT NULL                COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT NULL            COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工表';

-- ===================== 初始数据 =====================

-- 部门初始数据
INSERT INTO `dept` (`id`, `name`, `create_time`, `update_time`) VALUES
(1, '学工部', NOW(), NOW()),
(2, '教研部', NOW(), NOW()),
(3, '咨询部', NOW(), NOW()),
(4, '就业部', NOW(), NOW()),
(5, '人事部', NOW(), NOW())
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

-- 员工初始数据
INSERT INTO `emp` (`id`, `username`, `password`, `name`, `gender`, `image`, `job`, `entrydate`, `dept_id`, `create_time`, `update_time`) VALUES
(1,  'jinyong',   '123456', '金庸',   1, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/1.jpg', 1, '2000-01-01', 1, NOW(), NOW()),
(2,  'zhangwuji', '123456', '张无忌', 1, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/2.jpg', 2, '2015-01-01', 2, NOW(), NOW()),
(3,  'yangguo',   '123456', '杨过',   1, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/3.jpg', 2, '2008-05-01', 2, NOW(), NOW()),
(4,  'zhaomin',   '123456', '赵敏',   0, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/4.jpg', 3, '2007-01-01', 2, NOW(), NOW()),
(5,  'xiaolongnv','123456', '小龙女', 0, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/5.jpg', 2, '2020-09-01', 1, NOW(), NOW()),
(6,  'weixiaobao','123456', '韦小宝', 1, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/6.jpg', 5, '2010-04-01', 3, NOW(), NOW()),
(7,  'huangrong', '123456', '黄蓉',   0, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/7.jpg', 4, '2012-07-01', 4, NOW(), NOW()),
(8,  'guojing',   '123456', '郭靖',   1, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/8.jpg', 2, '2011-03-01', 1, NOW(), NOW()),
(9,  'linghuchong','123456', '令狐冲', 1, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/9.jpg', 2, '2018-11-01', 2, NOW(), NOW()),
(10, 'renwoxing', '123456', '任我行', 1, 'https://web-framework.oss-cn-hangzhou.aliyuncs.com/avatar/10.jpg', 1, '2005-08-01', 3, NOW(), NOW())
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);
