package puertoricotr;


/**
 * Classe permettant de lancer le jeu.
 */
public class Jeux {

    public Jeux(){
        ServeurStats serveurStats = new ServeurStats();

        Partie partie = new Partie(0, 2, serveurStats);

        new MoteurDuJeux(partie, 1);
    }

    public static void main(String[] args) {
        new Jeux();
    }
}
