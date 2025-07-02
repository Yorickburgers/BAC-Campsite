package nl.novi.baccampsite.repositories;

import nl.novi.baccampsite.models.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {
}
