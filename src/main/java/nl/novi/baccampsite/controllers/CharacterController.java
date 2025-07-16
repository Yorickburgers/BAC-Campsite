package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.services.CharacterService;
import nl.novi.baccampsite.utils.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<List<CharacterResponseDto>> retrieveAllCharacters() {
        return ResponseEntity.ok(characterService.retrieveAllCharacters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDto>  retrieveCharacter(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        CharacterResponseDto dto =  characterService.retrieveCharacter(id);
        if (!SecurityUtil.canAccessCharacter(userDetails, dto)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CharacterResponseDto> createCharacter(@RequestBody CharacterRequestDto characterRequestDto, @AuthenticationPrincipal  UserDetails userDetails) {
        CharacterResponseDto characterResponseDto = characterService.createCharacter(characterRequestDto, userDetails);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + characterResponseDto.id).toUriString());

        return ResponseEntity.created(uri).body(characterResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterResponseDto>  updateCharacter(@PathVariable Long id, @RequestBody CharacterRequestDto characterRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (!SecurityUtil.canEditCharacter(userDetails, characterService.retrieveCharacter(id))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(characterService.updateCharacter(id, characterRequestDto));
    }

    @PutMapping("/{id}/unclaim")
    public ResponseEntity<String> unclaimCharacter(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (!SecurityUtil.isSelfOrAdmin(userDetails, characterService.retrieveCharacter(id).user.username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(characterService.unclaimCharacter(id));
    }

    @PutMapping("/{id}/claim")
    public ResponseEntity<CharacterResponseDto> claimCharacter(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(characterService.claimCharacter(id, userDetails));
    }

    @PutMapping("/{charId}/specialize/{specId}")
    public ResponseEntity<CharacterResponseDto> specializeCharacter(@PathVariable Long charId, @PathVariable Long specId, @AuthenticationPrincipal  UserDetails userDetails) {
        if (!SecurityUtil.canSpecCharacter(userDetails, characterService.retrieveCharacter(charId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(characterService.specializeCharacter(charId, specId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        CharacterResponseDto dto = characterService.retrieveCharacter(id);
        if (!SecurityUtil.canDeleteCharacter(userDetails, dto)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(characterService.deleteCharacter(id));
    }
}
