package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.CampaignRequestDto;
import nl.novi.baccampsite.dtos.CampaignResponseDto;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CampaignResponseDto>> retrieveCampaigns(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(campaignService.retrieveCampaigns(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponseDto>  retrieveCampaign(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.retrieveCampaign(id));
    }

    @PostMapping
    public ResponseEntity<CampaignResponseDto> createCampaign(@RequestBody CampaignRequestDto campaignRequestDto) {
        CampaignResponseDto campaignResponseDto = campaignService.createCampaign(campaignRequestDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + campaignResponseDto.id).toUriString());

        return ResponseEntity.created(uri).body(campaignResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponseDto>  updateCampaign(@PathVariable Long id, @RequestBody CampaignRequestDto campaignRequestDto) {
        return ResponseEntity.ok().body(campaignService.updateCampaign(id, campaignRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.deleteCampaign(id));
    }
}
