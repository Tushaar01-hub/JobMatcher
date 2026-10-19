package com.jobMatcher.project.security;

import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.enums.AuthProvider;
import com.jobMatcher.project.enums.Role;
import com.jobMatcher.project.exception.DuplicateResourceException;
import com.jobMatcher.project.repository.UserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {
    private final UserRepository userRepository;

    public CustomOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
            throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);

        String providerSubject = oidcUser.getSubject();
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();

        // find/create our User here
        Optional<User> existingUser=userRepository.findByProviderAndProviderSubject(
                AuthProvider.GOOGLE,
                providerSubject
        );
        if(existingUser.isPresent()){
            return oidcUser;
        }
        else if(userRepository.existsByEmail(email)){
            OAuth2Error error = new OAuth2Error(
                    "account_exists",
                    "An account already exists with this email. Please log in using email/password.",
                    null
            );

            throw new OAuth2AuthenticationException(error);
        }
        User user= new User();
        user.setEmail(email);
        user.setRole(Role.USER);
        user.setName(name);
        user.setProvider(AuthProvider.GOOGLE);
        user.setProviderSubject(providerSubject);
        User savedUser=userRepository.save(user);

        return oidcUser;
    }
}
