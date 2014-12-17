package kth.game;

import kth.game.othello.Othello;

import java.util.Scanner;

/**
 * A factory for producing ASCII Othello games.
 *
 * @author Mattias Harrysson
 */
public class AsciiOthelloGameFactory implements OthelloGameFactory {

	@Override
	public OthelloGame createGame(Othello othello) {
		return new AsciiOthelloGame(othello, createInputScanner());
	}

	private Scanner createInputScanner() {
		return new Scanner(System.in);
	}

}
