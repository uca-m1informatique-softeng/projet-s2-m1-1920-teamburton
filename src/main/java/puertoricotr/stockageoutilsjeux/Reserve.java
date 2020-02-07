package puertoricotr.stockageoutilsjeux;

import puertoricotr.Constantes;

import java.util.HashMap;
import java.util.Map;

public class Reserve {

    private Map<String, Integer> marchandises;

    public Reserve(int nbMais, int nbIndigo, int nbSucre, int nbTabac, int nbCafe){
        this.marchandises = new HashMap<>();

        this.marchandises.put(Constantes.MAIS, nbMais);
        this.marchandises.put(Constantes.INDIGO, nbIndigo);
        this.marchandises.put(Constantes.SUCRE, nbSucre);
        this.marchandises.put(Constantes.TABAC, nbTabac);
        this.marchandises.put(Constantes.CAFE, nbCafe);
    }

    public Map<String, Integer> getMarchandises(){
        return this.marchandises;
    }

    public void setNbMarchandise(String nomMarchandise, int nbMarchandise){
        this.marchandises.put(nomMarchandise,nbMarchandise);
    }

    public int getNbMarchandise(String nomMarchandise){
        int nbMarchandise = -1;
        if (this.marchandises.containsKey(nomMarchandise)){
            nbMarchandise = this.marchandises.get(nomMarchandise);
        }

        return nbMarchandise;
    }

    public String getAffichage() {
        StringBuilder feedback = new StringBuilder();
        feedback.append("Réserve \t\t: ");

        int nbMarchandise;
        String nomMarchandise;

        for (HashMap.Entry<String, Integer> m : this.marchandises.entrySet()) {
            nbMarchandise = this.marchandises.get(m.getKey());
            nomMarchandise = m.getKey();
            feedback.append(nomMarchandise + " : " + nbMarchandise + "\t");
        }

        return feedback.toString() + "\n";
    }

    public void ajouterMarchandise(String nomMarchandise, int nbMarchandise){
        this.marchandises.put(nomMarchandise, this.getNbMarchandise(nomMarchandise) + nbMarchandise);
}
    public void prendreMarchandise(String nomMarchandise, int nbMarchandise){
        this.marchandises.put(nomMarchandise, this.getNbMarchandise(nomMarchandise) - nbMarchandise);
    }
}