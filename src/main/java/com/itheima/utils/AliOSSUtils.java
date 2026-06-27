package com.itheima.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云OSS文件上传工具类
 */
@Component
public class AliOSSUtils {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    /**
     * 上传文件到阿里云OSS
     * @param file 上传的文件
     * @return 文件的OSS公网访问URL
     * @throws IOException 文件读取异常
     */
    public String upload(MultipartFile file) throws IOException {
        // 1. 获取原始文件名并提取扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 2. 生成唯一文件名，防止覆盖
        String objectName = "images/" + UUID.randomUUID().toString() + extension;

        // 3. 创建OSS客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try (InputStream inputStream = file.getInputStream()) {
            // 4. 上传文件
            ossClient.putObject(bucketName, objectName, inputStream);
        } finally {
            // 5. 关闭OSS客户端
            ossClient.shutdown();
        }

        // 6. 拼接文件访问URL并返回
        // URL格式: https://<bucketName>.<endpoint域名>/<objectName>
        String host = endpoint.replace("https://", "").replace("http://", "");
        String url = "https://" + bucketName + "." + host + "/" + objectName;
        return url;
    }
}
