package com.tbland.whale.config;

import com.tbland.whale.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(
            CustomUserDetailsService customUserDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customUserDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth ->auth
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/accounts/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/checkout").permitAll()

                                .requestMatchers("/api/cart-items/**").permitAll()
                                .requestMatchers("/api/carts/**").permitAll()
                         .requestMatchers("/api/products/search/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/cart-items").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/order-details/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
//                      .requestMatchers("/api/products/**").hasRole("ADMIN")
                        .requestMatchers("/api/categories/**").hasRole("ADMIN")
//                        .requestMatchers("/api/orders/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/api/orders/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/brands/**").permitAll()
                                .requestMatchers("/api/brands/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        return http.build();
    }
    @Bean
    public AuthenticationManager   authenticationManager(
            AuthenticationConfiguration configuration
    )throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://127.0.0.1:5500",
                "http://localhost:5500"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}
