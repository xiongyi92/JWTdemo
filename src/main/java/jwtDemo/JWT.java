package jwtDemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;


import java.util.Date;

/**
 * @author hw
 */
public class JWT {

    private static final String PRIVATE_KEY="123456789";

    @Test
    public void jwtTest() throws InterruptedException {
        //设置3秒后过期
        String jwt = this.buildJWT(new Date(System.currentTimeMillis()+3*1000));
        System.out.println(jwt);
//        Thread.sleep(3*1000);
        //验证token是否可用
        boolean isok=this.isJwtValid(jwt);
        System.out.println(isok);
    }


    /**
     * 生成JWT
     * @param exp
     * @return
     */
    public String buildJWT(Date exp){
        String jwt= Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,PRIVATE_KEY)
                .setExpiration(exp)
                .claim("key","value")
                .compact();
        return jwt;
    }

    /**
     * 判断jwt
     * @param jwt
     * @return
     */
    public boolean isJwtValid(String jwt){
      try{
         //解析JWT字符串中的数据，并进行最基础的验证
          Claims claims=Jwts.parser()
                  .setSigningKey(PRIVATE_KEY)
                  .parseClaimsJws(jwt)
                  .getBody();
          String value;
          System.err.println("body:"+claims);
          value = claims.get("key", String.class);
          System.out.println("value:"+value);
          //判断自定义字段是否正确
          if("value".equals(value)){
              return true;
          }else{
              return false;
          }
      }
      catch (Exception e){
          System.out.println(e.getMessage());
          return  false;
      }
    }



}
