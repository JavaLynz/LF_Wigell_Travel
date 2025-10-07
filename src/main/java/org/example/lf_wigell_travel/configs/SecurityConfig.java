package org.example.lf_wigell_travel.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private LoggingFilter loggingFilter;

    @Autowired
    public SecurityConfig(LoggingFilter loggingFilter) {
        this.loggingFilter = loggingFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(loggingFilter, BasicAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails lynseyfox = User
                .withUsername("lynseyfox")
                .password("{noop}Lf123!")
                .roles("ADMIN")
                .build();

        UserDetails niklaseinarsson = User
                .withUsername("niklaseinarsson")
                .password("{noop}Ne123!")
                .roles("USER")
                .build();

        UserDetails christofferfrisk = User
                .withUsername("christofferfrisk")
                .password("{noop}Cf123!")
                .roles("USER")
                .build();

        UserDetails benjaminportsmouth = User
                .withUsername("benjaminportsmouth")
                .password("{noop}Bp123!")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(lynseyfox, niklaseinarsson, christofferfrisk, benjaminportsmouth);
    }

    public LoggingFilter getLoggingFilter() {
        return loggingFilter;
    }

    public void setLoggingFilter(LoggingFilter loggingFilter) {
        this.loggingFilter = loggingFilter;
    }
}
