package kth.tournament.match;

import kth.game.OthelloGame;
import kth.game.othello.score.Score;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * The responsibility of this class is to control the match of competitors in a Othello tournament.
 *
 * @author Mattias Harrysson
 */
public class OthelloMatch extends Observable implements Match, Observer {

	private OthelloGame othelloGame;
	private String startingPlayerId;

	/**
	 * @param othelloGame the othello game to control the game loop and present the game
	 * @param startingPlayerId the player id of the player to start moving
	 */
	public OthelloMatch(OthelloGame othelloGame, String startingPlayerId) {
		this.othelloGame = othelloGame;
		this.othelloGame.addObserver(this);
		this.startingPlayerId = startingPlayerId;
	}

	@Override
	public void start() {
		othelloGame.start(startingPlayerId);
	}

	@Override
	public void update(Observable observable, Object object) {
		Score score = (Score) object;
		String winningPlayerId = score.getPlayersScore().get(0).getPlayerId();
		setChanged();
		notifyObservers(Arrays.asList(null, winningPlayerId));
	}

}
