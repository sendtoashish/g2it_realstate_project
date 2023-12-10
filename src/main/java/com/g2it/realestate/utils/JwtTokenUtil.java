package com.g2it.realestate.utils;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.g2it.realestate.exception.ApplicationException;
import com.g2it.realestate.exception.ErrorStatus;
import com.g2it.realestate.model.CredentialsPayload;
import com.g2it.realestate.model.Tokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil{

	private static final long JWT_TOKEN_VALIDITY = 120;
	private static final String secret = "afinesgroup";

    public static Tokens generateTokens(CredentialsPayload credentialsPayload) {
        String token = doGenerateToken(credentialsPayload.getUserName());
        LocalDateTime tokenValidity = LocalDateTime.now().plusMinutes(JWT_TOKEN_VALIDITY);
        return new Tokens(token, tokenValidity);
    }
    
    public static Claims validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception ex) {
            throw new ApplicationException(ErrorStatus.INVALID_HEADER, "Invalid Authorization header", ex);
        }
    }
    
    public static Tokens refreshToken() {
    	String accessToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization");
        try {
        	Claims claims = JwtTokenUtil.validateToken(accessToken);
    		String refreshToken = doGenerateToken(JwtTokenUtil.getUserName(claims));
            LocalDateTime tokenValidity = LocalDateTime.now().plusMinutes(JWT_TOKEN_VALIDITY);
            return new Tokens(refreshToken, tokenValidity);
        } catch (Exception ex) {
            throw new ApplicationException(ErrorStatus.INVALID_HEADER, "Invalid Authorization header", ex);
        }
    }
    
	private static String doGenerateToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 60 * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
    public static Boolean isTokenExpired(Claims claims) {
		Date expirationDate = claims.getExpiration();
		Date currentDate = new Date();
		return expirationDate.before(currentDate);
	}
    
    public static String getUserName(Claims claims) {
		String userName = claims.getSubject();
		return userName;
	}
}
