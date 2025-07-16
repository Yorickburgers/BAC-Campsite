package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.CampaignRequestDto;
import nl.novi.baccampsite.dtos.CampaignResponseDto;
import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.exceptions.ForbiddenException;
import nl.novi.baccampsite.models.Campaign;
import nl.novi.baccampsite.services.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public ResponseEntity<List<CampaignResponseDto>> retrieveAllCampaigns() {
        return ResponseEntity.ok(campaignService.retrieveAllCampaigns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponseDto>  retrieveCampaign(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.retrieveCampaign(id));
    }

    @PostMapping
    public ResponseEntity<CampaignResponseDto> createCampaign(@RequestBody CampaignRequestDto campaignRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        CampaignResponseDto campaignResponseDto = campaignService.createCampaign(campaignRequestDto, userDetails);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + campaignResponseDto.id).toUriString());

        return ResponseEntity
                .created(uri)
                .header("Message",
                        "Campaign " + campaignRequestDto.name + " has been created!")
                .body(campaignResponseDto);
    }

    @PostMapping("/{id}/characters")
    public ResponseEntity<CharacterResponseDto> createCharacterForCampaign(@PathVariable Long id, @RequestBody CharacterRequestDto characterRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        CampaignResponseDto campaign = campaignService.retrieveCampaign(id);
        if (!campaign.dungeonMaster.equals(userDetails.getUsername())) {
            throw new ForbiddenException("Only the Dungeon Master may create an unclaimed character for their campaign.");
        }
        CharacterResponseDto character = campaignService.createCharacterForCampaign(id, characterRequestDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + character.id).toUriString());

        return ResponseEntity
                .created(uri)
                .header("Message",
                        "Character " + character.name + " has been created!")
                .body(character);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponseDto>  updateCampaign(@PathVariable Long id, @RequestBody CampaignRequestDto campaignRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        CampaignResponseDto campaign = campaignService.retrieveCampaign(id);
        if (!campaign.dungeonMaster.equals(userDetails.getUsername())) {
            throw new ForbiddenException("You are only allowed to edit a campaign if you are its Dungeon Master.");
        }
        return ResponseEntity
                .ok()
                .header("Message",
                        "Campaign has been updated.")
                .body(campaignService.updateCampaign(id, campaignRequestDto));
    }

    @PostMapping("/{campaignId}/characters/{characterId}")
    public ResponseEntity<CampaignResponseDto> assignCharacterToCampaign(@PathVariable Long campaignId, @PathVariable Long characterId, @AuthenticationPrincipal UserDetails userDetails) {
        CampaignResponseDto campaign = campaignService.retrieveCampaign(campaignId);
        if (campaign.dungeonMaster.equals(userDetails.getUsername())) {
            throw new ForbiddenException("You cannot assign a character to your own campaign.");
        }
        return ResponseEntity
                .ok()
                .header("Message",
                        "Character succesfully assigned to campaign " + campaign.name + "!")
                .body(campaignService.assignCharacterToCompaign);

    }

    @PutMapping("/{campaignId}/characters/{characterId}")
    public ResponseEntity<CampaignResponseDto> removeCharacterFromCampaign(@PathVariable Long campaignId, @PathVariable Long characterId, @AuthenticationPrincipal UserDetails userDetails) {
        CampaignResponseDto campaign = campaignService.retrieveCampaign(campaignId);
        if (!campaign.dungeonMaster.equals(userDetails.getUsername())) {
            throw new ForbiddenException("Only the Dungeon Master can remove characters from their campaign.");
        }
        return ResponseEntity
                .ok()
                .header("Message",
                        "Character has been removed from campaign!")
                .body(campaignService.removeCharacterFromCampaign(characterId, campaignId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        CampaignResponseDto campaign = campaignService.retrieveCampaign(id);
        if (!campaign.dungeonMaster.equals(userDetails.getUsername())) {
            throw new ForbiddenException("You are only allowed to delete your own campaigns.");
        }
        return ResponseEntity
                .ok(campaignService.deleteCampaign(id));
    }
}
