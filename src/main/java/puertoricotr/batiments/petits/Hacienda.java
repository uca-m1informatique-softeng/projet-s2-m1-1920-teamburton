package puertoricotr.batiments.petits;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class Hacienda extends Batiment {
    public Hacienda() {
        this.nom = Constantes.HACIENDA;
        this.nomPlantation = "";
        this.taille = "Petit";
        this.couleur = "Mauve";
        this.prix = 2;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 1;
        this.pointsVicoires = 1;
    }
}
