package kth.game;

import kth.game.othello.Othello;
import kth.game.othello.view.swing.OthelloView;

import java.util.Observable;
import java.util.Observer;

/**
 * The responsibility of this class is to run a Othello game with a graphical user interface.
 *
 * @author Mattias Harrysson
 */
public class GuiOthelloGame extends Observable implements OthelloGame, Observer {

	private OthelloView othelloView;
	private Othello othello;

	/**
	 * @param othelloView the othello view to use
	 * @param othello the othello that the view is using
	 */
	public GuiOthelloGame(OthelloView othelloView, Othello othello) {
		this.othelloView = othelloView;
		this.othelloView.addObserver(this);
		this.othello = othello;
	}

	@Override
	public void start() {
		this.othelloView.start();
	}

	@Override
	public void start(String playerId) {
		this.othelloView.start(playerId);
	}

	@Override
	public void update(Observable observable, Object object) {
		setChanged();
		notifyObservers(othello.getScore());
	}

}
