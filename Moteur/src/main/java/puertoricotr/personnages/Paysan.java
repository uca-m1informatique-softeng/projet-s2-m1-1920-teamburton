package puertoricotr.personnages;

import puertoricotr.Constantes;
import puertoricotr.Joueurs;
import puertoricotr.Partie;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.stockageoutilsjeux.Banque;

import java.util.ArrayList;

/**
 * Classe gerant les actions du paysan
 */
public class Paysan extends Personnage {

    public Paysan(){
        this.nom = Constantes.PAYSAN;
        this.doublon = 0;
    }

    /**
     * Methode permettant d'utiliser le bonus de l'Hospice: Placer un colon de la banque sur
     * l'exploitation choisie lors de la phase du paysan (hors hacienda)
     * @param joueur le joueur actuel.
     * @param exploitation exploitation choisie.
     * @param banque banque du jeu.
     * @return le feedback de l'action.
     */
    private String hospiceBonus(Joueurs joueur, Exploitation exploitation, Banque banque){
        StringBuilder feedback = new StringBuilder();

        banque.decrementeNbColon(1);
        exploitation.addColon();
        feedback.append("<" + joueur.getIdJoueur() + "> possède une Hospice et place un colon "
                                                     + "sur l'exploitation de " + exploitation.getNom() +"\n");

        return feedback.toString();
    }

    /**
     * Méthode s'occupant du bonus offert par l'hacienda (choix d'une plantation suppléméntaire)
     * @param joueur un joueur
     * @return le feedback si les conditons sont respectées.
     */
    private String haciendaBonus(Joueurs joueur, Partie partie){
        StringBuilder feedback = new StringBuilder();
        feedback.append("<" + joueur.getIdJoueur() + "> possède une Hacienda, il peut choisir "
                + "une plantation supplémentaire.");

        Exploitation exploitation;
        ArrayList<Exploitation> plantations = partie.getPlantations();

        if (!plantations.isEmpty()) {
            exploitation = joueur.choixExploitation(partie, false,1);
            joueur.addExploitation(exploitation);
            feedback.append("\n<" + joueur.getIdJoueur() + "> a choisit une exploitation de : "
                                  + exploitation.getNom() + "\n");
        }

        return feedback.toString();
    }

    /**
     * Méthode permettant de lancer le choix entre une carriere et une plantation avec le privilège
     * ou en étant en possession d'une barraque de chantier occupée
     * @param joueur joueur actuel.
     * @param feed feedback de choix.
     * @param jActif booléen joueur priilège ou non.
     * @param tour numéro de tour.
     * @return le feedback de cette action.
     */
    public String choixCarriere(Joueurs joueur, Partie partie, String feed,
                                boolean jActif, int tour){
        StringBuilder feedback = new StringBuilder();
        Exploitation exploitation;

        if (partie.getCarrieres().isEmpty()) {
            feedback.append("\nIl n'y a plus de carrieres.");
        }

        exploitation = joueur.choixExploitation(partie, jActif, tour);

        // Vérification du choix
        if (exploitation.getNom().equals(Constantes.CARRIERE)) {
            feedback.append("\n<" + joueur.getIdJoueur() + "> " +  feed);
        }

        feedback.append("\n<" + joueur.getIdJoueur() + "> a choisi une exploitation de : "
                              + exploitation.getNom() + "\n");

        // Le joueur possédant une hospice peut placer un colon de la banque sur son exploitation
        if (joueur.getPlateau().possedeBatimentOccupe(Constantes.HOSPICE)){
            feedback.append(hospiceBonus(joueur, exploitation, partie.getBanque()));
        }

        joueur.addExploitation(exploitation);

        return feedback.toString();
    }

    /**
     * Ajoute une nouvelle plantation au joueur
     * @param joueurs joueur pour lequel on réalise l'action
     */
    @Override
    public String action(Joueurs[] joueurs, int j, Partie partie, int tour){

        ArrayList<Exploitation> plantations = partie.getPlantations();
        ArrayList<Exploitation> carrieres = partie.getCarrieres();
        Banque banque = partie.getBanque();
        StringBuilder feedback = new StringBuilder();
        Exploitation exploitation;

        int nbJ = 0;
        while(nbJ < joueurs.length){
            // Action et privilège du joueur ayant choisi le rôle
            if (nbJ == 0 && !plantations.isEmpty()) {
                feedback.append(choixCarriere(joueurs[j], partie,"utilise son privilège.",
                          true, tour));
            }

            // Possède une barraque de chantier
            else if (joueurs[j].getPlateau().possedeBatimentOccupe(Constantes.BARRAQUE) && !plantations.isEmpty()){
                feedback.append(choixCarriere(joueurs[j], partie,
                          "possède une barraque de chantier.", false, tour));
            }

            // Action des autres joueurs
            else if (!plantations.isEmpty()){
                exploitation = joueurs[j].choixExploitation(partie,false, tour);
                feedback.append("\n<" + joueurs[j].getIdJoueur() + "> a choisi une exploitation de : "
                                      + exploitation.getNom() + "\n");

                // Le joueur possédant une hospice peut placer un colon de la banque sur son exploitation
                if (joueurs[j].getPlateau().possedeBatimentOccupe(Constantes.HOSPICE)){
                    feedback.append(hospiceBonus(joueurs[j], exploitation, banque));
                }
                joueurs[j].addExploitation(exploitation);
            }

            else{
                return "\nIl n'y a plus d'exploitation.";
            }

            if (joueurs[j].getPlateau().possedeBatimentOccupe(Constantes.HACIENDA)) {
                feedback.append(haciendaBonus(joueurs[j], partie));
            }
            nbJ++;
            j = (j + 1) % joueurs.length;
        }
        return feedback.toString();
    }
}