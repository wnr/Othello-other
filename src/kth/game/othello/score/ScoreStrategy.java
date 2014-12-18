package kth.game.othello.score;

import java.util.List;

public interface ScoreStrategy {


    public List<String> updateScores(List<ScoreItem> scores, Object args);

}
