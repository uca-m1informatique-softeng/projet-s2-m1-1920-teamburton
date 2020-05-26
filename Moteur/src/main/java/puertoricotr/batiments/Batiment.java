package puertoricotr.batiments;

/**
 * Classe abstraite définissant les propriétés d'un bâtiment.
 */
public abstract class Batiment {

    protected String nom;
    protected String nomPlantation;
    protected String taille;
    protected String couleur;
    protected int prix;
    protected int nbColon;
    protected int nbColonLimite;
    protected int categorie;
    protected int pointsVicoires;

    public String getNom(){
        return this.nom;
    }

    public String getNomPlantation() {
        return this.nomPlantation;
    }

    public String getTaille() {
        return this.taille;
    }

    public String getCouleur() { return this.couleur; }

    public int getPrix(){
        return this.prix;
    }

    public int getNbColon(){
        return nbColon;
    }

    public int getNbColonLimite() {
        return nbColonLimite;
    }

    public int getCategorie(){
        return categorie;
    }

    public int getPointsVicoires(){
        return pointsVicoires;
    }

    public void setNbColon(int nbColon){
        this.nbColon = nbColon;
    }

    public void setNbColonLimite(int nbColonLimite) {
        this.nbColonLimite = nbColonLimite;
    }

    public void setPointsVicoires(int pointsVicoires) {
        this.pointsVicoires = pointsVicoires;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public void addColon(){
        this.nbColon++;
    }

    public boolean estOccupe(){
        return this.nbColon > 0;
    }

    public boolean estGrand() { return this.taille.equals("Grand");}
}