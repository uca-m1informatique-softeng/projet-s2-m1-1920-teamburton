package puertoricotr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Personnage;

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
    private Partie partie;
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
        partie = new Partie(0, 2);
        joueur = partie.getJoueurs()[0];
        joueur.setIntelligenceArtificielle(bot);
        when(rand.nextInt(anyInt())).thenReturn(0,0, 1);
        bot.setRandom(rand);

    }

    /**
     * Test la méthode choixRôle.
     */
    @Test
    public void choixRoleTest(){

        roles = partie.getPersonnages();
        joueur.choixRole(partie,0);
        assertEquals(roles.size(), 6);

    }

    /**
     * Test la méthode choixExploitation.
     */
    @Test
    public void choixchoixExploitationTest(){
        plantations = partie.getPlantations();
        carrieres = partie.getCarrieres();

        bot.choixExploitation(partie, joueur.getPlateau(), false, 1);
        assertEquals(2, plantations.size());
    }

    /**
     * Test la méthode choixBatiment.
     */
    @Test
    public void choixBatimentTest(){

        batiments = partie.getBatiments();
        int nbBatiment = batiments.size();
        joueur.choixBatiment(partie);


        //assertEquals(nbBatiment, nbBatiment - 1);
    }

    /**
     * Test la méthode placerColon.
     */
    @Test
    public void placerColonTest(){
        Exploitation teinturerieIndigo = new Exploitation(Constantes.INDIGO);

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