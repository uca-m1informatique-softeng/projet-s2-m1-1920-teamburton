package puertoricotr;

import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class ScoreService {

    ArrayList <String> listeVainqueurs;

    public ScoreService() {
        this.listeVainqueurs = new ArrayList<>();
    }

    public void addScore(String j) {
        System.out.println(j);
        listeVainqueurs.add(j);
    }

    public ArrayList <String> getScores() {
        return listeVainqueurs;
    }

    public String getScore(int i) { return listeVainqueurs.get(i - 1); }
}