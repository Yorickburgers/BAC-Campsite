package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.CampaignRequestDto;
import nl.novi.baccampsite.dtos.CampaignResponseDto;
import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.services.CampaignService;
import org.springframework.http.HttpStatus;
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

        return ResponseEntity.created(uri).body(campaignResponseDto);
    }

    @PostMapping("/{id}/characters")
    public ResponseEntity<CharacterResponseDto> createCharacterForCampaign(@PathVariable Long id, @RequestBody CharacterRequestDto characterRequestDto) {
        CharacterResponseDto character = campaignService.createCharacterForCampaign(id, characterRequestDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + character.id).toUriString());

        return ResponseEntity.created(uri).body(character);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponseDto>  updateCampaign(@PathVariable Long id, @RequestBody CampaignRequestDto campaignRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        CampaignResponseDto campaign = campaignService.retrieveCampaign(id);
        if (!campaign.dungeonMaster.equals(userDetails.getUsername())) {
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().body(campaignService.updateCampaign(id, campaignRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        CampaignResponseDto campaign = campaignService.retrieveCampaign(id);
        if (!campaign.dungeonMaster.equals(userDetails.getUsername())) {
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to delete this campaign");
        }
        return ResponseEntity.ok(campaignService.deleteCampaign(id));
    }
}
