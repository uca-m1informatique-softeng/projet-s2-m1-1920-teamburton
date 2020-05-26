package puertoricotr;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;


@Component
public class ScoreService {

    JSONArray jsonArray;

    public ScoreService() {
        this.jsonArray = new JSONArray();
    }

    public void addScore(JSONObject j) {
        jsonArray.add(j);
    }

    public JSONArray getScores() {
        return jsonArray;
    }

    public Object getScore(int i) { return jsonArray.get(i); }
}
