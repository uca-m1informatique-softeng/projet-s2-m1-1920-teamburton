package puertoricotr.personnages;

import puertoricotr.Constantes;
import puertoricotr.Joueurs;
import puertoricotr.Partie;
import puertoricotr.stockageoutilsjeux.Banque;
import puertoricotr.stockageoutilsjeux.Navires;

import java.util.*;

public class Capitaine extends Personnage {

    public Capitaine() {
        this.nom = Constantes.CAPITAINE;
		this.doublon = 0;
    }

    /**
     * Méthodes determinant les tonneaux d'un joueurs pouvant être chargés sur le navire choisi
     * @param tonneauxJoueur : Tonneaux du joueurs.
     * @param navires : Arraylist des navires.
     * @param navire : Navire choisi.
     * @return une hashmap des choix de tonneaux possibles.
     */
    private Map<String, Integer> tonneauxDisponibles(Map<String, Integer> tonneauxJoueur, ArrayList<Navires> navires, Navires navire){

        ArrayList<String> listeTonneaux = new ArrayList<>();
        Map<String, Integer> tonneauxDispo = new HashMap<>();
        int ind = navires.indexOf(navire);

        // Liste de tonneaux présents dans tous les navires sauf le navire choisi
        for (int n = 0; n < navires.size(); n++){
            if (!navires.get(n).estVide() && n != ind){
                listeTonneaux.add(navires.get(n).getNomRessource());
            }
        }

        // Création d'une hashmap pour les noms et nombres de tonneaux disponibles
        for (String nomTonneau: listeTonneaux){
            for (Map.Entry<String, Integer> t: tonneauxJoueur.entrySet()){
                if (!nomTonneau.equals(t.getKey())){
                    tonneauxDispo.put(t.getKey(), t.getValue());
                }
            }
        }

        return (tonneauxDispo.size() ==  0) ? tonneauxJoueur : tonneauxDispo;
    }
    /**
     * Méthode listant les ressources dèjà présentes dans les navires.
     * @param navires arrylist de navire contenant les ressources.
     * @return une arrayliste contenant les noms de ressource.
     */
    private ArrayList<String> listeRessource(ArrayList<Navires> navires) {
        ArrayList<String> ressourceDansNavires = new ArrayList<>();
        for (Navires n : navires) {
            if (!n.estComplet() && !n.estVide()){
                ressourceDansNavires.add(n.getNomRessource());
            }
        }
        return ressourceDansNavires;
    }

    /**
     * Methode recherchant les navires pouvant être chargé par un type de tonneau.
     * @param navires arraylist des navires permettant le stockage des marchandises.
     * @param tonneaux nom du tonneau que le joueurs veut ajouter.
     * @return une arraylit des navires pouvant être chargés.
     */
    private ArrayList<Navires> naviresDisponibles(ArrayList<Navires> navires, Map<String, Integer> tonneaux){

        Set<Navires> naviresDisponibles = new HashSet<>();

        // Liste des ressources présent dans les navires.
        ArrayList<String> ressourceDansNavires = listeRessource(navires);
        for (Navires n : navires){
            for (String nomtonneau: tonneaux.keySet()) {
                if (!n.estComplet() && n.contientRessource(nomtonneau)) {
                    naviresDisponibles.add(n);
                }

                else if (n.estVide() && !ressourceDansNavires.contains(nomtonneau)) {
                    naviresDisponibles.add(n);
                }
            }
        }

        return new ArrayList<>(naviresDisponibles);
    }


    @Override
    public String action(Joueurs[] joueurs, int j, Partie partie, int tour) {

        ArrayList<Navires> navires = partie.getNavires();
        Banque banque = partie.getBanque();
        StringBuilder feedback = new StringBuilder();
        String tonneauChoisi;
        int nbTonneauACharger;

        Navires navireACharger;
        ArrayList<Navires> naviresDispo;

        int nbJ = 0;
        while(nbJ < joueurs.length){
            if (joueurs[j].getNbTonneauxActuel() == 0) {
                feedback.append("\n<" + joueurs[j].getIdJoueur() + "> ne possède aucun tonneaux et "
                                      + "il ne peut rien charger.\n");
            }

            else{
                Map<String, Integer> tonneauxJoueur = joueurs[j].getTonneauxProduits();
                naviresDispo = naviresDisponibles(navires, tonneauxJoueur);

                if (!naviresDispo.isEmpty()){
                    // Choix du navire
                    navireACharger = joueurs[j].choixNavire(partie, naviresDispo, tonneauxJoueur);
                    feedback.append("\n<" + joueurs[j].getIdJoueur() + "> a choisi le "
                                          + navireACharger.getNomNavire()) ;

                    // Choix du tonneau
                    if (!navireACharger.estVide()){
                        // Automatique, car possède dàjà ressource
                        tonneauChoisi = navireACharger.getNomRessource();
                    }

                    else {
                        // Choix non automatique (navire vide)
                        Map<String, Integer> tonneauxDispo = tonneauxDisponibles(tonneauxJoueur, navires, navireACharger);
                        tonneauChoisi = joueurs[j].choixTonneau(partie, this.nom, tonneauxDispo, navireACharger);
                    }

                    feedback.append("\n<" + joueurs[j].getIdJoueur()
                                          + "> a choisi de charger le " + tonneauChoisi);

                    // Chargement du navire
                    nbTonneauACharger = Math.min(tonneauxJoueur.get(tonneauChoisi), navireACharger.getNbPlaceDisponible());
                    navireACharger.chargerTonneau(tonneauChoisi, nbTonneauACharger);

                    joueurs[j].subTonneau(tonneauChoisi, nbTonneauACharger);
                    joueurs[j].addPointVictoire(banque.decrementeNbPointVictoire(nbTonneauACharger));

                    feedback.append("\n<" + joueurs[j].getIdJoueur() + "> charge " + nbTonneauACharger
                                          + " tonneau(x) de " + tonneauChoisi + " sur le navire à "
                                          + navireACharger.getTaille() + " places.\n");

                    // 1 PV par chargement s'il le joueurs possède un port
                    if (joueurs[j].getPlateau().possedeBatimentOccupe(Constantes.PORT)){
                        banque.decrementeNbPointVictoire(1);
                        joueurs[j].addPointVictoire(1);
                        feedback.append("<" + joueurs[j].getIdJoueur() + "> possede un Port et "
                                            + " reçoit 1 point de victoire supplémentaire.\n");
                    }

                    // Privilège du Capitaine
                    if (nbJ == 0){
                        banque.decrementeNbPointVictoire(1);
                        joueurs[j].addPointVictoire(1);
                        feedback.append("<" + joueurs[j].getIdJoueur() + "> a le privilège du "
                                            + "Capitaine et reçoit 1 point de victoire supplémentaire.\n");
                    }
                }

                else{
                    feedback.append("\n<" + joueurs[j].getIdJoueur() + "> ne peut pas charger de "
                                          + "tonneau sur les navires.\n");
                }
            }
            nbJ++;
            j = (j + 1) % joueurs.length;
        }

        // Transfert tonneaux si navires plein
        for (Navires n: navires){
            if (n.estComplet()){
                n.tranfertVersReserve(partie.getReserve());
                feedback.append("\nLes marchandises du " + n.getNomNavire()
                                                         + " ont été transférées vers la reserve .....");
            }
        }

        return feedback.toString();
    }
}