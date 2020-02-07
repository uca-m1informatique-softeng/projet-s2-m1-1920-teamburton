package puertoricotr;

import io.cucumber.java8.Fr;
import puertoricotr.batiments.*;
import puertoricotr.batiments.productions.*;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.*;
import puertoricotr.stockageoutilsjeux.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ChoixBatimentStepDefs implements Fr {

    private Partie partie;
    private StrategieRandom b;
    private Joueurs joueur;
    private Joueurs[] joueurs;
    private Batisseur batisseur;

    private ArrayList<Personnage> role;
    private ArrayList <Batiment> batiments;
    private ArrayList <Exploitation> plantations;
    private ArrayList <Exploitation> carrieres;
    private Magasin magasin;
    private Banque banque;
    private Reserve reserve;
    private ArrayList <Navires> navire;


    private RaffinerieSucre raffinerieSucre;
    private TeinturerieIndigo teinturerieIndigo;
    private SechoirTabac sechoirTabac;
    private BrulerieCafe brulerieCafe;
    private PetiteRaffinerieSucre petiteRaffinerieSucre;
    private PetiteTeinturerieIndigo petiteTeinturerieIndigo;

    private Batiment bat;

    public ChoixBatimentStepDefs() {

        Etantdonné("le joueur du nom de {string}", (String id) -> {
            partie = new Partie(0, 2);
            joueurs[0] = partie.getJoueurs()[0];
            joueurs = new Joueurs[1];
            joueurs[0] = joueur;
            joueur.setIDjoueur(id);
            joueur.addDoublon(10);
            plantations = new ArrayList<>();
            carrieres = new ArrayList<>();
            batiments = new ArrayList<>();
            magasin = new Magasin();
            banque = new Banque(10, 10, 10);
            reserve = new Reserve(10, 10, 10, 10, 10);
            navire = new ArrayList<>();


        });

        Et("la liste de batiments de productions {string}, {string}, {string}, {string}, {string} et {string}", (String nom1, String nom2, String nom3, String nom4, String nom5, String nom6) -> {

            batiments = new ArrayList<>();
            raffinerieSucre = new RaffinerieSucre();
            teinturerieIndigo = new TeinturerieIndigo();
            sechoirTabac = new SechoirTabac();
            brulerieCafe = new BrulerieCafe();
            petiteRaffinerieSucre = new PetiteRaffinerieSucre();
            petiteTeinturerieIndigo = new PetiteTeinturerieIndigo();

            batiments.add(raffinerieSucre);
            batiments.add(teinturerieIndigo);
            batiments.add(sechoirTabac);
            batiments.add(brulerieCafe);
            batiments.add(petiteRaffinerieSucre);
            batiments.add(petiteTeinturerieIndigo);


            assertEquals(nom1, raffinerieSucre.getNom());
            assertEquals(nom2, brulerieCafe.getNom());
            assertEquals(nom3, sechoirTabac.getNom());
            assertEquals(nom4, teinturerieIndigo.getNom());
            assertEquals(nom5, petiteRaffinerieSucre.getNom());
            assertEquals(nom6, petiteTeinturerieIndigo.getNom());
        });

        Quand("le {string} choisi le role {string}", (String id, String nom) -> {
            joueur.setIDjoueur(id);
            assertEquals(id, joueur.getIdJoueur());
            role = new ArrayList<>();
            batisseur = new Batisseur();
            role.add(batisseur);
            String choix = joueur.choixRole(partie, 0).getNom();
            assertEquals(choix, batisseur.getNom());
        });

        Alors("le {string} choisi un batiment parmi la liste de batiment", (String id) -> {
            joueur.setIDjoueur(id);
            assertEquals(id, joueur.getIdJoueur());
            ArrayList batimentsTmp = (ArrayList) batiments.clone();

            batisseur.action(joueurs, 0, partie,1);
            bat = joueur.getPlateau().getCite()[0];
            assertTrue(batimentsTmp.contains(bat));
        });

        Et("le batiment choisi est ajoute a son plateau", () -> {
            String nomBatiment = joueur.getPlateau().getCite()[0].getNom();
            assertEquals(nomBatiment, bat.getNom());
        });

        Et("le batiment choisi est retire de la liste", () -> {
            bat = joueur.getPlateau().getCite()[0];
            assertFalse(batiments.contains(bat));
        });
    }
}