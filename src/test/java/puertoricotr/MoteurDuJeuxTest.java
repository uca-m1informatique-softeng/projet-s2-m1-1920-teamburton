package puertoricotr;

import org.junit.jupiter.api.Test;
import puertoricotr.batiments.productions.TeinturerieIndigo;
import puertoricotr.personnages.ChercheurOR;
import puertoricotr.personnages.Personnage;
import puertoricotr.stockageoutilsjeux.Banque;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoteurDuJeuxTest {

    private MoteurDuJeux moteurDuJeux;

    /**
     * Test de la methode servant à initialiser la pile de plantation
     */
    @Test
    public void initPilePlantationTest(){
        moteurDuJeux = new MoteurDuJeux(0,2, 0);
        assertEquals(2, moteurDuJeux.getNbJoueurTotal());

        moteurDuJeux = new MoteurDuJeux(0,2,0);

        assertEquals (47, moteurDuJeux.getPilePlantation().size());
    }

    /**
     * Test méthode initPlantations
     */
    @Test
    public void initPlantationsTest(){
        moteurDuJeux = new MoteurDuJeux(0,2, 0);
        assertTrue(moteurDuJeux.getPlantations().size()  > moteurDuJeux.getNbJoueurTotal());

    }

    /**
     * Test méthode initBavire
     */
    @Test
    public void initNavireTest(){
        // 2 Joueurs
        moteurDuJeux = new MoteurDuJeux(0,2, 1);
        assertEquals(2, moteurDuJeux.getNavires().size());

        // 3 joueurs
        moteurDuJeux = new MoteurDuJeux(0,3, 1);

        assertEquals(3, moteurDuJeux.getNavires().size());
    }


    /**
     * Test méthode ajoujeDoublon(Arraylist <Personnage> p)
     */
    @Test
    public void ajouteDoublonTest(){

        moteurDuJeux = new MoteurDuJeux(0,2, 0);

        ArrayList<Personnage> personnages = new ArrayList<>();

        Personnage chercheurOr;
        chercheurOr = new ChercheurOR();
        personnages.add(chercheurOr);

        int doublonBanque = moteurDuJeux.getBanque().getNbDoublon();

        moteurDuJeux.ajouteDoublon(personnages);

        assertEquals(1, chercheurOr.getDoublon());
        assertEquals(doublonBanque - 1, moteurDuJeux.getBanque().getNbDoublon());
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
 
        // Cite vide
        assertFalse(moteurDuJeux.testCitePleine());

        // Cite pleine
        moteurDuJeux.getJoueurs()[0].getPlateau().setNbBatiment(12);

        assertTrue(moteurDuJeux.testCitePleine());

    }

    /**
     * Test méthode citePleine
     */
    @Test
    public void calculerPointsVictoiresBatimentsTest(){
        moteurDuJeux = new MoteurDuJeux(0,3, 0);

        Joueurs[] joueurs = moteurDuJeux.getJoueurs();
              /*
        assertEquals(0, joueurs[0].getPointVictoire());
        */


        TeinturerieIndigo teinturerieIndigo = new TeinturerieIndigo();
        joueurs[0].addBatiment(teinturerieIndigo);

        int pvBat = moteurDuJeux.calculerPvBatiments(joueurs[0]);

        assertEquals(pvBat, teinturerieIndigo.getPointsVicoires());
    }

}