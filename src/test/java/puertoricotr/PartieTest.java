package puertoricotr;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartieTest {


    /**
     * Test de la methode servant à initialiser la pile de plantation
     */
    @Test
    public void initPilePlantationTest(){

        Partie partie = new Partie(0, 2);
        assertEquals(2, partie.getNbJoueurTotal());

        partie = new Partie(0, 2);
        assertEquals (47, partie.getPilePlantation().size());
    }

    /**
     * Test méthode initPlantations
     */
    @Test
    public void initPlantationsTest(){
        Partie partie = new Partie(0, 2);
        assertTrue(partie.getPlantations().size()  > partie.getNbJoueurTotal());
    }

    /**
     * Test méthode initBavire
     */
    @Test
    public void initNavireTest(){
        // 2 Joueurs
        Partie partie = new Partie(0, 2);
        assertEquals(2, partie.getNavires().size());
    }
    /**
     * Test méthode joueurMaxPV(Joueur[] joueur)
     */
    @Test
    public void joueurMaxPvTest(){
        Partie partie = new Partie(0, 2);
        ArrayList<Joueurs> joueurs = new ArrayList<>();

        Joueurs joueur1 = new Joueurs("Joueur 1",new StrategieRandom());
        Joueurs joueur2 = new Joueurs("Joueur 2",new StrategieGarantie());

        // Le premier joueur à plus de points victoires
        joueur1.addPointVictoire(5);
        joueurs.add(joueur1);
        joueurs.add(joueur2);

        assertEquals(partie.joueurMaxPv(joueurs), joueur1);

        // Le seccond joueur en a plus
        joueur1.setPointVictoire(0);
        joueur2.addPointVictoire(5);
        joueurs.add(joueur1);
        joueurs.add(joueur2);

        assertEquals(0, joueur1.getNbPointVictoire());
        assertEquals(partie.joueurMaxPv(joueurs), joueur2);
    }
}
