package kth.game.othello.board.factory;

import kth.game.othello.player.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Tomas Ekholm
 */
public class Square {

	/**
	 * This board is the traditional board for two players.
	 *
	 * @param size an even number determining the size of the board
	 * @param players the list players of players, that must be two
	 * @return the nodes of the board
	 */
	public Set<NodeData> getNodes(int size, List<Player> players) {
		return getNodes(size, players, 0, 0);
	}

	Set<NodeData> getNodes(int size, List<Player> players, int startX, int startY) {
		if (players.size() != 2) {
			throw new IllegalArgumentException("The number of players must be two.");
		}
		if (size % 2 != 0) {
			throw new IllegalArgumentException("The size must be even.");
		}

		String player1Id = players.get(0).getId();
		String player2Id = players.get(1).getId();

		Set<NodeData> nodes = new HashSet<NodeData>();

		int middle = size / 2;
		nodes.add(new NodeData(startX + middle - 1, startY + middle, player1Id));
		nodes.add(new NodeData(startX + middle, startY + middle - 1, player1Id));
		nodes.add(new NodeData(startX + middle - 1, startY + middle - 1, player2Id));
		nodes.add(new NodeData(startX + middle, startY + middle, player2Id));

		for (int x = startX; x < startX + size; x++) {
			for (int y = startY; y < startY + size; y++) {
				nodes.add(new NodeData(x, y));
			}
		}
		return nodes;
	}

}
