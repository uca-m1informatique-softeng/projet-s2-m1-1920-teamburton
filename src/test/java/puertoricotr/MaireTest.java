package puertoricotr;

import org.junit.Test;

import puertoricotr.personnages.Maire;
import puertoricotr.stockageoutilsjeux.Banque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaireTest {
    @Test
    public void actionTest() {

        Partie partie = new Partie(0,2);
        Joueurs[] joueur;

        Maire maire;
        Banque banque;

        banque = partie.getBanque();

        maire = new Maire();

        int i = 0;
        int nbColonB1;
        int nbColonB2;

        joueur = partie.getJoueurs();

        int nbColonBanque = banque.getNbColon();
        maire.action(joueur, i, partie,0);
        nbColonB1 = joueur[0].getPlateau().getNbColon();
        nbColonB2 = joueur[1].getPlateau().getNbColon();


        // Joueur sans privilège
        assertEquals(nbColonB2, joueur[1].getPlateau().getNbColon());

        // Joueur avec privilège
        assertEquals(nbColonB1, joueur[0].getPlateau().getNbColon());
        assertEquals(nbColonBanque - (nbColonB1 + nbColonB2), banque.getNbColon());
    }
}
