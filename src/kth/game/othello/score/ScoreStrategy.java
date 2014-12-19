package kth.game.othello.score;

import java.util.List;
import java.util.Observable;

/**
 * Is responsible for changing the scores of players on different events.
 */
public interface ScoreStrategy {

    /**
     * Updates the score of players given the event.
     *
     * @param scores The scores to be updated.
     * @param observable The observable that emitted the event.
     * @param args The data that the event has.
     * @return A list of player id's that had their score changed.
     */
    public List<String> updateScores(List<ScoreItem> scores, Observable observable, Object args);

}
