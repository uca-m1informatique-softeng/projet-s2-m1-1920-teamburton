package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.batiments.productions.BrulerieCafe;
import puertoricotr.batiments.productions.PetiteRaffinerieSucre;
import puertoricotr.batiments.productions.SechoirTabac;
import puertoricotr.personnages.ChercheurOR;
import puertoricotr.personnages.Personnage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import puertoricotr.exploitations.Exploitation;

import java.security.SecureRandom;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;


/**
 * Unit test for simple App.
 */
@ExtendWith(MockitoExtension.class)
public class BotRandomTest
{
    private StrategieRandom bot;
    private Joueurs joueur;
    private ArrayList<Personnage> roles;
    private ArrayList<Exploitation> plantations;
    private ArrayList<Exploitation> carrieres;
    private ArrayList<Batiment> batiments;
    
    @Mock
    SecureRandom rand;

    @BeforeEach
    void init(){
        bot = new StrategieRandom();
        joueur = new Joueurs("0", bot);
        when(rand.nextInt(anyInt())).thenReturn(0,0, 1);
        bot.setRandom(rand);
    }

    /**
     * Test la méthode choixRôle.
     */
    @Test
    public void choixRoleTest(){
        roles = new ArrayList<>();
        Personnage cherchOR = new ChercheurOR();
        roles.add(cherchOR);
        Personnage choix = joueur.choixRole(roles,0);
        assertEquals(cherchOR,choix);

    }

    /**
     * Test la méthode choixExploitation.
     */
    @Test
    public void choixchoixExploitationTest(){
        plantations = new ArrayList<>();
        carrieres = new ArrayList<>();

        Exploitation mais = new Exploitation(Constantes.MAIS);
        plantations.add(mais);

        Exploitation indigo = new Exploitation(Constantes.INDIGO);
        plantations.add(indigo);

        Exploitation carriere = new Exploitation(Constantes.CARRIERE);
        carrieres.add(carriere);

        Exploitation choiMais = bot.choixExploitation(plantations, carrieres, joueur.getPlateau(), false, 1);
        assertEquals(choiMais.getNom(),mais.getNom());
    }

    /**
     * Test la méthode choixBatiment.
     */
    @Test
    public void choixBatimentTest(){
        batiments = new ArrayList<>();
        Batiment brulerieCafe = new BrulerieCafe();
        Batiment sechoirTabac = new SechoirTabac();

        batiments.add(brulerieCafe);
        batiments.add(sechoirTabac);
        Batiment choixBrulerieCafe = joueur.choixBatiment(batiments);
        assertEquals(choixBrulerieCafe.getNom(),brulerieCafe.getNom());

        Batiment choixSechoirTabac = joueur.choixBatiment(batiments);
        assertEquals(choixSechoirTabac.getNom(),sechoirTabac.getNom());
    }

    /**
     * Test la méthode placerColon.
     */
    @Test
    public void placerColonTest(){
        Exploitation teinturerieIndigo = new Exploitation(Constantes.INDIGO);
        PetiteRaffinerieSucre petiteRaffinerieSucre = new PetiteRaffinerieSucre();

        joueur.addExploitation(teinturerieIndigo);

        // Le bot ne possede aucun colon
        assertEquals(0, joueur.getPlateau().getNbColon());
        joueur.placerColon();

        assertFalse(joueur.getPlateau().getIle()[0].estOccupe());

        // Le bot possède 1 colon et a des plantations non occupés
        joueur.getPlateau().addNbColon(1);
        joueur.placerColon();

        assertEquals(0, joueur.getPlateau().getNbColon());
        assertTrue(joueur.getPlateau().getIle()[0].estOccupe());
    }
}