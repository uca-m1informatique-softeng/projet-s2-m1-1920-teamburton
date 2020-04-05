package puertoricotr.serveurstats;

import puertoricotr.IntelligenceArtificielle;

public class Joueur {
    private String nom;
    private int nbVictoire;
    private int nbBatConstuits;
    private int nbTonneaux;
    private int pv;
    private int doublons;
    private int pvBonusBatiments;
    private int nbColons;
    private int nbCarrieres;
    private int nbPlantations;
    private int pvBatiments;
    private int pvChargements;
    private float pourcentageVictoires;

    public Joueur(String nom){
        nom = nom;
        nbVictoire=0;
        nbBatConstuits=0;
        nbTonneaux=0;
        pv=0;
        doublons=0;
        pvBonusBatiments=0;
        nbColons=0;
        nbCarrieres=0;
        nbPlantations=0;
        pvBatiments=0;
        pvChargements=0;
        pourcentageVictoires=0;
    }

}
