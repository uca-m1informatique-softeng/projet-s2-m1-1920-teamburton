package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Personnage;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class StrategieDoublon implements IntelligenceArtificielle {
    private String nomBot;
    private String nomStrategie;

    public StrategieDoublon(){
        this.nomStrategie = Constantes.SDOUBLON;
        this.nomBot = "BOT " + this.nomStrategie;
    }

    public String getNomBot() {
        return nomBot;
    }

    public String getNomStrategie() {
        return nomStrategie;
    }

    /**
     * Choisi de rôles suivant une chaine précise (max Pv)  parmis les roles disponibles
     * @return Personnage represantant le role choisi
     */
    @Override
    public Personnage choixRole(Partie partie, Plateau plateau, int nbDoublon, int nbTonneauxTotal,
                                int tour) {

        int i = 0;
        int strategie = -1;
        ArrayList<Personnage> roles = partie.getPersonnages();
        String[] tableauPersonnage = new String[roles.size()];
        for (int j = 0; j < tableauPersonnage.length; j++) {
            tableauPersonnage[j] = roles.get(j).getNom();
        }

        // Si on a de la place dans l'ile
        if (plateau.getNbExploitation() < 12){
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.PAYSAN);
        }

        // Si il reste des plantations/bâtiments non occupées
        if (plateau.placeDispoPlateau()){
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.MAIRE);
        }

        // Si on a des plantations occupée
        if (plateau.peutProduire()){
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.PRODUCTEUR);
        }

        // Si on possède des tonneaux
        if (nbTonneauxTotal > 0) {
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.MARCHAND);
        }

        if (Arrays.asList(tableauPersonnage).contains(Constantes.CHERCHEUR_OR) && strategie == -1){
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.CHERCHEUR_OR);
        }

        // Sinon s'il y a un personnage possédant des doublons pour l'achat de bâtiment
        if (strategie == -1){
            for (Personnage p : roles) {
                if (p.getDoublon() > 0) {
                    strategie = Arrays.asList(tableauPersonnage).indexOf(p.getNom());
                }
            }
        }

        if (strategie != -1) {
            i = strategie;
        }

        return roles.remove(i);
    }

    /**
     * Choisi une exploitation (principalement mais), sinon liée à des bâtiments occupés.
     * @param joueursActif : true s'il s'agit du joueur qui a choisi le rôle, false sinon
     * @return la plantation choisie.
     */
    @Override
    public Exploitation choixExploitation(Partie partie, Plateau plateau, boolean joueursActif,
                                          int tour) {

        int i = 0;
        Exploitation plantation;
        ArrayList<Exploitation> plantations = partie.getPlantations();

        Batiment batiment;
        for (int l = 0; l < plantations.size(); l++) {
            plantation = plantations.get(l);
            for (int b = 0; b < plateau.getNbBatiment(); b++) {
                batiment = plateau.getCite()[b];
                if (plantation.getNom().equals(batiment.getNomPlantation())){
                    return plantations.remove(l);
                }

                // On evite de choisir le mais car dans le pire des cas on le revend pour 0 doublon
                else if(!plantation.getNom().equals(Constantes.MAIS)){
                    i =l;
                }
            }
        }

        return plantations.remove(i);
    }

    /**
     * Cherche d'abord à choisir des grands bâtiments, sinon bâtiment liée à des plantations
     * occupée, sinon le premier bâtiment de la liste.
     * @param nbDoublon : Doublon du joueur.
     * @param plateau : Plateu du joueur.
     * @return batiment choisi.
     */
    @Override
    public Batiment choixBatiment(Partie partie, int nbDoublon, Plateau plateau) {
        Batiment batiment;
        ArrayList<Batiment> batiments = partie.getBatiments();
        int indice = 0;

        // Priorité bâatiments permettant de rush doublons
        for (int b = 0; b < batiments.size(); b++) {
            batiment = batiments.get(b);

            if (!plateau.possedeBatiment(Constantes.PETIT_MARCHE) && batiment.getNom().equals(Constantes.PETIT_MARCHE)
                    && batiment.getPrix() <= nbDoublon) {
                return batiments.remove(b);
            }

            if (!plateau.possedeBatiment(Constantes.GRAND_MARCHE) && batiment.getNom().equals(Constantes.GRAND_MARCHE)
                    && batiment.getPrix() <= nbDoublon) {
                return batiments.remove(b);
            }

            if (!plateau.possedeBatiment(Constantes.COMPTOIRE) && batiment.getNom().equals(Constantes.COMPTOIRE)
                    && batiment.getPrix() <= nbDoublon) {
                return batiments.remove(b);
            }

            if (!plateau.possedeBatiment(Constantes.MANUFACTURE) && batiment.getNom().equals(Constantes.MANUFACTURE)
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

            if (!plateau.possedeBatiment(batiment.getNom())){
                indice = b;
            }
        }

        return batiments.remove(indice);
    }

    /**
     * Choisi un tonneau en fonction des rôles et des propriétés lèes à ces rôles
     * @param nomRole : Le rôle.
     * @param tonneaux : Tonneaux du joueur pouvant être choisi.
     * @param navire : Navires du jeu.
     * @return le tonneau choisi pour les rôles Capitaine, Marchand, Producteur, respectivement,
     * le tonneau rapportant le plus de PVs, le plus de doublon et celui dont on possède le plus.
     */
    @Override
    public String choixTonneau(Partie partie, String nomRole, Map<String, Integer> tonneaux, Navires navire) {

        int nbTonneau;
        int prix;

        String nomTonneau = tonneaux.keySet().iterator().next();
        Magasin magasin = partie.getMagasin();
        switch (nomRole) {
            case Constantes.CAPITAINE:
                int nbTonneauMax = 0;
                for (Map.Entry<String, Integer> t : tonneaux.entrySet()) {
                    nbTonneau = navire.nbTonneauACharger(t.getValue());
                    if (nbTonneauMax < nbTonneau) {
                        nbTonneauMax = nbTonneau;
                        nomTonneau = t.getKey();
                    }
                }
                break;

            case Constantes.PRODUCTEUR:
                // Choix de produire tonneau coutant le plus chère
                int prixMaxTonneau = 0;
                for (Map.Entry<String, Integer> t : tonneaux.entrySet()) {
                    prix = magasin.getPrix(t.getKey());
                    if (prixMaxTonneau < prix) {
                        prixMaxTonneau = prix;
                        nomTonneau = t.getKey();
                    }
                }
                break;

            case Constantes.MARCHAND:
                // Choix du tonneau coutant le plus chère
                prix = 0;
                for (String nomT: tonneaux.keySet()){
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
     * Choisi le navire dont on pourra charger le plus de tonneau.
     * @param navires ArrayList des navires disponible.
     * @param tonneaux Tonneaux du joueur pouvant être chargé.
     * @return le navire choisie.
     */
    @Override
    public Navires choixNavire(Partie partie, ArrayList<Navires> navires, Map<String, Integer> tonneaux) {

        int ind = 0;
        Magasin magasin = partie.getMagasin();
        int prixMin = 0;
        int prix;
        for (Map.Entry<String, Integer> t : tonneaux.entrySet()) {
            for (Navires n: navires) {
                prix = magasin.getPrix(t.getKey());
                if (prixMin > prix) {
                    prixMin = prix;
                    ind = navires.indexOf(n);
                }
            }
        }
        return navires.remove(ind);
    }

    /**
     * Methode permettant de placer des colons dans des bâtiments
     *
     * @return le feedback de la phase de placement.
     */
    public String placerColonExploitaion(Plateau plateau, String id) {

        StringBuilder feedback = new StringBuilder();
        int nbColonPlace = 0;
        Exploitation exploitation;

        for (int e = 0; e < plateau.getNbExploitation(); e++) {
            exploitation = plateau.getIle()[e];
            // Ajout du colon
            if (!exploitation.estOccupe() && plateau.getNbColon() > 0) {
                exploitation.addColon();
                nbColonPlace++;
                plateau.subColon();
                plateau.addExploitationsOccupees(exploitation.getNom());
                feedback.append("\n<" + id + "> place un colon sur l'exploitation de : "
                        + exploitation.getNom());
            }
        }

        // Mise a jour du nombre de colons dans l'île
        plateau.setNbColonIle(nbColonPlace);

        return feedback.toString();
    }

    /**
     * Methode permettant de placer des colons dans des bâtiments
     *
     * @return le feedback de la phase de placement.
     */
    public String placerColonBatiment(Plateau plateau, String id) {

        StringBuilder feedback = new StringBuilder();
        int b = 0;
        int nbColonPlace;                 // Stocke le nombre de colon placé par bâtiment
        Batiment batiment;

        // Placement prioritaire grands bâtiments
        for (int g = 0; g < plateau.getNbBatiment(); g++){
            batiment = plateau.getCite()[g];
            nbColonPlace = 0;
            if (batiment.estGrand() && batiment.getNbColon() == 0){
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
        }

        while (plateau.getNbColon() > 0 && b < plateau.getNbBatiment()) {
            batiment = plateau.getCite()[b];
            nbColonPlace = 0;

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
    public String placerColon(Plateau plateau, String id) {
        StringBuilder feedback = new StringBuilder();

        if (plateau.getNbColon() == 0) {
            return "ne possede plus d'colons.";
        }

        feedback.append("décide de placer ses colons.");

        // Possède des colons et un ou plusieurs grands bâtiments non occupés
        if (plateau.getNbColon() > 0 && plateau.possedeGrandsBatimentsNonOccupe()){
            feedback.append(placerColonBatiment(plateau, id));
        }

        // Possède des colons et il reste des plantations non occupées.
        if (plateau.getNbColon() > 0 && plateau.placeDispoIle()) {
            feedback.append(placerColonExploitaion(plateau, id));
        }

        // Toutes les plantations occupées.
        else if (plateau.getNbColon() > 0 && !plateau.placeDispoIle()) {
            feedback.append("\n<" + id + "> ne peut plus placer de colons dans ses exlpoitations.");
        }

        // Possede des colons et il reste des puertorico.batiments non occupés.
        if (plateau.getNbColon() > 0 && plateau.placeDispoCite()) {
            feedback.append(placerColonBatiment(plateau, id));
        }

        // Tous les bâtiments sont occupés
        else if (plateau.getNbColon() > 0 && !plateau.placeDispoCite()) {
            feedback.append("\n<" + id + "> ne peut plus placer de colons dans ses puertorico.batiments.");
        }

        return feedback.toString();
    }

}
