package kth.game.othello.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is responsible for finding nodes.
 * 
 * @author Henrik Hygerth
 */
public class NodeFinder {

	/**
	 * Returns node from specified id.
	 * 
	 * @param nodes the list of all the nodes
	 * @param nodeId the node id
	 * @return the node with specified id, or null if not found
	 */
	public Node getNodeFromId(List<Node> nodes, String nodeId) {
		Node result = null;
		for (Node node : nodes) {
			if (node.getId().equals(nodeId)) {
				result = node;
			}
		}

		return result;
	}

	/**
	 * Returns list of marked adjacent nodes to specified node.
	 * 
	 * @param nodes the list of all the nodes
	 * @param node the pivot node
	 * @return list of adjacent nodes to the pivot node that are marked
	 */
	public List<Node> getAdjacentMarkedNodes(List<Node> nodes, Node node) {
		List<Node> markedNodes = new ArrayList<Node>();

		final int x = node.getXCoordinate();
		final int y = node.getYCoordinate();

		for (Node n : nodes) {
			if (n.isMarked()) {
				final int adjX = n.getXCoordinate();
				final int adjY = n.getYCoordinate();
				final boolean deltaH = (adjX == x + 1 || adjX == x - 1);
				final boolean deltaV = (adjY == y + 1 || adjY == y - 1);
				final boolean adjHorizontal = deltaH && (adjY == y);
				final boolean adjVertical = deltaV && (adjX == x);
				final boolean adjDiagonal = deltaH && deltaV;
				if (adjDiagonal || adjHorizontal || adjVertical) {
					markedNodes.add(n);
				}
			}
		}

		return markedNodes;
	}

	/**
	 * Returns list of nodes around specified node that are the opposing player to the specified player.
	 * 
	 * @param nodes the list of all the nodes
	 * @param playerId the playing player id
	 * @param node the pivot node
	 * @return list of adjacent nodes to the pivot node that are occupied by the opponent player
	 */
	public List<Node> getAdjacentOpponentNodes(List<Node> nodes, String playerId, Node node) {
		List<Node> markedNodes = getAdjacentMarkedNodes(nodes, node);

		Iterator<Node> iterator = markedNodes.iterator();
		while (iterator.hasNext()) {
			Node n = iterator.next();
			if (n.getOccupantPlayerId().equals(playerId)) {
				iterator.remove();
			}
		}

		return markedNodes;
	}
}
