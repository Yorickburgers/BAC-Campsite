package nl.novi.baccampsite.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Ability {
    STRENGTH,
    DEXTERITY,
    CONSTITUTION,
    INTELLIGENCE,
    WISDOM,
    CHARISMA;

    @JsonCreator
    public static Ability fromString(String value) {
        return Ability.valueOf(value.toUpperCase());
    }
}
