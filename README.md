
# CampaignHub

Application web de gestion des campagnes marketing et des relances clients.

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- Spring Batch
- API REST
- H2 Database
- HTML, CSS et JavaScript
- Maven

## Fonctionnalités

### Gestion des clients
- Consultation des clients
- Identification des clients actifs
- Suivi du nombre de jours depuis le dernier contact

### Gestion des relances
- Sélection automatique des clients éligibles
- Exécution d'un traitement Spring Batch
- Consultation des relances générées
- Mise à jour du statut des relances

### Gestion des campagnes
- Création de campagnes
- Consultation des campagnes
- Modification des campagnes
- Activation des campagnes
- Suppression des campagnes
- Gestion des dates et des statuts

### Dashboard
- Nombre total de clients
- Nombre de clients actifs
- Nombre de relances
- Nombre de campagnes
- Tableaux de suivi et actions interactives

## Architecture

```text
campaignhub/
├── src/
│   ├── main/
│   │   ├── java/com/campaignhub/
│   │   │   ├── CampaignhubApplication.java
│   │   │   ├── Campaign.java
│   │   │   ├── CampaignController.java
│   │   │   ├── CampaignRepository.java
│   │   │   ├── Client.java
│   │   │   ├── ClientController.java
│   │   │   ├── ClientRepository.java
│   │   │   ├── Relance.java
│   │   │   ├── RelanceController.java
│   │   │   ├── RelanceRepository.java
│   │   │   └── RelanceBatchConfig.java
│   │   └── resources/
│   │       ├── static/index.html
│   │       └── application.properties
│   └── test/
│       ├── java/
│       └── resources/application.properties
├── pom.xml
└── README.md
```

## Lancement

Prérequis : Java 21.

Depuis le dossier principal du projet, sous Windows :

```powershell
.\mvnw.cmd spring-boot:run
```

Ouvrir ensuite :

http://localhost:8080

## Tests

```powershell
.\mvnw.cmd clean verify
```

## Génération du JAR

```powershell
.\mvnw.cmd clean package
```

Puis lancer :

```powershell
java -jar target/campaignhub-0.0.1-SNAPSHOT.jar
```

## Base de données

L'application utilise une base H2 persistante en local.

Les tests utilisent une base H2 en mémoire afin d'isoler les données de test.

## Validation

- Compilation Maven réussie
- Test de démarrage Spring Boot réussi
- Fonctionnalités principales vérifiées manuellement
