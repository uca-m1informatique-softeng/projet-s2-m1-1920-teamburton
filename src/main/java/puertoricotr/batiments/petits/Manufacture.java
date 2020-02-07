package puertoricotr.batiments.petits;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class Manufacture extends Batiment {
    public Manufacture() {
        this.nom = Constantes.MANUFACTURE;
        this.nomPlantation = "";
        this.taille = "Petit";
        this.couleur = "Mauve";
        this.prix = 7;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 3;
        this.pointsVicoires = 3;
    }
}
