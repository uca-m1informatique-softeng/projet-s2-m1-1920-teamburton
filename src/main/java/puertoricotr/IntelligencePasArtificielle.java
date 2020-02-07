package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.Personnage;
import puertoricotr.stockageoutilsjeux.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class IntelligencePasArtificielle {
    private  String id;
    public IntelligencePasArtificielle(String id){
        this.id = id;
    }
    /**
     * Recupere la ligne tapé par le joueur et recupere le personnage associé
     * @param roles ArrayList des personnages disponibles
     * @return Personnage choisit par le joueur
     */
    public Personnage choixRole(ArrayList<Personnage> roles) {
        Scanner sc = new Scanner(System.in);
        String s = "";

        String[] nomsRoles = new String[roles.size()];
        for (int i = 0; i < roles.size(); i++) {
            nomsRoles[i] = roles.get(i).getNom();
        }
        int choix = Arrays.asList(nomsRoles).indexOf(s);

        while (choix < 0) {
            if (sc.hasNext()) {
                s = sc.nextLine();
            }
            choix = Arrays.asList(nomsRoles).indexOf(s);
        }
        return roles.remove(choix);
    }

    /**
     * Recupere la ligne tapé par le joueur et recupere la plantation associé
     * @param plantations ArrayList des plantations disponibles
     * @return plantation choisi par le joueur
     */
    public Exploitation choixExploitation(ArrayList<Exploitation> plantations,
                                          ArrayList<Exploitation> carrieres, boolean joueursActif, int tour) {
        Scanner sc = new Scanner(System.in);
        String s = "";
        String[] nomsPlantations = new String[plantations.size()];
        for (int i = 0; i < plantations.size(); i++) {
            nomsPlantations[i] = plantations.get(i).getNom();
        }
        int choix = Arrays.asList(nomsPlantations).indexOf(s);

        while (choix < 0) {
            if (sc.hasNext()) {
                s = sc.nextLine();
            }
            choix = Arrays.asList(nomsPlantations).indexOf(s);
        }
        return plantations.remove(choix);

    }

    /**
     * Recupere la ligne tapé par le joueur et recupere le batiment associé
     * @param batiments ArrayList des batiments disponibles
     * @return batiment choisi par le joueur
     */
    public Batiment choixBatiment(ArrayList<Batiment> batiments) {
        Scanner sc = new Scanner(System.in);
        String s = "";

        String[] nomsBatiments = new String[batiments.size()];
        for (int i = 0; i < batiments.size(); i++) {
            nomsBatiments[i] = batiments.get(i).getNom();
        }
        int choix = Arrays.asList(nomsBatiments).indexOf(s);

        while (choix < 0) {
            if (sc.hasNext()) {
                s = sc.nextLine();
            }
            choix = Arrays.asList(nomsBatiments).indexOf(s);
        }
        return batiments.remove(choix);
    }
    /**
     * Methode offrant le choix entre un bâtiment ou une exploitation.
     * @return le feedback de cette action.
     */
   public String choixPlacement(Plateau plateau){

        StringBuilder feedback = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        String placerQuoi = sc.nextLine();

        Batiment batiment;
        Exploitation exploitation;
        ArrayList<Batiment> batiments = new ArrayList<>();
        ArrayList<Exploitation> exploitations = new ArrayList<>();

        // Récuperations des bâtiments/exploitations dans une Arraylist
        exploitations.addAll(Arrays.asList(plateau.getIle()).subList(0,
                plateau.getNbExploitation()));

        batiments.addAll(Arrays.asList(plateau.getCite()).subList(0,
                plateau.getNbBatiment()));

        // Les choix de placements
        while (!(placerQuoi.equals("Batiment") || placerQuoi.equals("Exploitation"))){
            placerQuoi = sc.nextLine();
        }

        if (placerQuoi.equals("Batiment") && plateau.getNbBatiment() > 0){
            batiment = choixBatiment(batiments);
            feedback.append(placerColonBatiment(plateau));
        }

        else if (placerQuoi.equals("Exploitation") && plateau.getNbExploitation() > 0){
            exploitation = choixExploitation(exploitations, exploitations, true,1);
            feedback.append(placerColonExploitaion(plateau));
        }
        return feedback.toString();
    }

    public String choixTonneau(ArrayList<String> vente){
        Scanner sc = new Scanner(System.in);
        String s = "";
        String[] nomTonneaux = new String[vente.size()];
        for (int i = 0; i < vente.size(); i++) {
            nomTonneaux[i] = vente.get(i);
        }
        int choix = Arrays.asList(nomTonneaux).indexOf(s);

        while (choix < 0) {
            if (sc.hasNext()) {
                s = sc.nextLine();
            }
            choix = Arrays.asList(nomTonneaux).indexOf(s);
        }
        return vente.remove(choix);
    }

    public Navires choixNavire (ArrayList<Navires> navires){
        Navires nav = new Navires( 4);
        return nav;
    }

    /**
     * Methode permettant de placer des colons dans des bâtiments
     * @return le feedback de la phase de placement.
     */
    public String placerColonExploitaion(Plateau plateau){
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
                feedback.append("\n<" + this.id + "> place un colon sur l'exploitation de : "
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
    public String placerColonBatiment(Plateau plateau){
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
}
