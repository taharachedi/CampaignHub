
package com.campaignhub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository
        extends JpaRepository<Client, Long> {

    // Selectionner les clients actifs
    // dont le dernier contact date d'au moins X jours
    List<Client> findByActifTrueAndJoursDepuisDernierContactGreaterThanEqual(
            int jours
    );
}
