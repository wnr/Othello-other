package kth.game.othello.score;

import java.util.ArrayList;
import java.util.List;

public class OthelloScoreStrategy implements ScoreStrategy {

    @Override
    public List<String> updateScores(List<ScoreItem> scores, Object args) {
        List<String> changes = (List<String>) args;
        String prevPlayerId = changes.get(0);
        String nextPlayerId = changes.get(1);

        List<String> updatedScores = new ArrayList<String>();
        for (int i = 0; i <  scores.size(); i++) {
            ScoreItem item = scores.get(i);
            if (item.getPlayerId().equals(prevPlayerId) && item.getScore() > 0) {
                scores.set(i, new ScoreItem(item.getPlayerId(), item.getScore() - 1));
                updatedScores.add(prevPlayerId);
            } else if (item.getPlayerId().equals(nextPlayerId)) {
                scores.set(i, new ScoreItem(item.getPlayerId(), item.getScore() + 1));
                updatedScores.add(nextPlayerId);
            }
        }
        return updatedScores;
    }

}
