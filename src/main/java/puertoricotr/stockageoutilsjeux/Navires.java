package puertoricotr.stockageoutilsjeux;

public class Navires {
    private String[] ressource;
    private String nomNavire;
    private int nbRessource;
    private int taille;


    public Navires(int taille){
        this.nbRessource = 0;
        this.nomNavire = "Navire " + taille;
        this.taille = taille;
        this.ressource = new String[this.taille];
    }


    public int getNbPlaceDisponible(){
        return this.taille - this.nbRessource;
    }

    public String getNomRessource(){
        return this.ressource[0];
    }

    public String getNomNavire(){
        return this.nomNavire;
    }

    public int getTaille() {
        return taille;
    }

    public int getNbRessource(){
        return this.nbRessource;
    }

    public String[] getRessource(){
        return this.ressource;
    }

    public boolean estComplet(){
        return this.nbRessource == this.taille;
    }

    public boolean estVide() {return this.nbRessource == 0;}


    /**
     * Methode verifiant le contenu d'un navire
     * @param nomTonneau : nom du tonneau à chercher dans le navire
     * @return : true s'il contient le tonneau, false sinon
     */
    public boolean contientRessource(String nomTonneau){
        if (this.nbRessource > 0){
            return this.ressource[0].equals(nomTonneau);
        }
        return false;
    }


    /**
     * Methode permettant de rajouter des tonneaux au navire.
     * @param nomTonneau : nom du tonneau à rajouter.
     * @param nbreTonneau : nombre de tonneau à ajouter:
     */
    public void chargerTonneau(String nomTonneau, int nbreTonneau){
        for(int t = 0; t < nbreTonneau; t++){
            this.ressource[this.nbRessource] = nomTonneau;
            this.nbRessource++;
        }
    }


    /**
     * Methode permettant de rechercher le nombre de tonneau pouvant être chargé
     * selon l'espace restant et un nombre n de tonneaux.
     * @param nbTonneau : Nombre n de tonneau que l'on veut charger.
     * @return le nombre de tonneau pouvant être chargé.
     */
    public int nbTonneauACharger(int nbTonneau){
        if (this.estVide()){
            return Math.min(this.taille, nbTonneau);
        }

        else{
            return Math.min(this.getNbPlaceDisponible(), nbTonneau);
        }
    }


    /**
     * Methode permettant le transfert des ressources du navire vers la réserve.
     * @param reserve : La réserver principale.
     */
    public void tranfertVersReserve(Reserve reserve){
        String nomMarchandise = this.getNomRessource();
        for (int t = 0; t < this.getNbRessource(); t++){
            reserve.ajouterMarchandise(nomMarchandise, 1);
            this.ressource[t] = null;
        }
        this.nbRessource = 0;
    }
}
