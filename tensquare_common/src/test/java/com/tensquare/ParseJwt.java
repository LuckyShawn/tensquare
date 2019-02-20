package com.tensquare;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/20 0020
 */
public class ParseJwt {
    public static void main(String[] args) {
        //因为过期的原因，过期后会抛异常，所以解析的时候需要做异常处理
        Claims claims = Jwts.parser().setSigningKey("shawn")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NTA2NDQzODEsImV4cCI6MTU1MDY0NDQ0MX0.lFUVkDFrN_oj0cW51rOGbNFOd_e50LbMYZeA5zcqVIs")
                .getBody();
        System.out.println("用户id："+claims.getId());
        System.out.println("用户名称："+claims.getSubject());
        System.out.println("登录时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("角色："+claims.get("role"));
    }
}
