package org.hdcd.config;

import javax.sql.DataSource;

import org.hdcd.common.security.CustomAccessDeniedHandler;
import org.hdcd.common.security.CustomLoginSuccessHandler;
import org.hdcd.common.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/auth/login").permitAll()
			.antMatchers("/user/register", "/user/registerSuccess").permitAll()
			.antMatchers("/user/**").hasRole("ADMIN")
			.antMatchers("/codegroup/**").hasRole("ADMIN")
			.antMatchers("/codedetail/**").hasRole("ADMIN")
			.antMatchers("/board/list", "/board/read").permitAll()
			.antMatchers("/board/remove").hasAnyRole("MEMBER", "ADMIN")
			.antMatchers("/board/**").hasRole("MEMBER")
			.anyRequest().authenticated();
		
		http.formLogin()
			.loginPage("/auth/login")
			.loginProcessingUrl("/login")
			.successHandler(authenticationSuccessHandler());
		
		http.logout()
			.logoutUrl("/auth/logout")
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.deleteCookies("remember-me", "JSESSION_ID");
		
		http.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler());
		
		http.rememberMe()
			.key("hdcd")
			.tokenRepository(createJDBCRepository())
			.tokenValiditySeconds(60*60*24);
	}
	
	@Bean
	public UserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService())
			.passwordEncoder(passwordEncoder());
	}
	
	private PersistentTokenRepository createJDBCRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		
		return repo;
	}
	
}
