package puertoricotr;

import org.junit.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.batiments.petits.Hacienda;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Paysan;
import puertoricotr.personnages.Personnage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaysanTest {

    @Test
    public void actionTest(){

        Partie partie = new Partie(0,2);
        Joueurs[] joueur;

        ArrayList <Batiment> batiments;
        ArrayList <Exploitation> plantations;

        Hacienda hacienda;

        int i = 0;

        joueur = partie.getJoueurs();
        plantations = partie.getPlantations();
        batiments = partie.getBatiments();

        hacienda=new Hacienda();
        batiments.add(hacienda);

        Personnage paysan = new Paysan();

        /* Privilège du joueur, mais plus de carrière, donc choisi une autre plantation
         * -------------------------------------------------------------------------------------- */
        paysan.action(joueur, i, partie,1);

        assertTrue(joueur[0].getPlateau().possedeExploitation());

        /* Le bot2 choisie une plantation sans privilège
         * -------------------------------------------------------------------------------------- */
        plantations.add(new Exploitation(Constantes.SUCRE));
        paysan.action(joueur, i,partie,1);

        assertTrue(joueur[1].getPlateau().possedeExploitation());

        /* bot2 possède une Hacienda bonus (+2 plantations) sans privilège
         * -------------------------------------------------------------------------------------- */
        batiments.add(hacienda);
        joueur[1].addBatiment(batiments.get(0));

        paysan.action(joueur, i, partie,1);

        // assertEquals(3, joueur[1].getPlateau().getNbExploitation());
    }}