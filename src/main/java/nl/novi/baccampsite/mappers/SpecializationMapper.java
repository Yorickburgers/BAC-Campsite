package nl.novi.baccampsite.mappers;

import nl.novi.baccampsite.dtos.SpecializationRequestDto;
import nl.novi.baccampsite.dtos.SpecializationResponseDto;
import nl.novi.baccampsite.dtos.SpecializationSummaryDto;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.models.Specialization;
import nl.novi.baccampsite.utils.StringUtils;

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
        dto.path = "/specializations/" + spec.getId();
        dto.name = spec.getName();
        return dto;
    }

    public static void updateSpecializationFromDto (SpecializationRequestDto dto, Specialization spec, Profession profession) {
        if (dto.name != null && !dto.name.equals(spec.getName())) {
            spec.setName(dto.name);
            spec.setId(StringUtils.generateSpecializationCode(profession.getId(), StringUtils.slugify(dto.name)));
        }
        if (dto.description != null && !dto.description.equals(spec.getDescription())) {
            spec.setDescription(dto.description);
        }
        if (dto.traitOne != null && !dto.traitOne.equals(spec.getTraitOne())) {
            spec.setTraitOne(dto.traitOne);
        }
        if (dto.traitTwo != null && !dto.traitTwo.equals(spec.getTraitTwo())) {
            spec.setTraitTwo(dto.traitTwo);
        }
        if (dto.traitThree != null && !dto.traitThree.equals(spec.getTraitThree())) {
            spec.setTraitThree(dto.traitThree);
        }
        if (dto.traitFour != null && !dto.traitFour.equals(spec.getTraitFour())) {
            spec.setTraitFour(dto.traitFour);
        }
        if (dto.professionId != null && !dto.professionId.equals(spec.getProfession().getId())) {
            spec.setProfession(profession);
        }
    }
}
