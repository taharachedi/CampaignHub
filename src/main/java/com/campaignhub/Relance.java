
package com.campaignhub;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "relance",
    uniqueConstraints = @UniqueConstraint(
        columnNames = "client_id"
    )
)
public class Relance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    private String nom;
    private String prenom;
    private String email;

    private int joursDepuisDernierContact;

    private LocalDateTime dateCreation;

    private String statut;

    public Relance() {
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getJoursDepuisDernierContact() {
        return joursDepuisDernierContact;
    }

    public void setJoursDepuisDernierContact(int jours) {
        this.joursDepuisDernierContact = jours;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
