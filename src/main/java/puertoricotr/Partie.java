package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.batiments.grands.*;
import puertoricotr.batiments.petits.*;
import puertoricotr.batiments.productions.*;
import puertoricotr.exploitations.Exploitation;
import puertoricotr.personnages.*;
import puertoricotr.stockageoutilsjeux.*;
import java.security.SecureRandom;
import java.util.*;

/**
 * Classe contenant les élément d'une partie
 */
public class
Partie {
    private int nbJoueurTotal;
    private Joueurs[] joueurs;

    private ArrayList<Personnage> personnages;
    private Paysan paysan;
    private ChercheurOR chercheurOr;
    private Maire maire;
    private Batisseur batisseur;
    private Producteur producteur;
    private Marchand marchand;
    private Capitaine capitaine;

    private ArrayList<Exploitation> plantations;
    private ArrayList<Exploitation> carrieres;
    private Stack<Exploitation> pilePlantation;
    private ArrayList<Batiment> batiments;
    private ArrayList<Navires> navires;

    private Magasin magasin;
    private Banque banque;
    private Reserve reserve;

    /**
     * Constructeur de la classe
     * @param nbJoueur nombre de joueurs jouable
     * @param nbBot nombre de joueurs non jouable (bot)
     */
    public Partie(int nbJoueur, int nbBot) {
        this.nbJoueurTotal = nbJoueur + nbBot;
        this.joueurs = new Joueurs[nbJoueurTotal];
        this.pilePlantation = new Stack<>();
        this.plantations = new ArrayList<>();
        this.carrieres = new ArrayList<>();
        this.personnages = new ArrayList<>();
        this.batiments = new ArrayList<>();
        this.magasin = new Magasin();
        this.navires = new ArrayList<>();

        int nbColonBanque = 20 + ((this.nbJoueurTotal - 1) * 20);
        int nbPointVictoirebanque = this.nbJoueurTotal * 25;
        int nbDoubonBanque = 86;
        this.banque = new Banque(nbDoubonBanque, nbColonBanque, nbPointVictoirebanque);
        this.reserve = new Reserve(12, 12, 12, 12, 12);

        paysan = new Paysan();
        maire = new Maire();
        chercheurOr = new ChercheurOR();
        batisseur = new Batisseur();
        producteur = new Producteur();
        marchand = new Marchand();
        capitaine = new Capitaine();

        initRoles();
        initPilePlantations();
        initPlantations();
        initCarrieres();
        initBatiments();
        initNavires();

        // Initialisation des bots

        // GARANTIE VS AMBITIEUX
        joueurs[0] = new Joueurs("BOT Garantie " + 1, (new StrategieGarantie()));
        joueurs[1] = new Joueurs("BOT Ambitieux " + 1, (new StrategieBatiment()));
        joueurs[1].setAmbitieuse(true);
    }


    /* ==================================       Getters       ===================================
     * ========================================================================================== */

    public Joueurs[] getJoueurs(){
        return joueurs;
    }

    public int getNbJoueurTotal(){
        return this.nbJoueurTotal;
    }

    public ArrayList<Exploitation> getPlantations(){
        return this.plantations;
    }

    public ArrayList<Batiment> getBatiments() {
        return batiments;
    }

    public ArrayList<Exploitation> getCarrieres() {
        return carrieres;
    }

    public ArrayList<Personnage> getPersonnages() {
        return personnages;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public ArrayList<Navires> getNavires(){
        return this.navires;
    }

    public Banque getBanque(){
        return this.banque;
    }

    public Reserve getReserve(){
        return this.reserve;
    }

    public Stack<Exploitation> getPilePlantation(){
        return this.pilePlantation;
    }

    /* ==================================   Initialisations   ===================================
     * ========================================================================================== */

    public void initRoles(){
        personnages.clear();
        personnages.add(paysan);
        personnages.add(chercheurOr);
        personnages.add(maire);
        personnages.add(batisseur);
        personnages.add(producteur);
        personnages.add(marchand);
        personnages.add(capitaine);
    }

    /**
     * initialise la pile de plantation de maniere aleatoire.
     */
    public void initPilePlantations(){
        Exploitation[] vPlantations = new Exploitation[50];
        SecureRandom random = new SecureRandom();
        int i;
        for(i = 0 ; i< 10 ; i++){
            vPlantations[i] = new Exploitation(Constantes.MAIS);
        }
        for(i = 10 ; i< 22 ; i++){
            vPlantations[i] = new Exploitation(Constantes.INDIGO);
        }
        for(i = 22 ; i< 30 ; i++){
            vPlantations[i] = new Exploitation(Constantes.SUCRE);
        }
        for (i = 30; i< 39; i++){
            vPlantations[i] = new Exploitation(Constantes.TABAC);
        }
        for (i = 39; i< 50; i++) {
            vPlantations[i] = new Exploitation(Constantes.CAFE);
        }
        for(i = vPlantations.length-1; i>0; i--){
            int j = random.nextInt(i+1);
            Exploitation a = vPlantations[j];
            vPlantations[j] = vPlantations[i];
            vPlantations[i] = a;
        }

        for(i = 0; i< vPlantations.length ; i++){
            pilePlantation.push(vPlantations[i]);
        }
    }

    public void initPlantations(){

        Exploitation p;
        int size = plantations.size();
        if(size < nbJoueurTotal + 1){
            for(int i = 0; i < nbJoueurTotal+1-size; i++){
                if(!pilePlantation.empty()) {
                    p = pilePlantation.pop();
                    this.plantations.add(p);
                }
            }
        }
    }

    /**
     * Initialisation des carrieres.
     */
    public void initCarrieres(){
        for (int c = 0; c < 8; c++){
            this.carrieres.add(new Exploitation(Constantes.CARRIERE));
        }
    }

    /**
     * Initialisation des bâtiments de productions.
     */
    public void initBatiments() {

        // Bâtiments de production
        for(int b = 0; b < nbJoueurTotal; b++) {
            this.batiments.add(new PetiteRaffinerieSucre());
            this.batiments.add(new PetiteTeinturerieIndigo());
            this.batiments.add(new RaffinerieSucre());
            this.batiments.add(new TeinturerieIndigo());
            this.batiments.add(new SechoirTabac());
            this.batiments.add(new BrulerieCafe());
        }

        // Petits batiments mauve
        int nbPetitBat = (this.nbJoueurTotal < 3) ? 1 : 2;
        for (int b = 0; b < nbPetitBat; b++){
            this.batiments.add(new Hacienda());
            this.batiments.add(new Barraque());
            this.batiments.add(new PetitMarche());
            this.batiments.add(new GrandMarche());
            this.batiments.add(new Comptoire());
            this.batiments.add(new Port());
            this.batiments.add(new Universite());
            this.batiments.add(new Manufacture());
            this.batiments.add(new Hospice());
        }

        // Grands bâtiments mauve
        this.batiments.add(new Residence());
        this.batiments.add(new Douane());
        this.batiments.add(new Forteresse());
        this.batiments.add(new HotelDeVille());
        this.batiments.add(new Guilde());
    }

    /**
     * Initialisation des navires de marchandises
     */
    public void initNavires(){
        if (nbJoueurTotal == 2) {
            navires.add(new Navires(4));
            navires.add(new Navires(6));
        }

        else{
            for (int j = nbJoueurTotal + 1; j <= nbJoueurTotal + 3; j++) {
                navires.add(new Navires(j));
            }
        }
    }

    /**
     * Recherche le joueur possédant le plus grand nombre de points victoires.
     * @param joueur: liste des joueurs.
     * @return ce joueur.
     */
    public Joueurs joueurMaxPv(ArrayList<Joueurs> joueur){
        Joueurs joueurMaxPV = joueur.get(0);
        for (int j = 1; j < joueur.size(); j++){
            if (joueur.get(j).getNbPointVictoire() > joueurMaxPV.getNbPointVictoire()){
                joueurMaxPV = joueur.get(j);
            }
        }
        return joueurMaxPV;
    }


    /**
     * Trie les joueurs par ordre décroissant selon leurs nombre de points victoires
     * @return la liste de classement des joueurs0
     */
    public Joueurs [] classementJoueurs(){
        Joueurs[] classement = new Joueurs[this.nbJoueurTotal];
        ArrayList<Joueurs> listeJoueurs = new ArrayList<>(Arrays.asList(this.joueurs));
        Joueurs jMaxPV;

        for (int j = 0; j < this.nbJoueurTotal; j++){
            jMaxPV = joueurMaxPv(listeJoueurs);
            classement[j] = jMaxPV;
            listeJoueurs.remove(jMaxPV);
        }
        return classement;
    }

    /**
     * Methode determinant le nombre de joueurs avec le même nombre de points victoires
     * @return nombre de joueurs avec PV equivalents au premier.
     */
    public int partieNulle(){
        int nbJoueurEq = 0;
        for (int j = 0; j < this.nbJoueurTotal - 1; j++){
            if (this.joueurs[j].getNbPointVictoire() == this.joueurs[j + 1].getNbPointVictoire()){
                nbJoueurEq++;
            }
        }
        return nbJoueurEq;
    }

    public void resetPartie(){
        // Banque
        int nbJoueurTotal = this.nbJoueurTotal;
        int nbColonBanque = 20 + ((nbJoueurTotal - 1) * 20);
        int nbPointVictoirebanque = nbJoueurTotal * 25;
        int nbDoublonBanque = 86;

        Banque banque = this.banque;
        banque.setNbColon(nbColonBanque);
        banque.setNbDoublon(nbDoublonBanque);
        banque.setNbPointVictoire(nbPointVictoirebanque);

        // Réserve
        Reserve reserve = this.reserve;
        reserve.setNbMarchandise(Constantes.MAIS, 12);
        reserve.setNbMarchandise(Constantes.CAFE, 12);
        reserve.setNbMarchandise(Constantes.TABAC, 12);
        reserve.setNbMarchandise(Constantes.SUCRE, 12);
        reserve.setNbMarchandise(Constantes.INDIGO, 12);

        // Personnage
        for(Personnage p : this.personnages){
            p.recupereDoublon();
        }

        // Joueurs
        Joueurs[] listeJoueurs = this.joueurs;
        for(int j = 0; j < nbJoueurTotal; j++){
            listeJoueurs[j].resetJoueur();
        }

        // Plantations
        this.pilePlantation.clear();
        initPilePlantations();
        initPlantations();

        // Carriere
        this.carrieres.clear();
        initCarrieres();

        // Batiments
        this.batiments.clear();
        initBatiments();

        // Navires
        this.navires.clear();
        initNavires();
    }
}