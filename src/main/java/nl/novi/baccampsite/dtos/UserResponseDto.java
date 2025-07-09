package nl.novi.baccampsite.dtos;

import java.util.ArrayList;
import java.util.List;

public class UserResponseDto {
    public Long id;
    public String username;
    public String password;
    public String email;
    public List<CharacterSummaryDto> characters = new ArrayList<>();
}
