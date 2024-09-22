package com.git.Nog022.MeetHub.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig  {
    public static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Bean
    public InMemoryUserDetailsManager userDatailService(PasswordEncoder passwordEncoder){
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password1"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        logger.info("Senha do user: " + passwordEncoder.encode("password"));
        logger.info("Senha do admin: " + passwordEncoder.encode("admin"));

        return new InMemoryUserDetailsManager(user, admin);

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request.anyRequest()
                .authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }





}
