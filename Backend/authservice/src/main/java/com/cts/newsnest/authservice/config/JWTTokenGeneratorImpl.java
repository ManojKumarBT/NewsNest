package com.cts.newsnest.authservice.config;

import com.cts.newsnest.authservice.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTTokenGeneratorImpl implements JWTTokenGenerator {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${app.jwttoken.message}")
    private String message;

    @Override
    public Map<String, String> generateToken(User user) {
        String jwtToken = "";
        /*
         * Generate JWT token and store in String jwtToken
         */

//        jwtToken = Jwts.builder().setSubject(user.getId())
//                .setAudience(user.getId())
//                .setAudience("Manoj")
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+ 300000))
//                .signWith(SignatureAlgorithm.HS256,"secret").compact();

//        Map<String, String> jwtTokenMap = new HashMap<>();
//        cgidemo

        Map<String, String> jwtTokenMap = new HashMap<>();
        Map<String, Object> dataOfUser = new HashMap<>();
        dataOfUser.put("id", user.getEmail());
        dataOfUser.put("password", user.getPassword());

        jwtToken = Jwts.builder().setClaims(dataOfUser).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        jwtTokenMap.put("token", jwtToken);
        jwtTokenMap.put("email", user.getEmail());
        jwtTokenMap.put("message", message);
        return jwtTokenMap;

//        HashMap hashMap = new HashMap();
//        hashMap.put("token",jwtToken);
//        return hashMap;
    }
}
