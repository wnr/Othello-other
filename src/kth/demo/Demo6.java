package kth.demo;

import kth.game.AsciiOthelloGameFactory;
import kth.game.OthelloGame;
import kth.game.othello.Othello;
import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.board.factory.Diamond;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.RandomMoveStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Setup and run a game between three computers on a diamond board.
 *
 * @author Mattias Harrysson
 */
public class Demo6 {

	/**
	 * Demo entry point.
	 */
	public static void main(String[] args) {
		Player player1 = new PlayerImpl("p1", "Player 1", Player.Type.COMPUTER, new RandomMoveStrategy());
		Player player2 = new PlayerImpl("p2", "Player 2", Player.Type.COMPUTER, new RandomMoveStrategy());
		Player player3 = new PlayerImpl("p3", "Player 3", Player.Type.COMPUTER, new RandomMoveStrategy());
		List<Player> players = Arrays.asList(player1, player2, player3);

		Diamond diamond = new Diamond();
		Set<NodeData> nodesData = diamond.getNodes(9, players);

		Othello othello = new OthelloFactoryImpl().createGame(nodesData, players);
		OthelloGame othelloGame = new AsciiOthelloGameFactory().createGame(othello);
		othelloGame.start();
	}

}