package nl.novi.baccampsite.repositories;

import nl.novi.baccampsite.models.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    List<Specialization> findAllByProfessionId(Long professionId);
}
