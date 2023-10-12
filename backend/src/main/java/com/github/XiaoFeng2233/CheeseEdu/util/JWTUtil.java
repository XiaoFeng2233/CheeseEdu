package com.github.XiaoFeng2233.CheeseEdu.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.github.XiaoFeng2233.CheeseEdu.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/12/12 20:33
 */
@Component
public class JWTUtil {
    @Autowired
    private RedisUtil redisUtil;


    public String createToekn(Users user,int days){
        Object jwtSecret = redisUtil.get("JWTSecret");
        while (jwtSecret == null){
            redisUtil.set("JWTSecret", RandomUtil.randomString(100));
            jwtSecret = redisUtil.get("JWTSecret");
        }
        String secret = (String) jwtSecret;
        String jwt = JWT.create()
                .withIssuer("Server")
                .withAudience(user.getUsername())
                .withIssuedAt(DateUtil.date())
                .withExpiresAt(DateUtil.offset(DateUtil.date(), DateField.DAY_OF_MONTH,days))
                .withClaim("userName",user.getUsername())
                .withClaim("userId",user.getId())
                .withClaim("isAdmin",user.getIsAdmin())
                .withClaim("isVip",user.getIsVip())
                .withClaim("nickname",user.getNickname())
                .withClaim("avatar",user.getAvatar())
                .sign(Algorithm.HMAC256(secret));
        return jwt;
    }

    public Boolean verify(String token){

        Object jwtSecret = redisUtil.get("JWTSecret");
        while (jwtSecret == null){
            redisUtil.set("JWTSecret", RandomUtil.randomString(100));
            jwtSecret = redisUtil.get("JWTSecret");
        }
        String secret = (String) jwtSecret;

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            verifier.verify(token);
            Date expireTime = JWT.decode(token).getExpiresAt();
            if (expireTime.getTime() - DateUtil.date().getTime()<0){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }

    public Claim getClaimByName(String token,String key){
        if (verify(token)){
            return JWT.decode(token).getClaim(key);
        }else{
            return null;
        }
    }
}
