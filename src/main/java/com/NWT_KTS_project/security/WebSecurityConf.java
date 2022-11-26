package com.NWT_KTS_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConf {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll().and()
                .authorizeRequests().antMatchers("/client/*").hasRole("CLIENT").and()
                .authorizeRequests().antMatchers("/driver/*").hasRole("DRIVER").and()
                .authorizeRequests().antMatchers("/history/**").hasRole("ADMIN").and()

                // ALWAYS AT THE END OF THE CHAIN
                .authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.build();
    }
}
