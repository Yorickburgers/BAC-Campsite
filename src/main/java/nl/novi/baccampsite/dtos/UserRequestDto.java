package nl.novi.baccampsite.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDto {
    @NotBlank(message = "Please provide a username")
    @Size(min = 6, max = 24, message = "Username must be between 6 and 24 characters long")
    public String username;
    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 24, message = "Password must be at between 4 and 24 characters long")
    public String password;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    public String email;
}
