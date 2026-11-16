
package com.campaignhub;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RelanceRepository
        extends JpaRepository<Relance, Long> {

    Optional<Relance> findByClientId(Long clientId);
}
