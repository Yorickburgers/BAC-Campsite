package nl.novi.baccampsite.controllers;

import jakarta.validation.Valid;
import nl.novi.baccampsite.dtos.SpecializationRequestDto;
import nl.novi.baccampsite.dtos.SpecializationResponseDto;
import nl.novi.baccampsite.services.SpecializationService;
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
    public ResponseEntity<List<SpecializationResponseDto>> retrieveAllSpecializations() {
        return ResponseEntity
                .ok(specializationService.retrieveAllSpecializations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecializationResponseDto>  retrieveSpecialization(@PathVariable String id) {
        return ResponseEntity
                .ok(specializationService.retrieveSpecialization(id));
    }

    @PostMapping
    public ResponseEntity<SpecializationResponseDto> createSpecializationForProfession(@Valid @RequestBody SpecializationRequestDto specializationRequestDto) {
        SpecializationResponseDto specializationResponseDto = specializationService.createSpecialization(specializationRequestDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + specializationResponseDto.id).toUriString());

        return ResponseEntity
                .created(uri)
                .header("Message",
                        "Specialization " + specializationResponseDto.name + "created!")
                .body(specializationResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecializationResponseDto>  updateSpecialization(@PathVariable String id, @Valid @RequestBody SpecializationRequestDto specializationRequestDto) {
        return ResponseEntity
                .ok()
                .header("Message",
                        "Specialization " + specializationRequestDto.name + " updated!")
                .body(specializationService.updateSpecialization(id, specializationRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpecialization(@PathVariable String id) {
        return ResponseEntity
                .ok(specializationService.deleteSpecialization(id));
    }
}
