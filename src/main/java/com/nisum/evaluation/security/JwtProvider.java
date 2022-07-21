package com.nisum.evaluation.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
    
    private static final String AUTHORITIES_KEY = "auth";
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationms}")
    private int jwtExpirationMs;
    
    private JwtParser jwtParser;
    
    private Key key;
    
    
	@Override
	public void afterPropertiesSet() throws Exception {
      log.debug("Using a Base64-encoded JWT secret key");

      key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
      jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
	}
	
		
	public String generateJwtToken(Authentication authentication)  {
       String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);
        
        String compactTokenString = Jwts.builder()
        		.setSubject(authentication.getName())
        		.claim(AUTHORITIES_KEY, authorities)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                //.serializeToJsonWith(new JacksonSerializer())
                .compact();

        return "Bearer " + compactTokenString;
    }

    public String getUserNameFromJwtToken(String token) {
        byte[] secretBytes = jwtSecret.getBytes();
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretBytes)
                .build()
                .parseClaimsJws(token);
        
        return jwsClaims.getBody()
                .getSubject();
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }


}