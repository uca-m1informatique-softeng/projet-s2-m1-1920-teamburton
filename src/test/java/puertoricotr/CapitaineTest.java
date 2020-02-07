package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Capitaine;
import puertoricotr.stockageoutilsjeux.Banque;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;
import puertoricotr.stockageoutilsjeux.Reserve;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CapitaineTest {

    @Test
    public void actionTest() {

        Joueurs[] joueur;
        StrategieRandom bot1;
        StrategieRandom bot2;

        Capitaine capitaine;
        ArrayList <Batiment> batiments;
        ArrayList <Exploitation> plantations;
        ArrayList <Exploitation> carrieres;
        Magasin magasin;
        Banque banque;
        Reserve reserve;
        ArrayList <Navires> navires;
        navires = new ArrayList<>();

        int i = 0;
        joueur = new Joueurs[2];

        bot1 = new StrategieRandom();
        bot2 = new StrategieRandom();

        joueur[0] = new Joueurs("0", bot1);
        joueur[1] = new Joueurs("1", bot2);

        plantations = new ArrayList<>();
        carrieres = new ArrayList<>();
        batiments = new ArrayList<>();
        magasin = new Magasin();
        banque = new Banque(10, 10, 10);
        reserve = new Reserve(10, 10, 10, 10, 10);

        navires.add(new Navires(4));

        capitaine = new Capitaine();

        /* Joueur ne possede aucune marchandise à charger sur un navire
         * -------------------------------------------------------------------------------------- */
        capitaine.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navires,1);

        assertEquals(0, joueur[0].getNbTonneauxTotal());
        assertEquals(0, navires.get(0).getNbRessource());

         /* Joueur possede une marchandise à charger sur un navire
         * -------------------------------------------------------------------------------------- */
        joueur[1].addTonneau(Constantes.CAFE, 1);
        assertEquals(1, joueur[1].getNbTonneauxTotal());

        capitaine.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navires,1);

        assertEquals(0, joueur[1].getNbTonneauxTotal());
        assertEquals(1, navires.get(0).getNbRessource());

        /* Joueur possède 4 marchandises et il charge toutes ses marchandises sur un navire
         * -------------------------------------------------------------------------------------- */
        joueur[1].addTonneau(Constantes.CAFE, 1);
        capitaine.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navires,1);

        assertEquals(2, joueur[1].getNbPointVictoire());
    }
}
