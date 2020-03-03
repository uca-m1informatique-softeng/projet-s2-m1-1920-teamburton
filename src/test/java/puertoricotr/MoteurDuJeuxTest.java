package puertoricotr;

import org.junit.jupiter.api.Test;
import puertoricotr.batiments.productions.TeinturerieIndigo;
import puertoricotr.personnages.ChercheurOR;
import puertoricotr.personnages.Personnage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoteurDuJeuxTest {

    private MoteurDuJeux moteurDuJeux;
    private Partie partie;


    /**
     * Test méthode ajoujeDoublon(Arraylist <Personnage> p)
     */
    @Test
    public void ajouteDoublonTest(){

        moteurDuJeux = new MoteurDuJeux(0,2, 1);
        partie = moteurDuJeux.getPartie();
        ArrayList<Personnage> personnages = new ArrayList<>();

        Personnage chercheurOr;
        chercheurOr = new ChercheurOR();
        personnages.add(chercheurOr);

        int doublonBanque = partie.getBanque().getNbDoublon();

        moteurDuJeux.ajouteDoublon(personnages);

        assertEquals(1, chercheurOr.getDoublon());
        assertEquals(doublonBanque - 1, partie.getBanque().getNbDoublon());
    }


    /**
     * Test méthode citePleine
     */
    @Test
    public void testCitePleineTest(){

        moteurDuJeux = new MoteurDuJeux(0,2, 1);
        partie = moteurDuJeux.getPartie();
        // Cite vide
        assertFalse(moteurDuJeux.testCitePleine());

        // Cite pleine
        partie.getJoueurs()[0].getPlateau().setNbBatiment(12);

        assertTrue(moteurDuJeux.testCitePleine());

    }


    /**
     * Test méthode citePleine
     */
    @Test
    public void calculerPointsVictoiresBatimentsTest(){

        moteurDuJeux = new MoteurDuJeux(0,2, 1);
        partie = moteurDuJeux.getPartie();
        Joueurs[] joueurs = partie.getJoueurs();
              /*
        assertEquals(0, joueurs[0].getPointVictoire());
        */


        TeinturerieIndigo teinturerieIndigo = new TeinturerieIndigo();
        joueurs[0].addBatiment(teinturerieIndigo);

        int pvBat = moteurDuJeux.calculerPvBatiments(joueurs[0]);

        assertEquals(pvBat, teinturerieIndigo.getPointsVicoires());
    }
}