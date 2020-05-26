package puertoricotr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("score/v1")
public class ScoreController {

    private static final Logger log = LoggerFactory.getLogger(ScoreController.class);

    private ScoreService scoreService;

    public ScoreController(ScoreService scoreService) { this.scoreService = scoreService; }

    @PostMapping(value = "/statistiques/save", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void addScore(@RequestBody String vainqueur) {
        scoreService.addScore(vainqueur);
    }

    @GetMapping(value = "/statistiques", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ArrayList getScores() {
        return scoreService.getScores();
    }

    @GetMapping(value = "/statistiques/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getScores(@PathVariable("id") final int i) {
        return scoreService.getScore(i);
    }
}
