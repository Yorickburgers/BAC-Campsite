package nl.novi.baccampsite.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "professions")
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int hpModifier;
    private String primaryAbility;
    private List<String> traits;

    public Profession() {}

    public Profession(String name, String description, int hpModifier, String primaryAbility, List<String> traits) {
        this.name = name;
        this.description = description;
        this.hpModifier = hpModifier;
        this.primaryAbility = primaryAbility;
        this.traits = traits;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHpModifier() {
        return hpModifier;
    }

    public void setHpModifier(int hpModifier) {
        this.hpModifier = hpModifier;
    }

    public String getPrimaryAbility() {
        return primaryAbility;
    }

    public void setPrimaryAbility(String primaryAbility) {
        this.primaryAbility = primaryAbility;
    }

    public List<String> getTraits() {
        return traits;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }
}
