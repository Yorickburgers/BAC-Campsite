package nl.novi.baccampsite.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.novi.baccampsite.models.Authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserResponseDto {
    public String username;
    public String password;
    public String email;
    public List<CharacterSummaryDto> characters = new ArrayList<>();
    public List<CampaignResponseDto> campaigns = new ArrayList<>();
    @JsonSerialize
    public Set<Authority> authorities;
}
