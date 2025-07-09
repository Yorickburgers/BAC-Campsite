package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.CampaignRequestDto;
import nl.novi.baccampsite.dtos.CampaignResponseDto;
import nl.novi.baccampsite.models.Campaign;

import java.util.ArrayList;

public class CampaignMapper {
    public static CampaignResponseDto toCampaignResponseDto(Campaign campaign) {
        CampaignResponseDto dto = new CampaignResponseDto();
        dto.id = campaign.getId();
        dto.name = campaign.getName();
        dto.description = campaign.getDescription();
        dto.characters = new ArrayList<>();
                campaign.getCharacters().forEach((character) ->
                        dto.characters.add(CharacterMapper.toCharacterSummaryDto(character)));
        return dto;
    }

    public static Campaign toCampaign(CampaignRequestDto dto) {
        Campaign campaign = new Campaign();
        updateCampaignFromDto(dto, campaign);
        return campaign;
    }

    public static void updateCampaignFromDto(CampaignRequestDto dto, Campaign campaign) {
        campaign.setName(dto.name);
        campaign.setDescription(dto.description);
    }
}