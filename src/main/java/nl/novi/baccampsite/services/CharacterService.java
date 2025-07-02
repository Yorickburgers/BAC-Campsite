package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.mappers.CharacterMapper;
import nl.novi.baccampsite.models.Character;
import nl.novi.baccampsite.models.User;
import nl.novi.baccampsite.repositories.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<CharacterResponseDto> retrieveAllCharacters() {
        List<CharacterResponseDto> characters = new ArrayList<>();
        characterRepository.findAll().forEach(character -> characters.add(CharacterMapper.toCharacterResponseDto(character)));
        return characters;
    }

    public CharacterResponseDto retrieveCharacter(Long id) {
        return CharacterMapper.toCharacterResponseDto(characterRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!")));
    }

    public CharacterResponseDto createCharacter(CharacterRequestDto characterRequestDto) {
        Character character = CharacterMapper.toCharacter(characterRequestDto);
        return CharacterMapper.toCharacterResponseDto(characterRepository.save(character));
    }

    public CharacterResponseDto updateCharacter(Long id, CharacterRequestDto characterRequestDto) {
        Character currentCharacter = characterRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        CharacterMapper.updateCharacterFromDto(characterRequestDto, currentCharacter);
        return CharacterMapper.toCharacterResponseDto(characterRepository.save(currentCharacter));
    }

    public String unclaimCharacter(Long id) {
        Character character = characterRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        character.setUser(null);
        characterRepository.save(character);
        return "Character " + id + " has been unclaimed!";
    }

    public CharacterResponseDto claimCharacter(Long id) {
        Character character = characterRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        if (character.getUser() != null) {
            throw new IllegalStateException("Character is already claimed");
        }

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findByUsername(auth.getName());
        character.setUser(user); // from loggedIn user, TODO
        return CharacterMapper.toCharacterResponseDto(characterRepository.save(character));
    }

    public String deleteCharacter(Long id) {
        Character character = characterRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        characterRepository.delete(character);
        return "Character " + id + " has been deleted!";
    }
}
