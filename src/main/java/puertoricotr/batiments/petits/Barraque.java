package puertoricotr.batiments.petits;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class Barraque extends Batiment {
    public Barraque() {
        this.nom = Constantes.BARRAQUE;
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
