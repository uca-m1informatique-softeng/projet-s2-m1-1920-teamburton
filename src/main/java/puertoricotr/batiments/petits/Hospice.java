package puertoricotr.batiments.petits;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class Hospice extends Batiment {
    public Hospice() {
        this.nom = Constantes.HOSPICE;
        this.nomPlantation = "";
        this.taille = "Petit";
        this.couleur = "Mauve";
        this.prix = 4;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 2;
        this.pointsVicoires = 2;
    }
}
