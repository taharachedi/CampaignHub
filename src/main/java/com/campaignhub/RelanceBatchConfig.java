
package com.campaignhub;

import java.time.LocalDateTime;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;

import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class RelanceBatchConfig {

    // Lecture des clients à chaque exécution
    @Bean
    @StepScope
    public ItemReader<Client> relanceReader(
            ClientRepository clientRepository) {

        return new ListItemReader<>(
            clientRepository.findAll()
        );
    }

    // Sélection des clients à relancer
    @Bean
    public ItemProcessor<Client, Relance> relanceProcessor() {

        return client -> {

            if (!client.isActif()
                    || client.getJoursDepuisDernierContact() <= 30) {
                return null;
            }

            Relance relance = new Relance();

            relance.setClientId(client.getId());
            relance.setNom(client.getNom());
            relance.setPrenom(client.getPrenom());
            relance.setEmail(client.getEmail());

            relance.setJoursDepuisDernierContact(
                client.getJoursDepuisDernierContact()
            );

            relance.setDateCreation(LocalDateTime.now());
            relance.setStatut("A_RELANCER");

            return relance;
        };
    }

    // Enregistrement sans doublons
    @Bean
    public ItemWriter<Relance> relanceWriter(
            RelanceRepository repository) {

        return chunk -> {

            for (Relance relance : chunk) {

                if (repository.findByClientId(
                        relance.getClientId()).isEmpty()) {

                    repository.save(relance);
                }
            }
        };
    }

    // Configuration du Step
    @Bean
    public Step relanceStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Client> relanceReader,
            ItemProcessor<Client, Relance> relanceProcessor,
            ItemWriter<Relance> relanceWriter) {

        return new StepBuilder("relanceStep", jobRepository)
            .<Client, Relance>chunk(10)
            .transactionManager(transactionManager)
            .reader(relanceReader)
            .processor(relanceProcessor)
            .writer(relanceWriter)
            .build();
    }

    // Configuration du Job
    @Bean
    public Job relanceJob(
            JobRepository jobRepository,
            Step relanceStep) {

        return new JobBuilder("relanceJob", jobRepository)
            .start(relanceStep)
            .build();
    }
}
