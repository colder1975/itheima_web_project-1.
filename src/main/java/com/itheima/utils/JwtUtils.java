package com.itheima.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itheima.pojo.Emp;
import com.itheima.pojo.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类：生成和验证令牌
 */
@Component
public class JwtUtils {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 根据员工信息生成JWT令牌
     * @param emp 员工实体
     * @return JWT令牌字符串
     */
    public String generateToken(Emp emp) {
        return JWT.create()
                .withClaim("id", emp.getId())
                .withClaim("username", emp.getUsername())
                .withClaim("name", emp.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpireHours() * 3600 * 1000))
                .sign(Algorithm.HMAC256(jwtProperties.getSecret()));
    }

    /**
     * 验证并解析JWT令牌
     * @param token JWT令牌
     * @return 解析后的令牌对象，验证失败返回 null
     */
    public DecodedJWT verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecret())).build();
            return verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从令牌中获取所有Claims
     * @param token JWT令牌
     * @return Claims Map，验证失败返回 null
     */
    public Map<String, Object> parseToken(String token) {
        DecodedJWT jwt = verifyToken(token);
        if (jwt == null) return null;
        return Map.of(
            "id", jwt.getClaim("id").asInt(),
            "username", jwt.getClaim("username").asString(),
            "name", jwt.getClaim("name").asString()
        );
    }
}
