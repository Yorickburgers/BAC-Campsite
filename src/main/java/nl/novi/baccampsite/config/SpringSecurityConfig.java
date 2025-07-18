package nl.novi.baccampsite.config;

import nl.novi.baccampsite.filter.JwtRequestFilter;
import nl.novi.baccampsite.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                .requestMatchers("/authenticate").permitAll()

                                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                                .requestMatchers("/users/*/authorities/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/characters").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/campaigns").hasRole("ADMIN")
                                .requestMatchers("/professions/**").hasRole("ADMIN")
                                .requestMatchers("/specializations/**").hasRole("ADMIN")

                                .requestMatchers("/users/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/specializations/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/professions/**").authenticated()
                                .requestMatchers("/characters/**").authenticated()
                                .requestMatchers("/campaigns/**").authenticated()
                                .requestMatchers("/authenticated").authenticated()

                                .anyRequest().denyAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler)
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
