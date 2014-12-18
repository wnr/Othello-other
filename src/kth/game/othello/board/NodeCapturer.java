package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for calculating nodes to capture
 * 
 * @author Henrik Hygerth
 */
public class NodeCapturer {

	private NodeFinder nodeFinder;

	/**
	 * Constructs node capturer from specified node finder.
	 *
	 * @param nodeFinder the node finder
	 */
	public NodeCapturer(NodeFinder nodeFinder) {
		this.nodeFinder = nodeFinder;
	}

	/**
	 * Returns the captured nodes surrounding the specified empty node in all directions which is occupied by the
	 * opponent player.
	 *
	 * @param board the board with nodes
	 * @param playerId the moving player id
	 * @param nodeId the empty node
	 * @param includeStart include the node where the player made the move to be updated and return
	 *
	 * @return list of nodes to be captured surrounding the empty node
	 */
	public List<Node> getNodesToCapture(Board board, String playerId, String nodeId, boolean includeStart) {
		List<Node> captures = new ArrayList<Node>();

		final Node startNode = nodeFinder.getNodeFromId(board.getNodes(), nodeId);
		if (startNode == null) {
			return captures;
		}

		if (includeStart) {
			captures.add(nodeFinder.getNodeFromId(board.getNodes(), nodeId));
		}

		if (startNode.isMarked()) {
			return captures;
		}

		for (Node node : nodeFinder.getAdjacentOpponentNodes(board.getNodes(), playerId, startNode)) {
			if(isDiagonallyAdjacent(node, startNode)) {
				continue; //Never swap diagonally.
			}
			captures.addAll(getNodesToCaptureInDirection(board, playerId, startNode, node));
		}

		return captures;
	}

	/**
	 * Returns the captured nodes that a player can make from the specified empty node in the specified direction which
	 * is occupied by the opponent player.
	 *
	 * @param board the board with nodes
	 * @param playerId the moving player id
	 * @param from the empty node
	 * @param direction the adjacent node occupied by the moving player opponent
	 * @return list of nodes to be captured in this direction by the empty node
	 */
	public List<Node> getNodesToCaptureInDirection(Board board, String playerId, Node from, Node direction) {
		List<Node> captures = new ArrayList<Node>();

		final int maxX = board.getMaxX();
		final int maxY = board.getMaxY();

		int x = from.getXCoordinate();
		int y = from.getYCoordinate();
		int adjX = direction.getXCoordinate();
		int adjY = direction.getYCoordinate();

		final int stepX = adjX - x;
		final int stepY = adjY - y;
		// start looking on the next adjacent node
		x += stepX;
		y += stepY;

		boolean validCapture = false;
		while (x >= 0 && y >= 0 && x < maxX && y < maxY) {
			if (!board.hasNode(x, y)) {
				// there can exist "gaps" in the board, it should be treated the same way as hitting the end of the
				// board and not be considered an error
				break;
			}
			Node n = board.getNode(x, y);

			if (!n.isMarked()) {
				// we hit a unmarked node before finding a node which was occupied by one of the moving players
				break;
			} else if (n.getOccupantPlayerId().equals(playerId)) {
				validCapture = true;
				break;
			} else {
				captures.add(n);
			}

			x += stepX;
			y += stepY;
		}

		if (validCapture) {
			return captures;
		} else {
			// could not embrace one or more opponent nodes
			captures.clear();
			return captures;
		}
	}

	private boolean isDiagonallyAdjacent(Node n1, Node n2) {
		return n1.getXCoordinate() != n2.getXCoordinate() && n1.getYCoordinate() != n2.getYCoordinate();
	}

}
