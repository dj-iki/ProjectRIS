package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JWTService {

	@Value("${secret}")
	private String secret;

	private SecretKey secretKey;
	
	

	@PostConstruct
	public void init() {
		this.secret = Base64.getEncoder().encodeToString(this.secret.getBytes());
		this.secretKey = Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(UserDetails userDetails) {
		List<String> roles = userDetails.getAuthorities().stream()
			    .map(a -> a.getAuthority().replace("ROLE_", ""))
			    .toList();
		
		ClaimsBuilder claimsBuilder = Jwts.claims().subject(userDetails.getUsername()).add("roles", roles);
		Claims claims = claimsBuilder.build();
		Date issuedAt = new Date();
		Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

		return Jwts.builder().claims(claims).issuedAt(issuedAt).expiration(expiration)
				.signWith(this.secretKey, Jwts.SIG.HS256).compact();
	}

	public String extractUsername(String token) {
		Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
		return claims.getSubject();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String username = this.extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date()); 
	}

	private Date extractExpiration(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();
	}

}
