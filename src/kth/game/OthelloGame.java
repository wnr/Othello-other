package kth.game;

import java.util.Observer;

/**
 * The responsibility of this class is to run a Othello game from start to finish. It must inform all observers when
 * the game has finished, it will include the score.
 *
 * @author Mattias Harrysson
 */
public interface OthelloGame {

	/**
	 * Adds an observer. The observer will be called when the game has finished with the included score.
	 *
	 * @param observer the observer
	 */
	public void addObserver(Observer observer);

	/**
	 * Start a Othello game with random starting player.
	 */
	public void start();

	/**
	 * Start a Othello game with a specified starting player.
	 *
	 * @param playerId the starting player id
	 */
	public void start(String playerId);

}
