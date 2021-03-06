package com.edugroupe.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.edugroupe.demo.security.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired	private MyUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
								.antMatchers("/recette**","/recette").permitAll()
								.antMatchers("/users**","/users/").authenticated()
								.and().httpBasic()
								.and().csrf().disable();
								
								
//								.and().csrf().disable().cors()
//								.and().httpBasic();		
		// .antMatchers("/**").hasAnyAuthority("ADMIN")
		
		// Corectif pour pallier au CORS policy 
		http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cors = new CorsConfiguration().applyPermitDefaultValues();
                cors.addAllowedMethod(HttpMethod.OPTIONS);
                cors.addAllowedMethod(HttpMethod.PUT);
                cors.addAllowedMethod(HttpMethod.POST);
                cors.addAllowedMethod(HttpMethod.DELETE);
                cors.addAllowedMethod(HttpMethod.GET);
                cors.addAllowedOrigin("http://localhost:4200");
                return cors;
            }
        });
	}
	
}
