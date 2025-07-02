package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<List<CharacterResponseDto>> retrieveCharactersByUser() {
        return ResponseEntity.ok(characterService.retrieveCharactersByUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDto>  retrieveCharacter(@PathVariable Long id) {
        return ResponseEntity.ok(characterService.retrieveCharacter(id));
    }

    @PostMapping
    public ResponseEntity<CharacterResponseDto> createCharacter(@RequestBody CharacterRequestDto characterRequestDto) {
        CharacterResponseDto characterResponseDto = characterService.createCharacter(characterRequestDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + characterResponseDto.id).toUriString());

        return ResponseEntity.created(uri).body(characterResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterResponseDto>  updateCharacter(@PathVariable Long id, @RequestBody CharacterRequestDto characterRequestDto) {
        return ResponseEntity.ok().body(characterService.updateCharacter(id, characterRequestDto));
    }

    @PutMapping("/{id}/unclaim")
    public ResponseEntity<CharacterResponseDto> unclaimCharacter(@PathVariable Long id) {
        return ResponseEntity.ok().body(characterService.unclaimCharacter(id));
    }

    @PutMapping("/{id}/claim")
    public ResponseEntity<CharacterResponseDto> claimCharacter(@PathVariable Long id) {
        return ResponseEntity.ok().body(characterService.claimCharacter(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Long id) {
        return ResponseEntity.ok(characterService.deleteCharacter(id));
    }
}
