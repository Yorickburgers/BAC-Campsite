package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.CampaignRequestDto;
import nl.novi.baccampsite.dtos.CampaignResponseDto;
import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.mappers.CampaignMapper;
import nl.novi.baccampsite.mappers.CharacterMapper;
import nl.novi.baccampsite.models.Campaign;
import nl.novi.baccampsite.models.Character;
import nl.novi.baccampsite.repositories.CampaignRepository;
import nl.novi.baccampsite.repositories.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;
    private final CharacterRepository characterRepository;

    public CampaignService(CampaignRepository campaignRepository, CharacterRepository characterRepository) {
        this.campaignRepository = campaignRepository;
        this.characterRepository = characterRepository;
    }

    public List<CampaignResponseDto> retrieveAllCampaigns() {
        List<CampaignResponseDto> campaigns = new ArrayList<>();
        campaignRepository.findAll().forEach(campaign -> campaigns.add(CampaignMapper.toCampaignResponseDto(campaign)));
        return campaigns;
    }

    public CampaignResponseDto retrieveCampaign(Long id) {
        return CampaignMapper.toCampaignResponseDto(campaignRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Campaign " + id + " not found!")));
    }

    public CampaignResponseDto createCampaign(CampaignRequestDto campaignRequestDto) {
        Campaign campaign = CampaignMapper.toCampaign(campaignRequestDto);
        return CampaignMapper.toCampaignResponseDto(campaignRepository.save(campaign));
    }

    public CharacterResponseDto createCharacterForCampaign(Long id, CharacterRequestDto characterRequestDto) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Campaign " + id + " not found!"));
        Character character = CharacterMapper.toCharacter(characterRequestDto);
        character.setUser(null);
        character.setCampaign(campaign);
        return CharacterMapper.toCharacterResponseDto(characterRepository.save(character));
    }

    public CampaignResponseDto updateCampaign(Long id, CampaignRequestDto campaignRequestDto) {
        Campaign currentCampaign = campaignRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Campaign " + id + " not found!"));
        currentCampaign.setName(campaignRequestDto.name);
        currentCampaign.setDescription(campaignRequestDto.description);
        return CampaignMapper.toCampaignResponseDto(campaignRepository.save(currentCampaign));
    }

    public String deleteCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Campaign " + id + " not found!"));
        campaignRepository.delete(campaign);
        return "Campaign " + campaign.getName() + "with id " + id + " has been deleted!";
    }
}
