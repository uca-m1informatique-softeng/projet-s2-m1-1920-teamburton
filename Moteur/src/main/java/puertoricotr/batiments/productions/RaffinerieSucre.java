package puertoricotr.batiments.productions;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class RaffinerieSucre extends Batiment {
    public RaffinerieSucre(){
        this.nom = "Raffinerie de sucre";
        this.nomPlantation = Constantes.SUCRE;
        this.taille = "Moyen";
        this.couleur = "";
        this.prix = 4;
        this.nbColon = 0;
        this.nbColonLimite = 3;
        this.categorie = 2;
        this.pointsVicoires = 2;
    }
}
