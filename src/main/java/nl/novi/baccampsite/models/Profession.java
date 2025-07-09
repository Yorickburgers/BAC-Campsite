package nl.novi.baccampsite.models;

import jakarta.persistence.*;

import java.util.ArrayList;
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
    private String traitOne;
    private String traitTwo;
    private String traitThree;
    private String traitFour;

    @OneToMany(mappedBy = "profession", fetch = FetchType.EAGER)
    private List<Specialization> specializations = new ArrayList<>();

    public Profession() {}

    public Profession(String name, String description, int hpModifier, String primaryAbility,  String traitOne, String traitTwo, String traitThree, String traitFour) {
        this.name = name;
        this.description = description;
        this.hpModifier = hpModifier;
        this.primaryAbility = primaryAbility;
        this.traitOne = traitOne;
        this.traitTwo = traitTwo;
        this.traitThree = traitThree;
        this.traitFour = traitFour;
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

    public String getTraitOne() {
        return traitOne;
    }

    public void setTraitOne(String traitOne) {
        this.traitOne = traitOne;
    }

    public String getTraitTwo() {
        return traitTwo;
    }

    public void setTraitTwo(String traitTwo) {
        this.traitTwo = traitTwo;
    }

    public String getTraitThree() {
        return traitThree;
    }

    public void setTraitThree(String traitThree) {
        this.traitThree = traitThree;
    }

    public String getTraitFour() {
        return traitFour;
    }

    public void setTraitFour(String traitFour) {
        this.traitFour = traitFour;
    }

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> specializations) {
        this.specializations = specializations;
    }
}
