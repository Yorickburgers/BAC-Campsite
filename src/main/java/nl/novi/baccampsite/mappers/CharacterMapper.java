package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.dtos.CharacterSummaryDto;
import nl.novi.baccampsite.models.Character;
import nl.novi.baccampsite.models.Profession;

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
        dto.profession = ProfessionMapper.toProfessionSummaryDto(character.getProfession());
        if (character.getUser() != null) {
            dto.user = UserMapper.toUserResponseDto(character.getUser());
        }
        if (character.getSpecialization() != null) {
            dto.specialization = SpecializationMapper.toSpecializationSummaryDto(character.getSpecialization());
        }
        if (character.getCampaign() != null) {
            dto.campaign = CampaignMapper.toCampaignResponseDto(character.getCampaign());
        }
        return dto;
    }

    public static Character toCharacter (CharacterRequestDto dto, Profession profession) {
        Character character = new Character();
        updateCharacterFromDto(dto, character, profession);
        return character;
    }

    public static CharacterSummaryDto toCharacterSummaryDto (Character character) {
        CharacterSummaryDto dto = new CharacterSummaryDto();
        dto.id = character.getId();
        dto.name = character.getName();
        dto.species = character.getSpecies();
        dto.level = character.getLevel();
        dto.profession = ProfessionMapper.toProfessionSummaryDto(character.getProfession());
        if (character.getSpecialization() != null) {
            dto.specialization = SpecializationMapper.toSpecializationSummaryDto(character.getSpecialization());
        }
        return dto;
    }

    public static void updateCharacterFromDto(CharacterRequestDto dto, Character character, Profession profession) {
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
        character.setProfession(profession);
    }
}
