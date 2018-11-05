package com.github.kaczors.home.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/js/**", "/webjars/**", "/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login.html")
                .failureUrl("/login.html?error=true")
                .defaultSuccessUrl("/")
                .permitAll();
        // @formatter:on
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("user").password("password").roles("USER");

    }
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
////        	 * PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
////	 * // outputs {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
////	 * // remember the password that is printed out and use in the next step
////	 * System.out.println(encoder.encode("password"));
//        UserDetails user = User
//            .withUsername("user")
//            .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
//            .roles("USER")
//            .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
