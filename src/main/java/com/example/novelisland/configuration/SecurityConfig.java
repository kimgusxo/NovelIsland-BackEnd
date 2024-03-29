package com.example.novelisland.configuration;

import com.example.novelisland.Jwt.JwtAuthenticationFilter;
import com.example.novelisland.Jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeHttpRequests()
                        .antMatchers("/swagger-ui.html#/**").permitAll()
                        .antMatchers("/login/**").permitAll()
                        .antMatchers("/bookmark/**").authenticated()
                        .antMatchers("/user/**").authenticated()
                    .anyRequest().permitAll()
                .and()
                .cors()
                .configurationSource(request -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of("https://kimgusxo.github.io/", "https://kimgusxo.github.io/NovelIsland-FrontEnd/", "http://localhost:8080/")); // 허용할 오리진을 설정하세요
                    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
                    corsConfig.addExposedHeader("Authorization");
                    return corsConfig;
                }) // CORS 설정 추가
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
