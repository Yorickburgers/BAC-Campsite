package nl.novi.baccampsite.repositories;

import nl.novi.baccampsite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
