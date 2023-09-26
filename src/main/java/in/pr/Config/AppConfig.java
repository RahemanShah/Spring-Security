package in.pr.Config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions (JWT)
                .and()
                
            .authorizeRequests(authorize -> 
                authorize
                    .requestMatchers("/api/**").authenticated() // Secure /api/** endpoints
                    .anyRequest().permitAll() // Allow all other requests
            )
            
            .addFilterBefore(new JwtValidator(), UsernamePasswordAuthenticationFilter.class) // Add JwtValidator before UsernamePasswordAuthenticationFilter
            .cors() // Enable CORS (Cross-Origin Resource Sharing)
                .and()
                
                
            .csrf().disable() // Disable CSRF (Cross-Site Request Forgery)
            .httpBasic().disable() // Disable HTTP Basic Authentication
            .formLogin().disable() // Disable form-based login
            .headers().frameOptions().disable() // Disable X-Frame-Options header (if needed)
            
            
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff"))
                .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block"))
                .and()
                
                
            .cors().configurationSource(request -> {
            	
                CorsConfiguration corsConfig = new CorsConfiguration();
                
                corsConfig.addAllowedOrigin("http://localhost:4200");
                corsConfig.setAllowedMethods(Arrays.asList("*"));
                corsConfig.setAllowCredentials(true);
                corsConfig.setAllowedHeaders(Collections.singletonList("*"));
                corsConfig.addExposedHeader("Authorization");
                corsConfig.setMaxAge(3600L);
                
                return corsConfig;
            });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password hashing and comparison
    }
}
