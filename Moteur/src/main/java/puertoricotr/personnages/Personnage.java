package puertoricotr.personnages;

import puertoricotr.Joueurs;
import puertoricotr.Partie;

/**
 * Classe abstraite dont herite les different personnage que l'on peux choisir
 */
public abstract class Personnage {

    protected String nom;
    protected int doublon;

    public String getNom() {
        return nom;
    }
    public int getDoublon(){
        return this.doublon;
    }
    public void setNom(String nom){
        this.nom = nom;
    }

    /**
     * Ajoute un doublon au personnage
     */
    public void addDoublon(){
        this.doublon += 1;
    }

    /**
     * retourne les doublon accumulé par le personnage et remet le compteur a zero.
     * @return int nombre de doublon
     */
    public int recupereDoublon(){
        int d = this.doublon;
        this.doublon = 0;
        return d;
    }

    /**
     * realise l'action du personnage
     * @param joueur joueur pour lequel on réalise l'action
     */
    public abstract String action(Joueurs[] joueur, int j, Partie partie, int tour);

}