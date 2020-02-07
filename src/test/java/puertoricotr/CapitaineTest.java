package puertoricotr;

import org.junit.Test;

import puertoricotr.personnages.Capitaine;
import puertoricotr.stockageoutilsjeux.Navires;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CapitaineTest {

    @Test
    public void actionTest() {
        Partie partie;
        Joueurs[] joueur;

        Capitaine capitaine;
        ArrayList <Navires> navires;

        int i = 0;

        partie = new Partie(0,2);
        navires = partie.getNavires();
        joueur = partie.getJoueurs();

        capitaine = new Capitaine();

        /* Joueur ne possede aucune marchandise à charger sur un navire
         * -------------------------------------------------------------------------------------- */
        capitaine.action(joueur, i, partie,1);

        assertEquals(0, joueur[0].getNbTonneauxTotal());
        assertEquals(0, navires.get(0).getNbRessource());

         /* Joueur possede une marchandise à charger sur un navire
         * -------------------------------------------------------------------------------------- */
        joueur[1].addTonneau(Constantes.CAFE, 1);
        assertEquals(1, joueur[1].getNbTonneauxTotal());

        capitaine.action(joueur, i,partie,1);

        assertEquals(0, joueur[1].getNbTonneauxTotal());

        /* Joueur possède 4 marchandises et il charge toutes ses marchandises sur un navire
         * -------------------------------------------------------------------------------------- */
        joueur[1].addTonneau(Constantes.CAFE, 1);
        capitaine.action(joueur, i, partie,1);

        assertEquals(2, joueur[1].getNbPointVictoire());
    }
}
