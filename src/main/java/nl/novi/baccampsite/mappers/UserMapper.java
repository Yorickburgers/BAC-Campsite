package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.UserRequestDto;
import nl.novi.baccampsite.dtos.UserResponseDto;
import nl.novi.baccampsite.models.User;

public class UserMapper {
    public static UserResponseDto toUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.email = user.getEmail();
        return dto;
    }

    public static User toUser(UserRequestDto dto) {
        User user = new User();
        updateUserFromDto(dto, user);
        return user;
    }

    public static void updateUserFromDto(UserRequestDto dto, User user) {
        user.setUsername(dto.username);
        user.setPassword(dto.password);
        user.setEmail(dto.email);
    }
}
