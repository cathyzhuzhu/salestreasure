package com.docetor.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JWTUtil {

    // 过期时间1分钟
    @Value("${expire_time}")
    private long expireTime;//过期时间

    @Value("${secret}")
    private String secret;//密钥


    /**
     * 校验token是否正确
     * @param token 密钥
     * @return 是否正确
     */
    public  boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 校验token是否正确
     * @param token 密钥
     * @return 是否正确
     */
    public  boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param username 用户名
     * @return 加密的token
     */
    public String sign(String username) {
        try {
            Date date = new Date(System.currentTimeMillis()+expireTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


//    public static void main(String []args){
//        String token = sign("root");
//        System.out.println(token);
//        System.out.println(getUsername(token));
////        System.out.println(token);
////        String token ="eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MTgwNzM0ODgsInVzZXJuYW1lIjoicm9vdCJ9.WqSkQKcJgSM7AFWvwUv2_mIreVx9Fsdr1rRNIAH_Uus";
//        System.out.println(verify(token,"root"));
//    }

}
