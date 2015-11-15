package org.mingle.pear.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/resources/j_spring_security_check")
			.failureUrl("/login?login_error=t")
			.and()
			.logout()
			.logoutUrl("/resources/j_spring_security_logout");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.passwordEncoder(new ShaPasswordEncoder())
			.withUser("admin").password("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918").roles("ADMIN").and()
			.withUser("user").password("04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb").roles("USER");
	}

}
