package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.ProfessionRequestDto;
import nl.novi.baccampsite.dtos.ProfessionResponseDto;
import nl.novi.baccampsite.dtos.ProfessionSummaryDto;
import nl.novi.baccampsite.models.Profession;

import java.util.ArrayList;

public class ProfessionMapper {
    public static ProfessionResponseDto toProfessionResponseDto (Profession profession) {
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
        dto.specializations = new ArrayList<>();
            profession.getSpecializations().forEach(specialization ->
                    dto.specializations.add(SpecializationMapper.toSpecializationSummaryDto(specialization)));
        return dto;
    }

    public static Profession toProfession (ProfessionRequestDto dto) {
        Profession profession = new Profession();
        updateProfessionFromDto(dto, profession);
        return profession;
    }

    public static ProfessionSummaryDto toProfessionSummaryDto (Profession profession) {
        ProfessionSummaryDto dto = new ProfessionSummaryDto();
        dto.id = profession.getId();
        dto.name = profession.getName();
        return dto;
    }

    public static void updateProfessionFromDto (ProfessionRequestDto dto, Profession profession) {
        profession.setName(dto.name);
        profession.setDescription(dto.description);
        profession.setHpModifier(dto.hpModifier);
        profession.setPrimaryAbility(dto.primaryAbility);
        profession.setTraitOne(dto.traitOne);
        profession.setTraitTwo(dto.traitTwo);
        profession.setTraitThree(dto.traitThree);
        profession.setTraitFour(dto.traitFour);
    }
}
