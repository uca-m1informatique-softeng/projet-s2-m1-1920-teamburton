package puertoricotr;

public class ScoresDTO {
    int nbPlantation;
    int nbCarriere;
    int nbBat;
    int nbColon;
    int nbTonneaux;
    int nbDoublon;
    int nbPvBat;
    int nbPvChargement;
    int nbPointBonusBat;
    int nbPv;
    int nbVictoire;


    public ScoresDTO(Joueurs joueur){

        this.nbPlantation = joueur.getNbPlantationTotal();
        this.nbCarriere = joueur.getNbCarriereTotal();
        this.nbBat = joueur.getNbBatimentTotal();
        this.nbColon = joueur.getNbColonTotal();
        this.nbTonneaux = joueur.getNbTonneauxTotal();

        this.nbDoublon = joueur.getNbDoublonTotal();
        this.nbPvBat = joueur.getNbPVBatimentTotal();
        this.nbPvChargement = joueur.getNbPVChargementTotal();
        this.nbPointBonusBat = joueur.getNbPointsBonusBatimentsTotal();

        this.nbPv = nbPvBat + nbPointBonusBat + nbPvChargement;
        this.nbVictoire = joueur.getNbVictoires();
    }
}
