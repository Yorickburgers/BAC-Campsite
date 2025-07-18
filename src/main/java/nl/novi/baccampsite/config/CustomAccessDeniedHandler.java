package nl.novi.baccampsite.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        String uri = request.getRequestURI();

        if (!(uri.startsWith("/users") ||
                uri.startsWith("/authenticate") ||
                uri.startsWith("/campaigns") ||
                uri.startsWith("/characters") ||
                uri.startsWith("/professions") ||
                uri.startsWith("/specializations") ||
                uri.startsWith("/authenticated")
        )) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("""
                    {
                        "timestamp":  "%s",
                        "status": 404,
                        "message": "Invalid path!",
                        "path": "%s"
                    }
                    """.formatted(Instant.now(), uri
            ));
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("""
                        {
                        "timestamp": "%s",
                        "status": 403,
                        "message": "Access is denied: you don't have permission.",
                        "path": "%s"
                        }
                    """.formatted(
                    Instant.now(),
                    uri
            ));
        }
    }
}