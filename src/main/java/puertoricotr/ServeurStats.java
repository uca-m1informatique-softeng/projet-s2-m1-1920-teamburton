package puertoricotr;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import sun.security.krb5.internal.crypto.Aes128;

import java.io.*;
import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Classe permettant la génération et la lecture dans un fichier json des
 * statistiques du jeux
 */
public class ServeurStats {
    private int idPartie;
    private String chemin;
    private JSONObject jsonObject;
    private JSONArray partieArr;

    public ServeurStats() {
        this.idPartie = 0;

        DateFormat dateFormat = new SimpleDateFormat("_dd-MM-yyyy_HH-mm-ss");
        Date date = new Date();
        this.chemin = "Statistiques/partie".concat(dateFormat.format(date)).concat(".json");

        this.partieArr = new JSONArray();
        this.jsonObject = new JSONObject();
        jsonObject.put("partie", partieArr);
    }


    /***
     * Méthode crééer un tableau des statistiques des joueurs
     * @param partie pour récupérer les informations des joueurs
     * @param nbJoueursTotal nombre total des joueurs
     * @return un JSONArray des statistiques des joueurs.
     */
    private JSONArray insererStatsJoueurs(Partie partie, int nbJoueursTotal){

        Joueurs[] joueurs = partie.getJoueurs();
        JSONArray joueursArr = new JSONArray();

        // Insertion des stats du joueurs
        for (int j = 0; j < nbJoueursTotal; j++) {
            int nbPlantation = joueurs[j].getNbPlantationTotal() / this.idPartie;
            int nbCarriere = joueurs[j].getNbCarriereTotal() / this.idPartie;
            int nbBat = joueurs[j].getNbBatimentTotal() / this.idPartie;
            int nbColon = joueurs[j].getNbColonTotal() / this.idPartie;
            int nbTonneaux = joueurs[j].getNbTonneauxTotal() / this.idPartie;

            int nbDoublon = joueurs[j].getNbDoublonTotal() / this.idPartie;
            int nbPvBat = joueurs[j].getNbPVBatimentTotal() / this.idPartie;
            int nbPvChargement = joueurs[j].getNbPVChargementTotal() / this.idPartie;
            int nbPointBonusBat = joueurs[j].getNbPointsBonusBatimentsTotal() / this.idPartie;

            int nbPv = nbPvBat + nbPointBonusBat + nbPvChargement;
            int nbVictoire = joueurs[j].getNbVictoires();
            float prVictoire = ((float)(joueurs[j].getNbVictoires()) / this.idPartie) * 100;

            // Insertions dans un objet joueur
            JSONObject joueursObj = new JSONObject();
            joueursObj.put("nom", joueurs[j].getIdJoueur());
            joueursObj.put(Constantes.NBPLANTATIONS, nbPlantation);
            joueursObj.put(Constantes.NBCARRIERES, nbCarriere);
            joueursObj.put(Constantes.NBBATS, nbBat);
            joueursObj.put(Constantes.NBCOLONS, nbColon);
            joueursObj.put(Constantes.NBTONNEAUXTOT, nbTonneaux);
            joueursObj.put(Constantes.NBDOUBLONS, nbDoublon);

            joueursObj.put(Constantes.NBPVBAT, nbPvBat);
            joueursObj.put(Constantes.NBPVCHARGEMENT, nbPvChargement);
            joueursObj.put(Constantes.NBPVBONUSBAT, nbPointBonusBat);
            joueursObj.put(Constantes.NBPV, nbPv);
            joueursObj.put(Constantes.NBVICTOIRES, nbVictoire);
            joueursObj.put(Constantes.PRCTVICTOIRES, prVictoire);

            // Insertion dans le tableau de joueurs
            joueursArr.add(joueursObj);
        }

        return joueursArr;
    }

    /**
     * Méthode permettant l'insertion des stats d'une seule partie dans un fichier
     * json.
     * @param partie Partie en cours
     */
    public void insererStatsPartie(Partie partie) {
        idPartie++;
        int nbJoueursTotal = partie.getNbJoueurTotal();

        // Créations des élément à écrire dans le fichier json
        JSONObject partieObj = new JSONObject();
        partieObj.put("id", idPartie);
        partieObj.put(Constantes.NBJOUEURS, nbJoueursTotal);

        // Insertion du taleau de joueurs dans un objet partie
        partieObj.put("joueurs", insererStatsJoueurs(partie, nbJoueursTotal));

        // Insertion d'un objet partie dans le tableau des parties
        this.partieArr.add(partieObj);
    }


    /**
     * Méthode permettant l'insertion du résultats d'un ou de plusieurs parties dans
     * un fichier json.
     * @param partie Partie en cours.
     */
    public void insererResultats(Partie partie){
        int nbJoueursTotal = partie.getNbJoueurTotal();

        // Créations des élément à écrire dans le fichier json
        JSONObject resultatsObj = new JSONObject();
        resultatsObj.put("nbPartie", this.idPartie);
        resultatsObj.put(Constantes.NBJOUEURS, nbJoueursTotal);

        // Insertion du taleau de joueurs dans un objet partie
        resultatsObj.put("joueurs", insererStatsJoueurs(partie, nbJoueursTotal));

        // Insertion d'un objet partie dans le tableau des parties
        this.jsonObject.put("resultats", resultatsObj);

        // Création du fichier ou modification du fichier
        try (FileWriter file = new FileWriter(chemin)) {
            //file.write(jsonObject.toString(4));
            file.write(jsonObject.toJSONString());
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Affichage des résultats finaux.
     */
    public void afficherResultats(){

        JSONParser parser = new JSONParser();
        String[] clesStats = {Constantes.NBPV, Constantes.NBPVCHARGEMENT, Constantes.NBPVBAT,
                              Constantes.NBPVBONUSBAT, Constantes.NBDOUBLONS, Constantes.NBBATS,
                              Constantes.NBPLANTATIONS, Constantes.NBCARRIERES, Constantes.NBCOLONS,
                              Constantes.NBTONNEAUXTOT, Constantes.NBVICTOIRES, Constantes.PRCTVICTOIRES};

        try {
            Object obj = parser.parse(new FileReader(chemin));
            JSONObject jsonObjectu = (JSONObject) obj;

            JSONObject resulats = (JSONObject) jsonObjectu.get("resultats");
            JSONArray listeJoueurs = (JSONArray) resulats.get("joueurs");

            // Affichage infos partie
            System.out.print("\nNombre de joueurs : " + resulats.get(Constantes.NBJOUEURS) + "\n");
            System.out.print("Nombre de parties : " + resulats.get("nbPartie") + "\n\n");

            // Affichage noms joueurs
            System.out.printf("%36s", " ");
            StringBuilder trait = new StringBuilder("------------------------------");
            for (Object listeJoueur : listeJoueurs) {
                JSONObject joueurObj = (JSONObject) listeJoueur;
                System.out.printf("%20S", joueurObj.get("nom"));
                for(int t = 0; t < 10 + joueurObj.get("nom").toString().length(); t++){
                    trait.append("-");
                }
            }
            System.out.println("\n" + trait);

            int i = 0;
            int longueur = 0;
            // Affichages statistiques du jeu
            for (String nomStats : clesStats) {
                System.out.print(nomStats);
                String formattage;
                int j = 0;
                for (Object listeJoueur : listeJoueurs) {
                    JSONObject joueurObj = (JSONObject) listeJoueur;
                    String nomJ = joueurObj.get("nom").toString();

                    if (j == 0) {
                        int esp = 56 - (nomStats.length() + nomJ.length() / 2);
                        formattage = "%" + esp + "S";
                    }

                    else {
                        formattage = "%" + (longueur + 9 + nomJ.length() / 2) + "S";
                    }

                    System.out.printf(formattage, joueurObj.get(nomStats));
                    longueur = (nomJ.length() / 2);
                    j++;
                }
                i++;
                System.out.println((i % 5 == 0) ? "\n----------------------------------" : "" );
            }
            System.out.println(trait + "\n");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
