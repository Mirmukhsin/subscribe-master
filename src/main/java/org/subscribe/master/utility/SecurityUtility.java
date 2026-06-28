package org.subscribe.master.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.subscribe.master.exceptionHandling.customExceptions.BadCredentialsException;
import org.subscribe.master.security.jwtConfiguration.JWTService;

@Component
public class SecurityUtility {
    private final JWTService jwtService;

    public SecurityUtility(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialsException("User not authenticated");
        }
        String token = authentication.getCredentials().toString();
        return jwtService.getUserId(token);
    }
}
