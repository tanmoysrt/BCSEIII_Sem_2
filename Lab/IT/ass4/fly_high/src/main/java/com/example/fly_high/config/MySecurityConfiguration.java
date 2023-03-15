//package com.example.fly_high.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//public class MySecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers(HttpMethod.POST, "flight/search").permitAll()
//                .requestMatchers(HttpMethod.POST, "/flight/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/flight/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/flight/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.GET, "/flight/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "/offer/**").hasAnyRole("ADMIN", "STAFF")
//                .requestMatchers(HttpMethod.PUT, "/offer/**").hasAnyRole("ADMIN", "STAFF")
//                .requestMatchers(HttpMethod.DELETE, "/offer/**").hasAnyRole("ADMIN", "STAFF")
//                .requestMatchers(HttpMethod.GET, "/offer/**").permitAll()
//                .and()
//                .httpBasic(withDefaults())
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        ;
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}