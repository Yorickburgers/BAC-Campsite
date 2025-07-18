package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.ProfessionRequestDto;
import nl.novi.baccampsite.dtos.ProfessionResponseDto;
import nl.novi.baccampsite.dtos.SpecializationSummaryDto;
import nl.novi.baccampsite.services.ProfessionService;
import nl.novi.baccampsite.services.SpecializationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/professions")
public class ProfessionController {
    private final ProfessionService professionService;
    private final SpecializationService specializationService;

    public ProfessionController(ProfessionService professionService, SpecializationService specializationService) {
        this.professionService = professionService;
        this.specializationService = specializationService;
    }

    @GetMapping
    public ResponseEntity<List<ProfessionResponseDto>> retrieveAllProfessions() {
        return ResponseEntity.ok(professionService.retrieveAllProfessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionResponseDto>  retrieveProfession(@PathVariable Long id) {
        return ResponseEntity.ok(professionService.retrieveProfession(id));
    }

    @GetMapping("/{professionId}/specializations")
    public ResponseEntity<List<SpecializationSummaryDto>> retrieveSpecializationsByProfession(@PathVariable Long professionId) {
        return ResponseEntity.ok(specializationService.retrieveSpecializationsByProfession(professionId));
    }

    @PostMapping
    public ResponseEntity<ProfessionResponseDto> createProfession(@RequestBody ProfessionRequestDto professionRequestDto) {
        ProfessionResponseDto professionResponseDto = professionService.createProfession(professionRequestDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + professionResponseDto.id).toUriString());

        return ResponseEntity
                .created(uri)
                .header("Message",
                        "Profession " + professionResponseDto.name + " created!")
                .body(professionResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionResponseDto>  updateProfession(@PathVariable Long id, @RequestBody ProfessionRequestDto professionRequestDto) {
        return ResponseEntity
                .ok()
                .header("Message",
                        "Profession " + professionRequestDto.name + " updated!")
                .body(professionService.updateProfession(id, professionRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfession(@PathVariable Long id) {
        return ResponseEntity.ok(professionService.deleteProfession(id));
    }
}
