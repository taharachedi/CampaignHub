
package com.campaignhub;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/relances")
public class RelanceController {

    private final JobLauncher jobLauncher;
    private final Job relanceJob;
    private final RelanceRepository repository;

    public RelanceController(
            JobLauncher jobLauncher,
            @Qualifier("relanceJob") Job relanceJob,
            RelanceRepository repository) {

        this.jobLauncher = jobLauncher;
        this.relanceJob = relanceJob;
        this.repository = repository;
    }

    // Executer le traitement Spring Batch
    @PostMapping("/executer")
    public Map<String, String> executer() throws Exception {

        var parameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        var execution = jobLauncher.run(relanceJob, parameters);

        return Map.of(
                "job", "relanceJob",
                "statut", execution.getStatus().toString(),
                "executionId",
                String.valueOf(execution.getJobInstance().getInstanceId())
        );
    }

    // Recuperer toutes les relances
    @GetMapping
    public List<Relance> toutesLesRelances() {
        return repository.findAll();
    }

    // Marquer une relance comme envoyee
    @PutMapping("/{id}/envoyer")
    public Relance marquerCommeEnvoyee(@PathVariable Long id) {

        Relance relance = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Relance introuvable : " + id
                ));

        relance.setStatut("ENVOYEE");

        return repository.save(relance);
    }
}
