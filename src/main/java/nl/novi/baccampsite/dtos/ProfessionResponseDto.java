package nl.novi.baccampsite.dtos;

import java.util.List;

public class ProfessionResponseDto {
    public Long id;
    public String name;
    public String description;
    public int hpModifier;
    public String primaryAbility;
    public String traitOne;
    public String traitTwo;
    public String traitThree;
    public String traitFour;
    public List<SpecializationSummaryDto> specializations;
}
