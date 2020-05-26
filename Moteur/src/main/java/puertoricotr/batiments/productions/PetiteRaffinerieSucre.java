package puertoricotr.batiments.productions;
import puertoricotr.Constantes;
import puertoricotr.batiments.Batiment;

public class PetiteRaffinerieSucre extends Batiment {
    public PetiteRaffinerieSucre() {
        this.nom = "Petite raffinerie de sucre";
        this.nomPlantation = Constantes.SUCRE;
        this.taille = "Petite";
        this.couleur = "";
        this.prix = 1;
        this.nbColon = 0;
        this.nbColonLimite = 1;
        this.categorie = 1;
        this.pointsVicoires = 1;
    }
}
