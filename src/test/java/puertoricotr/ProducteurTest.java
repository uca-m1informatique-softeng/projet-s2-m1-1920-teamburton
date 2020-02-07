package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.batiments.productions.SechoirTabac;
import puertoricotr.batiments.productions.TeinturerieIndigo;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Producteur;
import puertoricotr.stockageoutilsjeux.Banque;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;
import puertoricotr.stockageoutilsjeux.Reserve;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProducteurTest {

    @Test
    public void actionTest() {

        Joueurs[] joueur;
        StrategieRandom bot1;
        StrategieRandom bot2;

        Producteur producteur;
        ArrayList <Batiment> batiments;
        ArrayList <Exploitation> plantations;
        ArrayList <Exploitation> carrieres;
        Magasin magasin;
        Banque banque;
        Reserve reserve;
        ArrayList <Navires> navires;

        Exploitation indigo;
        Exploitation mais;
        TeinturerieIndigo teinturerieIndigo;
        SechoirTabac sechoirTabac;

        int i = 0;
        joueur = new Joueurs[2];

        bot1 = new StrategieRandom();
        bot2 = new StrategieRandom();

        joueur[0] = new Joueurs("0", bot1);
        joueur[1] = new Joueurs("1", bot2);

        plantations = new ArrayList<>();
        carrieres = new ArrayList<>();
        batiments = new ArrayList<>();
        navires = new ArrayList<>();
        magasin = new Magasin();
        banque = new Banque(10, 10, 10);
        reserve = new Reserve(10, 10, 10, 10, 10);

        producteur = new Producteur();
        navires.add(new Navires(4));

        /* Joueurs ne possede ni bâtiments, ni plantations
         * -------------------------------------------------------------------------------------- */
        producteur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navires, 1);


        assertEquals(0, joueur[1].getPlateau().getNbBatiment());
        assertEquals(0, joueur[1].getPlateau().getNbExploitation());


        assertEquals(0, joueur[1].getNbTonneauxTotal());


        /* Joueurs possede un bâtiment, mais pas de plantations mais ne produit rien
         * -------------------------------------------------------------------------------------- */
        sechoirTabac = new SechoirTabac();

        joueur[1].addBatiment(sechoirTabac);
        producteur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navires, 1);


        assertEquals(1, joueur[1].getPlateau().getNbBatiment());
        assertEquals(0, joueur[1].getPlateau().getNbExploitation());
        assertEquals(0, joueur[1].getNbTonneauxTotal());


        /* Joueurs possede bâtiments et plantations du même type
         * -------------------------------------------------------------------------------------- */
        indigo = new Exploitation(Constantes.INDIGO);
        teinturerieIndigo = new TeinturerieIndigo();

        joueur[1].addExploitation(indigo);
        joueur[1].addBatiment(teinturerieIndigo);


        joueur[1].getPlateau().addNbColon(joueur[1].getPlateau().getNbPlaceCite() + 1);
        joueur[1].placerColonExploitaion();
        joueur[1].placerColonBatiment();
        producteur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navires,1);

        assertTrue(reserve.getNbMarchandise(Constantes.INDIGO) > 0);
        assertEquals(1, joueur[1].getNbIndigo());


        /* Joueurs possede une plantation de mais
         * -------------------------------------------------------------------------------------- */
        mais = new Exploitation(Constantes.MAIS);

        joueur[1].addExploitation(mais);
        joueur[1].getPlateau().addNbColon(1);
        joueur[1].placerColonExploitaion();
        producteur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navires,1);


        assertTrue(reserve.getNbMarchandise(Constantes.MAIS) > 0);
        assertEquals(1, joueur[1].getNbMais());

        /* Joueurs possede une plantation de mais, mais la reserve n'a plus de mais
         * -------------------------------------------------------------------------------------- */
        Reserve reserveMaisTest = new Reserve(0, 10, 1, 1, 1);

        mais = new Exploitation(Constantes.MAIS);
        joueur[1].addExploitation(mais);

        joueur[1].getPlateau().addNbColon(1);
        joueur[1].placerColonExploitaion();
        int nbMais = joueur[1].getNbMais();
        producteur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserveMaisTest, navires,1);
        assertEquals(0, reserveMaisTest.getNbMarchandise(Constantes.MAIS));
        assertEquals(nbMais, joueur[1].getNbMais());
    }
}
