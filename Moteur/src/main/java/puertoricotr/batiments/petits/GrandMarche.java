package puertoricotr.batiments.petits;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class GrandMarche extends Batiment {
    public GrandMarche() {
        this.nom = Constantes.GRAND_MARCHE;
        this.nomPlantation = "";
        this.taille = "Petit";
        this.couleur = "Mauve";
        this.prix = 5;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 2;
        this.pointsVicoires = 2;
    }
}
