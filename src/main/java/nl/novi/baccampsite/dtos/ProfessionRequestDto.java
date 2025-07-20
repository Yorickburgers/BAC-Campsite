package nl.novi.baccampsite.dtos;

import jakarta.validation.constraints.*;

public class ProfessionRequestDto {
    @NotBlank(message = "Please provide a name")
    @Size(min = 3, max = 24, message = "Name must be between 3 and 24 characters long")
    public String name;
    @NotBlank(message = "please provide a short description")
    @Size(min = 8, max = 500, message = "Description must be between 8 and 500 characters long")
    public String description;
    @Min(value = 4, message = "Must be at least 4")
    @Max(value = 12, message = "Must be at most 12")
    public int hpModifier;
    @NotBlank(message = "Please provide the primary ability of this profession")

    public String primaryAbility;
    @NotBlank(message = "Please provide a name for trait one")
    @Size(min = 3, max = 24, message = "name of trait must be between 3 and 24 characters long")
    public String traitOne;
    @NotBlank(message = "Please provide a name for trait one")
    @Size(min = 3, max = 24, message = "name of trait must be between 3 and 24 characters long")
    public String traitTwo;
    @NotBlank(message = "Please provide a name for trait one")
    @Size(min = 3, max = 24, message = "name of trait must be between 3 and 24 characters long")
    public String traitThree;
    @NotBlank(message = "Please provide a name for trait one")
    @Size(min = 3, max = 24, message = "name of trait must be between 3 and 24 characters long")
    public String traitFour;

    @AssertTrue(message = "hpModifier must be an even number")
    public boolean isEven() {
        return hpModifier % 2 == 0;
    }
}
