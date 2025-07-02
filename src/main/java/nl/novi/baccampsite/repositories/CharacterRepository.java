package nl.novi.baccampsite.repositories;

import nl.novi.baccampsite.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
