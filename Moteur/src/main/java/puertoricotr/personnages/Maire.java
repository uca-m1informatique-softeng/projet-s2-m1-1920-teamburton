package puertoricotr.personnages;

import puertoricotr.Constantes;
import puertoricotr.Joueurs;
import puertoricotr.Partie;

/**
 * Classe qui définie les action du personnage maire
 */
public class Maire extends Personnage {

    public Maire(){
        this.nom = Constantes.MAIRE;
        this.doublon = 0;
    }

    @Override
    public String action(Joueurs[] joueurs, int j, Partie partie, int tour ){

        StringBuilder feedback = new StringBuilder();
        feedback.append("\nTous les joueurs recoivent un colon.\n");

        int nbJ = 0;
        while(nbJ < joueurs.length){
            // Action et privilège de l'élu
            if (nbJ == 0){
                joueurs[j].getPlateau().addNbColon(2);
                partie.getBanque().decrementeNbColon(2);
                feedback.append("\n<" + joueurs[j].getIdJoueur() +"> à le privilège du maire et "
                        + "obtient un colon de plus.\n<" + joueurs[j].getIdJoueur() + "> "
                        + joueurs[j].placerColon() + "\n");
            }

            else {
                joueurs[j].getPlateau().addNbColon(1);
                partie.getBanque().decrementeNbColon(1);
                feedback.append("\n<" + joueurs[j].getIdJoueur() + "> " + joueurs[j].placerColon() + "\n");
            }
            nbJ++;
            j = (j + 1) % joueurs.length;
        }
        return feedback.toString();
    }
}