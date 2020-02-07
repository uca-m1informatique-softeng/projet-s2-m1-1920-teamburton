package puertoricotr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoricotr.batiments.Batiment;
import puertoricotr.batiments.productions.PetiteRaffinerieSucre;
import puertoricotr.batiments.productions.SechoirTabac;
import puertoricotr.batiments.productions.TeinturerieIndigo;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.*;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;

import java.util.ArrayList;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class JoueursTest {

    private Joueurs joueur;

    @BeforeEach
    void init(){
        joueur = new Joueurs("Joueur 1",new StrategieTour());
    }

    /**
     * Test methode Personnage choixRole(ArrayList<Personnage> roles)
     */
    /*@Test
    public void choixRoleTest() {

        ArrayList<Personnage> personnages = new ArrayList<>();

        Maire maire = new Maire();
        ChercheurOR chercheurOR = new ChercheurOR();
        personnages.add(maire);
        personnages.add(chercheurOR);

        // Entrée stdin
        System.setIn(new ByteArrayInputStream(maire.getNom().getBytes()));
        Personnage personnageChoisi = joueur.choixRole(personnages, 0);

        assertEquals(maire.getNom(), personnageChoisi.getNom());
    }*/

    /**
     * Test methode Personnage choixExploitation
     */
    @Test
    public void choixExploitationTest() {
        ArrayList<Exploitation> exploitations = new ArrayList<>();
        ArrayList<Exploitation> carrieres = new ArrayList<>();
        Exploitation mais = new Exploitation(Constantes.MAIS);

        exploitations.add(mais);
        System.setIn(new ByteArrayInputStream(mais.getNom().getBytes()));
        Exploitation exploitation = joueur.choixExploitation(exploitations, carrieres, false,1);

        assertEquals(mais.getNom(), exploitation.getNom());

    }

    /**
     * Test methode Personnage choixBatiment(ArrayList<Batiment>)
     */
    @Test
    public void choixBatimentTest() {
        ArrayList<Batiment> batiments = new ArrayList<>();
        ArrayList<Exploitation> carrieres = new ArrayList<>();
        SechoirTabac sechoirTabac = new SechoirTabac();

        batiments.add(sechoirTabac);
        System.setIn(new ByteArrayInputStream(sechoirTabac.getNom().getBytes()));
        Batiment batiment = joueur.choixBatiment(batiments);

        assertEquals(sechoirTabac.getNom(), batiment.getNom());

    }

    /**
     * Test methode Personnage choixTonneaut(ArrayList<String>)
     */
    @Test
    public void choixTonneauxTest() {
        Magasin magasin = new Magasin();
        Navires navire = new Navires(4);

        Exploitation mais = new Exploitation(Constantes.MAIS);
        joueur.addTonneau(mais.getNom(), 1);
        System.setIn(new ByteArrayInputStream(mais.getNom().getBytes()));
        String tonneauChoisi = joueur.choixTonneau(Constantes.PRODUCTEUR, joueur.getTonneauxProduits(), magasin, navire);

        assertEquals(tonneauChoisi, mais.getNom());
    }


    /**
     * Test méthode peutConstruire(ArrayList<Batiment> batiments)
     */
    @Test
    public void peutConstruireTest(){
        ArrayList<Batiment> batiments = new ArrayList<>();
        TeinturerieIndigo teinturerieIndigo = new TeinturerieIndigo();
        SechoirTabac sechoirTabac = new SechoirTabac();

        batiments.add(teinturerieIndigo);
        batiments.add(sechoirTabac);

        joueur.addBatiment(teinturerieIndigo);
        // Posséde teinturerie indigo, mais pas séchoir tabac
        assertTrue(joueur.peutConstruire(batiments));

        joueur.addBatiment(sechoirTabac);
        // Posséde les deux
        assertFalse(joueur.peutConstruire(batiments));
    }


    /**
     * Test méthode placerColonBatiment(Batiment batiment)
     */
    @Test
    public void placerColonBatimentTest(){
        PetiteRaffinerieSucre petiteRaffinerieSucre = new PetiteRaffinerieSucre();

        joueur.addBatiment(petiteRaffinerieSucre);
        joueur.getPlateau().addNbColon(3);

        // Nombre colon dans batiment < nombre colon limite
        joueur.placerColonBatiment();

        assertEquals(2, joueur.getPlateau().getNbColon());
        assertEquals(1, joueur.getPlateau().getCite()[0].getNbColon());

        int nbColonBat = joueur.getPlateau().getCite()[0].getNbColon();

        // Nombre colon dans batiment == nombre colon limite
        joueur.placerColonBatiment();

        // Pas d'ajout
        assertEquals(2, joueur.getPlateau().getNbColon());
        assertEquals(nbColonBat, joueur.getPlateau().getCite()[0].getNbColon());
    }


    /**
     * Test méthode placerColonExploitationt(Exploitaion exploitation)
     */
    @Test
    public void placerColonExploitationTest(){
        Exploitation mais = new Exploitation(Constantes.MAIS);

        joueur.addExploitation(mais);
        joueur.getPlateau().addNbColon(2);

        // Nombre colon dans batiment < nombre colon limite
        joueur.placerColonExploitaion();

        assertEquals(1, joueur.getPlateau().getNbColon());
        assertTrue(joueur.getPlateau().getIle()[0].estOccupe());

        int nbColonExplt = (joueur.getPlateau().getNbColonIle());

        // Plantation dejà occupé, pas d'ajout
        joueur.placerColonExploitaion();

        // Pas d'ajout
        assertEquals(nbColonExplt, joueur.getPlateau().getNbColon());
        assertTrue(joueur.getPlateau().getIle()[0].estOccupe());
    }

    /**
     * Test méthode subDoublon(int doublon)
     */
    @Test
    public void subDoublonTest(){
        int doublon = joueur.getNbDoublon();

        // Peut soustraire, car doublon > 0
        assertTrue(joueur.subDoublon(3));
        assertEquals(doublon - 3, joueur.getPlateau().getNbColon());

        // Ne soustrait pas, car doublon serait < 0
        assertFalse(joueur.subDoublon(10));
        assertEquals(doublon - 3, joueur.getPlateau().getNbColon());
    }

    /**
     * Test méthode subColon(int conlon)
     */
    @Test
    public void subColonTest(){
        joueur.getPlateau().addNbColon(1);
        int colon = joueur.getPlateau().getNbColon();

        // Peut soustraire, car doublon > 0
        joueur.getPlateau().subColon();
        assertEquals(colon - 1, joueur.getPlateau().getNbColon());

        // Ne soustrait pas, car doublon serait < 0
        joueur.getPlateau().subColon();
        assertEquals(colon - 1, joueur.getPlateau().getNbColon());
    }

    /**
     * Test méthode addtonneau(String nomProduction, int nombre)
     */
    @Test
    public void addTonneauTest(){
        joueur.addTonneau(Constantes.MAIS, 1);
        joueur.addTonneau(Constantes.INDIGO, 1);
        joueur.addTonneau(Constantes.SUCRE, 1);
        joueur.addTonneau(Constantes.TABAC, 1);
        joueur.addTonneau(Constantes.CAFE, 1);

        assertEquals(1, joueur.getNbMais());
        assertEquals(1, joueur.getNbIndigo());
        assertEquals(1, joueur.getNbSucre());
        assertEquals(1, joueur.getNbTabac());
        assertEquals(1, joueur.getNbCafe());

        int nbTonneauTotal = joueur.getNbTonneauxTotal();

        joueur.addTonneau("toto", 1000);

        assertEquals(nbTonneauTotal, joueur.getNbTonneauxTotal());
    }

    /**
     * Test méthode subtonneau(String nomProduction, int nombre)
     */
    @Test
    public void subTonneauTest(){
        joueur.addTonneau(Constantes.MAIS, 1);
        joueur.addTonneau(Constantes.INDIGO, 1);
        joueur.addTonneau(Constantes.SUCRE, 1);
        joueur.addTonneau(Constantes.TABAC, 1);
        joueur.addTonneau(Constantes.CAFE, 1);

        // Nombre tonneau superieur à zero
        joueur.subTonneau(Constantes.MAIS, 1);
        joueur.subTonneau(Constantes.INDIGO, 1);
        joueur.subTonneau(Constantes.SUCRE, 1);
        joueur.subTonneau(Constantes.TABAC, 1);
        joueur.subTonneau(Constantes.CAFE, 1);

        assertEquals(0, joueur.getNbMais());
        assertEquals(0, joueur.getNbIndigo());
        assertEquals(0, joueur.getNbSucre());
        assertEquals(0, joueur.getNbTabac());
        assertEquals(0, joueur.getNbCafe());

        int nbTonneauTotal = joueur.getNbTonneauxTotal();

        joueur.subTonneau("toto", 1000);

        assertEquals(nbTonneauTotal, joueur.getNbTonneauxTotal());

        // Nombre de tonneau deviendrai inferieur à zero, pas de décrémentation
        joueur.subTonneau(Constantes.MAIS, 1);
        joueur.subTonneau(Constantes.INDIGO, 1);
        joueur.subTonneau(Constantes.SUCRE, 1);
        joueur.subTonneau(Constantes.TABAC, 1);
        joueur.subTonneau(Constantes.CAFE, 1);

        assertEquals(0, joueur.getNbMais());
        assertEquals(0, joueur.getNbIndigo());
        assertEquals(0, joueur.getNbSucre());
        assertEquals(0, joueur.getNbTabac());
        assertEquals(0, joueur.getNbCafe());
    }


}
