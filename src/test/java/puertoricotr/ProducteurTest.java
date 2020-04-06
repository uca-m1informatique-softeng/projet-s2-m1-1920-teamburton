package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.productions.SechoirTabac;
import puertoricotr.batiments.productions.TeinturerieIndigo;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Producteur;
import puertoricotr.stockageoutilsjeux.Navires;
import puertoricotr.stockageoutilsjeux.Reserve;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProducteurTest {

    @Test
    public void actionTest() {
        Partie partie = new Partie(0, 2, new ServeurStats());
        Joueurs[] joueur;

        Producteur producteur;
        Reserve reserve;
        ArrayList <Navires> navires;

        Exploitation indigo;
        Exploitation mais;
        TeinturerieIndigo teinturerieIndigo;
        SechoirTabac sechoirTabac;

        int i = 0;

        joueur = partie.getJoueurs();
        navires = partie.getNavires();
        reserve = partie.getReserve();

        producteur = new Producteur();
        navires.add(new Navires(4));

        /* Joueurs ne possede ni bâtiments, ni plantations
         * -------------------------------------------------------------------------------------- */
        producteur.action(joueur, i, partie, 1);


        assertEquals(0, joueur[1].getPlateau().getNbBatiment());
        assertEquals(0, joueur[1].getPlateau().getNbExploitation());

        assertEquals(0, joueur[1].getNbTonneauxActuel());


        /* Joueurs possede un bâtiment, mais pas de plantations mais ne produit rien
         * -------------------------------------------------------------------------------------- */
        sechoirTabac = new SechoirTabac();

        joueur[1].addBatiment(sechoirTabac);
        producteur.action(joueur, i, partie, 1);

        assertEquals(1, joueur[1].getPlateau().getNbBatiment());
        assertEquals(0, joueur[1].getPlateau().getNbExploitation());
        assertEquals(0, joueur[1].getNbTonneauxActuel());


        /* Joueurs possede bâtiments et plantations du même type
         * -------------------------------------------------------------------------------------- */
        indigo = new Exploitation(Constantes.INDIGO);
        teinturerieIndigo = new TeinturerieIndigo();

        joueur[1].addExploitation(indigo);
        joueur[1].addBatiment(teinturerieIndigo);


        joueur[1].getPlateau().addNbColon(joueur[1].getPlateau().getNbPlaceCite() + 1);
        joueur[1].placerColonExploitaion();
        joueur[1].placerColonBatiment();
        producteur.action(joueur, i,partie,1);

        assertTrue(reserve.getNbMarchandise(Constantes.INDIGO) > 0);
        assertEquals(1, joueur[1].getNbIndigo());


        /* Joueurs possede une plantation de mais
         * -------------------------------------------------------------------------------------- */
        mais = new Exploitation(Constantes.MAIS);

        joueur[1].addExploitation(mais);
        joueur[1].getPlateau().addNbColon(1);
        joueur[1].placerColonExploitaion();
        producteur.action(joueur, i, partie,1);


        assertTrue(reserve.getNbMarchandise(Constantes.MAIS) > 0);
        assertEquals(1, joueur[1].getNbMais());

        /* Joueurs possede une plantation de mais, mais la reserve n'a plus de mais
         * -------------------------------------------------------------------------------------- */
        Reserve reserveMaisTest = new Reserve(0, 10, 1, 1, 1);

        mais = new Exploitation(Constantes.MAIS);
        joueur[1].addExploitation(mais);

        joueur[1].getPlateau().addNbColon(1);
        joueur[1].placerColonExploitaion();
        int nbMais = joueur[1].getNbMais() + 2;
        producteur.action(joueur, i, partie,1);
        assertEquals(0, reserveMaisTest.getNbMarchandise(Constantes.MAIS));
        assertEquals(nbMais, joueur[1].getNbMais());
    }
}
