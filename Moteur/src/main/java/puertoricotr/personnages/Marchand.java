package puertoricotr.personnages;

import puertoricotr.Constantes;
import puertoricotr.Joueurs;
import puertoricotr.Partie;
import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.stockageoutilsjeux.*;

import java.util.*;

public class Marchand extends Personnage {
    public Marchand(){
        this.nom = Constantes.MARCHAND;
        this.doublon = 0;
    }

    /**
     * Méthode recherchant les ventes possibles qu'un joueur qu'un joueur peut effectuer en fonction
     * de ses tonneaux et du contenu du magasin
     * @param joueur un joueur
     * @param magasin stockant les tonneaux pour la vente (4 max)
     * @return une Arraylist contenant les marchandise pouvant être vendu par le joueur (peut être vide)
     */
    private Map<String, Integer> venteDisponible(Joueurs joueur, Magasin magasin){

        Map<String, Integer> vente = new HashMap<>();

        for (HashMap.Entry<String, Integer> t : joueur.getTonneauxProduits().entrySet()) {

            // Le tonneau n'est pas dans le magasin
            if (!magasin.possedeMarchndise(t.getKey())) {
                vente.put(t.getKey(), t.getValue());
            }
        }

        return vente;
    }

    @Override
    public String action(Joueurs[] joueurs, int j, Partie partie, int tour){
        Magasin magasin = partie.getMagasin();

        StringBuilder feedback = new StringBuilder();

        int prix;
        String tonneauChoisi;
        Map <String, Integer> vente;

        int nbJ = 0;
        while(nbJ < joueurs.length){
            // Le joueurs ne possède auccun tonneaux
            if (joueurs[j].getNbTonneauxActuel() == 0) {
                feedback.append("\n<" + joueurs[j].getIdJoueur() + "> ne possède aucun tonneaux.\n");
            }

            // Il reste de la place dans le magasin
            else if (!magasin.estPlein()) {
                vente = venteDisponible(joueurs[j], magasin);
                // Le joueurs possède des tonneaux, mais ne peut les vendre au magasin car il y en a
                if (vente.isEmpty() && !joueurs[j].getPlateau().possedeBatimentOccupe(Constantes.COMPTOIRE)) {
                    feedback.append("\n<" + joueurs[j].getIdJoueur() + "> ne peux pas vendre de "
                                          + "tonneau, car il y a déjà un exemplaire dans le magasin.\n");
                }

                // Le joueurs peut vendre un tonneau
                else {
                    // Possede un comptoire, vente possible d'un tonneau déjà dans le magasin
                    if (joueurs[j].getPlateau().possedeBatimentOccupe(Constantes.COMPTOIRE)){
                        feedback.append("\n<" + joueurs[j].getIdJoueur() + "> possède le comptoire et "
                                              + "peut vendre un tonneau déjà présent dans le magasin.");

                        tonneauChoisi = joueurs[j].choixTonneau(partie, this.nom, joueurs[j].getTonneauxProduits(), partie.getNavires().get(0));
                    }

                    else{
                        tonneauChoisi = joueurs[j].choixTonneau(partie, this.nom, vente, partie.getNavires().get(0));
                    }

                    joueurs[j].subTonneau(tonneauChoisi, 1);
                    magasin.ajouterMarchandises(tonneauChoisi);
                    prix = magasin.getPrix(tonneauChoisi);
                    feedback.append("\n<" + joueurs[j].getIdJoueur() + "> choisit de vendre un tonneau de "
                                          + tonneauChoisi + " pour " + prix + " doublon(s).\n");

                    // Privilège
                    if (nbJ == 0){
                        feedback.append("<" + joueurs[j].getIdJoueur() + "> possède le privilège du "
                                            + "marchand et reçoit 1 doublon de plus pour sa vente.\n");
                        prix++;
                    }

                    // Bonus petit marché
                    if (joueurs[j].getPlateau().possedeBatimentOccupe(Constantes.PETIT_MARCHE)){
                        feedback.append("<" + joueurs[j].getIdJoueur() + "> possède le petit marché "
                                            + "et reçoit 1 doublon de plus pour sa vente.\n");
                        prix++;
                    }

                    // Bonus grand marché
                    if (joueurs[j].getPlateau().possedeBatimentOccupe(Constantes.GRAND_MARCHE)){
                        feedback.append("<" + joueurs[j].getIdJoueur() + "> possède le grand marché "
                                            + "et reçoit 2 doublons de plus pour sa vente.\n");
                        prix += 2;
                    }

                    joueurs[j].addDoublon(partie.getBanque().decrementeNbDoublon(prix));
                }
            }
            nbJ++;
            j = (j + 1) % joueurs.length;
        }

        // Transfert tonneaux si magasin plein
        if (magasin.estPlein()) {
            magasin.tranfertVersReserve(partie.getReserve());
            feedback.append("\nLe magasin est plein.\nTransfert vers la réserve .....");
        }

        return feedback.toString();
    }
}
