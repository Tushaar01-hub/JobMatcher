package com.jobMatcher.project.security;

import com.jobMatcher.project.dtos.UserLoginResponseDTO;
import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public CustomOAuth2SuccessHandler(JwtService jwtService, UserRepository userRepository, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String email = oidcUser.getEmail();
        User existingUser=userRepository.findByEmail(email).orElseThrow(
                ()->new ResourceNotFoundException("User Not Found")
        );
        CustomerUserDetails userDetails =
                new CustomerUserDetails(existingUser);
        Authentication ourAuthentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        String token=jwtService.generateToken(ourAuthentication);

        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        new UserLoginResponseDTO(token)
                )
        );
    }
}
