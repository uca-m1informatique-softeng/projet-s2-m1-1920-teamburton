package puertoricotr.batiments.petits;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class Comptoire extends Batiment {
    public Comptoire() {
        this.nom = Constantes.COMPTOIRE;
        this.nomPlantation = "";
        this.taille = "Petit";
        this.couleur = "Mauve";
        this.prix = 5;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 2;
        this.pointsVicoires = 1;
    }
}
