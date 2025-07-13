package nl.novi.baccampsite.utils;

import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
    public static boolean hasRole(UserDetails userDetails, String role) {
        return userDetails.getAuthorities().stream().anyMatch(auth ->
                auth.getAuthority().equals(role));
    }

    public static boolean isSelfOrAdmin(UserDetails userDetails, String username) {
        return userDetails.getUsername().equals(username) || hasRole(userDetails, "ROLE_ADMIN");
    }
}
