package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Personnage;
import puertoricotr.stockageoutilsjeux.*;

import java.util.ArrayList;
import java.util.Map;

public interface IntelligenceArtificielle {
    /**
     * Choisi un role au hasard parmis les roles disponibles
     * @param partie Permet de récuprer l'Arraylist des personnages
     * @return Personnage represantant le role choisi
     */
    Personnage choixRole(Partie partie, Plateau plateau, int nbDoublon,
                         int nbTonneauxTotal, int tour);


    /**
     * choisi une exploitation au hasard parmis les plantations disponibles
     * @param joueurActif true s'il s'agit du joueur qui a choisi le rôle, false sinon
     * @return plantation ou carriere choisie
     */
    Exploitation choixExploitation(Partie partie, Plateau plateau,
                                   boolean joueurActif, int tour);


    /**
     * choisi un batiment au hasard parmis les batiments disponibles
     * @return batiment choisi
     */
     Batiment choixBatiment(Partie partie, int nbDoublon, Plateau plateau);


    /**
     * choisi une marchandise au hasard parmis celles disponibles à la vente
     * @param tonneaux ArrayList des marchandises pouvant être vendu
     * @return la marchandise choisie (Arraylist pouvant être vide)
     */
     String choixTonneau(Partie partie, String nomRole, Map<String, Integer> tonneaux, Navires navire);

    /**
     * choisi un navire au hasard parmis ceux disponibles
     * @return le navir choisie (Arraylist pouvant être vide)
     */

     Navires choixNavire (Partie partie, ArrayList<Navires> navires, Map<String, Integer> nbtonneau);


    /**
     * Methode permettant de placer des colons dans des bâtiments
     * @return le feedback de la phase de placement.
     */
    String placerColonExploitaion(Plateau plateau, String id);


    /**
     * Methode permettant de placer des colons dans des bâtiments
     * @return le feedback de la phase de placement.
     */
    String placerColonBatiment(Plateau plateau, String id);


    /***
     * Methode permettant de placer des colons sur une plantation ou un bâtiment aléatoirement.
     * Le placement des colons sur les plantations est prioritaire.
     * @return le feedback de la phase de placement.
     */
    String placerColon(Plateau plateau, String id);
}
