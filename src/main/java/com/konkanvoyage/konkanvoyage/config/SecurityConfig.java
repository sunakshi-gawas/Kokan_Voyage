package com.konkanvoyage.konkanvoyage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/", "/login", "/book", "/ticket/**", "/confirmation",
                                                                "/style.css", "/assets/**")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/admin/bookings", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/")
                                                .permitAll());
                return http.build();
        }

        // ✅ In-memory admin user
        @Bean
        public InMemoryUserDetailsManager userDetailsService() {
                UserDetails admin = User.withUsername("admin")
                                .password(passwordEncoder().encode("admin123"))
                                .roles("ADMIN")
                                .build();
                return new InMemoryUserDetailsManager(admin);
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
