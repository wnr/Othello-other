package kth.tournament;

import kth.game.othello.score.ScoreItem;
import kth.game.othello.score.ScoreStrategy;

import java.util.ArrayList;
import java.util.List;

public class TournamentScoreStrategy implements ScoreStrategy {

    public List<String> updateScores(List<ScoreItem> scores, Object args) {
        String winner = (String) args;

        List<String> updatedScores = new ArrayList<String>();
        for (int i = 0; i <  scores.size(); i++) {
            ScoreItem item = scores.get(i);
            if (item.getPlayerId().equals(winner)) {
                scores.set(i, new ScoreItem(item.getPlayerId(), item.getScore() + 1));
                updatedScores.add(winner);
            }
        }
        return updatedScores;
    }

}
