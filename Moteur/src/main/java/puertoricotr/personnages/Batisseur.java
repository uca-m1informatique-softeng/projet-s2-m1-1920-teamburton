package puertoricotr.personnages;

import puertoricotr.Constantes;
import puertoricotr.Joueurs;
import puertoricotr.Partie;
import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.stockageoutilsjeux.*;

import java.util.ArrayList;

/**
 * Classe qui définie les actions du personnage bâtisseur
 */
public class Batisseur extends Personnage{
    public Batisseur() {
        this.nom = Constantes.BATISSEUR;
        this.doublon = 0;
    }

    @Override
    public String action(Joueurs[] joueurs, int j, Partie partie, int tour){

        StringBuilder feedback = new StringBuilder();
        Batiment batiment;
        ArrayList<Batiment> batiments= partie.getBatiments();
        Banque banque = partie.getBanque();

        boolean riche;
        int prixFinal;
        int reduction;
        int nbCarriereOccupee;

        int nbJ = 0;
        while(nbJ < joueurs.length){
            if(batiments.isEmpty()){
                return "\nIl n'y a plus de batiments.";
            }

            else if (joueurs[j].peutConstruire(batiments)) {
                nbCarriereOccupee = joueurs[j].getPlateau().getNbCarriereOccupee();
                batiment = joueurs[j].choixBatiment(partie);

                // Prise en charge du nombre de carrieres occupées pour reduction de prix
                reduction = Math.min(batiment.getCategorie(), nbCarriereOccupee);

                // Privilège
                if (nbJ == 0) {
                    feedback.append("\n<" + joueurs[j].getIdJoueur() + "> a le privilège du Batisseur"
                                          + " et peut acheter un bâtiment à 1 doublon de moins.");
                    reduction += 1;
                }

                // Prix ne pouvant aller en dessous de 0
                prixFinal = Math.max(0, batiment.getPrix() - reduction);
                riche = joueurs[j].subDoublon(prixFinal);

                if (riche) {
                    banque.ajouteNbDoublon(prixFinal);
                    feedback.append("\n<" + joueurs[j].getIdJoueur() + "> a choisi le batiment : "
                                          + batiment.getNom() + "\n");

                    // Feedback reduction de prix
                    if (nbCarriereOccupee > 0) {
                        feedback.append("<" + joueurs[j].getIdJoueur() + "> possède " + nbCarriereOccupee
                                + " carriere(s) occupée(s) et obtient une réduction totale de "
                                + Math.max(batiment.getPrix(), reduction) + " doublon(s) pour l'achat de "
                                + batiment.getNom() + " de categorie " + batiment.getCategorie() + "\n");
                    }

                    // Placement de colon sur le bâtiment si le joueurs possède une université
                    if (joueurs[j].getPlateau().possedeBatimentOccupe(Constantes.UNIVERSITE)) {
                        batiment.addColon();
                        banque.decrementeNbColon(1);
                        feedback.append("\n<" + joueurs[j].getIdJoueur() + "> possede une Universite "
                                              + "et place un colon sur " + batiment.getNom() + "\n");
                    }
                    joueurs[j].addBatiment(batiment);
                }

                else {
                    batiments.add(batiment);
                    feedback.append("\n<" + joueurs[j].getIdJoueur() + "> n'a pas assez de doublons "
                                          + "pour acheter : " + batiment.getNom() + "\n");
                }
            }

            else{
                feedback.append("\n<" + joueurs[j].getIdJoueur() + "> ne peut plus construire de "
                                      + "bâtiments.\n");
            }
            nbJ++;
            j = (j + 1) % joueurs.length;
        }
        return feedback.toString();
    }
}