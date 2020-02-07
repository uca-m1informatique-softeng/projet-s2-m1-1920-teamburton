package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.Batiment;

import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Maire;
import puertoricotr.stockageoutilsjeux.Banque;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;
import puertoricotr.stockageoutilsjeux.Reserve;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaireTest {
    @Test
    public void actionTest() {

        Joueurs[] joueur;
        StrategieRandom bot1;
        StrategieRandom bot2;

        Maire maire;
        ArrayList<Batiment> batiments;
        ArrayList<Exploitation> plantations;
        ArrayList<Exploitation> carrieres;
        Magasin magasin;
        Banque banque;
        Reserve reserve;

        plantations = new ArrayList<>();
        carrieres = new ArrayList<>();
        batiments = new ArrayList<>();
        magasin = new Magasin();
        banque = new Banque(10, 10, 10);
        reserve = new Reserve(10, 10, 10, 10, 10);
        ArrayList <Navires> navire;
        navire = new ArrayList<>();


        maire = new Maire();

        int i = 0;
        int nbColonB1;
        int nbColonB2;
        joueur = new Joueurs[2];


        bot1 = new StrategieRandom();
        bot2 = new StrategieRandom();

        joueur[0] = new Joueurs("0", bot1);
        joueur[1] = new Joueurs("1", bot2);


        maire.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,0);
        nbColonB1 = joueur[0].getPlateau().getNbColon();
        nbColonB2 = joueur[1].getPlateau().getNbColon();

        // Joueur sans privilège
        assertEquals(nbColonB2, joueur[1].getPlateau().getNbColon());

        // Joueur avec privilège
        assertEquals(nbColonB1, joueur[0].getPlateau().getNbColon());
        assertEquals(10 - (nbColonB1 + nbColonB2), banque.getNbColon());
    }
}
