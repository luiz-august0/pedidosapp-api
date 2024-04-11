package com.pedidosapp.api.config.security;

import com.pedidosapp.api.constants.paths.Paths;
import com.pedidosapp.api.controller.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Value(Paths.prefixPath)
    private String prefixPath;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        final String[] blockedEndpointsEmployees = {
                ISupplierController.PATH.replace(Paths.prefixPath, prefixPath) + "/**",
                IUserController.PATH.replace(Paths.prefixPath, prefixPath)  + "/**",
                ICustomerController.PATH.replace(Paths.prefixPath, prefixPath)  + "/**",
                IProductController.PATH.replace(Paths.prefixPath, prefixPath)  + "/**",
                IEmployeeController.PATH.replace(Paths.prefixPath, prefixPath)  + "/**"
        };

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                IAuthenticationController.PATH.replace(Paths.prefixPath, prefixPath) + "/**",
                                "/v3/api-docs/**",
                                "/configuration/ui",
                                "/swagger-resources/**",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/webjars/**",
                                "/swagger-ui/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, blockedEndpointsEmployees).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, blockedEndpointsEmployees).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, blockedEndpointsEmployees).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, blockedEndpointsEmployees).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
