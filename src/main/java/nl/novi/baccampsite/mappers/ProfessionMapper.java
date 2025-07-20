package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.ProfessionRequestDto;
import nl.novi.baccampsite.dtos.ProfessionResponseDto;
import nl.novi.baccampsite.dtos.ProfessionSummaryDto;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.utils.StringUtils;

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
        dto.path = "/professions/" + profession.getId();
        dto.name = profession.getName();
        return dto;
    }

    public static void updateProfessionFromDto (ProfessionRequestDto dto, Profession profession) {
        if (dto.name != null && !dto.name.equals(profession.getName())) {
            profession.setName(dto.name);
            profession.setId(StringUtils.slugify(dto.name));
        }
        if (dto.description != null && !dto.description.equals(profession.getDescription())) {
            profession.setDescription(dto.description);
        }
        if (dto.hpModifier != 0 && dto.hpModifier != profession.getHpModifier()) {
            profession.setHpModifier(dto.hpModifier);
        }
        if (dto.primaryAbility != null && !dto.primaryAbility.equals(profession.getPrimaryAbility())) {
            profession.setPrimaryAbility(dto.primaryAbility);
        }
        if (dto.traitOne != null && !dto.traitOne.equals(profession.getTraitOne())) {
            profession.setTraitOne(dto.traitOne);
        }
        if (dto.traitTwo != null && !dto.traitTwo.equals(profession.getTraitTwo())) {
            profession.setTraitTwo(dto.traitTwo);
        }
        if (dto.traitThree != null && !dto.traitThree.equals(profession.getTraitThree())) {
            profession.setTraitThree(dto.traitThree);
        }
        if (dto.traitFour != null && !dto.traitFour.equals(profession.getTraitFour())) {
            profession.setTraitFour(dto.traitFour);
        }
    }
}
