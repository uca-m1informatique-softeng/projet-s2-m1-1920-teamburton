package puertoricotr.batiments.productions;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class PetiteTeinturerieIndigo extends Batiment {
    public PetiteTeinturerieIndigo() {
        this.nom = "Petite teinturerie d'indigo";
        this.nomPlantation = Constantes.INDIGO;
        this.taille = "Petite";
        this.couleur = "";
        this.prix = 1;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 1;
        this.pointsVicoires = 1;
    }
}
