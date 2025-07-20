package nl.novi.baccampsite.dtos;

import jakarta.validation.constraints.*;

public class CharacterRequestDto {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    public String name;
    public String backstory;
    @NotBlank(message = "Species is required")
    public String species;
    @Min(value = 1, message = "Level must be at least 1")
    @Max(value = 20, message = "Level must be at most 20")
    public int level;
    @Min(value = 1, message = "Strength must be at least 1")
    @Max(value = 20, message = "Strength must be at most 20")
    public int strength;
    @Min(value = 1, message = "Dexterity must be at least 1")
    @Max(value = 20, message = "Dexterity must be at most 20")
    public int dexterity;
    @Min(value = 1, message = "Constitution must be at least 1")
    @Max(value = 20, message = "Constitution must be at most 20")
    public int constitution;
    @Min(value = 1, message = "Intelligence must be at least 1")
    @Max(value = 20, message = "Intelligence must be at most 20")
    public int intelligence;
    @Min(value = 1, message = "Wisdom must be at least 1")
    @Max(value = 20, message = "Wisdom must be at most 20")
    public int wisdom;
    @Min(value = 1, message = "Charisma must be at least 1")
    @Max(value = 20, message = "Charisma must be at most 20")
    public int charisma;
    @NotBlank(message = "Profession ID is required (for instance: 'fighter'")
    public String professionId;
}
