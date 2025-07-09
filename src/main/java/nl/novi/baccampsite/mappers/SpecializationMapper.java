package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.SpecializationRequestDto;
import nl.novi.baccampsite.dtos.SpecializationResponseDto;
import nl.novi.baccampsite.dtos.SpecializationSummaryDto;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.models.Specialization;

public class SpecializationMapper {
    public static SpecializationResponseDto toSpecializationResponseDto (Specialization spec) {
        SpecializationResponseDto dto = new SpecializationResponseDto();
        dto.id = spec.getId();
        dto.name = spec.getName();
        dto.description = spec.getDescription();
        dto.traitOne = spec.getTraitOne();
        dto.traitTwo = spec.getTraitTwo();
        dto.traitThree = spec.getTraitThree();
        dto.traitFour = spec.getTraitFour();
        dto.profession = ProfessionMapper.toProfessionSummaryDto(spec.getProfession());
        return dto;
    }

    public static Specialization toSpecialization (SpecializationRequestDto dto, Profession profession) {
        Specialization spec = new Specialization();
        updateSpecializationFromDto(dto, spec, profession);
        return spec;
    }

    public static SpecializationSummaryDto toSpecializationSummaryDto (Specialization spec) {
        SpecializationSummaryDto dto = new SpecializationSummaryDto();
        dto.id = spec.getId();
        dto.name = spec.getName();
        return dto;
    }

    public static void updateSpecializationFromDto (SpecializationRequestDto dto, Specialization spec, Profession profession) {
        spec.setName(dto.name);
        spec.setDescription(dto.description);
        spec.setTraitOne(dto.traitOne);
        spec.setTraitTwo(dto.traitTwo);
        spec.setTraitThree(dto.traitThree);
        spec.setTraitFour(dto.traitFour);
        spec.setProfession(profession);
    }
}
