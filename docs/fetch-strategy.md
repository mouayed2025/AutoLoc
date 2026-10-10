# Stratégie de fetch et de cascade : AutoLoc

## 1. Les trois notions utilisées

- Fetch LAZY : les données liées ne sont chargées que si le code les demande vraiment.
  Cela évite de charger des informations inutiles.
- Cascade : une action faite sur le parent (enregistrer, supprimer...) est répétée
  automatiquement sur ses enfants.
- orphanRemoval : si on retire un enfant de la liste de son parent, cet enfant est
  supprimé de la base.

## 2. Choix pour chaque association

| Association            | Fetch   | Cascade                  | Justification                                                                                                                                                               |
|------------------------|---------|--------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Contrat → Paiement     | LAZY    | ALL + orphanRemoval      | Un paiement n'existe pas sans son contrat. Si on supprime le contrat, ses paiements sont supprimés. Si on retire un paiement de la liste du contrat, il est supprimé aussi. |
| Agence → Vehicule      | LAZY    | Aucune                   | Un véhicule continue d'exister si son agence disparaît : il peut être transféré dans une autre.                                                                             |
| Agence → Employe       | LAZY    | Aucune                   | Un employé continue d'exister si son agence disparaît : il peut être muté.                                                                                                  |
| Vehicule ↔ Equipement  | LAZY    | Aucune                   | Un même équipement (ex. GPS) est partagé par plusieurs véhicules. Supprimer un véhicule ne doit pas le supprimer. On utilise un Set pour éviter les doublons.               |
| Client → Reservation   | LAZY    | PERSIST                  | Enregistrer un nouveau client peut enregistrer sa première réservation. Mais supprimer un client ne doit pas effacer l'historique des réservations.                         |
| Reservation → Vehicule | LAZY    | Aucune                   | Le véhicule existe avant et après la réservation. L'association n'est déclarée que d'un côté.                                                                               |
| Reservation ↔ Contrat  | LAZY    | ALL (côté Reservation)   | Le contrat naît de la réservation et n'a pas de sens sans elle. Supprimer la réservation supprime le contrat, puis ses paiements.                                           |
| Vehicule → Maintenance | LAZY    | PERSIST                  | Une maintenance peut être enregistrée avec le véhicule. Mais l'historique doit rester si le véhicule quitte la flotte.                                                      |

## 3. Pourquoi tout est en LAZY

- Par défaut, @ManyToOne et @OneToOne sont chargés tout de suite (EAGER).
  Charger un objet chargerait alors toute une chaîne d'objets liés, même inutiles.
- Avec LAZY, une lecture reste légère et on ne charge que ce dont on a besoin.

## 4. Règles retenues

1. **Cascade seulement pour une composition** : l'enfant ne peut pas vivre sans son parent
   (exemple : Paiement et Contrat).
2. **Pas de cascade de suppression** quand l'enfant existe de façon indépendante
   (exemple : Vehicule et Agence).
3. **Le côté propriétaire** est le côté @ManyToOne : c'est lui qui porte la clé étrangère
   en base. Le côté inverse utilise mappedBy et ne crée aucune colonne.
4. **Pas de @Data** sur les entités liées : risque de StackOverflowError.
   On utilise @Getter, @Setter, @NoArgsConstructor et @AllArgsConstructor.