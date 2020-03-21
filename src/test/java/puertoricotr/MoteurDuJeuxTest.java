package puertoricotr;

import org.junit.jupiter.api.Test;
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

        this.partie = new Partie(0, 2, new ServeurStats());
        moteurDuJeux = new MoteurDuJeux(partie,1);

        ArrayList<Personnage> personnages = new ArrayList<>();

        Personnage chercheurOr;
        chercheurOr = new ChercheurOR();
        personnages.add(chercheurOr);

        int doublonBanque = partie.getBanque().getNbDoublon();

        moteurDuJeux.ajouteDoublon(personnages);

        assertEquals(1, chercheurOr.getDoublon());
        assertEquals(doublonBanque - 1, partie.getBanque().getNbDoublon());
    }
}
