package nl.novi.baccampsite.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "specializations")
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private List<String> traits;

    public Specialization() {}

    public Specialization(String name, String description, List<String> traits) {
        this.name = name;
        this.description = description;
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

    public List<String> getTraits() {
        return traits;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }
}
