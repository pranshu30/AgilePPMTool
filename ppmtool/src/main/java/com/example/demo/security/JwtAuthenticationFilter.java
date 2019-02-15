package com.example.demo.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.domain.User;
import com.example.demo.services.CustomUserDetailService;
import static com.example.demo.security.SecurityConstant.HEADER_STRING;
import static com.example.demo.security.SecurityConstant.TOKEN_PREFIX;



public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			
			String jwt = getJWTFromRequest(request);
			
			if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userID = tokenProvider.getUserId(jwt);
				User userDetails = customUserDetailService.loadUserById(userID);
				
				
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails, null, Collections.emptyList());
			
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
					
			}
			
		}catch(Exception ex) {
			logger.error("Could not set user authentication in security context",ex);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getJWTFromRequest(HttpServletRequest request) {
		
		//Getting the token from the Authorization column in Header tab
		String bearerToken = request.getHeader(HEADER_STRING);//HEADER_STRING =  Authorization
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(7,bearerToken.length());
		}
		return null;
	}

}
