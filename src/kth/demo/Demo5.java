package kth.demo;

import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;

import java.util.List;

/**
 * Starts a human versus human game, makes four moves for each player and displays the score.
 *
 * @author Mattias Harrysson
 */
public class Demo5 {

	/**
	 * Demo entry point.
	 */
	public static void main(String[] args) {
		Othello othello = new OthelloFactoryImpl().createHumanGame();

		List<Player> players = othello.getPlayers();
		othello.start(players.get(0).getId());

		// make the moves
		for (int i = 0; i < 8; i++) {
			Player player = players.get(i % 2);
			for (Node node : othello.getBoard().getNodes()) {
				if (othello.isMoveValid(player.getId(), node.getId())) {
					othello.move(player.getId(), node.getId());
					break;
				}
			}
		}

		Score score = othello.getScore();
		final int p1Points = score.getPoints(players.get(0).getId());
		final int p2Points = score.getPoints(players.get(1).getId());
		final String p1Score = players.get(0).getName() + " (" + p1Points + ")";
		final String p2Score = players.get(1).getName() + " (" + p2Points + ")";
		System.out.println("Score: " + (p1Points > p2Points ? p1Score + " " + p2Score : p2Score + " " + p1Score));
	}

}