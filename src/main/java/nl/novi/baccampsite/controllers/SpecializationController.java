package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.SpecializationRequestDto;
import nl.novi.baccampsite.dtos.SpecializationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/specializations")
public class SpecializationController {
    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping
    public ResponseEntity<List<SpecializationResponseDto>> retrieveSpecializations(@RequestParam Long professionId) {
        return ResponseEntity.ok(specializationService.retrieveSpecializations(professionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecializationResponseDto>  retrieveSpecialization(@PathVariable Long id) {
        return ResponseEntity.ok(specializationService.retrieveSpecialization(id));
    }

    @PostMapping
    public ResponseEntity<SpecializationResponseDto> createSpecialization(@RequestBody SpecializationRequestDto specializationRequestDto) {
        SpecializationResponseDto specializationResponseDto = specializationService.createSpecialization(specializationRequestDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + specializationResponseDto.id).toUriString());

        return ResponseEntity.created(uri).body(specializationResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecializationResponseDto>  updateSpecialization(@PathVariable Long id, @RequestBody SpecializationRequestDto specializationRequestDto) {
        return ResponseEntity.ok().body(specializationService.updateSpecialization(id, specializationRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpecialization(@PathVariable Long id) {
        return ResponseEntity.ok(specializationService.deleteSpecialization(id));
    }
}
