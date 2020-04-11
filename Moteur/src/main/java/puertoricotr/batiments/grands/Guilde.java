package puertoricotr.batiments.grands;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class Guilde extends Batiment {
    public Guilde() {
        this.nom = Constantes.GUILDE;
        this.nomPlantation = "";
        this.taille = "Grand";
        this.couleur = "Mauve";
        this.prix = 10;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 4;
        this.pointsVicoires = 4;

    }
}
