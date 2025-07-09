package nl.novi.baccampsite.dtos;

import java.util.List;

public class CampaignResponseDto {
    public Long id;
    public String name;
    public String description;
    public List<CharacterSummaryDto> characters;
}
