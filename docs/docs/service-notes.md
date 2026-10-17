# Atelier 4 : IoC, injection de dépendances et couche Service

## A. Modes d'injection comparés

| Critère                                        | Constructeur (@RequiredArgsConstructor + final) | Attribut (@Autowired sur le champ)        |
|------------------------------------------------|-------------------------------------------------|-------------------------------------------|
| Champ final possible ?                         | Oui                                             | Non                                       |
| Dépendance visible dans la signature ?         | Oui, dans le constructeur                       | Non, cachée dans le corps                 |
| Utilisable avec new hors conteneur sans null ? | Oui : le repository est obligatoire             | Non : new compile mais le champ vaut null |
| Signalé par SonarQube for IDE ?                | Non                                             | Oui (S6813, à confirmer dans le panneau)  |

## B. Services

| Service                | Dépendances injectées  | Mode         | Justification                                                                                                       |
|------------------------|------------------------|--------------|---------------------------------------------------------------------------------------------------------------------|
| ContratServiceImpl     | IContratRepository     | Constructeur | CRUD ; refuse un id à la création et un montant négatif ; update recopie sans toucher aux paiements (orphanRemoval) |
| AgenceServiceImpl      | IAgenceRepository      | Constructeur | CRUD ; refuse nom, ville ou adresse vide                                                                            |
| EmployeServiceImpl     | IEmployeRepository     | Constructeur | CRUD ; refuse nom ou prénom vide, ou rôle absent                                                                    |
| VehiculeServiceImpl    | IVehiculeRepository    | Constructeur | CRUD ; refuse un tarif journalier nul ou négatif                                                                    |
| EquipementServiceImpl  | IEquipementRepository  | Constructeur | CRUD ; refuse un libellé vide (pas d'attribut quantité dans le modèle)                                              |
| ClientServiceImpl      | IClientRepository      | Constructeur | CRUD ; refuse nom, prénom ou e-mail vide                                                                            |
| ReservationServiceImpl | IReservationRepository | Constructeur | CRUD ; refuse une date de fin antérieure à la date de début                                                         |
| PaiementServiceImpl    | IPaiementRepository    | Constructeur | Lecture seule : un paiement est une composition du contrat                                                          |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | CRUD ; refuse une date de fin antérieure au début (pas d'attribut coût dans le modèle)                              |

## C. Messages d'erreur

**Message 1 (NoSuchBeanDefinitionException sur IContratService).** Aucun bean de ce type n'existe. Causes probables côté ContratServiceImpl : @Service oublié, ou classe placée hors du package tn.esprit.autoloc (hors scan). Correction : ajouter @Service, replacer la classe dans le bon package.

**Message 2 (NoUniqueBeanDefinitionException).** Deux beans de même type : emailNotificateur et smsNotificateur. Correction 1 : @Primary sur la classe du candidat par défaut (ex. EmailNotificateur). Correction 2 : @Qualifier("smsNotificateur") sur le paramètre du constructeur de NotificationController.

**Message 3 (dépendance circulaire).** Avec l'injection par constructeur, chaque bean doit être entièrement construit avec ses dépendances avant d'exister : ClientServiceImpl attend ReservationServiceImpl qui attend ClientServiceImpl, aucun ne peut être créé en premier. Piste : extraire la responsabilité commune dans un troisième composant dont les deux dépendent, ou faire dépendre un service du repository de l'autre plutôt que de son service.

## D. Anomalies SonarQube for IDE

| #  | Règle                            | Explication                                                                 | Correction                                      |
|----|----------------------------------|-----------------------------------------------------------------------------|-------------------------------------------------|
| 1  | S6813                            | @Autowired sur un champ : non final, dépendance cachée, null hors conteneur | Retour à @RequiredArgsConstructor + champ final |
| 2  | S112                             | RuntimeException levée dans findById : exception générique                  | Retour à ResourceNotFoundException              |
| 3  | (à compléter avec votre analyse) |                                                                             |                                                 |

## E. Questions de compréhension

1. Le new n'est écrit nulle part : c'est le conteneur Spring (ApplicationContext créé par SpringApplication.run) qui appelle le constructeur généré par Lombok après détection de @Service, en lui passant le proxy de IContratRepository.
2. Le contrôleur dépend de l'interface pour dépendre d'une abstraction : on peut remplacer l'implémentation (autre version, doublure de test) sans modifier le contrôleur.
3. Un singleton est partagé par tous les threads. Exemple de bug : un attribut « contratCourant » renseigné par la requête A est écrasé par la requête B ; A modifie alors le contrat de B.
4. Passer l'objet reçu à save écraserait toutes les colonnes (attributs absents mis à null) et, avec orphanRemoval, une liste de paiements vide pourrait supprimer tous les paiements. On charge donc l'existant, on recopie les attributs voulus, puis on enregistre.
5. @Component se pose sur une classe détectée par le scan ;
6. @Bean est une méthode d'une classe 
7. @Configuration, pour une classe qu'on ne peut pas annoter (bibliothèque, JDK) ou une construction particulière.
8. @Primary désigne sur la classe le candidat par défaut ; 
9. @Qualifier choisit un bean précis à l'endroit de l'injection. IPaiementService est en lecture seule car Paiement est une composition de Contrat (cascade ALL + orphanRemoval) : ses écritures passent par le contrat.