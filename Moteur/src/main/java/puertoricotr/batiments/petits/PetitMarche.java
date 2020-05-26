package puertoricotr.batiments.petits;

import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class PetitMarche extends Batiment {
    public PetitMarche() {
        this.nom = Constantes.PETIT_MARCHE;
        this.nomPlantation = "";
        this.taille = "Petit";
        this.couleur = "Mauve";
        this.prix = 1;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 1;
        this.pointsVicoires = 1;
    }
}
