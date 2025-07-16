package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.UserRequestDto;
import nl.novi.baccampsite.dtos.UserResponseDto;
import nl.novi.baccampsite.exceptions.ForbiddenException;
import nl.novi.baccampsite.services.UserService;
import nl.novi.baccampsite.exceptions.BadRequestException;
import nl.novi.baccampsite.utils.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> retrieveAllUsers() {
        return ResponseEntity
                .ok()
                .body(userService.retrieveAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto>  retrieveUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String username) {
        if (!userDetails.getUsername().equals(username)) {
            throw new ForbiddenException("You are only allowed to see your own details.");

        }
        return ResponseEntity
                .ok()
                .body(userService.retrieveUser(username));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        userService.addAuthority(userResponseDto.username, "ROLE_USER");
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + userResponseDto.username).toUriString());

        return ResponseEntity
                .created(uri)
                .header("Message",
                        "User created!")
                .body(userResponseDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponseDto>  updateUser(@PathVariable String username, @RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (!userDetails.getUsername().equals(username)) {
            throw new ForbiddenException("You can only update your own details.");

        }
        return ResponseEntity
                .ok()
                .header("Message",
                        "User details updated!")
                .body(userService.updateUser(username, userRequestDto));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username, @AuthenticationPrincipal UserDetails userDetails) {
        if (!SecurityUtil.isSelfOrAdmin(userDetails, username)) {
            throw new ForbiddenException("You can only delete your own details.");
        }
        return ResponseEntity
                .ok(userService.deleteUser(username));
    }

    @PutMapping("/{username}/authorities")
    public ResponseEntity<String> addUserAuthority(@PathVariable String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            return ResponseEntity
                    .ok(userService.addAuthority(username, authorityName));
        }
        catch (Exception e) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("/{username}/authorities/{authority}")
    public ResponseEntity<String> deleteUserAuthority(@PathVariable String username, @PathVariable String authority) {
        return ResponseEntity.ok(userService.removeAuthority(username, authority));
    }
}
