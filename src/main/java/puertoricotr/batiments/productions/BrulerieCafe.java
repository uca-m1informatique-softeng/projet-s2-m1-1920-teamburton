package puertoricotr.batiments.productions;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class BrulerieCafe extends Batiment {
    public BrulerieCafe() {
        this.nom = "Brulerie de cafe";
        this.nomPlantation = Constantes.CAFE;
        this.taille = "Moyen";
        this.couleur = "";
        this.prix = 6;
        this.nbColon = 0;
        this.nbColonLimite = 3;
        this.categorie = 3;
        this.pointsVicoires = 3;
    }
}
