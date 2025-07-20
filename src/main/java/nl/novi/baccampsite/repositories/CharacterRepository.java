package nl.novi.baccampsite.repositories;

import nl.novi.baccampsite.models.Campaign;
import nl.novi.baccampsite.models.Character;
import nl.novi.baccampsite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByUser(User user);
    List<Character> findByCampaign(Campaign campaign);
}
