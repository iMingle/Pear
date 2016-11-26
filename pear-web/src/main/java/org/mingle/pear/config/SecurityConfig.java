/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全配置
 * 
 * @since 1.8
 * @author Mingle
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/resources/**")
				.permitAll()
				.antMatchers("/images/**")
				.permitAll()
				.antMatchers("/styles/**")
				.permitAll()
				.antMatchers("/scripts/**")
				.permitAll()
				.antMatchers("/login/**")
				.permitAll()
				.antMatchers("/logout/**")
				.permitAll()
				.antMatchers("/**")
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/loginProcess")
				.failureUrl("/login?login_error=t")
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/logoutSuccess")
			.and()
				.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.passwordEncoder(new BCryptPasswordEncoder())
			.withUser("admin").password("$2a$10$E94cbGn/p5hEvj1Hmfo4UuUBjVTPkFWnGFGD4/iy35tFnvZe3h9Ke").authorities("ROLE_ADMIN")
			.and()
			.withUser("user").password("$2a$10$tQnNdm.r3gofhyYP3oITluyYs/LdLU1WswS9prYNdlKziWgYWMQVS").authorities("ROLE_USER");
	}

}
