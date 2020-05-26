package puertoricotr;

import puertoricotr.batiments.Batiment;
import puertoricotr.exploitations.Exploitation;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe gerant le plateau du joueur contenant les emplacements pour les plantations et les puertorico.batiments (a venir)
 */
public class Plateau {
    private Exploitation[] ile;
    private Batiment[] cite;
    private int nbExploitation;
    private int nbPlantation;
    private int nbCarriere;

    private int nbBatiment;
    private int nbColonIle;
    private int nbColonCite;
    private int nbPlaceCite;
    private int nbColon;
    private int nbBatimentMauve;

    private Map<String, Integer> exploitationsOccupees;

    public Plateau(){
        this.ile = new Exploitation[12];
        this.cite = new Batiment[12];
        this.exploitationsOccupees = new HashMap<>();
        this.nbExploitation = 0;
        this.nbPlantation = 0;
        this.nbBatiment = 0;
        this.nbColonIle = 0;
        this.nbColonCite = 0;
        this.nbPlaceCite = 0;
        this.nbColon = 0;
        this.nbBatimentMauve = 0;
        this.nbCarriere = 0;
    }

    public Exploitation[] getIle() {
        return ile;
    }

    public Batiment[] getCite(){
        return cite;
    }

    public int getNbColon(){
        return this.nbColon;
    }

    public int getNbPlantation() {
        return this.nbPlantation;
    }

    public int getNbBatiment(){
        return nbBatiment;
    }

    public int getNbBatimentMauve() {
        return this.nbBatimentMauve;
    }

    public int getNbBatimentOccupe(String taille){
        int nbBatOccupe = 0;
        for (int b = 0; b < this.nbBatiment; b++){
            Batiment batiment = this.cite[b];
            if (batiment.getTaille().equals(taille) && batiment.estOccupe()){
                nbBatOccupe++;
            }
        }
        return nbBatOccupe;
    }

    public int getNbExploitation(){
        return nbExploitation;
    }

    public int getNbCarriere() {
        return nbCarriere;
    }

    public int getNbCarriereOccupee(){
        int nbCarrieresOccupees = 0;
        for (int c = 0; c < this.nbExploitation; c++){
            if (ile[c].getNom().equals(Constantes.CARRIERE) && ile[c].estOccupe()){
                nbCarrieresOccupees++;
            }
        }
        return nbCarrieresOccupees;
    }

    public int getNbColonIle(){
        return this.nbColonIle;
    }

    public int getNbColonCite(){
        return this.nbColonCite;
    }

    public int getNbColonTotal() {return this.nbColonIle + this.nbColonCite; }

    public int getNbPlaceCite() {
        return this.nbPlaceCite;
    }

    public Map<String, Integer> getExploitationsOccupees() {
        return exploitationsOccupees;
    }

    public String getAffichage(){
        StringBuilder feedback = new StringBuilder();
        String feed;
        feedback.append("BATIMENTS\t|");

        for(int b = 0; b < nbBatiment; b++){
            feedback.append((b == 4) ? "\n\t\t " : "");
            feedback.append(cite[b].getNom() + " {" + cite[b].getNbColon() + "}|");
        }

        feedback.append("\nEXPLOITATION\t|");
        for(int e = 0; e < nbExploitation; e++){
            feedback.append((e == 8) ? "\n\t\t " : "");
            if(ile[e].estOccupe()){
                feedback.append(ile[e].getNom() + " {1}|");
            }

            else{
                feedback.append(ile[e].getNom() + " {0}|");
            }
        }
        return feedback.toString();
    }

    public void setNbColonIle(int nbColonIle) {
        this.nbColonIle += nbColonIle;
    }

    public void setNbColonCite(int nbColonCite) {
        this.nbColonCite += nbColonCite;
    }

    public void setNbPlaceCite(int nbPlaceCite) {
        this.nbPlaceCite += nbPlaceCite;
    }

    public void setNbExploitation(int nbExploitation){
        this.nbExploitation = nbExploitation;
    }

    public void setNbBatiment(int nbBatiment){
        this.nbBatiment = nbBatiment;
    }

    public void setNbColon(int nbColon ){
        this.nbColon = nbColon;
    }

    public void addNbColon(int nbColon ){
        this.nbColon += nbColon;
    }

    public void subColon() {
        if (this.nbColon > 0) {
            this.nbColon--;
        }
    }

    public void addExploitation(Exploitation exploitation){
        if(nbExploitation < 12){
            ile[nbExploitation] = exploitation;
            this.nbExploitation++;
            this.nbPlantation++;
            if (exploitation.getNom().equals(Constantes.CARRIERE)){
                this.nbCarriere++;
            }
        }
    }

    public void addBatiment(Batiment batiment){
        if(nbBatiment < 12){
            cite[nbBatiment] = batiment;
            this.nbBatiment++;
            if (batiment.getCouleur().equals("Mauve")){
                this.nbBatimentMauve++;
            }
        }
    }

    /**
     * Méthode permettant de faire un histogramme des puertorico.exploitations occupées au sein d'une
     * hashmap (nom de l'exploitation, nombre d'exploitation du même nom occupée).
     * @param nomExploitation nom de l'exploitation à prendre en compte
     */
    public void addExploitationsOccupees(String nomExploitation) {
        // Recupère la valeur si existante, sinon 0
        int nbExploitationOccupee = this.exploitationsOccupees.containsKey(nomExploitation) ?
                exploitationsOccupees.get(nomExploitation) : 0;

        // Incrémentation de la valeur
        this.exploitationsOccupees.put(nomExploitation, nbExploitationOccupee + 1);
    }

    public boolean placeDispoCite(){
        return this.getNbColonCite() < this.getNbPlaceCite();
    }

    public boolean placeDispoIle(){
        return this.getNbColonIle() < this.getNbExploitation();
    }

    public boolean placeDispoPlateau(){ return this.placeDispoIle() || this.placeDispoCite(); }

    public boolean contientExploitation(String nomExploitation){
        for (int e = 0; e < this.nbExploitation; e++){
            if (ile[e].getNom().equals(nomExploitation)){
                return true;
            }
        }
        return false;
    }

    public boolean possedeExploitationOccupee(String nomPlantation){
        return this.exploitationsOccupees.containsKey(nomPlantation);
    }

    public boolean possedeGrandsBatimentsNonOccupe(){
        for (int b = 0; b < this.nbBatiment; b++){
            Batiment batiment = this.cite[b];
            if (nbColon > 0 && batiment.estGrand() && batiment.getNbColon() == 0){
                return true;
            }
        }
        return false;
    }


    /**
     *Méthode indiquant si un le joueur possède le bâtiment donné.
     * @param nomBatiment un bâtiment.
     * @return true si le joueur le possède, false sinon.
     */
    public boolean possedeBatiment(String nomBatiment){
        for (int b = 0; b < this.nbBatiment; b++){
            if (this.cite[b].getNom().equals(nomBatiment)){
                return true;
            }
        }
        return false;
    }


    public boolean possedeBatimentOccupe(String nomBatiment){
        for (int b = 0; b < this.nbBatiment; b++){
            if (this.cite[b].getNom().equals(nomBatiment) && this.cite[b].getNbColon() > 0){
                return true;
            }
        }

        return false;
    }


    public boolean exploitationOccupee(){
        for (int e = 0; e < this.nbExploitation; e++){
            if (ile[e].estOccupe()){
                return true;
            }
        }

        return false;
    }


    public boolean peutProduire(){
        for (int e = 0; e < this.getNbExploitation(); e++){
            if (ile[e].estOccupe()){
                if (ile[e].getNom().equals(Constantes.MAIS)){
                    return true;
                }

                for (int b = 0; b < this.getNbBatiment(); b++){
                    if (cite[b].getNomPlantation().equals(ile[e].getNom()) && cite[b].getNbColon() > 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean possedeExploitation(){
        return this.nbExploitation > 0;
    }
}
