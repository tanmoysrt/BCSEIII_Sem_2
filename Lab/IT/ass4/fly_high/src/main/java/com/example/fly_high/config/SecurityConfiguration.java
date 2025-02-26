package com.example.fly_high.config;

import com.example.fly_high.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    @NonNull
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @NonNull

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/flight/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/flight/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/flight/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/offer").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/offer/**").permitAll()
                .requestMatchers("/auth/register/admin", "/auth/register/staff").permitAll()
                .requestMatchers("/auth/users").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/auth/user/**").permitAll()
                .requestMatchers("/flight", "/flight/**", "/offer", "/offer/**", "/offer/generic", "/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // allow all origins
        configuration.addAllowedMethod("*"); // allow all methods
        configuration.addAllowedHeader("*"); // allow all headers
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
