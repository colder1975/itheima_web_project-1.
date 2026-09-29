package com.itheima.config;

import com.itheima.pojo.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器：将数据库异常转换为前端友好提示
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理唯一约束冲突（如部门名称重复、用户名重复）
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKey(DuplicateKeyException e) {
        String msg = e.getMessage();
        // 提取包含约束字段的友好提示
        String friendlyMsg = parseDuplicateKeyMessage(msg);
        return Result.error(friendlyMsg);
    }

    /**
     * 处理数据完整性约束冲突（NOT NULL 等）
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result handleDataIntegrity(DataIntegrityViolationException e) {
        String msg = e.getMessage();
        String friendlyMsg = parseIntegrityMessage(msg);
        return Result.error(friendlyMsg);
    }

    /**
     * 兜底异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // 记录原始异常到日志（便于排查）
        e.printStackTrace();
        return Result.error("服务器内部错误，请联系管理员");
    }

    // ========== 消息解析 ==========

    /**
     * 从 DuplicateKeyException 中提取可读信息
     * 原始消息示例：
     *   Duplicate entry '学工部' for key 'dept.name_2'
     *   Duplicate entry 'jinyong' for key 'emp.username'
     */
    private String parseDuplicateKeyMessage(String msg) {
        if (msg == null) {
            return "数据已存在，请检查后重试";
        }

        // 提取重复的值（引号内的内容）
        String value = null;
        int firstQuote = msg.indexOf("'");
        int secondQuote = msg.indexOf("'", firstQuote + 1);
        if (firstQuote != -1 && secondQuote != -1) {
            value = msg.substring(firstQuote + 1, secondQuote);
        }

        // 根据约束字段名返回对应提示（username 必须在 name 之前判断，否则 "name" 会误匹配 "username"）
        if (msg.contains("username")) {
            return value != null ? "用户名「" + value + "」已被占用，请使用其他用户名" : "用户名已被占用，请使用其他用户名";
        }
        if (msg.contains("name")) {
            return value != null ? "部门名称「" + value + "」已存在，请使用其他名称" : "部门名称已存在，请使用其他名称";
        }

        // 通用兜底
        return value != null ? "「" + value + "」已存在，无法重复添加" : "数据已存在，请检查后重试";
    }

    /**
     * 从 DataIntegrityViolationException 中提取可读信息
     * 原始消息示例：
     *   Column 'job' cannot be null
     */
    private String parseIntegrityMessage(String msg) {
        if (msg == null) {
            return "数据不完整，请填写所有必填项";
        }

        if (msg.contains("cannot be null")) {
            // 提取列名
            String column = extractColumn(msg);
            String fieldName = translateColumnName(column);
            return "「" + fieldName + "」为必填项，请填写后重试";
        }

        return "数据不完整，请填写所有必填项";
    }

    /**
     * 从 "Column 'xxx' cannot be null" 中提取列名
     */
    private String extractColumn(String msg) {
        int start = msg.indexOf("'");
        int end = msg.indexOf("'", start + 1);
        if (start != -1 && end != -1) {
            return msg.substring(start + 1, end);
        }
        return "";
    }

    /**
     * 将数据库列名翻译为中文
     */
    private String translateColumnName(String column) {
        return switch (column.toLowerCase()) {
            case "job" -> "职位";
            case "entrydate" -> "入职日期";
            case "dept_id", "deptid" -> "所属部门";
            case "name" -> "姓名";
            case "password" -> "密码";
            case "username" -> "用户名";
            case "gender" -> "性别";
            default -> column;
        };
    }
}
