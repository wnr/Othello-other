package kth.demo;

import kth.game.GuiOthelloGameFactory;
import kth.game.OthelloGame;
import kth.game.othello.Othello;
import kth.game.othello.OthelloFactoryImpl;

/**
 * Starts Othello game between a human versus a computer on a classic board using a graphical user interface.
 *
 * @author Mattias Harrysson
 */
public class Demo8 {

	/**
	 * Demo entry point.
	 */
	public static void main(String[] args) {
		Othello othello = new OthelloFactoryImpl().createHumanVersusComputerGame();
		OthelloGame othelloGame = new GuiOthelloGameFactory().createGame(othello);
		othelloGame.start();
	}

}
