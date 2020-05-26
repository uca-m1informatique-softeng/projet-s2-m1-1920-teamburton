package puertoricotr.batiments.petits;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class Port extends Batiment {
    public Port() {
        this.nom = Constantes.PORT;
        this.nomPlantation = "";
        this.taille = "Petit";
        this.couleur = "Mauve";
        this.prix = 8;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 3;
        this.pointsVicoires = 3;
    }
}
