package puertoricotr;

import java.util.ArrayList;

public class ListeScoreDTO {

    public ListeScoreDTO(Partie partie) {

        ArrayList<ScoresDTO> scoresDTO = new ArrayList<>();

        int nbJoueursTotal = partie.getNbJoueurTotal();
        Joueurs[] joueurs = partie.getJoueurs();

        // Scores de chaque joueurs
        for (int j = 0; j < nbJoueursTotal; j++) {
            scoresDTO.add(new ScoresDTO(joueurs[j]));
        }
    }

}
