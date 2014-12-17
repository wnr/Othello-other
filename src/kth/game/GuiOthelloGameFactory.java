package kth.game;

import kth.game.othello.Othello;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

/**
 * A factory for producing Othello games with a graphical user interface.
 *
 * @author Mattias Harrysson
 */
public class GuiOthelloGameFactory implements OthelloGameFactory {

	private int timeBetweenSwaps;
	private int timeBetweenMoves;

	public GuiOthelloGameFactory() {
		timeBetweenSwaps = 500;
		timeBetweenMoves = 1500;
	}

	public GuiOthelloGameFactory(int timeBetweenSwaps, int timeBetweenMoves) {
		this.timeBetweenSwaps = timeBetweenSwaps;
		this.timeBetweenMoves = timeBetweenMoves;
	}

	@Override
	public OthelloGame createGame(Othello othello) {
		OthelloView othelloView = OthelloViewFactory.create(othello, timeBetweenSwaps, timeBetweenMoves);
		return new GuiOthelloGame(othelloView, othello);
	}

}
