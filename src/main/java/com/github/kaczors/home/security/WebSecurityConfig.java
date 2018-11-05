package com.github.kaczors.home.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/js/**", "/webjars/**", "/favicon.ico").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails paulinka = User
            .withUsername("paulinka")
            .password("{bcrypt}$2a$10$j.nfCrxuh9dLAtjd29W3i.bh2OkEbZvLA/X5oyteavpVuCTG8UbJq")
            .roles("USER")
            .build();

        UserDetails pawel = User
            .withUsername("pawel")
            .password("{bcrypt}$2a$10$F/sB2NMp9F9lLhbjlg3NIe/JNNXCfBt05YjF3F8qY/OWCsvirlHo.")
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(paulinka, pawel);
    }
}
