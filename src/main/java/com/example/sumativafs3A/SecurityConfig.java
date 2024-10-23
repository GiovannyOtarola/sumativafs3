package com.example.sumativafs3A;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/usuarios/**").hasRole("admin") 
                                .anyRequest().authenticated() 
                )
                .httpBasic(withDefaults()); 

        return http.build(); 
    }

    // Usuario admin en memoria para pruebas rapidas
    @Bean
    public UserDetailsService usuarioDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
            org.springframework.security.core.userdetails.User.withUsername("admin")
                .password(passwordEncoder.encode("adminPass"))
                .roles("admin")
                .build(),
            org.springframework.security.core.userdetails.User.withUsername("user")
                .password(passwordEncoder.encode("userPass"))
                .roles("user")
                .build()
        );
    }
}