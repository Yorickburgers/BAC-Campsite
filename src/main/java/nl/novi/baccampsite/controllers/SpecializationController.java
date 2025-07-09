package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.SpecializationRequestDto;
import nl.novi.baccampsite.dtos.SpecializationResponseDto;
import nl.novi.baccampsite.services.SpecializationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SpecializationController {
    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping("/professions/{professionId}/specializations")
    public ResponseEntity<List<SpecializationResponseDto>> retrieveSpecializationsByProfession(@PathVariable Long professionId) {
        return ResponseEntity.ok(specializationService.retrieveSpecializationsByProfession(professionId));
    }

    @GetMapping("/specializations")
    public ResponseEntity<List<SpecializationResponseDto>> retrieveAllSpecializations() {
        return ResponseEntity.ok(specializationService.retrieveAllSpecializations());
    }

    @GetMapping("/specializations/{id}")
    public ResponseEntity<SpecializationResponseDto>  retrieveSpecialization(@PathVariable Long id) {
        return ResponseEntity.ok(specializationService.retrieveSpecialization(id));
    }

    @PostMapping("/specializations")
    public ResponseEntity<SpecializationResponseDto> createSpecializationForProfession(@RequestBody SpecializationRequestDto specializationRequestDto) {
        SpecializationResponseDto specializationResponseDto = specializationService.createSpecialization(specializationRequestDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + specializationResponseDto.id).toUriString());

        return ResponseEntity.created(uri).body(specializationResponseDto);
    }

    @PutMapping("/specializations/{id}")
    public ResponseEntity<SpecializationResponseDto>  updateSpecialization(@PathVariable Long id, @RequestBody SpecializationRequestDto specializationRequestDto) {
        return ResponseEntity.ok().body(specializationService.updateSpecialization(id, specializationRequestDto));
    }

    @DeleteMapping("/specializations/{id}")
    public ResponseEntity<String> deleteSpecialization(@PathVariable Long id) {
        return ResponseEntity.ok(specializationService.deleteSpecialization(id));
    }
}
