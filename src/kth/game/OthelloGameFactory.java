package kth.game;

import kth.game.othello.Othello;

/**
 * A factory for producing a complete Othello games.
 *
 * @author Mattias Harrysson
 */
public interface OthelloGameFactory {

	/**
	 * Creates a Othello game from specified Othello.
	 *
	 * @param othello the Othello game
	 * @return complete Othello game
	 */
	public OthelloGame createGame(Othello othello);

}
