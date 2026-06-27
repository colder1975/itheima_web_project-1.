package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 */
@RestController
public class UploadController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    /**
     * 上传文件到阿里云OSS
     * @param file 上传的文件（参数名必须与前端表单的name属性一致，默认使用"file"）
     * @return 上传结果，成功时data中包含文件访问URL
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        // 1. 检查文件是否为空
        if (file == null || file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        try {
            // 2. 调用工具类上传文件
            String url = aliOSSUtils.upload(file);
            // 3. 返回成功结果，data中包含文件URL
            return Result.success(url);
        } catch (Exception e) {
            // 4. 捕获异常，返回错误信息
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }
}
