package puertoricotr.personnages;

import puertoricotr.Constantes;
import puertoricotr.Joueurs;
import puertoricotr.Partie;

/**
 * Classe gerant les actions du chercheur d'or
 */
public class ChercheurOR extends Personnage {

	public ChercheurOR(){
		this.nom = Constantes.CHERCHEUR_OR;
        this.doublon = 0;
	}

	/**
	 * ajoute un doublon au joueurs
	 * @param joueurs joueurs pour lequel on réalise l'action
	 */
	@Override
	public String action(Joueurs[] joueurs, int j, Partie partie, int tour ) {

		joueurs[j].addDoublon(partie.getBanque().decrementeNbDoublon(1));


    	return "\n<" + joueurs[j].getIdJoueur() + "> reçoit un doublon et termine la phase.";
	}

}
