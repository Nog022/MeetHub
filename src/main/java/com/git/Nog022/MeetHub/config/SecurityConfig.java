package com.git.Nog022.MeetHub.config;import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig  {

    public static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        logger.info("Configuring HttpSecurity");
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        //HealthController
                        .requestMatchers(HttpMethod.GET, "/health").permitAll()

                        //Auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        //user
                        .requestMatchers(HttpMethod.POST, "/api/users/save").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/update/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/listAll").hasRole("ADMIN")

                        //institution
                        .requestMatchers(HttpMethod.POST, "/institution/save").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/institution/findUsersInstitution/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/institution/refresh").hasAnyRole("USER", "ADMIN")

                        //Local
                        .requestMatchers(HttpMethod.POST, "/api/locations/save").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/locations/listLocal").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/locations/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/locations/localById/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/locations/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/locations/findAddressByZipCode/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/locations/listLocalByInstitution/**").hasAnyRole("USER", "ADMIN")

                        //Reservation
                        .requestMatchers(HttpMethod.POST, "/api/reservations/save").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reservations/reservationById/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/reservations/update").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/reservations/delete/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reservations/listReservations").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reservations/listReservationsByInstitution/**").hasAnyRole("USER", "ADMIN")

                        //Room
                        .requestMatchers(HttpMethod.POST, "/api/rooms/save").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/rooms/listRoom").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/rooms/update/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/rooms/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/rooms/listRoomByLocal/**").hasAnyRole("USER", "ADMIN")






                        .anyRequest().permitAll()

                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.info("Configuring AuthenticationManager");
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Configuring PasswordEncoder");
        return new BCryptPasswordEncoder();
    }

}