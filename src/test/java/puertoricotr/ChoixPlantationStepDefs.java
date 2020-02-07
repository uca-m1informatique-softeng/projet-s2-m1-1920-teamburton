package puertoricotr;

import io.cucumber.java8.Fr;
import puertoricotr.batiments.Batiment;
import puertoricotr.personnages.Paysan;
import puertoricotr.personnages.Personnage;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.stockageoutilsjeux.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ChoixPlantationStepDefs implements Fr {

    private Partie partie;
    private StrategieRandom bot;
    private Joueurs[] joueurs;
    private Joueurs joueur;
    private Paysan paysan;

    private ArrayList<Personnage> role;
    private ArrayList <Batiment> batiments;
    private ArrayList <Exploitation> plantations;
    private ArrayList <Exploitation> carrieres;
    private Magasin magasin;
    private Banque banque;
    private Reserve reserve;

    private Exploitation plantationCafe;
    private Exploitation plantationIndigo;
    private Exploitation plantationMais;
    private Exploitation plantationSucre;
    private Exploitation plantationTabac;
    private ArrayList <Navires> navire;


    private Exploitation plantation;
    private String nomPlantation;

    public ChoixPlantationStepDefs() {

        Etantdonné("un autre joueur du nom de {string}", (String id) -> {
            partie = new Partie(0,2);

            joueurs = new Joueurs[1];
            joueurs[0] = partie.getJoueurs()[0];

            joueurs[0] = joueur;
            joueur.setIDjoueur(id);

            carrieres = new ArrayList<>();
            batiments = new ArrayList<>();
            magasin = new Magasin();
            banque = new Banque(10, 10, 10);
            reserve = new Reserve(10, 10, 10, 10, 10);
            navire = new ArrayList<>();


        });

        Et("la liste de plantation {string}, {string}, {string}, {string} et {string}", (String nom1, String nom2, String nom3, String nom4, String nom5) -> {

            plantations = new ArrayList<>();
            plantationMais = new Exploitation(Constantes.MAIS);
            plantationCafe = new Exploitation(Constantes.CAFE);
            plantationIndigo = new Exploitation(Constantes.INDIGO);
            plantationSucre = new Exploitation(Constantes.SUCRE);
            plantationTabac = new Exploitation(Constantes.TABAC);

            plantations.add(plantationMais);
            plantations.add(plantationCafe);
            plantations.add(plantationIndigo);
            plantations.add(plantationSucre);
            plantations.add(plantationTabac);

            assertEquals(nom1, plantationCafe.getNom());
            assertEquals(nom2, plantationSucre.getNom());
            assertEquals(nom3, plantationIndigo.getNom());
            assertEquals(nom4, plantationMais.getNom());
            assertEquals(nom5, plantationTabac.getNom());
        });
        Quand("le {string} choisi un role {string}", (String id, String nom) -> {
            joueur.setIDjoueur(id);
            assertEquals(id, joueur.getIdJoueur());
            role = new ArrayList<>();
            paysan = new Paysan();
            role.add(paysan);
            String choix = joueur.choixRole(partie, 0).getNom();
            assertEquals(choix, paysan.getNom());

        });

        Alors("le {string} choisi une plantation parmi la liste de plantation", (String id) -> {
            joueur.setIDjoueur(id);
            assertEquals(id, joueur.getIdJoueur());

            ArrayList plantationsTmp = (ArrayList) plantations.clone();
            paysan.action(joueurs, 0, partie,0);

            plantation = joueur.getPlateau().getIle()[0];
            assertTrue(plantationsTmp.contains(plantation));
        });

        Et("la plantation choisie est ajoutee a son plateau", () -> {
            nomPlantation = joueur.getPlateau().getIle()[0].getNom();
            assertEquals(plantation.getNom(), nomPlantation);
        });

        Et("la plantation choisie est retiree de la liste", () -> {
            nomPlantation = joueur.getPlateau().getIle()[0].getNom();
            assertFalse(plantations.contains(nomPlantation));

        });
    }
}
