package nl.novi.baccampsite.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CampaignRequestDto {
    @NotBlank(message = "Please provide a name for your campaign.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long.")
    public String name;
    public String description;
}
