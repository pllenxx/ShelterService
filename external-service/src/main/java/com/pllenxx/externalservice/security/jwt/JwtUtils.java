package com.pllenxx.externalservice.security.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.pllenxx.externalservice.security.UserDetailsServiceImpl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.jwtExpirationInMs}")
    private Integer expiration;

    private final UserDetailsServiceImpl userDetailsService;

    public String createToken(String username, Collection<? extends GrantedAuthority> roles) {
        Map<String, Object> claims = new HashMap<>();
        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("admin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            claims.put("user", true);
        }
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(
                        SignatureAlgorithm.HS256,
                        secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Invalid signature", e);
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token has expired", e);
        }
    }
 }
