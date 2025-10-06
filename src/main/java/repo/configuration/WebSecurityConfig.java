package repo.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import repo.constant.Endpoint;
import repo.entity.enums.UserRole;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private static final String ALL_SUB_PATH = "/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(
                                        Endpoint.PUBLIC + ALL_SUB_PATH,
                                        Endpoint.AUTHENTICATE,
                                        Endpoint.REFRESH_TOKEN,
                                        Endpoint.REGISTER + Endpoint.AUTHOR,
                                        Endpoint.SWAGGER_UI + ALL_SUB_PATH,
                                        Endpoint.API_DOCS + ALL_SUB_PATH
                                        )
                                .permitAll()

                                .requestMatchers(
                                        Endpoint.ADMIN + ALL_SUB_PATH,
                                        Endpoint.ACTUATOR + ALL_SUB_PATH
                                )
                                .hasAuthority(UserRole.ADMIN.getAuthority())

                                .requestMatchers(
                                        Endpoint.STAFF + ALL_SUB_PATH
                                )
                                .hasAuthority(UserRole.STAFF.getAuthority())

                                .requestMatchers(
                                        Endpoint.AUTHOR + ALL_SUB_PATH
                                )
                                .hasAuthority(UserRole.AUTHOR.getAuthority())

                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
