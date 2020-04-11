package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.personnages.Personnage;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.stockageoutilsjeux.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe gérant les bots
 */
public class StrategieRandom implements IntelligenceArtificielle{

    private SecureRandom random;
    private String nomBot;
    private String nomStrategie;

    public StrategieRandom(){
        this.nomStrategie = Constantes.SRANDOM;
        this.nomBot = "BOT " + this.nomStrategie;
        random = new SecureRandom();
    }

    public String getNomBot() {
        return nomBot;
    }

    public String getNomStrategie() {
        return nomStrategie;
    }

    public void setRandom(SecureRandom rand){
        this.random = rand;
    }

    /**
     * Choisi un role au hasard parmis les roles disponibles
     * @return Personnage represantant le role choisi
     */
    @Override
    public Personnage choixRole(Partie partie, Plateau plateau, int nbDoublon,
                                int nbTonneauxTotal ,int tour){

        ArrayList<Personnage> roles = partie.getPersonnages();
        int i = this.random.nextInt(roles.size());

        return roles.remove(i);
    }

    /**
     * choisi une exploitation au hasard parmis les plantations disponibles
     * @param joueursActif true s'il s'agit du joueur qui a choisi le rôle, false sinon
     * @return plantation ou carriere choisie
     */
    @Override
    public Exploitation choixExploitation(Partie partie, Plateau plateau, boolean joueursActif,
                                          int tour){

        ArrayList<Exploitation> plantations = partie.getPlantations();
        ArrayList<Exploitation> carrieres = partie.getCarrieres();

        int size = plantations.size();
        int i = this.random.nextInt(size);
        int decision = this.random.nextInt(2);
        // Choix pour les privilèges de l'élu.
        if (joueursActif) {
            // Si plus de carrière on choisit par défaut une plantation.
            if (decision == 0 || carrieres.isEmpty()) {
                return plantations.remove(i);
            }

            else {
                return carrieres.remove(0);
            }
        }

        // Choix par défaut pour les autres joueurs.
        else {
            return plantations.remove(i);
        }
    }

    /**
     * choisi un batiment au hasard parmis les batiments disponibles
     * @return batiment choisi
     */
    @Override
    public Batiment choixBatiment(Partie partie, int nbDoublon, Plateau plateau){

        ArrayList<Batiment> batiments = partie.getBatiments();

        int size = batiments.size();
        int rand = this.random.nextInt(size);

        Batiment batiment = batiments.get(rand);

        // Unicité du bâtiment
        while (plateau.possedeBatiment(batiment.getNom())){
            rand = this.random.nextInt(size);
            batiment = batiments.get(rand);
        }
        return batiments.remove(rand);
    }

    /**
     * choisi une tonneau au hasard parmis ceux disponibles.
     * @param tonneaux ArrayList des marchandises pouvant être choisi
     * @return la marchandise choisie
     */
    @Override
    public String choixTonneau(Partie partie, String nomRole, Map<String, Integer> tonneaux, Navires navire){

        ArrayList<String> listeTonneau = new ArrayList<>(tonneaux.keySet());
        int i = this.random.nextInt(listeTonneau.size());

        return listeTonneau.remove(i);
    }

    /**
     * choisi un navire au hasard parmis ceux disponibles
     * @param navires ArrayList des navires disponible
     * @return le navir choisie (Arraylist pouvant être vide)
     */
    @Override
    public Navires choixNavire (Partie partie, ArrayList<Navires> navires, Map<String, Integer> nbtonneau){
        int i = this.random.nextInt(navires.size());

        return navires.remove(i);
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
        int decision = this.random.nextInt(2);

        if (plateau.getNbColon() == 0){
            return "ne possede plus d'colons.";
        }

        // Décision aléatoire pour le placement ou non
        else if (decision == 0) {
            feedback.append("décide de placer ses colons.");
            // Possède des colons et il reste des plantations non occupées.
            if (plateau.getNbColon() > 0 && plateau.placeDispoIle()) {
                feedback.append(placerColonExploitaion(plateau, id));
            }

            // Toutes les plantations occupées.
            else if (plateau.getNbColon() > 0 && !plateau.placeDispoIle()) {
                feedback.append("\n<" + id + "> ne peut plus placer de colons dans ses exlpoitations.");
            }

            // Possede des colons et il reste des batiments non occupés.
            if (plateau.getNbColon() > 0 && plateau.placeDispoCite()) {
                feedback.append(placerColonBatiment(plateau, id));
            }

            // Tous les bâtiments sont occupés
            else if (plateau.getNbColon() > 0 && !plateau.placeDispoCite()) {
                feedback.append("\n<" + id + "> ne peut plus placer de colons dans ses batiments.");
            }
        }

        else{
            feedback.append("veut garder ses colons.");
        }

        return feedback.toString();
    }
}
