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
            dto.user = character.getUser().getUsername();
        } else {
            dto.user = null;
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
        dto.path = "/characters/" + character.getId();
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
        if (dto.name != null && !dto.name.equals(character.getName())) {
            character.setName(dto.name);
        }
        if (dto.backstory != null && !dto.backstory.equals(character.getBackstory())) {
            character.setBackstory(dto.backstory);
        }
        if (dto.species != null && !dto.species.equals(character.getSpecies())) {
            character.setSpecies(dto.species);
        }
        if (dto.level >= 1 && dto.level != character.getLevel()) {
            character.setLevel(dto.level);
        }
        if (dto.strength >= 1 && dto.strength != character.getStrength()) {
            character.setStrength(dto.strength);
        }
        if (dto.dexterity >= 1 && dto.dexterity != character.getDexterity()) {
            character.setDexterity(dto.dexterity);
        }
        if (dto.constitution >= 1 && dto.constitution != character.getConstitution()) {
            character.setConstitution(dto.constitution);
        }
        if (dto.intelligence >= 1 && dto.intelligence != character.getIntelligence()) {
            character.setIntelligence(dto.intelligence);
        }
        if (dto.wisdom >= 1 && dto.wisdom != character.getWisdom()) {
            character.setWisdom(dto.wisdom);
        }
        if (dto.charisma >= 1 && dto.charisma != character.getCharisma()) {
            character.setCharisma(dto.charisma);
        }
        if (dto.professionId != null && (character.getProfession() == null || !dto.professionId.equals(character.getProfession().getId()))) {
            character.setProfession(profession);
            character.setSpecialization(null);
        }
        character.recalculateHpTotal();
    }
}
