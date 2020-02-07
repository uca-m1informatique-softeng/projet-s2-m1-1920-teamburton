package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.batiments.petits.Hacienda;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Paysan;
import puertoricotr.personnages.Personnage;
import puertoricotr.stockageoutilsjeux.Banque;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;
import puertoricotr.stockageoutilsjeux.Reserve;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaysanTest {



    @Test
    public void actionTest(){

        Joueurs[] joueur;
        StrategieRandom bot1;
        StrategieRandom bot2;
        int tour;

        ArrayList <Batiment> batiments;
        ArrayList <Exploitation> plantations;
        ArrayList <Exploitation> carrieres;
        ArrayList <Navires> navire;
        Magasin magasin;
        Banque banque;
        Reserve reserve;

        Exploitation cafe;
        Exploitation sucre;
        Exploitation mais;
        Exploitation carriere;
        Hacienda hacienda;

        int i = 0;
        joueur = new Joueurs[2];

        bot1 = new StrategieRandom();
        bot2 = new StrategieRandom();

        joueur[0] = new Joueurs("0",bot1);
        joueur[1] = new Joueurs("1",bot2);

        plantations = new ArrayList<>();
        carrieres = new ArrayList<>();
        batiments = new ArrayList<>();
        navire = new ArrayList<>();
        magasin = new Magasin();
        banque = new Banque(10, 10, 10);
        reserve = new Reserve(10, 10, 10, 10, 10);


        hacienda=new Hacienda();
        batiments.add(hacienda);

        cafe = new Exploitation(Constantes.CAFE);
        sucre= new Exploitation(Constantes.SUCRE);
        carriere = new Exploitation(Constantes.CARRIERE);
        mais = new Exploitation(Constantes.MAIS);

        Personnage paysan = new Paysan();

        /* Privilège du joueur, mais plus de carrière, donc choisi une autre plantation
         * -------------------------------------------------------------------------------------- */
        plantations.add(cafe);
        paysan.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,1);

        assertTrue(joueur[0].getPlateau().contientExploitation(cafe.getNom()));

        /* Le bot2 choisie une plantation sans privilège
         * -------------------------------------------------------------------------------------- */
        plantations.add(sucre);
        plantations.add(new Exploitation(Constantes.SUCRE));
        paysan.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,1);

        assertTrue(joueur[1].getPlateau().contientExploitation(sucre.getNom()));

        /* bot2 possède une Hacienda bonus (+2 plantations) sans privilège
         * -------------------------------------------------------------------------------------- */
        plantations.add(mais);
        plantations.add(new Exploitation(Constantes.TABAC));
        plantations.add(new Exploitation(Constantes.CAFE));
        batiments.add(hacienda);
        joueur[1].addBatiment(batiments.get(0));

        paysan.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,1);

        // assertEquals(3, joueur[1].getPlateau().getNbExploitation());
    }}