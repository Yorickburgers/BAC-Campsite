package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.CampaignRequestDto;
import nl.novi.baccampsite.dtos.CampaignResponseDto;
import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.exceptions.BadRequestException;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.exceptions.UsernameNotFoundException;
import nl.novi.baccampsite.mappers.CampaignMapper;
import nl.novi.baccampsite.mappers.CharacterMapper;
import nl.novi.baccampsite.models.Campaign;
import nl.novi.baccampsite.models.Character;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.repositories.CampaignRepository;
import nl.novi.baccampsite.repositories.CharacterRepository;
import nl.novi.baccampsite.repositories.ProfessionRepository;
import nl.novi.baccampsite.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;
    private final CharacterRepository characterRepository;
    private final ProfessionRepository professionRepository;
    private final UserRepository userRepository;

    public CampaignService(CampaignRepository campaignRepository, CharacterRepository characterRepository, ProfessionRepository professionRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.characterRepository = characterRepository;
        this.professionRepository = professionRepository;
        this.userRepository = userRepository;
    }

    public List<CampaignResponseDto> retrieveAllCampaigns() {
        List<CampaignResponseDto> campaigns = new ArrayList<>();
        campaignRepository.findAll().forEach(campaign -> campaigns.add(CampaignMapper.toCampaignResponseDto(campaign)));
        return campaigns;
    }

    public CampaignResponseDto retrieveCampaign(Long id) {
        return CampaignMapper.toCampaignResponseDto(campaignRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Campaign " + id + " not found!")));
    }

    public CampaignResponseDto createCampaign(CampaignRequestDto campaignRequestDto, UserDetails userDetails) {
        Campaign campaign = CampaignMapper.toCampaign(campaignRequestDto);
        campaign.setDungeonMaster(userRepository.findById(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User " + userDetails.getUsername() + " not found!")));
        return CampaignMapper.toCampaignResponseDto(campaignRepository.save(campaign));
    }

    public CharacterResponseDto createCharacterForCampaign(Long id, CharacterRequestDto characterRequestDto) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Campaign " + id + " not found!"));
        Profession profession = professionRepository.findById(characterRequestDto.professionId)
                .orElseThrow(() -> new RecordNotFoundException("Profession " + characterRequestDto.professionId + " not found!"));
        Character character = CharacterMapper.toCharacter(characterRequestDto, profession);
        character.setUser(null);
        character.setCampaign(campaign);
        return CharacterMapper.toCharacterResponseDto(characterRepository.save(character));
    }

    public CampaignResponseDto updateCampaign(Long id, CampaignRequestDto campaignRequestDto) {
        Campaign currentCampaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Campaign " + id + " not found!"));
        CampaignMapper.updateCampaignFromDto(campaignRequestDto, currentCampaign);
        return CampaignMapper.toCampaignResponseDto(campaignRepository.save(currentCampaign));
    }

    public CampaignResponseDto assignCharacterToCampaign(Long campaignId, Long characterId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RecordNotFoundException("Campaign " + campaignId + " not found!"));
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RecordNotFoundException("Character " + characterId + " not found!"));
        if (campaign.getCharacters().contains(character)) {
            throw new BadRequestException("Character is already part of this campaign!");
        }
        return CampaignMapper.toCampaignResponseDto(campaignRepository.save(campaign));
    }

    public CampaignResponseDto removeCharacterFromCampaign(Long campaignId, Long characterId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RecordNotFoundException("Campaign " + campaignId + " not found!"));
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RecordNotFoundException("Character " + characterId + " not found!"));
        if (campaign.getCharacters() == null || !campaign.getCharacters().remove(character)) {
            throw new RecordNotFoundException("Character is not part of this campaign!");
        }
        return CampaignMapper.toCampaignResponseDto(campaignRepository.save(campaign));
    }

    public String deleteCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Campaign " + id + " not found!"));
        campaignRepository.delete(campaign);
        return "Campaign " + campaign.getName() + "with id " + id + " has been deleted!";
    }
}
