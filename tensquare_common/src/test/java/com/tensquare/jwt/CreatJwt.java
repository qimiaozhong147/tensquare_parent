package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatJwt {

    @Test
    public void testCreatJwt(){
        JwtBuilder jwtBuilder = Jwts.builder().setId("888") //用户id
                .setSubject("小黑") //名字
                .setIssuedAt(new Date())//签发时间
                .signWith(SignatureAlgorithm.HS256, "itcast")//盐
                .setExpiration(new Date(new Date().getTime() + 1000*60))//设置过期时间为一分钟
                .claim("roles", "admin");
        System.out.println(jwtBuilder.compact());//输出token
    }

    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_pu5EiLCJpYXQiOjE1NzY3NDE3MjUsImV4cCI6MTU3Njc0MTc4NSwicm9sZXMiOiJhZG1pbiJ9.pTUbA82CcTC6-9E4ugjU1DhcGV4hu1KjqN5vSqjRLs0";
        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
        System.out.println("id" + claims.getId());
        System.out.println("subject" + claims.getSubject());
        System.out.println("IssuedAt" + claims.getIssuedAt());
        System.out.println("roles" + claims.get("roles"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("签发时间" + sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间" + sdf.format(claims.getExpiration())); //超过过期时间会报异常 所以用的时候用try catch
        System.out.println("当前时间" + sdf.format(new Date()));


    }

}
