package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.batiments.productions.BrulerieCafe;
import puertoricotr.batiments.productions.PetiteRaffinerieSucre;
import puertoricotr.batiments.productions.SechoirTabac;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Batisseur;
import puertoricotr.personnages.Personnage;
import puertoricotr.stockageoutilsjeux.Banque;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;
import puertoricotr.stockageoutilsjeux.Reserve;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BatisseurTest {


    @Test
    public void actionTest(){

        Joueurs[] joueur;
        StrategieRandom bot1;
        StrategieRandom bot2;

        PetiteRaffinerieSucre petiteRaffinerieSucre;
        SechoirTabac sechoirTabac;
        BrulerieCafe brulerieCafe;

        ArrayList <Batiment> batiments;
        ArrayList <Exploitation> plantations;
        ArrayList <Exploitation> carrieres;

        Magasin magasin;
        Banque banque;
        Reserve reserve;
        ArrayList <Navires> navire;

        plantations = new ArrayList<>();
        carrieres = new ArrayList<>();
        batiments = new ArrayList<>();
        navire = new ArrayList<>();
        magasin = new Magasin();
        banque = new Banque(10, 10, 10);
        reserve = new Reserve(10, 10, 10, 10, 10);

        int i = 0;
        joueur = new Joueurs[2];

        bot1 = new StrategieRandom();
        bot2 = new StrategieRandom();

        joueur[0] = new Joueurs("0",bot1);
        joueur[1] = new Joueurs("1",bot2);

        Personnage batisseur = new Batisseur();
        int doublon;

        /* Plus de bâtiments disponible
         * -------------------------------------------------------------------------------------- */

        doublon = joueur[1].getNbDoublon();
        assertFalse(joueur[1].peutConstruire(batiments));
        batisseur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,1);

        assertTrue(batiments.isEmpty());
        assertEquals(doublon, joueur[1].getNbDoublon());
        assertEquals(0, joueur[1].getPlateau().getNbBatiment());

        /* Le joueur peut construire un bâtiment
         * -------------------------------------------------------------------------------------- */

        petiteRaffinerieSucre = new PetiteRaffinerieSucre();
        batiments.add(petiteRaffinerieSucre);
        batiments.add(new PetiteRaffinerieSucre());

        assertTrue(joueur[1].peutConstruire(batiments));
        assertFalse(batiments.isEmpty());
        assertTrue(petiteRaffinerieSucre.getPrix() <= joueur[1].getNbDoublon());

        doublon = joueur[1].getNbDoublon() - petiteRaffinerieSucre.getPrix();
        batisseur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,1);


        assertEquals(doublon, joueur[1].getNbDoublon());
        assertTrue(joueur[1].getPlateau().possedeBatiment(petiteRaffinerieSucre.getNom()));

        /* Le joueur peut construire un bâtiment, mais il a déjà un exemeplaire
         * -------------------------------------------------------------------------------------- */
        petiteRaffinerieSucre = new PetiteRaffinerieSucre();
        batiments.add(petiteRaffinerieSucre);


        doublon = joueur[1].getNbDoublon();
        batisseur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,1);

        assertEquals(doublon, joueur[1].getNbDoublon());
        assertTrue(joueur[1].getPlateau().possedeBatiment(petiteRaffinerieSucre.getNom()));


        /* Privilège du joueur
         * -------------------------------------------------------------------------------------- */
        i = 0;
        joueur[0].addDoublon(15);

        doublon = joueur[0].getNbDoublon();
        int reductionPrivilege = 1;

        brulerieCafe = new BrulerieCafe();
        batiments.add(brulerieCafe);

        int prixCafe = brulerieCafe.getPrix();

        batisseur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,1);

        assertEquals(doublon - (prixCafe - reductionPrivilege), joueur[0].getNbDoublon());
        assertTrue(joueur[0].getPlateau().possedeBatiment(brulerieCafe.getNom()));

        /* Le joueur peut construire un bâtiment et possède 1 carriere occupée
         * -------------------------------------------------------------------------------------- */
        i = 0;
        joueur[1].addDoublon(10);
        joueur[1].addExploitation(new Exploitation(Constantes.CARRIERE));
        joueur[1].getPlateau().addNbColon(1);
        joueur[1].placerColonExploitaion();

        assertTrue(joueur[1].getPlateau().getIle()[0].estOccupe());

        doublon = joueur[1].getNbDoublon();

        int reductionCarriere = 1;

        sechoirTabac = new SechoirTabac();
        batiments.add(sechoirTabac);
        batiments.add(new SechoirTabac());

        int prixTabac = sechoirTabac.getPrix();

        batisseur.action(joueur, i, plantations, carrieres, batiments, magasin, banque, reserve, navire,1);

        assertEquals(doublon - (prixTabac - reductionCarriere), joueur[1].getNbDoublon());
        assertTrue(joueur[1].getPlateau().possedeBatiment(sechoirTabac.getNom()));
    }
}
