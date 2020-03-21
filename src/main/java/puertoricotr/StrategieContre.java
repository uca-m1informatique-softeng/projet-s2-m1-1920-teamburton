package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Personnage;
import puertoricotr.stockageoutilsjeux.Magasin;
import puertoricotr.stockageoutilsjeux.Navires;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class StrategieContre implements  IntelligenceArtificielle {
    private String nomBot;
    private String nomStrategie;

    public StrategieContre(){
        this.nomBot = "BOT Contre";
        this.nomStrategie = "Contre";
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

        ArrayList<Personnage> roles = partie.getPersonnages();
        Joueurs[] joueurs = partie.getJoueurs();
        int i = 0;
        int strategie = -1;
        String[] tableauPersonnage = new String[roles.size()];
        for (int j = 0; j < tableauPersonnage.length; j++) {
            tableauPersonnage[j] = roles.get(j).getNom();
        }

        // Si on a de la place dans l'ile
        if (plateau.placeDispoIle()){
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.PAYSAN);
        }

        // Forcer l'adversair à choisir un bâtiment
        if (joueurs[0].getNbDoublon() >= 0){
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.BATISSEUR);
        }

        // Si il reste des plantations/bâtiments non occupées
        if (plateau.placeDispoPlateau()){
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.MAIRE);
        }

        // Si on a des plantations occupée
        if (plateau.peutProduire() && strategie == -1 || !joueurs[0].getPlateau().peutProduire()){
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.PRODUCTEUR);
        }

        // Si on possède des tonneaux
        if (nbTonneauxTotal > 1 || nbTonneauxTotal > joueurs[0].getNbTonneauxActuel()) {
            strategie = Arrays.asList(tableauPersonnage).indexOf(Constantes.CAPITAINE);
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
    public Exploitation choixExploitation(Partie partie, Plateau plateau,
                                          boolean joueursActif, int tour) {

        Joueurs[] joueurs = partie.getJoueurs();
        ArrayList<Exploitation> plantations = partie.getPlantations();
        int i = 0;
        Exploitation plantation;
        for (int l = 0; l < plantations.size(); l++) {
            plantation = plantations.get(l);
            if (plantation.getNom().equals(Constantes.MAIS)) {
                return plantations.remove(l);
            }
        }

        Batiment batiment;
        for (int l = 0; l < plantations.size(); l++) {
            plantation = plantations.get(l);
            for (int b = 0; b < plateau.getNbBatiment(); b++) {
                batiment = plateau.getCite()[b];
                if (!joueurs[0].getPlateau().contientExploitation(plantation.getNom())){
                    return plantations.remove(l);
                }
                if (plantation.getNom().equals(batiment.getNomPlantation())){
                    return plantations.remove(l);
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

        ArrayList<Batiment> batiments = partie.getBatiments();
        Batiment batiment;

        int indice = 0;

        // Priorité grand bâtiments
        for (int b = 0; b < batiments.size(); b++){
            batiment = batiments.get(b);
            if (batiment.estGrand() && nbDoublon >= 8) {
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
        int nbTonneauMax = 0;
        String nomTonneau = tonneaux.keySet().iterator().next();

        ArrayList<String> res = new ArrayList<>();
        ArrayList<Navires> navires = partie.getNavires();
        Joueurs[] joueurs = partie.getJoueurs();
        Magasin magasin = partie.getMagasin();

        for (Navires n: navires){
            if (!n.estVide()){
                res.add(n.getNomRessource());
            }
        }
        switch (nomRole) {
            case Constantes.CAPITAINE:
                for (Map.Entry<String, Integer> t : tonneaux.entrySet()) {
                    if (navire.getNbRessource() > 0) {
                        nbTonneau = navire.nbTonneauACharger(t.getValue());
                        if (nbTonneauMax < nbTonneau) {
                            nbTonneauMax = nbTonneau;
                            nomTonneau = t.getKey();
                        }
                    }

                    else if (navire.estVide() && joueurs[0].getNbTonneauxActuel() > 0) {
                        Map<String, Integer> tonneauJ = joueurs[0].getTonneauxProduits();
                        for (String nomTonneauJ1 : tonneaux.keySet()) {
                            if (!tonneauJ.containsKey(nomTonneauJ1) && !res.contains(nomTonneauJ1)) {
                                return nomTonneauJ1;
                            }
                        }
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
                Map<String, Integer> tonneauJ = joueurs[0].getTonneauxProduits();
                for (String nomTonneauJ1 : tonneaux.keySet()) {
                    if (!tonneauJ.containsKey(nomTonneauJ1) && !magasin.possedeMarchndise(nomTonneauJ1)) {
                        return nomTonneauJ1;
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
        int nbTonneauMax = 0;
        int numN = 0;
        ArrayList<String> res = new ArrayList<>();
        Joueurs[] joueurs = partie.getJoueurs();

        for (Navires n: navires){
            if (!n.estVide()){
                res.add(n.getNomRessource());
            }
        }
        for(int nbTonneauxJ: tonneaux.values()){
            for (Navires n: navires) {
                if (n.estVide()) {
                    for (String nomTonneau : tonneaux.keySet()) {
                        Map<String, Integer> tonneauJ = joueurs[0].getTonneauxProduits();
                        if (!tonneauJ.containsKey(nomTonneau) && !res.contains(nomTonneau)){
                            return navires.remove(navires.indexOf(n));
                        }
                    }
                }
                int nbTonneau = n.nbTonneauACharger(nbTonneauxJ);
                if (nbTonneauMax < nbTonneau){
                    nbTonneauMax = nbTonneau;
                    ind = navires.indexOf(n);
                }
            }
        }
        if (nbTonneauMax < navires.get(numN).getTaille())
            ind = numN;
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
        int nbColonPlace;
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
    public String placerColon(Plateau plateau, String id) {
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
