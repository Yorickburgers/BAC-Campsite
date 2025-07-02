package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.ProfessionRequestDto;
import nl.novi.baccampsite.dtos.ProfessionResponseDto;
import nl.novi.baccampsite.models.Profession;

public class ProfessionMapper {
    public static ProfessionResponseDto dto (Profession profession){
        ProfessionResponseDto dto = new ProfessionResponseDto();
        dto.id = profession.getId();
        dto.name = profession.getName();
        dto.description = profession.getDescription();
        dto.hpModifier = profession.getHpModifier();
        dto.primaryAbility = profession.getPrimaryAbility();
        dto.traitOne = profession.getTraitOne();
        dto.traitTwo = profession.getTraitTwo();
        dto.traitThree = profession.getTraitThree();
        dto.traitFour = profession.getTraitFour();
        return dto;
    }

    public static Profession toProfession (ProfessionRequestDto dto){
        Profession profession = new Profession();
        profession.setName(dto.name);
        profession.setDescription(dto.description);
        profession.setHpModifier(dto.hpModifier);
        profession.setPrimaryAbility(dto.primaryAbility);
        profession.setTraitOne(dto.traitOne);
        profession.setTraitTwo(dto.traitTwo);
        profession.setTraitThree(dto.traitThree);
        profession.setTraitFour(dto.traitFour);
        return profession;
    }
}
