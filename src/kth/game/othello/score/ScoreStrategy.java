package kth.game.othello.score;

import java.util.List;
import java.util.Observable;

public interface ScoreStrategy {


    public List<String> updateScores(List<ScoreItem> scores, Observable observable, Object args);

}
