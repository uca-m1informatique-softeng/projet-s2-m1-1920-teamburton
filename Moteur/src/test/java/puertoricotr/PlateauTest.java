package puertoricotr;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.batiments.productions.TeinturerieIndigo;
import puertoricotr.exploitations.Exploitation;

import static org.junit.jupiter.api.Assertions.*;

public class PlateauTest {
    private Plateau plateau;

    @BeforeEach
    void init(){
        plateau = new Plateau();

    }

    /**
     * Methode de test de la methode addExploitation
     */
    @Test
    public void addExploitationTest(){
        plateau.setNbExploitation(0);

        Exploitation indigo;
        indigo = new Exploitation(Constantes.INDIGO);

        plateau.addExploitation(indigo);
        int nbExploitation = plateau.getNbExploitation();

        assertEquals(plateau.getIle()[0].getNom(), indigo.getNom());
        assertEquals(nbExploitation, plateau.getNbExploitation());

        while (plateau.getNbExploitation() < 12){
            plateau.addExploitation(indigo);
        }

        assertEquals(12, plateau.getNbExploitation());

        plateau.addExploitation(indigo);

        assertEquals(12, plateau.getNbExploitation());
    }


    /**
     * Methode de test de la methode addBatiment
     */
    @Test
    public void addBatimentTest(){
        plateau.setNbBatiment(0);

        Batiment teinturerieIndigo = new TeinturerieIndigo();

        plateau.addBatiment(teinturerieIndigo);
        int nbBatiment = plateau.getNbBatiment();
        
        // Nombre teinturerieIndigo inferieur à 12
        assertEquals(plateau.getCite()[0].getNom(), teinturerieIndigo.getNom());
        assertEquals(nbBatiment, plateau.getNbBatiment());

        // Nombre teinturerieIndigo supérieur à 12
        while (plateau.getNbBatiment() < 12){
            plateau.addBatiment(teinturerieIndigo);
        }

        assertEquals(12, plateau.getNbBatiment());

        plateau.addBatiment(teinturerieIndigo);

        assertEquals(12, plateau.getNbBatiment());

    }

    /**
     * Methode de test de placeDispoPlateau
     */
    @Test
    public void placeDispoPlateauTest(){
        plateau.setNbBatiment(0);
        plateau.setNbExploitation(0);

        Exploitation indigo = new Exploitation(Constantes.INDIGO);
        Batiment teinturerieIndigo = new TeinturerieIndigo();

        plateau.addExploitation(indigo);
        plateau.addBatiment(teinturerieIndigo);

        assertTrue(plateau.placeDispoPlateau());

        plateau.setNbColonIle(1);
        plateau.setNbColonCite(teinturerieIndigo.getNbColonLimite());

        assertFalse(plateau.placeDispoPlateau());
    }

    /**
     * Methode de test de placeDispoIle
     */
    @Test
    public void placeDispoIleTest(){
        plateau.setNbExploitation(0);

        Exploitation indigo = new Exploitation(Constantes.INDIGO);
        plateau.addExploitation(indigo);

        assertTrue(plateau.placeDispoIle());

        plateau.setNbColonIle(1);

        assertFalse(plateau.placeDispoIle());
    }

    /**
     * Methode de test de placeDispoPlateau
     */
    @Test
    public void placeDispoCiteTest(){
        plateau.setNbBatiment(0);

        Batiment teinturerieIndigo = new TeinturerieIndigo();
        plateau.setNbPlaceCite(teinturerieIndigo.getNbColonLimite());

        plateau.addBatiment(teinturerieIndigo);

        assertTrue(plateau.placeDispoCite());

        plateau.setNbColonCite(teinturerieIndigo.getNbColonLimite());

        assertFalse(plateau.placeDispoCite());
    }

    /**
     * Methode de test de contientExploitation(String nomExploitation)
     */
    @Test
    public void contientExploitationTest(){
        Exploitation indigo = new Exploitation(Constantes.INDIGO);

        // Ne contient rien
        assertFalse(plateau.contientExploitation(indigo.getNom()));

        // Contient 1 exploitation
        plateau.addExploitation(indigo);

        assertTrue(plateau.contientExploitation(indigo.getNom()));

        // Ne contient pas la plantation donnée en paramètre
        assertFalse(plateau.contientExploitation(Constantes.MAIS));
    }
    /**
     * Methode de test de getAffichage
     */
    @Test
    public void getAffichageTest(){
        Batiment teinturerieIndigo = new TeinturerieIndigo();
        Exploitation indigo = new Exploitation(Constantes.INDIGO);

        plateau.addExploitation(indigo);
        plateau.addBatiment(teinturerieIndigo);

        assertNotNull(plateau.getAffichage());
    }
}
