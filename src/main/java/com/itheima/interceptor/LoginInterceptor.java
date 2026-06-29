package com.itheima.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * JWT登录拦截器：校验请求中的令牌
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头获取 token
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            writeError(response, "未登录");
            return false;
        }

        // 2. 验证 token
        Map<String, Object> claims = jwtUtils.parseToken(token);
        if (claims == null) {
            writeError(response, "令牌无效或已过期");
            return false;
        }

        // 3. 验证通过，将用户信息存入 request 供后续使用
        request.setAttribute("userId", claims.get("id"));
        request.setAttribute("username", claims.get("username"));
        request.setAttribute("name", claims.get("name"));
        return true;
    }

    private void writeError(HttpServletResponse response, String msg) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(msg)));
    }
}
