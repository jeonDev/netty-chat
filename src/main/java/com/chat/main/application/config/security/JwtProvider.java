package com.chat.main.application.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final UserService userService;
    private final String TEST_KEY = "testtesttest";
    private static final Long EXPIRED_TIME = 60 * 60 * 60 * 10L;

    public JwtProvider(UserService userService) {
        this.userService = userService;
    }

    public String generateIdentificationInfo(Long memberId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRED_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, TEST_KEY)
                .compact();
    }

    public Authentication getAuthenticationInfo(Object identificationInfo) {
        Claims body = this.getTokenForSubject((String) identificationInfo);
        UserDetails userDetails = userService.loadUserByUsername(body.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims getTokenForSubject(String token) {
        return Jwts.parser()
                .setSigningKey(TEST_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validCheck(Object identificationInfo) {
        try{
            Jwts.parser().setSigningKey(TEST_KEY).parseClaimsJws((String) identificationInfo);
            return true;
        } catch(JwtException e) {
            log.debug("Jwt Exception : {}", e.getMessage());
        }
        return false;
    }
}
