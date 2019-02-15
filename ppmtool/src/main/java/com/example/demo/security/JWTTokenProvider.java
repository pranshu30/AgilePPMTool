package com.example.demo.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.example.demo.security.SecurityConstant.EXPIRATION_TIME;
import static com.example.demo.security.SecurityConstant.SECRET;


@Component
public class JWTTokenProvider {
//Generated Token
	public String generateToken(Authentication authentication) {
		
		User user = (User)authentication.getPrincipal();
		Date now= new Date(System.currentTimeMillis());
		
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
		
		String userID = Long.toString(user.getId());
		
		Map<String,Object> claims = new HashMap<>();
		claims.put("id",Long.toString(user.getId()));
		claims.put("username", user.getUsername());
		claims.put("Full name", user.getFullName());
		return Jwts.builder()
				.setSubject(userID)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
	}
//Validate token
	
	public Boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		}catch(SignatureException ex) {
			//CHecking the Signature Algorithm is right or not
			System.out.println("Invalid Signature");
		}
		catch(MalformedJwtException ex) {
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
	
//Get userId from Token
	
	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String)claims.get("id");
		return Long.parseLong(id);
	}
}
