package nl.novi.baccampsite.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SpecializationRequestDto {
    @NotBlank(message = "Please provide a name")
    @Size(min = 3, max = 24, message = "Name must be between 3 and 24 characters long")
    public String name;
    @NotBlank(message = "please provide a short description")
    @Size(min = 8, max = 500, message = "Description must be between 8 and 500 characters long")
    public String description;
    @NotBlank(message = "Please provide a name for trait one")
    @Size(min = 3, max = 24, message = "name of trait must be between 3 and 24 characters long")
    public String traitOne;
    @NotBlank(message = "Please provide a name for trait two")
    @Size(min = 3, max = 24, message = "name of trait must be between 3 and 24 characters long")
    public String traitTwo;
    @NotBlank(message = "Please provide a name for trait three")
    @Size(min = 3, max = 24, message = "name of trait must be between 3 and 24 characters long")
    public String traitThree;
    @NotBlank(message = "Please provide a name for trait four")
    @Size(min = 3, max = 24, message = "name of trait must be between 3 and 24 characters long")
    public String traitFour;
    @NotBlank(message = "Profession ID is required (for instance: 'fighter'")
    public String professionId;
}
