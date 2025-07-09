package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.mappers.CharacterMapper;
import nl.novi.baccampsite.models.Character;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.models.User;
import nl.novi.baccampsite.repositories.CharacterRepository;
import nl.novi.baccampsite.repositories.ProfessionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final ProfessionRepository professionRepository;

    public CharacterService(CharacterRepository characterRepository, ProfessionRepository professionRepository) {
        this.characterRepository = characterRepository;
        this.professionRepository = professionRepository;
    }

    public List<CharacterResponseDto> retrieveAllCharacters() {
        List<CharacterResponseDto> characters = new ArrayList<>();
        characterRepository.findAll().forEach(character -> characters.add(CharacterMapper.toCharacterResponseDto(character)));
        return characters;
    }

    public CharacterResponseDto retrieveCharacter(Long id) {
        return CharacterMapper.toCharacterResponseDto(characterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!")));
    }

    public CharacterResponseDto createCharacter(CharacterRequestDto characterRequestDto) {
        Profession profession = professionRepository.findById(characterRequestDto.professionId)
                .orElseThrow(() -> new RecordNotFoundException("Profession not found!"));
        Character character = CharacterMapper.toCharacter(characterRequestDto, profession);
        return CharacterMapper.toCharacterResponseDto(characterRepository.save(character));
    }

    public CharacterResponseDto updateCharacter(Long id, CharacterRequestDto characterRequestDto) {
        Character currentCharacter = characterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        Profession profession = professionRepository.findById(characterRequestDto.professionId)
                .orElseThrow(() -> new RecordNotFoundException("Profession not found!"));
        CharacterMapper.updateCharacterFromDto(characterRequestDto, currentCharacter, profession);
        return CharacterMapper.toCharacterResponseDto(characterRepository.save(currentCharacter));
    }

    public String unclaimCharacter(Long id) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        character.setUser(null);
        characterRepository.save(character);
        return "Character " + id + " has been unclaimed!";
    }

    public CharacterResponseDto claimCharacter(Long id) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        if (character.getUser() != null) {
            throw new IllegalStateException("Character is already claimed");
        }

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findByUsername(auth.getName());
        character.setUser(user); // from loggedIn user, TODO
        return CharacterMapper.toCharacterResponseDto(characterRepository.save(character));
    }

    public String deleteCharacter(Long id) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        characterRepository.delete(character);
        return "Character " + character.getName() + " with id " + id + " has been deleted!";
    }
}
