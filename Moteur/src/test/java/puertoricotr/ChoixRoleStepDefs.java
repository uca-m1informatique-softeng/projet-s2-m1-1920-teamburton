package puertoricotr;

import io.cucumber.java8.Fr;
import puertoricotr.personnages.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ChoixRoleStepDefs implements Fr {

    private StrategieRandom bot;
    private Joueurs joueur;
    private ChercheurOR chercheurOr;
    private Paysan paysan;
    private Maire maire;
    private Partie partie;
    private ArrayList<Personnage> role;
    private String nomPersonnage;

    public ChoixRoleStepDefs() {

        Etantdonné("un joueur du nom de {string}", (String id) -> {
            bot = new StrategieRandom();
            Joueurs joueur;
            joueur = partie.getJoueurs()[0];
        });

        Et("une liste de rôle {string}, {string} et {string}", (String nom1, String nom2, String nom3) -> {
            role = new ArrayList<Personnage>();
            chercheurOr = new ChercheurOR();
            chercheurOr.setNom(nom1);
            role.add(chercheurOr);
        });

        Quand("{string} choisi le personnage {string}", (String id, String nom) -> {
            partie = new Partie(0, 2, new ServeurStats());
            joueur.setIDjoueur(id);
            assertEquals(id, joueur.getIdJoueur());
            nomPersonnage =  joueur.choixRole(partie, 0).getNom();
            assertEquals(nomPersonnage, chercheurOr.getNom());
        });

        Alors("le {string} gagne {int} doublon", (String id, Integer doublon) -> {
            joueur.setIDjoueur(id);
            assertEquals(id, joueur.getIdJoueur());

            int nbDoublon = joueur.getNbDoublon();
            joueur.addDoublon(doublon);
            assertEquals(joueur.getNbDoublon(),nbDoublon + 1);
        });

        Et("le role {string} est supprimer de la liste", (String nom) -> {
            assertEquals(nom, chercheurOr.getNom());
            assertFalse(role.contains(chercheurOr.getNom()));
        });
    }
}
