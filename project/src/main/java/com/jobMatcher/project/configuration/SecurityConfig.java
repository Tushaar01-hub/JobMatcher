package com.jobMatcher.project.configuration;

import com.jobMatcher.project.security.CustomerUserDetails;
import com.jobMatcher.project.security.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.csrf(csrf->csrf.disable())
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth->
                auth.requestMatchers("/auth/login","/auth/register").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2->oauth2.jwt(
                        jwt->jwt.jwtAuthenticationConverter(
                                jwtAuthenticationConverter()
                        )
                ));

        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(CustomerUserDetailsService customerUserDetailsService
                                                         ) {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customerUserDetailsService);


        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
    @Bean
    public SecretKey secretKey(@Value("${jwt.secret}") String secret){
        byte[] decodedKey= Base64.getDecoder().decode(secret);
        return new SecretKeySpec(
                decodedKey,
                "HmacSHA256"
        );
    }
    @Bean
    public JwtEncoder jwtEncoder(SecretKey secretKey){
        return NimbusJwtEncoder.
                withSecretKey(secretKey)
                .algorithm(MacAlgorithm.HS256)
                .build();
    }
    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey){
        NimbusJwtDecoder decoder=
                NimbusJwtDecoder.withSecretKey(secretKey)
                        .macAlgorithm(MacAlgorithm.HS256)
                        .build();
        return decoder;
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter authoritiesConverter=new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("authorities");
        authoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter authenticationConverter=new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return authenticationConverter;
    }

}
