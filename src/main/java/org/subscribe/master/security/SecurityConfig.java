package org.subscribe.master.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.subscribe.master.exceptionHandling.ErrorResponseDTO;
import org.subscribe.master.security.jwtConfiguration.JWTFilter;

import java.time.Instant;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTFilter jwtFilter;
    private final ObjectMapper objectMapper;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JWTFilter jwtFilter, ObjectMapper objectMapper) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtFilter = jwtFilter;
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/users/register",
                                        "/users/login",
                                        "/users/refresh",

                                        "/subs/all",

                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**")
                                .permitAll()

                                .requestMatchers(
                                        "/subs/get/**").hasRole("ADMIN")

                                .requestMatchers(
                                        "users/statistics/mostExpSub"
                                ).hasRole("USER")

                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(customUserDetailsService);
        dao.setPasswordEncoder(passwordEncoder());

        return dao;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            authException.printStackTrace();
            String path = request.getRequestURI();
            String detail = authException.getMessage();
            int status = 401;
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(detail, status, detail, path, Instant.now());
            response.setStatus(status);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, errorResponseDTO);
        };

    }
}
