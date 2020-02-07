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
     * Test de la methode servant à initialiser la pile de plantation
     */
    @Test
    public void initPilePlantationTest(){

        moteurDuJeux = new MoteurDuJeux(0,2, 0);
        partie = moteurDuJeux.getPartie();
        assertEquals(2, partie.getNbJoueurTotal());

        moteurDuJeux = new MoteurDuJeux(0,2,0);

        assertEquals (47, partie.getPilePlantation().size());
    }

    /**
     * Test méthode initPlantations
     */
    @Test
    public void initPlantationsTest(){
        moteurDuJeux = new MoteurDuJeux(0,2, 0);
        partie = moteurDuJeux.getPartie();
        assertTrue(partie.getPlantations().size()  > partie.getNbJoueurTotal());

    }

    /**
     * Test méthode initBavire
     */
    @Test
    public void initNavireTest(){
        // 2 Joueurs
        moteurDuJeux = new MoteurDuJeux(0,2, 1);
        partie = moteurDuJeux.getPartie();
        assertEquals(2, partie.getNavires().size());
    }


    /**
     * Test méthode ajoujeDoublon(Arraylist <Personnage> p)
     */
    @Test
    public void ajouteDoublonTest(){

        moteurDuJeux = new MoteurDuJeux(0,2, 0);
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
     * Test méthode joueurMaxPV(Joueur[] joueur)
     */
    @Test

    public void joueurMaxPvTest(){
        ArrayList<Joueurs> joueurs = new ArrayList<>();
        moteurDuJeux = new MoteurDuJeux(0,2, 1);

        Joueurs joueur1 = new Joueurs("Joueur 1",new StrategieRandom());
        Joueurs joueur2 = new Joueurs("Joueur 2",new StrategieGarantie());

        // Le premier joueur à plus de points victoires
        joueur1.addPointVictoire(5);
        joueurs.add(joueur1);
        joueurs.add(joueur2);

        assertEquals(moteurDuJeux.joueurMaxPv(joueurs), joueur1);

        // Le seccond joueur en a plus
        joueur1.setPointVictoire(0);
        joueur2.addPointVictoire(5);
        joueurs.add(joueur1);
        joueurs.add(joueur2);

        assertEquals(0, joueur1.getNbPointVictoire());
        assertEquals(moteurDuJeux.joueurMaxPv(joueurs), joueur2);

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

        moteurDuJeux = new MoteurDuJeux(0,3, 0);
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