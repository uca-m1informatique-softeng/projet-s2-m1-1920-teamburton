package puertoricotr.personnages;

import puertoricotr.Constantes;
import puertoricotr.Joueurs;
import puertoricotr.Partie;
import puertoricotr.batiments.Batiment;
import puertoricotr.stockageoutilsjeux.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe qui définie les actions du personnage Producteur
 */
public class Producteur extends Personnage {
    public Producteur() {
        this.nom = Constantes.PRODUCTEUR;
        this.doublon = 0;
    }

    /**
     * Methode permettant la production de tonneaux en fonctions des colons par bâtiments et
     * plantations.
     * @param joueur un joueur.
     * @return le feedback de l'étape de production.
     */
    public String production(Joueurs joueur, Partie partie, int numJ) {
        Reserve reserve = partie.getReserve();

        StringBuilder feedback = new StringBuilder();
        Batiment batiment;

        String mais = Constantes.MAIS;
        String nomPlantation;
        String tonneauChoisi;

        int nbColonBatiment;
        int nbTonneau;

        // Contient les tonneaux produits lors de la phase
        Map<String, Integer> tonneauxProduits = new HashMap<>();

        // Contient les noms et nombres de plantaions et bâtiments occupées
        Map<String, Integer> exploitationsOccupees = joueur.getPlateau().getExploitationsOccupees();
        Map<String, Integer> batimentsOccuppes = new HashMap<>();

        // Bâtiments occupés
        for (int b = 0; b < joueur.getPlateau().getNbBatiment(); b++){
            batiment = joueur.getPlateau().getCite()[b];
            nbColonBatiment = batiment.getNbColon();
            nomPlantation = batiment.getNomPlantation();
            if (nbColonBatiment > 0) {
                batimentsOccuppes.put(nomPlantation, batimentsOccuppes.containsKey(nomPlantation) ?
                        batimentsOccuppes.get(nomPlantation) + nbColonBatiment : nbColonBatiment);
            }
        }

        // Condition pour le maïs
        if (exploitationsOccupees.containsKey(mais) && reserve.getNbMarchandise(mais) > 0){
            nbTonneau = Math.min(exploitationsOccupees.get(mais), reserve.getNbMarchandise(mais));
            tonneauxProduits.put(mais, nbTonneau);
        }

        // Plantations occupées
        for (HashMap.Entry<String, Integer> t: exploitationsOccupees.entrySet()){
            nomPlantation = t.getKey();
            if (batimentsOccuppes.containsKey(nomPlantation)){
                nbTonneau = Math.min(reserve.getNbMarchandise(nomPlantation),
                            Math.min(t.getValue(), batimentsOccuppes.get(nomPlantation)));
                tonneauxProduits.put(nomPlantation, nbTonneau);
            }
        }

        // Feedback
        for (HashMap.Entry<String, Integer> t: tonneauxProduits.entrySet()) {
            if (reserve.getNbMarchandise(t.getKey()) > 0) {
                reserve.prendreMarchandise(t.getKey(), t.getValue());
                joueur.addTonneau(t.getKey(), t.getValue());
                feedback.append("\n<" + joueur.getIdJoueur() + "> a produit " + t.getValue()
                                      + " tonneau(x) de : " + t.getKey());
            }
        }

        // Privilège
        if (numJ == 0 && joueur.getNbTonneauxTotal() > 0 && !tonneauxProduits.isEmpty()){
            tonneauChoisi = joueur.choixTonneau(partie, this.nom, joueur.getTonneauxProduits(), partie.getNavires().get(0));
            if (reserve.getNbMarchandise(tonneauChoisi) > 0) {
                reserve.prendreMarchandise(tonneauChoisi, 1);
                joueur.addTonneau(tonneauChoisi, 1);
                feedback.append("\n<" + joueur.getIdJoueur() + "> possède le privilège du producteur "
                                      + "et choisit dans la réserve un tonneau de : " + tonneauChoisi);
            }
        }

        // Le joueur possedant une Manufacture, reçoit des doublons pour chaque marchandises différentes
        if (joueur.getPlateau().possedeBatimentOccupe(Constantes.MANUFACTURE) && tonneauxProduits.size() > 1){
            int nbDoublon = (tonneauxProduits.size() < 5) ? tonneauxProduits.size() - 1 : 5;
            partie.getBanque().decrementeNbColon(nbDoublon);
            joueur.addDoublon(nbDoublon);
        }

        // Aucune production, car mauvais placement ou pas assez de colon placé.
        if (tonneauxProduits.isEmpty()){
            feedback.append("\n<" + joueur.getIdJoueur() + "> ne peut rien produire.");
        }

        return feedback.toString();
    }


    @Override public String action(Joueurs[] joueurs, int j, Partie partie, int tour) {

        StringBuilder feedback = new StringBuilder();
        int nbBatiment;
        int nbExploitation;
        int nbJ = 0;
        while(nbJ < joueurs.length){
            nbBatiment = joueurs[j].getPlateau().getNbBatiment();
            nbExploitation = joueurs[j].getPlateau().getNbExploitation();

            // Le joueurs possede des bâtments et pas de plantations
            if ((nbBatiment > 0) && (nbExploitation == 0)) {
                feedback.append("\n<" + joueurs[j].getIdJoueur() + "> n'a rien à produire, car il ne"
                                      + " possède pas de plantations.");
            }

            // Le joueurs ne possede pas de bâtments et pas de plantations
            else if ((nbBatiment == 0) && (nbExploitation == 0)){
                feedback.append("\n<" + joueurs[j].getIdJoueur() + "> n'a rien à produire, car il ne"
                                      + " possède pas de plantations et pas de bâtiments.");
            }

            // Le joueurs possede des bâtments et des plantations
            else{
                feedback.append(production(joueurs[j], partie, nbJ) + "\n");
            }
            nbJ++;
            j = (j + 1) % joueurs.length;
        }
        return feedback.toString();
    }
}