package kth.game.othello.board;

import kth.game.othello.board.factory.NodeData;

import java.util.List;

/**
 * This class is responsible for swapping nodes.
 *
 * @author Henrik Hygerth
 * @author Mattias Harrysson
 */
public interface NodeSwapper {

	/**
	 * Make move by specified player to specified node.
	 *
	 * @param nodesToSwap the nodes that will be swapped
	 * @param playerId the id of the moving player
	 * @param nodeId the id of the node to be placed
	 */
	public void swap(List<Node> nodesToSwap, String playerId, String nodeId);

	/**
	 * Make move by copying node values from specified list of nodes where x- and y-coordinates match.
	 *
	 * @param nodesToCopy the nodes that will be copied
	 */
	public void copy(List<NodeData> nodesToCopy);

}
