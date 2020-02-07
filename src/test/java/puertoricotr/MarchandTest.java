package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Marchand;
import puertoricotr.stockageoutilsjeux.Banque;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;
import puertoricotr.stockageoutilsjeux.Reserve;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarchandTest {


    @Test
    public void actionTest() {

        Partie partie = new Partie(0,2);
        Joueurs[] joueur;

        Marchand marchand;
        Exploitation indigo;
        Map<String, Integer> tonneau;
        ArrayList <Navires> navires;
        Magasin magasin;

        int i = 0;

        joueur = partie.getJoueurs();
        navires = partie.getNavires();
        magasin = partie.getMagasin();

        String tonneauIndigo = "";
        indigo = new Exploitation(Constantes.INDIGO);

        marchand = new Marchand();
        navires.add(new Navires(4));

        /* Les joueurs ne possèdent pas de tonneaux
         * -------------------------------------------------------------------------------------- */
        marchand.action(joueur, i, partie,1);

        assertEquals(0, joueur[0].getNbTonneauxTotal());
        assertEquals(0, joueur[1].getNbTonneauxTotal());

        assertEquals(0, magasin.getNbTonneau());

        /* Le bot2 possede un tonneau et peut le vendre
         * -------------------------------------------------------------------------------------- */
        int doublonsJoueur = joueur[1].getNbDoublon();
        int prixIndigo = magasin.getPrix(indigo.getNom());
        joueur[1].addTonneau(indigo.getNom(), 1);
        tonneau = joueur[1].getTonneaux();

        // Récupération du tonneau
        for (Map.Entry<String, Integer> t : tonneau.entrySet()) {
            tonneauIndigo = t.getKey();
        }


        marchand.action(joueur, i, partie,1);

        assertEquals(1, magasin.getNbTonneau());
        assertEquals(0, joueur[1].getNbTonneauxTotal());

        // Correspondance du nom
        assertTrue(magasin.possedeMarchndise(indigo.getNom()), tonneauIndigo);

        // Verification du doublon par rapport à la vente
        assertEquals(joueur[1].getNbDoublon(), doublonsJoueur + prixIndigo);

        /* Le joueur possede un tonneau, mais qui est déjà présent dans le magasin
         * -------------------------------------------------------------------------------------- */
        marchand.action(joueur, i, partie, 1);
        assertTrue(magasin.possedeMarchndise(indigo.getNom()), tonneauIndigo);
        assertEquals(1, magasin.getNbTonneau());
        assertEquals(0, joueur[1].getNbTonneauxTotal());


        /* Privilège du joueur
         * -------------------------------------------------------------------------------------- */
        i = 0;
        joueur[0].addTonneau(Constantes.CAFE, 1);
        int prixCafe = magasin.getPrix(Constantes.CAFE);


        int doublonAvantAchat = joueur[0].getNbDoublon();
        marchand.action(joueur, i, partie,1);


        // Second tonneau ajouté au magasin et tonneau vendu par le joueur
        assertEquals(2, magasin.getNbTonneau());
        assertEquals(0, joueur[0].getNbTonneauxTotal());

        // Doublons joueur incrémenté + doublon privilège
        assertEquals(joueur[0].getNbDoublon(), doublonAvantAchat + prixCafe + 1);

    }
}