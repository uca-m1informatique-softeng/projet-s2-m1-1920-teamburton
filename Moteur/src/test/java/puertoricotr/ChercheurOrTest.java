package puertoricotr;

import org.junit.Test;
import puertoricotr.personnages.ChercheurOR;
import puertoricotr.personnages.Personnage;
import puertoricotr.stockageoutilsjeux.Banque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChercheurOrTest {

    @Test
    public void actionTest() {

        Partie partie;
        Joueurs[] joueur;
        int i;

        Banque banque;


        partie = new Partie(0, 2);
        joueur = partie.getJoueurs();

        banque = partie.getBanque();

        Personnage chercheurOr = new ChercheurOR();

        /* Le joueur gagne un doublon de la banque
         * -------------------------------------------------------------------------------------- */
        i = 0;
        int doublonBanque = banque.getNbDoublon();
        int doublon = joueur[0].getNbDoublon();
        chercheurOr.action(joueur, i, partie,1);

        assertEquals(doublonBanque - 1, banque.getNbDoublon());
        assertEquals(joueur[0].getNbDoublon() , doublon + 1);
    }
}
