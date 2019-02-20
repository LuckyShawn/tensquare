package com.tensquare;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Description 生成Jwt
 * @Author shawn
 * @create 2019/2/20 0020
 */
public class CreateJwt {
    public static void main(String[] args){
        //setIssuedAt用于设置签发时间
        //signWith用于设置签名秘钥
        JwtBuilder jwtBuilder = Jwts.builder().setId("123")
                .setSubject("小白").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "shawn")
                .setExpiration(new Date(System.currentTimeMillis()+1000*1800))
                .claim("role","admin");
        System.out.println(jwtBuilder.compact());
    }
}
