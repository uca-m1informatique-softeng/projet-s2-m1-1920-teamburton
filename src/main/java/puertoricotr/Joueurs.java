package puertoricotr;

import puertoricotr.personnages.Personnage;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.batiments.Batiment;
import puertoricotr.stockageoutilsjeux.*;

import java.util.*;

/**
 * Classe gérant les joueurs (jouable)
 */
public class Joueurs {
    private String idJoueur;
    private Plateau plateau;

    private int nbVictoires;

    private int nbDoublon;
    private int nbPointVictoire;
    private int nbPVBatiment;
    private int nbPVChargement;
    private int nbPointsBonusBatiments;

    private int nbDoublonTotal;
    private int nbColonTotal;
    private int nbBatimentTotal;
    private int nbPlantationTotal;
    private int nbCarriereTotal;
    private int nbPVBatimentTotal;
    private int nbPVChargementTotal;
    private int nbPointsBonusBatimentsTotal;

    private int nbTonneaux;
    private int nbTonneauxTotal;

    private HashMap<String, Integer> tonneaux;
    private IntelligenceArtificielle intelligenceArtificielle;
    private IntelligenceArtificielle iaDepart;
    public boolean ambitieuse;


    /**
     * Constructeur
     * @param id identifiant du joueur
     * @param strategie strategie du joueur
     */
    public Joueurs(String id, IntelligenceArtificielle strategie) {
        this.idJoueur = id;
        this.iaDepart = strategie;
        this.intelligenceArtificielle = iaDepart;
        this.plateau = new Plateau();

        this.nbVictoires = 0;

        this.nbDoublon = 3;
        this.nbColonTotal = 0;
        this.nbBatimentTotal = 0;
        this.nbPlantationTotal = 0;
        this.nbCarriereTotal = 0;
        this.nbPointVictoire = 0;
        this.nbPVBatiment = 0;
        this.nbPVChargement = 0;
        this.nbPointsBonusBatiments = 0;

        this.nbPVBatimentTotal = 0;
        this.nbPVChargementTotal = 0;
        this.nbDoublonTotal = 0;

        this.tonneaux = new HashMap<>();
        this.tonneaux.put(Constantes.MAIS, 0);
        this.tonneaux.put(Constantes.INDIGO, 0);
        this.tonneaux.put(Constantes.SUCRE, 0);
        this.tonneaux.put(Constantes.TABAC, 0);
        this.tonneaux.put(Constantes.CAFE, 0);

        this.nbTonneaux = 0;
        this.nbTonneauxTotal = 0;

        this.ambitieuse = false;
    }


    /* Joueur Id
     * ------------------------------------------------------------------------------------------ */
    public String getIdJoueur() {
        return this.idJoueur;
    }

    public void setIDjoueur(String idJoueur) {
        this.idJoueur = idJoueur;
    }


    /* Stats : nombre de victoire/point de victoire/doublon
     * ------------------------------------------------------------------------------------------ */

    public int getNbColonTotal() {
        return this.nbColonTotal;
    }

    public int getNbPlantationTotal() {
        return this.nbPlantationTotal;
    }

    public int getNbCarriereTotal() {
        return this.nbCarriereTotal;
    }

    public int getNbBatimentTotal() {
        return this.nbBatimentTotal;
    }


    public int getNbVictoires(){
        return this.nbVictoires;
    }

    public void addVictoire(){
        this.nbVictoires++;
    }

    public int getNbPVBatiment() {
        return this.nbPVBatiment;
    }

    public void addPVBatiment(int nbPVBatiment){

        this.nbPVBatiment += nbPVBatiment;
        this.nbPVBatimentTotal += nbPVBatiment;
    }

    public int getNbPVChargement() {
        return this.nbPVChargement;
    }

    public void addPVChargement(int nbPVChargement){

        this.nbPVChargement += nbPVChargement;
        this.nbPVChargementTotal += nbPVChargement;
    }
    public int getNbPVBatimentTotal(){
        return this.nbPVBatimentTotal;
    }

    public int getNbPVChargementTotal(){
        return this.nbPVChargementTotal;
    }

    public int getNbPointsBonusBatiments() {
        return this.nbPointsBonusBatiments;
    }

    public int getNbPointsBonusBatimentsTotal() {
        return nbPointsBonusBatimentsTotal;
    }

    public void addNbPointsBonusBatiment(int nbPointsBonus){
        this.nbPointsBonusBatiments += nbPointsBonus;
        this.nbPointsBonusBatimentsTotal += nbPointsBonus;
    }


    /* Plateau
     * ------------------------------------------------------------------------------------------ */
    public Plateau getPlateau() {
        return this.plateau;
    }


    /* Points victoires
     * ------------------------------------------------------------------------------------------ */
    public int getNbPointVictoire() {
        return nbPointVictoire;
    }

    public void addPointVictoire(int nbPointVictoire) {
        this.nbPointVictoire += nbPointVictoire;
    }

    public void setPointVictoire(int nbPointVictoire) {
        this.nbPointVictoire = nbPointVictoire;
    }


    /* Doublons
     * ------------------------------------------------------------------------------------------ */
    public int getNbDoublon() {
        return this.nbDoublon;
    }

    public int getNbDoublonTotal(){
        return this.nbDoublonTotal;
    }

    public void addDoublon(int nbDoublon) {
        this.nbDoublon += nbDoublon;
        this.nbDoublonTotal += nbDoublon;
    }

    public boolean subDoublon(int nbDoublon) {
        if (this.nbDoublon >= nbDoublon) {
            this.nbDoublon -= nbDoublon;
            return true;
        }
        return false;
    }


    /* Mais
     * ------------------------------------------------------------------------------------------ */
    public int getNbMais() {
        return this.tonneaux.get(Constantes.MAIS);
    }

    public void addNbMais(int nbMais) {
        this.tonneaux.put(Constantes.MAIS, tonneaux.get(Constantes.MAIS) + nbMais);
    }

    public void subNbMais(int nbMais) {
        if (tonneaux.get(Constantes.MAIS) > 0) {
            this.tonneaux.put(Constantes.MAIS, tonneaux.get(Constantes.MAIS) - nbMais);
        }
    }


    /* Indigo
     * ------------------------------------------------------------------------------------------ */
    public int getNbIndigo() { return this.tonneaux.get(Constantes.INDIGO); }

    public void addNbIndigo(int nbIndigo) {
        this.tonneaux.put(Constantes.INDIGO, tonneaux.get(Constantes.INDIGO) + nbIndigo);
    }

    public void subNbIndigo(int nbIndigo) {
        if (tonneaux.get(Constantes.INDIGO) > 0) {
            this.tonneaux.put(Constantes.INDIGO, tonneaux.get(Constantes.INDIGO) - nbIndigo);
        }
    }


    /* Sucre
     * ------------------------------------------------------------------------------------------ */
    public int getNbSucre() { return this.tonneaux.get(Constantes.SUCRE); }

    public void addNbSucre(int nbSucre) {
        this.tonneaux.put(Constantes.SUCRE, tonneaux.get(Constantes.SUCRE) + nbSucre);
    }

    public void subNbSucre(int nbSucre) {
        if (tonneaux.get(Constantes.SUCRE) > 0) {
            this.tonneaux.put(Constantes.SUCRE, tonneaux.get(Constantes.SUCRE) - nbSucre);
        }
    }


    /* Tabac
     * ------------------------------------------------------------------------------------------ */
    public int getNbTabac() { return this.tonneaux.get(Constantes.TABAC); }

    public void addNbTabac(int nbTabac) {
        this.tonneaux.put(Constantes.TABAC, tonneaux.get(Constantes.TABAC) + nbTabac);
    }

    public void subNbTabac(int nbTabac) {
        if (tonneaux.get(Constantes.TABAC) > 0) {
            this.tonneaux.put(Constantes.TABAC, tonneaux.get(Constantes.TABAC) - nbTabac);
        }
    }


    /* Cafe
     * ------------------------------------------------------------------------------------------ */
    public int getNbCafe() { return this.tonneaux.get(Constantes.CAFE); }

    public void addNbCafe(int nbCafe) {
        this.tonneaux.put(Constantes.CAFE, tonneaux.get(Constantes.CAFE) + nbCafe);
    }

    public void subNbCafe(int nbCafe) {
        if (tonneaux.get(Constantes.CAFE) > 0) {
            this.tonneaux.put(Constantes.CAFE, tonneaux.get(Constantes.CAFE) - nbCafe);
        }
    }


    /* Tonneaux
     * ------------------------------------------------------------------------------------------ */
    public Map<String, Integer> getTonneaux(){
        return this.tonneaux;
    }

    public int getNbTonneauxActuel(){
        int nbTonneauxTotal = 0;
        for (int nbr : this.tonneaux.values()){
            nbTonneauxTotal += nbr;
        }

        return nbTonneauxTotal;
    }

    public int getNbTonneauxTotal() {
        return this.nbTonneauxTotal;
    }

    public Map<String, Integer> getTonneauxProduits(){
        Map<String, Integer> tonneauxProduits = new HashMap<>();
        for (Map.Entry<String, Integer> t : this.tonneaux.entrySet()) {
            if (t.getValue() > 0) {
                tonneauxProduits.put(t.getKey(), t.getValue());
            }
        }

        return tonneauxProduits;
    }
    public void addExploitation(Exploitation p) {
        this.plateau.addExploitation(p);
    }

    public void addBatiment(Batiment b) {
        this.plateau.addBatiment(b);
        this.getPlateau().setNbPlaceCite(b.getNbColonLimite());
    }


    /**
     * Méthode permettant d'ajouter un tonneau de production au stock du joueur.
     * @param nomProduction Nom du tonneau de production à ajouter.
     */
    public void addTonneau(String nomProduction, int nombre){
        this.nbTonneaux += nombre;
        switch (nomProduction){
            case Constantes.MAIS:
                this.addNbMais(nombre);
                break;
            case Constantes.INDIGO:
                this.addNbIndigo(nombre);
                break;
            case Constantes.SUCRE:
                this.addNbSucre(nombre);
                break;
            case Constantes.TABAC:
                this.addNbTabac(nombre);
                break;
            case Constantes.CAFE:
                this.addNbCafe(nombre);
                break;

            default:
                break;
        }
    }

    /**
     * Méthode permettant d'enlever un tonneau de production au stock du joueur.
     * @param nomProduction Nom du tonneau de production à enlever.
     */
    public void subTonneau(String nomProduction, int nombre){
        switch (nomProduction){
            case Constantes.MAIS:
                this.subNbMais(nombre);
                break;
            case Constantes.INDIGO:
                this.subNbIndigo(nombre);
                break;
            case Constantes.SUCRE:
                this.subNbSucre(nombre);
                break;
            case Constantes.TABAC:
                this.subNbTabac(nombre);
                break;
            case Constantes.CAFE:
                this.subNbCafe(nombre);
                break;

            default:
                break;
        }
    }

    /**
     * Recupere la ligne tapé par le joueur et recupere le personnage associé
     * @return Personnage choisit par le joueur
     */
    public Personnage choixRole(Partie partie, int tour) {
        return intelligenceArtificielle.choixRole(partie, this.plateau, this.nbDoublon, this.getNbTonneauxActuel(), tour);
    }

    /**
     * Recupere la ligne tapé par le joueur et recupere la plantation associé
     * @return plantation choisi par le joueur
     */
    public Exploitation choixExploitation(Partie partie, boolean joueursActif, int tour) {
       return intelligenceArtificielle.choixExploitation(partie, this.plateau, joueursActif, tour);

    }

    /**
     * Recupere la ligne tapé par le joueur et recupere le batiment associé
     * @return batiment choisi par le joueur
     */
    public Batiment choixBatiment(Partie partie) {
       return intelligenceArtificielle.choixBatiment(partie, this.getNbDoublon(), this.plateau);
    }


    /**
     * Détermine si un joueur peut toujours construire des bâtiments (uniques) selon la liste de
     * bâtiments disponible.
     * @param batiments liste des bâtiments disponibles
     * @return true s'il peut construire, false sinon.
     */
    public boolean peutConstruire(ArrayList<Batiment> batiments){

        for (Batiment b: batiments){
            if (!this.plateau.possedeBatiment(b.getNom())){
                return true;
            }
        }

        return false;
    }

    /**
     * Place un colon sur le bâtiment donné en parametre
     * @return le feedback de l'action
     */
    public String placerColonBatiment(){
       return intelligenceArtificielle.placerColonBatiment(this.plateau, this.idJoueur);
    }

    /**
     * Place un colon sur l'exploitation donné en parametre
     * @return le feedback de l'action
     */
    public String placerColonExploitaion(){
       return intelligenceArtificielle.placerColonExploitaion(this.plateau, this.idJoueur);
    }

    /**
     * Place un colon sur un bâtiment ou une exploitation selon l'entrée du joueur
     * @return le feedback de l'action
     */
    public String placerColon() {
        return intelligenceArtificielle.placerColon(this.plateau, this.idJoueur);
    }

    public String choixTonneau(Partie partie, String nomRole, Map<String, Integer> tonneaux, Navires navire){
        return intelligenceArtificielle.choixTonneau(partie, nomRole, tonneaux, navire);
    }
    public Navires choixNavire (Partie partie, ArrayList<Navires> navires, Map<String, Integer> tonneaux){
        return intelligenceArtificielle.choixNavire(partie, navires, tonneaux);
    }

    public void resetJoueur(){
        this.intelligenceArtificielle = iaDepart;

        this.nbPlantationTotal += this.plateau.getNbPlantation();
        this.nbCarriereTotal += this.plateau.getNbCarriere();
        this.nbBatimentTotal += this.plateau.getNbBatiment();
        this.nbColonTotal += this.plateau.getNbColon() + this.plateau.getNbColonTotal();
        this.plateau = new Plateau();

        this.nbDoublon = 3;
        this.nbPointVictoire = 0;
        this.nbPVBatiment = 0;
        this.nbPVChargement = 0;

        this.nbTonneauxTotal += this.nbTonneaux;
        this.nbTonneaux = 0;
        this.tonneaux.put(Constantes.MAIS, 0);
        this.tonneaux.put(Constantes.INDIGO, 0);
        this.tonneaux.put(Constantes.SUCRE, 0);
        this.tonneaux.put(Constantes.TABAC, 0);
        this.tonneaux.put(Constantes.CAFE, 0);
    }

    public void setIntelligenceArtificielle(IntelligenceArtificielle IA) {
        this.intelligenceArtificielle = IA;
    }

    public boolean estAmbitieux(){
        return this.ambitieuse;
    }


    public void setAmbitieuse(boolean ambitieuse) {
        this.ambitieuse = ambitieuse;
    }

    /**
     * Méthode permettant de modifier les stratégies de l'IA Ambitieuse
     * @param partie permet de récupérer la liste de joueurs
     */
    public void changerStrategie(Partie partie){

        for(Joueurs j: partie.getJoueurs()){
            if (!j.equals(this)) {

                // Si moins de points de victoire de bâtiments
                if ((j.getNbPVBatimentTotal() > this.getNbPVBatimentTotal())) {
                    // Assez de doublons, constructions bâtiments
                    if (this.getNbDoublon() > 1) {
                        this.setIntelligenceArtificielle(new StrategieBatiment());
                    }

                    // Pas assez de doublons, on essaie d'en gagner plus
                    else{
                        this.setIntelligenceArtificielle(new StrategieDoublon());
                    }
                }

                // Pas assez de tonneau comparer aux autres joueus, on essaie de les contrer
                else if ((j.getNbTonneauxActuel() > this.getNbTonneauxActuel())){
                    this.setIntelligenceArtificielle(new StrategieContre());
                }

                // On rush le maïs et les chargements sinon
                else{
                    this.setIntelligenceArtificielle(new StrategieMais());
                }
            }
        }
    }


}