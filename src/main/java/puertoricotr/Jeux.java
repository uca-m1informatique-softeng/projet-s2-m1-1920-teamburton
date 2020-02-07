package puertoricotr;

/*
import java.io.*;
public class Jeux {
    public Jeux(){ new MoteurDuJeux(1,1, 1); }
    public static void main(String[] args) throws FileNotFoundException
    {
        new Jeux();
        /*PrintStream fichier = new PrintStream(new File("A.txt"));
        System.setOut(fichier );
        int n = 20;
        for (int i = 0; i < n; i++) {
            new Jeux();
        }}
        }

 */
/**
 * Classe permettant de lancer le jeu.
 */
public class Jeux {
    public Jeux(){ new MoteurDuJeux(0,2, 1); }
    public static void main(String[] args)
    {
        new Jeux();
    }
}
