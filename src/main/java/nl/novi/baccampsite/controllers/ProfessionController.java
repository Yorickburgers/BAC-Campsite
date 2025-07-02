package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.ProfessionRequestDto;
import nl.novi.baccampsite.dtos.ProfessionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/professions")
public class ProfessionController {
    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping
    public ResponseEntity<List<ProfessionResponseDto>> retrieveProfessions() {
        return ResponseEntity.ok(professionService.retrieveProfessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionResponseDto>  retrieveProfession(@PathVariable Long id) {
        return ResponseEntity.ok(professionService.retrieveProfession(id));
    }

    @PostMapping
    public ResponseEntity<ProfessionResponseDto> createProfession(@RequestBody ProfessionRequestDto professionRequestDto) {
        ProfessionResponseDto professionResponseDto = professionService.createProfession(professionRequestDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + professionResponseDto.id).toUriString());

        return ResponseEntity.created(uri).body(professionResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionResponseDto>  updateProfession(@PathVariable Long id, @RequestBody ProfessionRequestDto professionRequestDto) {
        return ResponseEntity.ok().body(professionService.updateProfession(id, professionRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfession(@PathVariable Long id) {
        return ResponseEntity.ok(professionService.deleteProfession(id));
    }
}
