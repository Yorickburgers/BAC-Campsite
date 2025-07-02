package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.models.Character;

public class CharacterMapper {
    public static CharacterResponseDto toCharacterResponseDto (Character character) {
        CharacterResponseDto dto = new CharacterResponseDto();
        dto.id = character.getId();
        dto.name = character.getName();
        dto.backstory = character.getBackstory();
        dto.species = character.getSpecies();
        dto.level = character.getLevel();
        dto.hpTotal = character.getHpTotal();
        dto.strength = character.getStrength();
        dto.dexterity = character.getDexterity();
        dto.constitution = character.getConstitution();
        dto.intelligence = character.getIntelligence();
        dto.wisdom = character.getWisdom();
        dto.charisma = character.getCharisma();
        return dto;
    }

    public static Character toCharacter (CharacterRequestDto dto) {
        Character character = new Character();
        updateCharacterFromDto(dto, character);
        return character;
    }

    public static void updateCharacterFromDto(CharacterRequestDto dto, Character character) {
        character.setName(dto.name);
        character.setBackstory(dto.backstory);
        character.setSpecies(dto.species);
        character.setLevel(dto.level);
        character.setHpTotal(dto.hpTotal);
        character.setStrength(dto.strength);
        character.setDexterity(dto.dexterity);
        character.setConstitution(dto.constitution);
        character.setIntelligence(dto.intelligence);
        character.setWisdom(dto.wisdom);
        character.setCharisma(dto.charisma);
    }
}
