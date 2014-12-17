package kth.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.AggressiveMoveStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.player.movestrategy.NaiveMoveStrategy;
import kth.game.othello.player.movestrategy.RandomMoveStrategy;

/**
 * Setup and run a game between two computers and change the strategy for one player after ten moves with each player.
 *
 * @author Henrik Hygerth
 */
public class Demo4 {

	/**
	 * Demo entry point.
	 */
	public static void main(String[] args) {
		Othello othello = new OthelloFactoryImpl().createComputerGame();
		List<Player> players = othello.getPlayers();
		List<MoveStrategy> moveStrategies = new ArrayList<MoveStrategy>();
		moveStrategies.add(new NaiveMoveStrategy());
		moveStrategies.add(new RandomMoveStrategy());
		moveStrategies.add(new AggressiveMoveStrategy());
		othello.start(players.get(0).getId());

		while (othello.isActive()) {
			// Make 10 moves with each player
			for (int i = 0; i < 20; i++) {
				othello.move();
			}

			Random rand = new Random(System.currentTimeMillis());
			// Select one of the players at random
			int index = rand.nextInt(players.size());
			Player player = players.get(index);

			// Select a strategy at random
			index = rand.nextInt(moveStrategies.size());
			player.setMoveStrategy(moveStrategies.get(index));
			System.out.println(player.getName() + " is now using " + moveStrategies.get(index).getName());
		}
	}

}
