package nl.novi.baccampsite.dtos;

import nl.novi.baccampsite.enums.Ability;

import java.util.List;

public class ProfessionResponseDto {
    public String id;
    public String name;
    public String description;
    public int hpModifier;
    public Ability primaryAbility;
    public String traitOne;
    public String traitTwo;
    public String traitThree;
    public String traitFour;
    public List<SpecializationSummaryDto> specializations;
}
