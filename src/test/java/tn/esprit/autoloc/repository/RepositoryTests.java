package tn.esprit.autoloc.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.*;

@SpringBootTest
class RepositoryTests {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;

    @Autowired
    private IEquipementRepository equipementRepository;

    @Autowired
    private IEmployeRepository employeRepository;

    @Autowired
    private IContratRepository contratRepository;

    // les méthodes de test viennent ici (étapes suivantes)
    @Test
    void testAgenceRepository() {
        long avant = agenceRepository.count();

        // 1. Créer une agence
        Agence agence = new Agence();
        agence.setNom("Agence Tunis Centre");
        agence.setVille("Tunis");
        agence.setAdresse("12 avenue Habib Bourguiba");
        agence.setTelephone("71000000");

        // 2. save() puis affichage de l'objet retourné
        Agence saved = agenceRepository.save(agence);
        System.out.println("Agence enregistrée : " + saved);
        assertNotNull(saved.getIdAgence());
        Long id = saved.getIdAgence();

        // 3. findAll()
        List<Agence> agences = agenceRepository.findAll();
        System.out.println("Toutes les agences : " + agences);
        assertFalse(agences.isEmpty());

        // 4. findById()
        Optional<Agence> trouvee = agenceRepository.findById(id);
        System.out.println("findById : " + trouvee);
        assertTrue(trouvee.isPresent());
        assertEquals("Agence Tunis Centre", trouvee.get().getNom());

        // 5. existsById()
        assertTrue(agenceRepository.existsById(id));

        // 6. count()
        System.out.println("Nombre d'agences : " + agenceRepository.count());
        assertEquals(avant + 1, agenceRepository.count());

        // 7. deleteById() puis vérification
        agenceRepository.deleteById(id);
        assertFalse(agenceRepository.existsById(id));
        assertEquals(avant, agenceRepository.count());
    }
    @Test
    void testClientRepository() {
        long avant = clientRepository.count();
        String suffixe = String.valueOf(System.currentTimeMillis());

        // 1. Deux clients avec les attributs réels de l'entité
        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Sami");
        c1.setEmail("sami." + suffixe + "@test.tn");
        c1.setTelephone("20111111");
        c1.setNumPermis("P1-" + suffixe);
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Amel");
        c2.setEmail("amel." + suffixe + "@test.tn");
        c2.setTelephone("20222222");
        c2.setNumPermis("P2-" + suffixe);
        c2.setDateInscription(LocalDate.now());

        // 2. save() des deux clients
        Client s1 = clientRepository.save(c1);
        Client s2 = clientRepository.save(c2);
        System.out.println("Client 1 : " + s1);
        System.out.println("Client 2 : " + s2);
        assertNotNull(s1.getIdClient());
        assertNotNull(s2.getIdClient());
        assertNotEquals(s1.getIdClient(), s2.getIdClient());

        // 3. findAll()
        List<Client> clients = clientRepository.findAll();
        System.out.println("Tous les clients : " + clients);
        assertTrue(clients.size() >= 2);

        // 4. findById()
        Optional<Client> trouve = clientRepository.findById(s1.getIdClient());
        System.out.println("findById : " + trouve);
        assertTrue(trouve.isPresent());
        assertEquals("Ben Ali", trouve.get().getNom());

        // 5. existsById() : identifiant existant puis inexistant
        assertTrue(clientRepository.existsById(s1.getIdClient()));
        assertFalse(clientRepository.existsById(Long.MAX_VALUE));

        // 6. count()
        System.out.println("Nombre de clients : " + clientRepository.count());
        assertEquals(avant + 2, clientRepository.count());
    }
    @Test
    void testVehiculeRepository() {
        long avant = vehiculeRepository.count();

        // 1. Créer un véhicule (immatriculation unique)
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TN-" + (System.currentTimeMillis() % 1_000_000));
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("80.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        // 2. save()
        Vehicule saved = vehiculeRepository.save(vehicule);
        System.out.println("Véhicule enregistré : " + saved);
        assertNotNull(saved.getIdVehicule());
        Long id = saved.getIdVehicule();

        // 3. findAll()
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        System.out.println("Tous les véhicules : " + vehicules);
        assertFalse(vehicules.isEmpty());

        // 4. count()
        System.out.println("Nombre de véhicules : " + vehiculeRepository.count());
        assertEquals(avant + 1, vehiculeRepository.count());

        // 5. Modifier une propriété puis save()
        saved.setStatut(StatutVehicule.LOUE);
        saved.setTarifJournalier(new BigDecimal("95.00"));
        Vehicule modifie = vehiculeRepository.save(saved);
        System.out.println("Véhicule modifié : " + modifie);
        assertEquals(id, modifie.getIdVehicule());   // même identifiant : UPDATE, pas un nouvel INSERT

        Vehicule relu = vehiculeRepository.findById(id).orElseThrow();
        assertEquals(StatutVehicule.LOUE, relu.getStatut());
        assertEquals(0, new BigDecimal("95.00").compareTo(relu.getTarifJournalier()));
        // 6. deleteById()
        vehiculeRepository.deleteById(id);
        assertFalse(vehiculeRepository.existsById(id));
        assertEquals(avant, vehiculeRepository.count());
    }
    @Test
    void testEquipementRepository() {
        Equipement equipement = new Equipement();
        equipement.setLibelle("GPS-" + System.currentTimeMillis());   // libellé unique

        Equipement saved = equipementRepository.save(equipement);
        System.out.println("Équipement enregistré : " + saved);
        Long id = saved.getIdEquipement();
        assertNotNull(id);

        assertTrue(equipementRepository.findById(id).isPresent());
        System.out.println("Tous les équipements : " + equipementRepository.findAll());
        assertTrue(equipementRepository.existsById(id));

        equipementRepository.deleteById(id);
        assertFalse(equipementRepository.existsById(id));
    }

    @Test
    void testEmployeRepository() {
        Employe employe = new Employe();
        employe.setNom("Gharbi");
        employe.setPrenom("Nour");
        employe.setRole(RoleEmploye.AGENT);

        Employe saved = employeRepository.save(employe);
        System.out.println("Employé enregistré : " + saved);
        Long id = saved.getIdEmploye();
        assertNotNull(id);

        Employe lu = employeRepository.findById(id).orElseThrow();
        assertEquals(RoleEmploye.AGENT, lu.getRole());
        assertTrue(employeRepository.existsById(id));

        employeRepository.deleteById(id);
        assertFalse(employeRepository.existsById(id));
    }

    @Test
    void testContratRepository() {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("1500.00"));
        contrat.setValide(true);

        Contrat saved = contratRepository.save(contrat);
        System.out.println("Contrat enregistré : " + saved);
        Long id = saved.getIdContrat();
        assertNotNull(id);

        System.out.println("Tous les contrats : " + contratRepository.findAll());
        assertTrue(contratRepository.findById(id).isPresent());
        assertTrue(contratRepository.existsById(id));

        contratRepository.deleteById(id);
        assertFalse(contratRepository.existsById(id));
    }
}