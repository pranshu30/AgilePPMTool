package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.services.CustomUserDetailService;

import static com.example.demo.security.SecurityConstant.SIGN_UP_URLS;
import static com.example.demo.security.SecurityConstant.H2_URL;


@Configuration
@EnableWebSecurity
//Method level security
@EnableGlobalMethodSecurity(
		securedEnabled =true,
		jsr250Enabled=true,
		prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	//WebSecurityConfigurerAdapter:override method in web configure
	
	@Autowired
	private JwtAuthenticateEntryPoint jwtAuthenticateEntryPoint; 
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {return  new JwtAuthenticationFilter();}
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder);
	}

	
	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Since we are using JWT
		http.cors().and().csrf().disable()
		//How we handling exception,authenticationEntryPoint:give message when your are unauthorized
		.exceptionHandling().authenticationEntryPoint(jwtAuthenticateEntryPoint).and()
		.sessionManagement()
		//session doesn't save any states/cookies, we have authorization expiration time.
		//Redux will save the State
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.headers().frameOptions().sameOrigin() //to enable h2 database
		.and()
		.authorizeRequests()
		.antMatchers("/",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js")
		.permitAll()
		//permit register page without password
		.antMatchers(SIGN_UP_URLS).permitAll()
		.antMatchers(H2_URL).permitAll()
		.anyRequest().authenticated();
		//any other things require authentication 
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}

}

