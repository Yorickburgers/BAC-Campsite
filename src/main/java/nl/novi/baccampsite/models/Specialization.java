package nl.novi.baccampsite.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import nl.novi.baccampsite.utils.StringUtils;

@Entity
@Table(name = "specializations")
public class Specialization {
    @Id
    @Column(unique = true)
    private String id;

    @NotBlank
    private String name;
    private String description;
    private String traitOne;
    private String traitTwo;
    private String traitThree;
    private String traitFour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profession_id", nullable = false)
    private Profession profession;

    public Specialization() {}

    public Specialization(String name, String description,  String traitOne, String traitTwo, String traitThree, String traitFour, String professionId) {
        this.name = name;
        this.description = description;
        this.traitOne = traitOne;
        this.traitTwo = traitTwo;
        this.traitThree = traitThree;
        this.traitFour = traitFour;
        this.id = StringUtils.generateSpecializationCode(professionId, StringUtils.slugify(name));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
