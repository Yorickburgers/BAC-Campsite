package nl.novi.baccampsite.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InvalidPathController {
    @RequestMapping("/**")
    public ResponseEntity<Object> invalidPath(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "status", 404,
                "message", "Invalid path: " + request.getRequestURI()
        ));
    }
}
