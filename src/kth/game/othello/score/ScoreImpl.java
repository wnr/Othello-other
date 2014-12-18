package kth.game.othello.score;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Collections;

/**
 * The responsibility of this class is to control the score for the players in a Othello game. It will notify all
 * observers when a score is changed.
 *
 * @author Mattias Harrysson
 */
public class ScoreImpl extends Observable implements Score, Observer {

	private List<ScoreItem> scores;
	private ScoreStrategy scoreStrategy;

	/**
	 * @param scores the list of score items to track
	 */
	public ScoreImpl(List<ScoreItem> scores, ScoreStrategy scoreStrategy) {
		this.scores = scores;
		this.scoreStrategy = scoreStrategy;
		updateOrder();
	}

	public void setScoreStrategy(ScoreStrategy scoreStrategy) {
		this.scoreStrategy = scoreStrategy;
	}

	@Override
	public List<ScoreItem> getPlayersScore() {
		return scores;
	}

	@Override
	public int getPoints(String playerId) {
		for (ScoreItem item : scores) {
			if (item.getPlayerId().equals(playerId)) {
				return item.getScore();
			}
		}
		return 0;
	}

	@Override
	public void update(Observable observable, Object object) {
		List<String> changedScores = scoreStrategy.updateScores(scores, observable, object);
		if (!changedScores.isEmpty()) {
			setChanged();
		}
		notifyObservers(changedScores);
		updateOrder();
	}

	/**
	 * Sorts score list in descending order.
	 */
	private void updateOrder() {
		Collections.sort(scores, Collections.reverseOrder());
	}

}
