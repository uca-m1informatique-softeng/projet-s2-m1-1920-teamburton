package puertoricotr;


/**
 * Classe permettant de lancer le jeu.
 */
public class Jeux {

    public Jeux(){
        ServeurStats serveurStats = new ServeurStats();

        Partie partie = new Partie(0, 6, serveurStats, Constantes.SAMBITIEUSE);

        new MoteurDuJeux(partie, 666);
    }

    public static void main(String[] args) {
        new Jeux();
    }
}
