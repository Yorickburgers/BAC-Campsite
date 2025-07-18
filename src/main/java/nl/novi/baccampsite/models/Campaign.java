package nl.novi.baccampsite.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campaigns")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(length = 2000)
    private String description;

    @OneToMany(mappedBy = "campaign", fetch = FetchType.EAGER)
    private List<Character> characters = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dungeon_master", referencedColumnName = "username")
    private User dungeonMaster;

    public Campaign() {}

    public Campaign(String name, String description) {
        this.name = name;
        this.description = description;
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

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public User getDungeonMaster() {
        return dungeonMaster;
    }

    public void setDungeonMaster(User dungeonMaster) {
        this.dungeonMaster = dungeonMaster;
    }
}
