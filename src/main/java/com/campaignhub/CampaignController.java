
package com.campaignhub;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    private final CampaignRepository repository;

    public CampaignController(CampaignRepository repository) {
        this.repository = repository;
    }

    // 1. Afficher toutes les campagnes
    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return repository.findAll();
    }

    // 2. Afficher une campagne par ID
    @GetMapping("/{id}")
    public Campaign getCampaignById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Campagne introuvable"
                    )
                );
    }

    // 3. Créer une campagne
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Campaign createCampaign(
            @Valid @RequestBody Campaign details) {

        Campaign campaign = new Campaign();

        campaign.setName(details.getName());
        campaign.setDescription(details.getDescription());
        campaign.setDateDebut(details.getDateDebut());
        campaign.setDateFin(details.getDateFin());
        campaign.setStatut("BROUILLON");

        return repository.save(campaign);
    }

    // 4. Modifier une campagne
    @PutMapping("/{id}")
    public Campaign updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody Campaign details) {

        Campaign campaign = getCampaignById(id);

        campaign.setName(details.getName());
        campaign.setDescription(details.getDescription());
        campaign.setDateDebut(details.getDateDebut());
        campaign.setDateFin(details.getDateFin());

        return repository.save(campaign);
    }

    // 5. Modifier le statut d'une campagne
    @PutMapping("/{id}/statut")
    public Campaign updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {

        Campaign campaign = getCampaignById(id);

        if (!statut.equals("BROUILLON")
                && !statut.equals("ACTIVE")
                && !statut.equals("TERMINEE")) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Statut invalide"
            );
        }

        campaign.setStatut(statut);

        return repository.save(campaign);
    }

    // 6. Supprimer une campagne
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(@PathVariable Long id) {

        Campaign campaign = getCampaignById(id);
        repository.delete(campaign);
    }
}
