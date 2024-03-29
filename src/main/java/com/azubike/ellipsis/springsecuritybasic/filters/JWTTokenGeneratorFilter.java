package com.azubike.ellipsis.springsecuritybasic.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.azubike.ellipsis.springsecuritybasic.constants.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// only allow on successful authentication
		if (null != authentication) {
			// encrypt the key
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			// generate the token
			String jwt = Jwts.builder().setIssuer("Eazy Bank").setSubject("JWT Token").setIssuedAt(new Date())
					.claim("username", authentication.getName())
					.claim("authorities", populateAuthorities(authentication.getAuthorities())).setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + 30000000L)).signWith(key).compact();
			// append to the response header for subsequent requests
			response.setHeader(SecurityConstants.JWT_HEADER, SecurityConstants.TOKEN_PREFIX + jwt);
		}

		chain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/user");
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();
		for (GrantedAuthority authority : collection) {
			authoritiesSet.add(authority.getAuthority());
		}
		return String.join(",", authoritiesSet);
	}
}
