package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.exceptions.BadRequestException;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.exceptions.UsernameNotFoundException;
import nl.novi.baccampsite.mappers.CharacterMapper;
import nl.novi.baccampsite.models.Character;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.models.Specialization;
import nl.novi.baccampsite.models.User;
import nl.novi.baccampsite.repositories.CharacterRepository;
import nl.novi.baccampsite.repositories.ProfessionRepository;
import nl.novi.baccampsite.repositories.SpecializationRepository;
import nl.novi.baccampsite.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final ProfessionRepository professionRepository;
    private final SpecializationRepository specializationRepository;
    private final UserRepository userRepository;

    public CharacterService(CharacterRepository characterRepository, ProfessionRepository professionRepository, SpecializationRepository specializationRepository,  UserRepository userRepository) {
        this.characterRepository = characterRepository;
        this.professionRepository = professionRepository;
        this.specializationRepository = specializationRepository;
        this.userRepository = userRepository;
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

    public CharacterResponseDto createCharacter(CharacterRequestDto characterRequestDto, UserDetails userDetails) {
        Profession profession = professionRepository.findById(characterRequestDto.professionId)
                .orElseThrow(() -> new RecordNotFoundException("Profession not found!"));
        Character character = CharacterMapper.toCharacter(characterRequestDto, profession);
        User user = userRepository.findById(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        character.setUser(user);
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

    public CharacterResponseDto claimCharacter(Long id, UserDetails userDetails) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        if (character.getUser() != null) {
            throw new BadRequestException("Character is already claimed by " + character.getUser().getUsername() + "!");
        }

        User user = userRepository.findById(userDetails.getUsername())
                .orElseThrow(() -> new RecordNotFoundException("User " + userDetails.getUsername() + " not found!"));
        if (character.getCampaign() != null) {
            if (user.getUsername().equals(character.getCampaign().getDungeonMaster().getUsername())) {
                throw new BadRequestException("Dungeon Masters cannot claim characters in their own campaign.");
            }

            if (user.getCharacters().stream()
                    .anyMatch(ch -> ch.getCampaign() != null &&
                            ch.getCampaign().getId().equals(character.getCampaign().getId()))) {
                throw new BadRequestException("User already has a character in this campaign.");
            }
        }

        character.setUser(user);
        return CharacterMapper.toCharacterResponseDto(characterRepository.save(character));
    }

public CharacterResponseDto specializeCharacter(Long charId, Long specId) {
    Character character = characterRepository.findById(charId)
            .orElseThrow(() -> new RecordNotFoundException("Character " + charId + " not found!"));
    Specialization spec = specializationRepository.findById(specId)
            .orElseThrow(() -> new RecordNotFoundException("Specialization " + specId + " not found!"));
    if (character.getLevel() < 3) {
        throw new BadRequestException("Character must be level 3 or higher to choose a specialization.");
    }
    if (!spec.getProfession().equals(character.getProfession())) {
        throw new BadRequestException("This specialization does not belong to the character's profession.");
    }
    character.setSpecialization(spec);
    return CharacterMapper.toCharacterResponseDto(characterRepository.save(character));
}

    public String deleteCharacter(Long id) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Character " + id + " not found!"));
        characterRepository.delete(character);
        return "Character " + character.getName() + " with id " + id + " has been deleted!";
    }
}
