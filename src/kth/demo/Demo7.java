package kth.demo;

import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

import java.util.List;

/**
 * Starts a human game, plays a few move and then undo the last move.
 *
 * @author Mattias Harrysson
 */
public class Demo7 {

	/**
	 * Demo entry point.
	 */
	public static void main(String[] args) {
		Othello othello = new OthelloFactoryImpl().createHumanGame();

		List<Player> players = othello.getPlayers();
		othello.start(players.get(0).getId());

		// make the moves
		System.out.println(othello.getBoard());
		for (int i = 0; i < 4; i++) {
			Player player = players.get(i % 2);
			for (Node node : othello.getBoard().getNodes()) {
				if (othello.isMoveValid(player.getId(), node.getId())) {
					othello.move(player.getId(), node.getId());
					System.out.println();
					System.out.println(othello.getBoard());
					break;
				}
			}
		}

		System.out.println();
		System.out.println("Player in turn before undo: " + othello.getPlayerInTurn().getName());
		System.out.println("Undo last move.");
		othello.undo();
		System.out.println();
		System.out.println(othello.getBoard());
		System.out.println();
		System.out.println("Player in turn after undo: " + othello.getPlayerInTurn().getName());
	}

}