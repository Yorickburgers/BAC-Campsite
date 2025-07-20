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
import nl.novi.baccampsite.models.User;
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

    public List<CampaignResponseDto> retrieveAllCampaignsForUser(String username) {
        List<CampaignResponseDto> campaigns = new ArrayList<>();
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found!"));
        user.getCharacters().forEach(character -> campaigns.add(CampaignMapper.toCampaignResponseDto(character.getCampaign())));
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
        character.setCampaign(campaign);
        character.setUser(null);
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
        if (character.getCampaign() != null && character.getCampaign().getId().equals(campaignId)) {
            throw new BadRequestException("Character is already part of this campaign!");
        }
        if (campaign.getCharacters().stream().anyMatch(ch ->
                ch.getUser().getUsername().equals(character.getUser().getUsername()))) {
            throw new BadRequestException("Each user may only have one character per campaign");
        }
        character.setCampaign(campaign);
        characterRepository.save(character);
        campaign.getCharacters().add(character);
        return CampaignMapper.toCampaignResponseDto(campaignRepository.save(campaign));
    }

    public CampaignResponseDto removeCharacterFromCampaign(Long campaignId, Long characterId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RecordNotFoundException("Campaign " + campaignId + " not found!"));
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RecordNotFoundException("Character " + characterId + " not found!"));
        if (character.getCampaign() == null || !campaignId.equals(character.getCampaign().getId())) {
            throw new RecordNotFoundException("Character is not part of this campaign!");
        }
        if (character.getUser() == null) {
            throw new BadRequestException("Characters will disappear if removed from campaign without an owner! Choose to delete instead if intended. ");
        }
        character.setCampaign(null);
        characterRepository.save(character);
        campaign.getCharacters().remove(character);
        return CampaignMapper.toCampaignResponseDto(campaignRepository.save(campaign));
    }

    public String deleteCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Campaign " + id + " not found!"));
        campaignRepository.delete(campaign);
        return "Campaign " + campaign.getName() + "with id " + id + " has been deleted!";
    }
}
