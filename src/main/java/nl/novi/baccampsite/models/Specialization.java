package nl.novi.baccampsite.models;

import jakarta.persistence.*;

@Entity
@Table(name = "specializations")
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String traitOne;
    private String traitTwo;
    private String traitThree;
    private String traitFour;

    @ManyToOne
    @JoinColumn(name = "profession_id", nullable = false)
    private Profession profession;

    public Specialization() {}

    public Specialization(String name, String description,  String traitOne, String traitTwo, String traitThree, String traitFour) {
        this.name = name;
        this.description = description;
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
