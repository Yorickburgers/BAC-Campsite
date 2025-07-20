package nl.novi.baccampsite.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;
    private String backstory;

    @NotBlank(message = "Species is required")
    private String species;

    @Min(1)
    @Max(20)
    private int level;
    private int hpTotal;

    @Min(1)
    @Max(20)
    private int strength;

    @Min(1)
    @Max(20)
    private int dexterity;

    @Min(1)
    @Max(20)
    private int constitution;

    @Min(1)
    @Max(20)
    private int intelligence;

    @Min(1)
    @Max(20)
    private int wisdom;

    @Min(1)
    @Max(20)
    private int charisma;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profession_id", referencedColumnName = "id", nullable = false)
    private Profession profession;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    private Specialization specialization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private Campaign campaign;

    public Character() {}

    public Character(String name, String backstory, String species, int level, int hpTotal, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
        this.name = name;
        this.backstory = backstory;
        this.species = species;
        this.level = level;
        this.hpTotal = hpTotal;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackstory() {
        return backstory;
    }

    public void setBackstory(String backstory) {
        this.backstory = backstory;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        if (level <3) {
            setSpecialization(null);
        }
    }

    public int getHpTotal() {
        return hpTotal;
    }

    public void recalculateHpTotal() {
        int baseHp = profession.getHpModifier();
        int hpPerLevel = (profession.getHpModifier()/2)+1;
        int conModifier = (int) Math.floor((constitution - 10) / 2.0);
        this.hpTotal = baseHp + ((level-1)*hpPerLevel) + (level * conModifier);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }
}
