package br.com.project.spring.starter.template.api.configs;

import br.com.project.spring.starter.template.api.filters.AuthFilter;
import br.com.project.spring.starter.template.api.filters.LogFilter;
import br.com.project.spring.starter.template.api.handlers.ApiAccessDeniedHandler;
import br.com.project.spring.starter.template.api.handlers.ApiAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final ApiAccessDeniedHandler apiAccessDeniedHandler;
    private final ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;
    private final AuthFilter authFilter;
    private final LogFilter logFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/version",
                                        "/auth/login",
                                        "/auth/create-user"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigure()))
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(logFilter, AuthFilter.class)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(apiAccessDeniedHandler);
                    httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(apiAuthenticationEntryPoint);
                })
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigure() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
