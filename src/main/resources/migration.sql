-- ============================================
-- 数据库约束升级迁移脚本
-- 对应 schema.sql 的变更，适配已有数据库
-- 执行前请先备份数据！
-- ============================================

-- 1. 部门表修改
-- ====================

-- 1a. 部门名称添加唯一约束（如果有重名先检查）
-- SELECT name, COUNT(*) FROM dept GROUP BY name HAVING COUNT(*) > 1;
ALTER TABLE `dept` ADD UNIQUE KEY `uk_dept_name` (`name`);

-- 1b. create_time 设为 NOT NULL（已有数据应都有值）
ALTER TABLE `dept` MODIFY `create_time` DATETIME NOT NULL COMMENT '创建时间';

-- 2. 员工表修改
-- ====================

-- 2a. 填充空的密码为默认值
UPDATE `emp` SET `password` = '123456' WHERE `password` IS NULL OR `password` = '';

-- 2b. password 设为 NOT NULL，默认值 123456
ALTER TABLE `emp` MODIFY `password` VARCHAR(50) NOT NULL DEFAULT '123456' COMMENT '密码';

-- 2c. 填充空的 create_time
UPDATE `emp` SET `create_time` = NOW() WHERE `create_time` IS NULL;

-- 2d. create_time 设为 NOT NULL
ALTER TABLE `emp` MODIFY `create_time` DATETIME NOT NULL COMMENT '创建时间';

-- 2e. job 设为 NOT NULL
ALTER TABLE `emp` MODIFY `job` INT NOT NULL COMMENT '职位（1:班主任, 2:讲师, 3:学工主管, 4:教研主管, 5:咨询师）';

-- 2f. entrydate 设为 NOT NULL
ALTER TABLE `emp` MODIFY `entrydate` DATE NOT NULL COMMENT '入职日期';

-- 2g. dept_id 设为 NOT NULL
ALTER TABLE `emp` MODIFY `dept_id` INT NOT NULL COMMENT '部门ID';
