package com.example.__2021142_2022002.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(h -> h.frameOptions(f -> f.disable())) // για H2 console
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/", "/home", "/home.html",
                        "/login", "/login.html",
                        "/register", "/register.html",
                        "/error", "/error.html",
                        "/AdminDashboard", "/AdminDashboard.html",
                        "/BackerDashboard", "/BackerDashboard.html",
                        "/CreatorDashboard", "/CreatorDashboard.html",
                        "/projectList", "/projectList.html",
                        "/projectDetails", "/projectDetails.html",
                        "/projectCreate", "/projectCreate.html",
                        "/projectPending", "/projectPending.html",
                        "/css/**", "/js/**", "/images/**", "/img/**", "/webjars/**"
                ).permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/projects/**", "/support/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)  // μπορείς να το αλλάξεις π.χ. "/AdminDashboard"
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
