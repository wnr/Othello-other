package kth.game.othello;

import kth.game.othello.board.Node;
import kth.game.othello.board.factory.NodeData;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * The responsibility of this class is to store the history of moves by value in chronological order.
 *
 * @author Mattias Harrysson
 */
public class MoveHistory {

	private Stack<List<NodeData>> history;

	public MoveHistory() {
		history = new Stack<List<NodeData>>();
	}

	/**
	 * Stores by value a new list of moves.
	 *
	 * @param nodes the list of nodes to store
	 */
	public void pushNewMoves(List<Node> nodes) {
		List<NodeData> nodesData = new LinkedList<NodeData>();
		for (Node node : nodes) {
			final int x = node.getXCoordinate();
			final int y = node.getYCoordinate();
			final String occupantPlayerId = node.getOccupantPlayerId();
			nodesData.add(new NodeData(x, y, occupantPlayerId));
		}
		history.push(nodesData);
	}

	/**.
	 * Removes and returns last stored move.
	 *
	 * @return last move
	 */
	public List<NodeData> popLastMoves() {
		return history.pop();
	}

	/**
	 * Validates if there are any history of moves stored.
	 *
	 * @return true if has history of moves, false otherwise
	 */
	public boolean hasMoves() {
		return !history.isEmpty();
	}

	/**
	 * Clears all moves stored.
	 */
	public void clear() {
		history.clear();;
	}

}
