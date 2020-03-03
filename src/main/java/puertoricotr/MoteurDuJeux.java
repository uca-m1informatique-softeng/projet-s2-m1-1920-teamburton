package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.*;
import puertoricotr.stockageoutilsjeux.Navires;

import java.util.*;

/**
 * Classe gérant le déroulement de la partie
 */
public class MoteurDuJeux {

    private Partie partie;
    private ServeurStats serveurStats;
    public Partie getPartie() {
        return partie;
    }

    /**
     * Constructeur de la classe
     * @param nbJoueur nombre de joueurs jouable
     * @param nbBot nombre de joueurs non jouable (bot)
     */
    public MoteurDuJeux(int nbJoueur, int nbBot, int nbPartie){
        partie = new Partie(nbJoueur, nbBot);
        serveurStats = new ServeurStats();
        lancerParties(nbPartie);
    }

    /* ==================================   Autres méthodes   ===================================
     * ========================================================================================== */

    /**
     * Ajoute un doublon a chaque personnage present dans la liste
     * @param p liste de personnage
     */
    public void ajouteDoublon(ArrayList<Personnage> p){
        for(Personnage personnage : p){
            int doublon = partie.getBanque().decrementeNbDoublon(1);
            if(doublon == 1) {
                personnage.addDoublon();
            }
        }
    }


    /**
     * Condition de fin de partie vérifiant si toutes les cases de la cité d'un joueur sont occupées
     * @return true si elles sont toutes occupées, false sinon.
     */
    public boolean testCitePleine(){
        for(int j = 0; j < partie.getNbJoueurTotal(); j++){
            if(partie.getJoueurs()[j].getPlateau().getNbBatiment() == 12){
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode appellée en fin de partie pour calculer les points de victoires reçus par un joueur
     * selon les bâtiment construit
     * @param joueurs un joueur
     * @return le nombre de point de victoire total.
     */
    public int calculerPvBatiments(Joueurs joueurs){
        int nbPointsVictoires = 0;

        for (int b = 0; b < joueurs.getPlateau().getNbBatiment(); b++){
            nbPointsVictoires += joueurs.getPlateau().getCite()[b].getPointsVicoires();
        }

        return nbPointsVictoires;
    }

    /**
     * Fonction calculant les points bonus de grands bâtiments.
     * @param nomBatiment : Nom du grand bâtiment.
     * @param joueur : Le joueur.
     * @return le nombre de pv.
     */
    public int batimentsBonus(String nomBatiment, Joueurs joueur){
        int nbPvGagne = 0;
        int nbPlantations = joueur.getPlateau().getNbExploitation();
        int petitBatOccupe = joueur.getPlateau().getNbBatimentOccupe("Petit");
        int moyenBatOccupe = joueur.getPlateau().getNbBatimentOccupe("Moyen");

        // Entre 4-7 pv par plantations occupées (9-12)
        if (nomBatiment.equals(Constantes.RESIDENCE)) {
            nbPvGagne = (nbPlantations <= 9) ? 4 : nbPlantations - 5;
        }

        // 1 Pv par 4 Pvs gagnés durant la partie (sans pv bat)
        else if (nomBatiment.equals(Constantes.DOUANE)){
            nbPvGagne = joueur.getNbPointVictoire() / 4;
        }

        // 1 Pv par lot de 3 colons dans l'ile et la cité
        else if (nomBatiment.equals(Constantes.FORTERESSE)){
            nbPvGagne = joueur.getPlateau().getNbColonTotal() / 3;
        }

        // 1 Pv par bâtiments mauves
        else if (nomBatiment.equals(Constantes.HOTEL)){
            nbPvGagne = joueur.getPlateau().getNbBatimentMauve();
        }

        // 1 Pv par petit bat. industriel, 2 Pv par grand bat. industriel
        else if (nomBatiment.equals(Constantes.GUILDE)){
            nbPvGagne = petitBatOccupe + 2 * moyenBatOccupe;
        }

        return nbPvGagne;
    }

    /**
     * Méthode permettant de mettre à jour la stratégie de l'IA Ambitieuse durant la partie
     * @param joueurs liste de joueurs.
     */
    public void majStrategie(Joueurs[] joueurs){
        for (Joueurs j: joueurs){
            if (j.estAmbitieux()){
                j.changerStrategie(partie);
            }
        }
    }

    /* ==================================   Lancement du Jeu   ==================================
     * ========================================================================================== */
    /**
     * Gere le deroulement de la partie sans affichage
     */
    public void tourDeJeuSansAffichage(){
        int i;
        int gouverneur = 0;
        int tour = 1;

        int nbJoueurTotal = partie.getNbJoueurTotal();
        Joueurs[] joueurs = partie.getJoueurs();
        Joueurs joueurActif;

        Personnage personnageChoisi;

        while(partie.getBanque().getNbPointVictoire() > 0 && partie.getBanque().getNbColon() > 0 && !this.testCitePleine()){
            i = gouverneur;
            while (partie.getPersonnages().size() > 7 % nbJoueurTotal) {
                joueurActif = joueurs[i];

                personnageChoisi = joueurActif.choixRole(partie, tour);

                joueurActif.addDoublon(personnageChoisi.recupereDoublon());
                // Actions liées au rôle choisi
                personnageChoisi.action(joueurs, i, partie, tour);
                i = (i + 1) % nbJoueurTotal;
                majStrategie(joueurs);
            }

            gouverneur = (gouverneur + 1) % nbJoueurTotal;

            ajouteDoublon(partie.getPersonnages());
            partie.initRoles();
            partie.initPlantations();
            tour++;
        }

        // Points victoires de fin de partie
        for (int j = 0; j< nbJoueurTotal; j++){

            // Recuperation des points pour les stats
            int pvBat = calculerPvBatiments(joueurs[j]);
            joueurs[j].addPVBatiment(pvBat);
            joueurs[j].addPVChargement(joueurs[j].getNbPointVictoire());

            // Points de victoires bonus grand bâtiments
            for (int b = 0; b < joueurs[j].getPlateau().getNbBatiment(); b++) {
                Batiment batiment = joueurs[j].getPlateau().getCite()[b];
                if (batiment.estGrand() && batiment.getNbColon() > 0){
                    int nbPvBonusBatiment = batimentsBonus(batiment.getNom(), joueurs[j]);
                    joueurs[j].addPointVictoire(nbPvBonusBatiment);
                    joueurs[j].addNbPointsBonusBatiment(nbPvBonusBatiment);
                }
            }

            // Ajout points victoires bâtments
            joueurs[j].addPointVictoire(pvBat);
        }

        Joueurs[] classement = partie.classementJoueurs();

        // Si partie nulle, ajout des doublons de chaque joueur à leur nombre de points victoires
        int nbJoueursEq = partie.partieNulle();
        if (nbJoueursEq > 0) {
            for (int j = 0; j <= nbJoueursEq; j++) {
                int pvDoublon = joueurs[j].getNbDoublon();
                joueurs[j].addPointVictoire(pvDoublon);
            }
            classement = partie.classementJoueurs();
        }

        Joueurs vainqueur = classement[0];
        vainqueur.addVictoire();
    }

    /**
     * Gère le déroulement de la partie
     */
    public void tourDeJeu(){
        int i;
        int gouverneur = 0;
        int tour = 1;

        int nbJoueurTotal = partie.getNbJoueurTotal();
        Joueurs[] joueurs = partie.getJoueurs();
        Joueurs joueurActif;

        Personnage personnageChoisi;
        while(partie.getBanque().getNbPointVictoire() > 0 && partie.getBanque().getNbColon() > 0 && !this.testCitePleine()){
            System.out.println("=================================================================="
                    + "=========================\n\t\t\t\t\tTOUR n°" + tour + "\n================="
                    + "==========================================================================");

            i = gouverneur;

            System.out.print("\n\033[3" + (gouverneur + 2) + "m<" + joueurs[gouverneur].getIdJoueur()
                            + ">\033[0m est le GOUVERNEUR\n");

            while (partie.getPersonnages().size() > 7 % nbJoueurTotal) {
                joueurActif = joueurs[i];

                affichageChoixExploitation();
                affichageChoixBatiment();
                affichageNavires();
                affichageMagasin();
                affichageBanque();
                affichageReserve();
                affichageChoixRoles(joueurActif, i);

                personnageChoisi = joueurActif.choixRole(partie, tour);
                System.out.print("\033[3" + (i + 2) + "m<" + joueurActif.getIdJoueur()
                              + ">\033[0m a choisi le rôle : " + personnageChoisi.getNom()
                              + ((personnageChoisi.getDoublon() > 0) ? " et récupère |"
                              + personnageChoisi.getDoublon() + "| doublon(s)\n" : "\n"));

                joueurActif.addDoublon(personnageChoisi.recupereDoublon());

                // Actions liées au rôle choisi
                System.out.println(personnageChoisi.action(joueurs, i, partie, tour));

                // Affichage statistiques
                for(int j = 0; j < nbJoueurTotal; j++) {
                    affichageScore(joueurs[j], j);
                    affichagePlateau(joueurs[j]);
                }

                System.out.print("\n" + new String(new char[91]).replace("\0", "="));

                i = (i + 1) % nbJoueurTotal;
                majStrategie(joueurs);
            }
            gouverneur = (gouverneur + 1) % nbJoueurTotal;

            ajouteDoublon(partie.getPersonnages());
            partie.initRoles();
            partie.initPlantations();
            System.out.println("\n\n\033[31m********** TOUR n°" + tour + " terminé **********\n\033[0m");
            tour++;
        }


        System.out.println("Partie terminée. Distribution des points de victoires :");

        // Calcul des points victoires de fin
        for (int j = 0; j < nbJoueurTotal; j++){

            // Recuperation des points pour les stats
            int pvBat = calculerPvBatiments(joueurs[j]);
            joueurs[j].addPVBatiment(pvBat);
            joueurs[j].addPVChargement(joueurs[j].getNbPointVictoire());

            // Points victoires chargement capitaine
            System.out.println("\n\033[3" + (j + 2) + "m<" + joueurs[j].getIdJoueur()
                    + ">\033[0m a gagné " + joueurs[j].getNbPointVictoire() + " points de victoires "
                    + "pour ses chargements durant la partie.");

            // Points de victoires bonus grand bâtiments
            for (int b = 0; b < joueurs[j].getPlateau().getNbBatiment(); b++){
                Batiment batiment = joueurs[j].getPlateau().getCite()[b];
                if (batiment.estGrand() && batiment.getNbColon() > 0){
                    int nbPvBonusBatiment = batimentsBonus(batiment.getNom(), joueurs[j]);
                    joueurs[j].addPointVictoire(nbPvBonusBatiment);
                    joueurs[j].addNbPointsBonusBatiment(nbPvBonusBatiment);
                    System.out.println("\033[3" + (j + 2) + "m<" + joueurs[j].getIdJoueur()
                            + ">\033[0m a gagné " + nbPvBonusBatiment + " points de bonus"
                            + " (" + batiment.getNom() + ")");
                }
            }

            // Points victoires bâtiments construits
            joueurs[j].addPointVictoire(pvBat);
            System.out.println("\033[3" + (j + 2) + "m<" + joueurs[j].getIdJoueur()
                            + ">\033[0m a gagné " + pvBat + " points de victoires pour"
                            + "ses bâtiments.");
            System.out.println("\033[3" + (j + 2) + "m<" + joueurs[j].getIdJoueur()
                    + ">\033[0m a gagné au total " + joueurs[j].getNbDoublonTotal() + " doublons.");
        }

        Joueurs[] classement = partie.classementJoueurs();

        // Si partie nulle, ajout des doublons de chaque joueur à leur nombre de points victoires
        int nbJoueursEq = partie.partieNulle();
        if (nbJoueursEq > 0) {
            System.out.println("\nPartie nulle, nouveau calcul des points de victoires en "
                               + "ajoutant le nombre de doublon :\n");
            for (int j = 0; j <= nbJoueursEq; j++) {
                int pvDoublon = joueurs[j].getNbDoublon();
                joueurs[j].addPointVictoire(pvDoublon);
                System.out.println("\033[3" + (j + 2) + "m<" + joueurs[j].getIdJoueur()
                                + ">\033[0m reçoit " + pvDoublon + " point(s) de victoire(s).");
            }
            classement = partie.classementJoueurs();
        }

        // Affichage résultats
        for (int j = 0; j < nbJoueurTotal; j++){
            System.out.print("\n\033[3" + (j + 2) + "m<" + joueurs[j].getIdJoueur() + ">\033[0m"
                            + ((joueurs[j].getIdJoueur().length() < 4) ? "\t\t\t" : "\t")
                            + "Total PV: " + joueurs[j].getNbPointVictoire());
        }

        Joueurs vainqueur = classement[0];
        vainqueur.addVictoire();
        System.out.println("\n\n\033[36m<" + vainqueur.getIdJoueur()
                         + "> gagne la partie avec " + vainqueur.getNbPointVictoire()
                         + " point(s) de victoire(s).\033[0m\t\t'(-  _ -)");
        System.out.println("\n===================================================================" +
                "========================\n\t\t\t\t\tFin du jeu" + "\n===========================" +
                "================================================================\n");
    }


    private void lancerParties(int nbParties){
        int i = nbParties;
        if(nbParties == 0){
            tourDeJeu();
            serveurStats.insererStatsPartie(partie);
            partie.resetPartie();
        }

        else{

            while (i > 0){
                tourDeJeuSansAffichage();
                serveurStats.insererStatsPartie(partie);
                partie.resetPartie();
                i--;
            }
        }

        serveurStats.insererResultats(partie);
        serveurStats.afficherResultats();
    }


    /* =====================================   AFFICHAGES  ======================================
     * ========================================================================================== */

    /**
     * Affiche le choix des exeploitations.
     */
    private void affichageChoixExploitation(){
        StringBuilder feedback = new StringBuilder();

        feedback.append("\nPlantations disponibles : \\");

        for(Exploitation plantation : partie.getPlantations()){
            feedback.append(plantation.getNom()+"\\");
        }

        System.out.println(feedback.toString());
        System.out.println("Carrieres disponibles   : " + partie.getCarrieres().size() + "\n");
    }

    /**
     * Affiche le choix des rôles
     */
    private void affichageChoixRoles(Joueurs j, int i){
        StringBuilder feedback = new StringBuilder();
        String nom;
        int nbDoublon;
        feedback.append("\n\033[3" + (i + 2) + "m<" + j.getIdJoueur()
                + ">\033[0m doit choisir un rôle parmi:\n");

        for(Personnage personnage : partie.getPersonnages()){
            nom = personnage.getNom();
            nbDoublon = personnage.getDoublon();
            feedback.append((nbDoublon == 0) ? ("\t- " + nom + "\n") : "\t- " + nom +
                           ((nom.length() < 12) ? "\t\t" : "\t") + "+(" + nbDoublon + ")\n");
        }
        System.out.println(feedback.toString() + "\n");
    }

    /**
     * Affiche la liste de bâtiments disponibles.
     */
    private void affichageChoixBatiment(){
        StringBuilder feedback = new StringBuilder();
        feedback.append("Batiments disponibles   : \\");

        int i = 1;
        for(Batiment batiment : partie.getBatiments()){
            feedback.append(batiment.getNom() + ((i % 4 == 0) ? "\\\n\t\t\t  \\" : "\\"));
            i++;
        }
        System.out.println(feedback.toString());
    }

    /**
     * Affiche la liste des navires
     */
    private void affichageNavires(){
        StringBuilder feedback = new StringBuilder();

        feedback.append("\nNavires \t\t: ");
        for (Navires n: partie.getNavires()){
            if (n.getNbRessource() == 0){
                feedback.append("\\" + n.getNomNavire() + " : " + "vide\t  ");
            }
            else {
                feedback.append("\\" + n.getNomNavire() + " : " + n.getNomRessource()
                                     + " = " + n.getNbRessource() + "\t  ");
            }
        }

        System.out.println(feedback.toString());
    }

    /**
     * Affiche le nombre de points de victoire et le nombre de doublons du joueur.
     * @param j: un joueur.
     * @param i: indice de ce joueur pour une coloration différente.
     */
    private void affichageScore(Joueurs j, int i){
        System.out.print("\n\033[3" + (i + 2) + "m________________________________________"
                            + j.getIdJoueur() + "___________________________________________");

        System.out.println("\nSTATS " + "\t\tPV: " + j.getNbPointVictoire() +"\t\tDoublons(s): "
                                      + j.getNbDoublon() + "\t\tColon(s): " + j.getPlateau().getNbColon());

        System.out.println("TONNEAUX " + "\tMais: " + j.getNbMais() + "\t\tIndigo: "
                                       + j.getNbIndigo() + "\tSucre: " + j.getNbSucre() + "\tCafe: "
                                       + j.getNbCafe() + "\t\tTabac: " + j.getNbTabac());
    }


    /**
     * Affiche le plateau du joueur contenant ses plantations et ses batiments.
     * @param joueur: un joueur.
     */
    private void affichagePlateau(Joueurs joueur){
        System.out.println(joueur.getPlateau().getAffichage() + "\033[0m");
    }

    /**
     * Affiche le magasin du jeux.
     */
    private void affichageMagasin(){ System.out.println(partie.getMagasin().getAffichage() + "\n"); }

    /**
     * Affiche la banque.
     */
    private void affichageBanque(){ System.out.println(partie.getBanque().getAffichage()); }

    /**
     * Affiche la réserve.
     */
    private void affichageReserve(){ System.out.println(partie.getReserve().getAffichage() + "\n"); }
}
