package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.batiments.productions.PetiteRaffinerieSucre;
import puertoricotr.batiments.productions.SechoirTabac;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Batisseur;
import puertoricotr.personnages.Personnage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BatisseurTest {


    @Test
    public void actionTest(){
        Partie partie;
        Joueurs[] joueur;

        PetiteRaffinerieSucre petiteRaffinerieSucre;
        SechoirTabac sechoirTabac;
        ArrayList <Batiment> batiments;

        partie = new Partie(0,2, new ServeurStats());
        batiments = partie.getBatiments();

        int i = 0;
        joueur = partie.getJoueurs();

        Personnage batisseur = new Batisseur();
        int doublon;

        /* Bâtiments disponible
         * -------------------------------------------------------------------------------------- */

        doublon = joueur[1].getNbDoublon();
        assertTrue(joueur[1].peutConstruire(batiments));
        batisseur.action(joueur, i, partie,1);

        int prixBatiment = joueur[1].getPlateau().getCite()[0].getPrix();
        assertEquals(doublon - prixBatiment, joueur[1].getNbDoublon());
        assertEquals(1, joueur[1].getPlateau().getNbBatiment());

        /* Le joueur peut construire un bâtiment
         * -------------------------------------------------------------------------------------- */

        petiteRaffinerieSucre = new PetiteRaffinerieSucre();
        batiments.add(petiteRaffinerieSucre);
        batiments.add(new PetiteRaffinerieSucre());

        assertTrue(joueur[1].peutConstruire(batiments));
        assertFalse(batiments.isEmpty());
        assertTrue(petiteRaffinerieSucre.getPrix() <= joueur[1].getNbDoublon());

        doublon = joueur[1].getNbDoublon() - petiteRaffinerieSucre.getPrix();
        batisseur.action(joueur, i, partie,1);


        assertEquals(doublon, joueur[1].getNbDoublon());
        assertTrue(joueur[1].getPlateau().possedeBatiment(petiteRaffinerieSucre.getNom()));

        /* Le joueur peut construire un bâtiment, mais il a déjà un exemeplaire
         * -------------------------------------------------------------------------------------- */
        petiteRaffinerieSucre = new PetiteRaffinerieSucre();
        batiments.add(petiteRaffinerieSucre);


        doublon = joueur[1].getNbDoublon();
        batisseur.action(joueur, i, partie,1);

        assertEquals(doublon, joueur[1].getNbDoublon());
        assertTrue(joueur[1].getPlateau().possedeBatiment(petiteRaffinerieSucre.getNom()));


        /* Privilège du joueur
         * -------------------------------------------------------------------------------------- */
        i = 0;
        joueur[0].addDoublon(15);

        doublon = joueur[0].getNbDoublon();
        int reductionPrivilege = 1;
        batisseur.action(joueur, i, partie,1);
        int nbBat = joueur[0].getPlateau().getNbBatiment();
        prixBatiment = joueur[0].getPlateau().getCite()[nbBat - 1].getPrix();

        assertEquals(doublon - (prixBatiment - reductionPrivilege), joueur[0].getNbDoublon());

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

        batisseur.action(joueur, i, partie,1);

        assertEquals(doublon - (prixTabac - reductionCarriere), joueur[1].getNbDoublon());
        assertTrue(joueur[1].getPlateau().possedeBatiment(sechoirTabac.getNom()));
    }
}
