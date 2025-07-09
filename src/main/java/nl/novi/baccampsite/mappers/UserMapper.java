package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.dtos.UserRequestDto;
import nl.novi.baccampsite.dtos.UserResponseDto;
import nl.novi.baccampsite.models.User;

import java.util.ArrayList;

public class UserMapper {
    public static UserResponseDto toUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.email = user.getEmail();
        dto.characters = new ArrayList<>();

                user.getCharacters().forEach(character ->
            dto.characters.add(CharacterMapper.toCharacterSummaryDto(character)));
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
