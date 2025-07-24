package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	JWTService jwtService;

	@Autowired
	AppUserDetailsService auds;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
//			System.out.println("FILTER CALLED");
			String authHeader = request.getHeader("Authorization");
			String token, username = null;
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
				System.out.println("FILTER\n" + token + "\n");
				username = jwtService.extractUsername(token);
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = auds.loadUserByUsername(username);
					if (jwtService.validateToken(token, userDetails)) {
						UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails,
								null, userDetails.getAuthorities());
						upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(upat);
					}
				}
			}else if (request.getCookies() != null) {
			    for (Cookie cookie : request.getCookies()) {
			        if ("jwt".equals(cookie.getName())) {
			            token = cookie.getValue();
			            username = jwtService.extractUsername(token);
						if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
							UserDetails userDetails = auds.loadUserByUsername(username);
							if (jwtService.validateToken(token, userDetails)) {
								UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails,
										null, userDetails.getAuthorities());
								upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
								SecurityContextHolder.getContext().setAuthentication(upat);
							}
						}
			        }
			    }
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		}
	}

}
