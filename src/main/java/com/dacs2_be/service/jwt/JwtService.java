package com.dacs2_be.service.jwt;

import com.dacs2_be.dto.RoleDTO;
import com.dacs2_be.entity.User;
import com.dacs2_be.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    private static final String KEY_SECRET = "MTIzNDU2NDU5OThEMzIxM0F6eGMzNTE2NTQzMjEzMjE2NTQ5OHEzMTNhMnMxZDMyMnp4M2MyMQ==";

    @Autowired
    private UserService userSecurityService;

    // Tạo jwt dựa trên email (tạo thông tin cần trả về cho FE khi đăng nhập thành công)
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        User user = userSecurityService.findByEmail(email);

        RoleDTO roleDTO = new RoleDTO(
                user.getRole().getId(),
                user.getRole().getRoleName()
        );

        claims.put("name", user.getName());
        claims.put("id", user.getId());
        claims.put("status", user.getStatus());
        claims.put("role", roleDTO.getRoleName());
        claims.put("email", user.getEmail());
        claims.put("avatar", user.getAvatar());

        return createToken(claims, email);
    }

    // Toạ jwt với các claims đã chọn
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000L * 60 * 60 * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Lấy key_secret
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(KEY_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Trích xuất thông tin (lấy ra tất cả thông số)
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Trích xuất thông tin cụ thể nhưng triển khai tổng quát (Method Generic)
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);



    }

    // Lấy ra thời gian hết hạn
    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    // Lấy ra username
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Kiểm tra token đó hết hạn chưa
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Kiểm tra tính hợp lệ của token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
