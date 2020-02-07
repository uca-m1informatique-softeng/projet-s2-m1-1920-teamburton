package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.ChercheurOR;
import puertoricotr.personnages.Personnage;
import puertoricotr.stockageoutilsjeux.Banque;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;
import puertoricotr.stockageoutilsjeux.Reserve;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChercheurOrTest {

    @Test
    public void actionTest() {
        Joueurs[] joueur;
        int i;
        StrategieRandom bot;

        ArrayList<Batiment> batiments;
        ArrayList <Exploitation> plantations;
        ArrayList <Exploitation> carrieres;
        ArrayList <Navires> navire;
        Magasin magasin;
        Banque banque;
        Reserve reserve;

        bot = new StrategieRandom();
        joueur = new Joueurs[1];
        joueur[0] = new Joueurs("0",bot);

        plantations = new ArrayList<>();
        carrieres = new ArrayList<>();
        batiments = new ArrayList<>();
        navire = new ArrayList<>();
        magasin = new Magasin();
        banque = new Banque(10, 10, 10);
        reserve = new Reserve(10, 10, 10, 10, 10);

        Personnage chercheurOr = new ChercheurOR();

        /* Le joueur gagne un doublon de la banque
         * -------------------------------------------------------------------------------------- */
        i = 0;
        int doublon = joueur[0].getNbDoublon();
        chercheurOr.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,1);

        assertEquals(9, banque.getNbDoublon());
        assertEquals(joueur[0].getNbDoublon() , doublon + 1);
    }
}
