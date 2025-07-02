package nl.novi.baccampsite.repositories;

import nl.novi.baccampsite.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
