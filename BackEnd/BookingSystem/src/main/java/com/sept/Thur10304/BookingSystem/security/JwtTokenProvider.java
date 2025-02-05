package com.sept.Thur10304.BookingSystem.security;

import com.sept.Thur10304.BookingSystem.model.Account;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sept.Thur10304.BookingSystem.security.SecurityConstant.EXPIRATION_TIME;
import static com.sept.Thur10304.BookingSystem.security.SecurityConstant.SECRET;

import com.sept.Thur10304.BookingSystem.services.AccountService;

@Component
public class JwtTokenProvider {

    //Generate the token

    public String generateToken(Authentication authentication){
        Account user = (Account)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        // Anything you want to pull using JWT must be in claims.
        // Or you can just save the ID and then use that to lookup
        // other data normally.

        Map<String,Object> claims = new HashMap<>();
        //claims.put("email", user.getEmail());
        //claims.put("password", user.getPassword());

        claims.put("id", (Long.toString(user.getId())));
        claims.put("email", user.getEmail());
        //claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    //Validate the token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }


    //Get user Id from token

    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");

        return Long.parseLong(id);
    }

}
