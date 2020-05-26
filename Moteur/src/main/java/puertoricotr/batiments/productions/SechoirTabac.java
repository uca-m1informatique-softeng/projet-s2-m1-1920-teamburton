package puertoricotr.batiments.productions;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class SechoirTabac extends Batiment {
    public SechoirTabac(){
        this.nom = "Sechoir a tabac";
        this.nomPlantation = Constantes.TABAC;
        this.taille = "Moyen";
        this.couleur = "";
        this.prix = 5;
        this.nbColon = 0;
        this.nbColonLimite = 3;
        this.categorie = 3;
        this.pointsVicoires = 3;
    }
}
