package nl.novi.baccampsite.dtos;

import nl.novi.baccampsite.models.Campaign;

import java.util.ArrayList;
import java.util.List;

public class UserResponseDto {
    public Long id;
    public String username;
    public String password;
    public String email;
    public List<CharacterSummaryDto> characters = new ArrayList<>();
    public List<CampaignResponseDto> campaigns = new ArrayList<>();
}
