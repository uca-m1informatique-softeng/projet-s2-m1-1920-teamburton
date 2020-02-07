package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.personnages.Personnage;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.stockageoutilsjeux.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Classe gérant les bots
 */
public class StrategieMais implements IntelligenceArtificielle{

    /**
     * Choisi un role au hasard parmis les roles disponibles
     * @return Personnage represantant le role choisi
     */
    @Override
    public Personnage choixRole(Partie partie, Plateau plateau, int nbDoublon,
                                int nbTonneauxTotal ,int tour){

        int i = 0;
        int strategie = -1;
        ArrayList<String> personnages = new ArrayList<>();
        ArrayList<String> plantations = new ArrayList<>();
        ArrayList<Personnage> roles = partie.getPersonnages();

        for (Personnage p: roles) {
            personnages.add(p.getNom());
        }

        for (Exploitation e: partie.getPlantations()) {
            plantations.add(e.getNom());
        }

        /*if (plateau.placeDispoIle()){
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.PAYSAN);
        }*/
        // Si on a de la place dans l'ile
        if (plateau.getNbExploitation() < 12 && plantations.contains(Constantes.MAIS) && personnages.contains(Constantes.PAYSAN)){
            return roles.remove(personnages.indexOf(Constantes.PAYSAN));
        }

        // Si il reste des plantations/bâtiments non occupées
        if (plateau.placeDispoIle()){
            strategie = personnages.indexOf(Constantes.MAIRE);
        }

        // Si on a des plantations occupée
        if (plateau.peutProduire() && strategie == -1){
            strategie = personnages.indexOf(Constantes.PRODUCTEUR);
        }

        // Si on possède des tonneaux
        if (nbTonneauxTotal > 0) {
            strategie = personnages.indexOf(Constantes.CAPITAINE);
        }

        // Sinon s'il y a un personnage possédant des doublons pour l'achat de bâtiment
        if (strategie == -1){
            for (Personnage p : roles) {
                if (p.getDoublon() > 0) {
                    strategie = personnages.indexOf(p.getNom());
                }
            }
        }

        if (strategie != -1) {
            i = strategie;
        }

        return roles.remove(i);

    }

    /**
     * choisi une exploitation au hasard parmis les plantations disponibles
     * @param joueursActif true s'il s'agit du joueur qui a choisi le rôle, false sinon
     * @return plantation ou carriere choisie
     */
    @Override
    public Exploitation choixExploitation(Partie partie, Plateau plateau,
                                          boolean joueursActif, int tour){

        int i = 0;
        Exploitation plantation;
        ArrayList<Exploitation> plantations = partie.getPlantations();

        for (int l = 0; l < plantations.size(); l++) {
            plantation = plantations.get(l);
            if (plantation.getNom().equals(Constantes.MAIS)) {
                return plantations.remove(l);
            }
        }

        return plantations.remove(i);
    }

    /**
     * choisi un batiment au hasard parmis les batiments disponibles
     * @return batiment choisi
     */
    @Override
    public Batiment choixBatiment(Partie partie, int nbDoublon, Plateau plateau){
        Batiment batiment;
        ArrayList<Batiment> batiments = partie.getBatiments();
        int indice = 0;

        // Bâtiments liès à une plantation occupée
        for (int b = 0; b < batiments.size(); b++) {
            batiment = batiments.get(b);

            if (!plateau.possedeBatiment(Constantes.HACIENDA) && batiment.getNom().equals(Constantes.HACIENDA)
                    && batiment.getPrix() <= nbDoublon) {
                return batiments.remove(b);
            }
        }

        // Bâtiments liès à une plantation occupée
        for (int b = 0; b < batiments.size(); b++){
            batiment = batiments.get(b);
            if (plateau.possedeExploitationOccupee(batiment.getNomPlantation()) &&
                    !plateau.possedeBatiment(batiment.getNom())) {

                return batiments.remove(b);
            }

            if (!plateau.possedeBatiment(batiment.getNom()) && nbDoublon > batiment.getPrix()){
                indice = b;
            }
        }

        return batiments.remove(indice);
    }

    /**
     * choisi une tonneau au hasard parmis ceux disponibles.
     * @param tonneaux ArrayList des marchandises pouvant être choisi
     * @return la marchandise choisie
     */
    @Override
    public String choixTonneau(Partie partie, String nomRole, Map<String, Integer> tonneaux, Navires navire){
        int nbTonneau;
        int nbTonneauMax = 0;
        String nomTonneau = tonneaux.keySet().iterator().next();
        Magasin magasin = partie.getMagasin();

        switch (nomRole) {
            case Constantes.CAPITAINE:
                for (Map.Entry<String, Integer> t : tonneaux.entrySet()) {
                    nbTonneau = navire.nbTonneauACharger(t.getValue());
                    if (nbTonneauMax < nbTonneau) {
                        nbTonneauMax = nbTonneau;
                        nomTonneau = t.getKey();
                    }
                }
                break;

            case Constantes.PRODUCTEUR:
                for (Map.Entry<String, Integer> t : tonneaux.entrySet()) {
                    nbTonneau = t.getValue();
                    if (nbTonneauMax < nbTonneau) {
                        nbTonneauMax = nbTonneau;
                        nomTonneau = t.getKey();
                    }
                }
                break;

            case Constantes.MARCHAND:
                int prix = 0;
                for (String nomT: tonneaux.keySet()) {
                    if (prix < magasin.getPrix(nomT)){
                        prix = magasin.getPrix(nomT);
                        nomTonneau = nomT;
                    }
                }
                break;

            default:
                return nomTonneau;
        }

        return nomTonneau;
    }

    /**
     * choisi un navire au hasard parmis ceux disponibles
     * @param navires ArrayList des navires disponible
     * @return le navir choisie (Arraylist pouvant être vide)
     */
    @Override
    public Navires choixNavire(Partie partie, ArrayList<Navires> navires, Map<String, Integer> tonneaux){
        int ind = 0;
        int nbTonneauMax = 0;
        for(int nbTonneauxJ: tonneaux.values()){
            for (Navires n: navires) {
                int nbTonneau = n.nbTonneauACharger(nbTonneauxJ);
                if (nbTonneauMax < nbTonneau){
                    nbTonneauMax = nbTonneau;
                    ind = navires.indexOf(n);
                }
            }
        }
        return navires.remove(ind);
    }

    /**
     * Methode permettant de placer des colons dans des bâtiments
     * @return le feedback de la phase de placement.
     */
    @Override
    public String placerColonExploitaion(Plateau plateau, String id){
        int nbColonPlace = 0;
        StringBuilder feedback = new StringBuilder();
        Exploitation exploitation;

        for (int e = 0; e <plateau.getNbExploitation(); e++) {
            exploitation = plateau.getIle()[e];
            // Ajout du colon
            if (!exploitation.estOccupe() && plateau.getNbColon() > 0) {
                exploitation.addColon();
                nbColonPlace  ++;
                plateau.subColon();
                plateau.addExploitationsOccupees(exploitation.getNom());
                feedback.append("\n<" + id + "> place un colon sur l'exploitation de : "
                        + exploitation.getNom());
            }
        }

        // Mise a jour du nombre de colons dans l'île
        plateau.setNbColonIle(nbColonPlace );

        return feedback.toString();
    }

    /**
     * Methode permettant de placer des colons dans des bâtiments
     * @return le feedback de la phase de placement.
     */
    @Override
    public String placerColonBatiment(Plateau plateau,  String id){
        int b = 0;
        StringBuilder feedback = new StringBuilder();
        Batiment batiment;
        int nbColonPlace;

        while (plateau.getNbColon() > 0 && b <plateau.getNbBatiment()){
            batiment = plateau.getCite()[b];
            nbColonPlace = 0;         // Stocke le nombre de colon placé par bâtiment

            // Ajout du colon dans le bâtiment
            while (batiment.getNbColon() < batiment.getNbColonLimite() && plateau.getNbColon() > 0) {
                batiment.addColon();
                nbColonPlace++;
                plateau.subColon();
            }
            // Augmentation du nombre de colon dans la cité
            if (nbColonPlace > 0) {
                plateau.setNbColonCite(nbColonPlace);
                feedback.append("\n<" + id + "> place " + nbColonPlace
                        + " colon(s) sur le bâtiment : " + batiment.getNom());
            }

            b++;
        }
        return feedback.toString();
    }

    /***
     * Methode permettant de placer des colons sur une plantation ou un bâtiment aléatoirement.
     * Le placement des colons sur les plantations est prioritaire.
     * @return le feedback de la phase de placement.
     */
    @Override
    public String placerColon(Plateau plateau, String id){
        StringBuilder feedback = new StringBuilder();
        int nbColon = plateau.getNbColon();

        if (nbColon == 0) {
            return "ne possede plus d'colons.";
        }

        feedback.append("décide de placer ses colons.");

        // Possède des colons et un ou plusieurs grands bâtiments non occupés
        if (nbColon > 0 && plateau.possedeGrandsBatimentsNonOccupe()){
            feedback.append(placerColonBatiment(plateau, id));
        }

        // Possède des colons et il reste des plantations non occupées.
        else if (nbColon > 0 && plateau.placeDispoIle()) {
            feedback.append(placerColonExploitaion(plateau, id));
        }

        // Toutes les plantations occupées.
        else if (nbColon > 0 && !plateau.placeDispoIle()) {
            feedback.append("\n<" + id + "> ne peut plus placer de colons dans ses exlpoitations.");
        }

        nbColon = plateau.getNbColon();
        // Possede des colons et il reste des batiments non occupés.
        if (nbColon > 0 && plateau.placeDispoCite()) {
            feedback.append(placerColonBatiment(plateau, id));
        }

        // Tous les bâtiments sont occupés
        else if (nbColon > 0 && !plateau.placeDispoCite()) {
            feedback.append("\n<" + id + "> ne peut plus placer de colons dans ses batiments.");
        }

        return feedback.toString();
    }
}