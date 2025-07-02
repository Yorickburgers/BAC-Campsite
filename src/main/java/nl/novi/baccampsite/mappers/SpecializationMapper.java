package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.SpecializationRequestDto;
import nl.novi.baccampsite.dtos.SpecializationResponseDto;
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
        return dto;
    }

    public static Specialization toSpecialization (SpecializationRequestDto dto) {
        Specialization spec = new Specialization();
        spec.setName(dto.name);
        spec.setDescription(dto.description);
        spec.setTraitOne(dto.traitOne);
        spec.setTraitTwo(dto.traitTwo);
        spec.setTraitThree(dto.traitThree);
        spec.setTraitFour(dto.traitFour);
        return spec;
    }
}
