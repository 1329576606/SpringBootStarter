package cn.xzx.utils;

/**
 * @author zhanxin.xu
 * @version Created in 2023/2/20 21:16
 */

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * jwt工具类
 *
 * @author zhanxin.xu
 */
@Slf4j
public class JwtUtils {
    /**
     * 生成jwt token
     */
    public String generateToken(Object obj, long expire, String secret) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(JSON.toJSONString(obj))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public <T extends Object> T getObjectFromJwt(String token, String secret, Class<T> tClass) {
        Claims claims = getClaimByToken(token, secret);
        if (claims == null) {
            return null;
        }
        String userJSON = claims.getSubject();
        if (StringUtils.isNotBlank(userJSON)) {
            return JSON.parseObject(userJSON, tClass);
        } else {
            return null;
        }
    }

    private Claims getClaimByToken(String token, String secret) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
