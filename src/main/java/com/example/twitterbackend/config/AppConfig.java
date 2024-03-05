package com.example.twitterbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class AppConfig {

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.sessionManagement((sessionManagement) ->
                sessionManagement
                        .sessionConcurrency((sessionConcurrency) ->
                                sessionConcurrency
                                        .maximumSessions(1)
                                        .expiredUrl("/login?expired")
                        )
        ).authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
        ).addFilterBefore(null, BasicAuthenticationFilter.class).
                csrf((csrf) -> csrf.disable()).cors(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll())
                .rememberMe(Customizer.withDefaults());


        return http.build();

    }
}
