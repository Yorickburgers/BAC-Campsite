package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.dtos.CharacterSummaryDto;
import nl.novi.baccampsite.exceptions.ForbiddenException;
import nl.novi.baccampsite.services.CharacterService;
import nl.novi.baccampsite.services.UserService;
import nl.novi.baccampsite.utils.SecurityUtil;
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
    private final UserService userService;

    public CharacterController(CharacterService characterService, UserService userService) {
        this.characterService = characterService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<CharacterSummaryDto>> retrieveAllCharacters(@AuthenticationPrincipal UserDetails userDetails) {
        if (SecurityUtil.hasRole(userDetails, "ADMIN")) {
            return ResponseEntity.ok(characterService.retrieveAllCharacters());
        }
        return ResponseEntity.ok(userService.retrieveUser(userDetails.getUsername()).characters);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDto>  retrieveCharacter(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        CharacterResponseDto dto =  characterService.retrieveCharacter(id);
        if (!SecurityUtil.canAccessCharacter(userDetails, dto)) {
            throw new ForbiddenException("You are not the owner or dungeon master of this character");
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

        return ResponseEntity
                .created(uri)
                .header("Message",
                        "Character " + characterRequestDto.name + " has been created!")
                .body(characterResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterResponseDto>  updateCharacter(@PathVariable Long id, @RequestBody CharacterRequestDto characterRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (!SecurityUtil.canEditCharacter(userDetails, characterService.retrieveCharacter(id))) {
            throw new ForbiddenException("You are not the owner or dungeon master of this character");
        }
        return ResponseEntity
                .ok()
                .header("Message",
                        "Character " + characterRequestDto.name + " has been updated!")
                .body(characterService.updateCharacter(id, characterRequestDto));
    }

    @PutMapping("/{id}/unclaim")
    public ResponseEntity<String> unclaimCharacter(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        CharacterResponseDto character = characterService.retrieveCharacter(id);
        if (!SecurityUtil.isSelfOrAdmin(userDetails, character.user)) {
            throw new ForbiddenException("You cannot unclaim a character that is not your own.");
        }
        return ResponseEntity
                .ok()
                .body(characterService.unclaimCharacter(id));
    }

    @PutMapping("/{id}/claim")
    public ResponseEntity<CharacterResponseDto> claimCharacter(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok()
                .header("Message",
                        "Character " + characterService.retrieveCharacter(id).name +  " has been claimed by you!")
                .body(characterService.claimCharacter(id, userDetails));
    }

    @PutMapping("/{charId}/specialize/{specId}")
    public ResponseEntity<CharacterResponseDto> specializeCharacter(@PathVariable Long charId, @PathVariable Long specId, @AuthenticationPrincipal  UserDetails userDetails) {
        CharacterResponseDto character = characterService.retrieveCharacter(charId);
        if (!SecurityUtil.canSpecCharacter(userDetails, character)) {
            throw new ForbiddenException("You are not the owner or dungeon master of this character");
        }
        return ResponseEntity
                .ok()
                .header("Message",
                        "Character " + character.name + " has been specialized!")
                .body(characterService.specializeCharacter(charId, specId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        CharacterResponseDto character = characterService.retrieveCharacter(id);
        if (!SecurityUtil.canDeleteCharacter(userDetails, character)) {
            throw new ForbiddenException("You cannot delete a character that is not your own, nor is an unclaimed character in your campaign.");
        }
        return ResponseEntity
                .ok()
                .body(characterService.deleteCharacter(id));
    }
}
