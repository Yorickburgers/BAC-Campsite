package nl.novi.baccampsite.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.novi.baccampsite.exceptions.UsernameNotFoundException;
import nl.novi.baccampsite.services.CustomUserDetailsService;
import nl.novi.baccampsite.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    public JwtRequestFilter(CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPublicPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            setError(response, "Missing or invalid Authorization header");
            return;
        }

        jwt = authorizationHeader.substring(7);
        try {
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            setError(response, "Invalid or expired JWT token");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                } else {
                    setError(response, "JWT token is invalid or does not match user");
                    return;
                }
            } catch (UsernameNotFoundException e) {
                setError(response, "User not found: " + e.getMessage());
                return;
            } catch (Exception e) {
                setError(response, "Authentication error: " + e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();
        return ("POST".equalsIgnoreCase(method) && (path.equals("/users")
                || path.equals("/authenticate")
        ));
    }

    private void setError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("""
                {
                    "error": "%s"
                }
        """.formatted(message));
    }
}
