package com.itheima.controller;

import com.itheima.pojo.LoginRequest;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 */
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    /**
     * 员工登录
     * @param request 登录请求体（username, password）
     * @return 成功时返回JWT令牌，失败时返回错误信息
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request) {
        try {
            String token = empService.login(request.getUsername(), request.getPassword());
            return Result.success(token);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
