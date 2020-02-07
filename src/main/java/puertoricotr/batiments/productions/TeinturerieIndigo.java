package puertoricotr.batiments.productions;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class TeinturerieIndigo extends Batiment {
    public TeinturerieIndigo(){
        this.nom = "Teinturerie d'indigo";
        this.nomPlantation = Constantes.INDIGO;
        this.taille = "Moyen";
        this.couleur = "";
        this.prix = 3;
        this.nbColon = 0;
        this.nbColonLimite = 3;
        this.categorie = 2;
        this.pointsVicoires = 2;
    }
}
