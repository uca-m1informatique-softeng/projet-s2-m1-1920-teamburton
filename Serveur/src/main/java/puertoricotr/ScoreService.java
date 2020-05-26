package puertoricotr;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;


@Component
public class ScoreService {

    ArrayList <String> listeVainqueurs;

    public ScoreService() {
        this.listeVainqueurs = new ArrayList();
    }

    public void addScore(String j) {
        listeVainqueurs.add(j);
    }

    public ArrayList getScores() {
        return listeVainqueurs;
    }

    public String getScore(int i) { return listeVainqueurs.get(i); }
}
