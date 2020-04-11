package puertoricotr.stockageoutilsjeux;

import puertoricotr.Constantes;
/**
 * Classe définissant les propriétés du magasin.
 */
public class Magasin {
    private String[] marchandises;
    private int nbTonneau;

    public Magasin(){
        this.marchandises = new String[4];
    }

    public int getPrix(String tonneau){
        int prix = -1;

        switch (tonneau){
            case Constantes.MAIS:
                prix = 0;
                break;

            case Constantes.INDIGO:
                prix = 1;
                break;

            case Constantes.SUCRE:
                prix = 2;
                break;

            case Constantes.TABAC:
                prix = 3;
                break;

            case Constantes.CAFE:
                prix = 4;
                break;

            default:
                break;
        }
        return prix;
    }

    public String[] getMarchandises(){
        return marchandises;
    }

    public int getNbTonneau(){
        return this.nbTonneau;
    }

    public String getAffichage(){
        StringBuilder feedback = new StringBuilder();
        feedback.append("Magasin \t\t: \\");

        for(int t = 0; t < nbTonneau; t++){
            feedback.append(this.marchandises[t] + "\\");
        }

        return feedback.toString();
    }

    public boolean estPlein(){
        return (this.nbTonneau == 4);
    }

    public boolean possedeMarchndise(String tonneau){
        for (int t = 0; t < this.nbTonneau; t++){
            if (marchandises[t].equals(tonneau)){
                return true;
            }
        }

        return false;
    }

    public void ajouterMarchandises(String tonneau){
        this.marchandises[nbTonneau] = tonneau;
        this.nbTonneau++;
    }

    /**
     * Vide une fois remplie, les tonneaux du magasin dans la réserve
     * @param reserve Réserve de marchandises.
     */
    public void tranfertVersReserve(Reserve reserve){
        String nomMarchandise;
        for (int t = 0; t < this.getNbTonneau(); t++){
            nomMarchandise = marchandises[t];
            reserve.ajouterMarchandise(nomMarchandise, 1);
            this.marchandises[t] = null;
        }
        this.nbTonneau = 0;
    }
}