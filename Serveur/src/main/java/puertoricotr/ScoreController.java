package puertoricotr;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("score/v1")
public class ScoreController {

    private static final Logger log = LoggerFactory.getLogger(ScoreController.class);

    private ScoreService scoreService;

    public ScoreController(ScoreService scoreService) { this.scoreService = scoreService; }

    @PostMapping(value = "/statistiques/save", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void addScore(@RequestBody JSONObject json) {
        scoreService.addScore(json);
    }

    @GetMapping(value = "/statistiques", produces = {MediaType.APPLICATION_JSON_VALUE})
    public JSONArray getScores() {
        return scoreService.getScores();
    }

    @GetMapping(value = "/statistiques/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object getScores(@PathVariable("id") final int i) {
        return scoreService.getScore(i-1);
    }
}
