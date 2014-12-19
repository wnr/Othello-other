package kth.game.othello.board;

import java.util.HashMap;
import java.util.List;

/**
 * Describes a Othello board.
 * 
 * @author Henrik Hygerth
 * @author Mattias Harrysson
 */
public class BoardImpl implements Board {

	private List<Node> nodes;
	private HashMap<String, Character> colors;
	private int maxX;
	private int maxY;
	
	/**
	 * There must be one color assigned for each player if the string representation is to be used.
	 *
	 * @param nodes list of nodes for the board
	 * @param colors hash map with player id mapped to colors
	 */
	public BoardImpl(List<Node> nodes, HashMap<String, Character> colors) {
		this.nodes = nodes;
		this.colors = colors;
		computeMaxCoordinates();
	}

	@Override
	public int getMaxX() {
		return maxX;
	}

	@Override
	public int getMaxY() {
		return maxY;
	}

	@Override
	public Node getNode(int x, int y) throws IllegalArgumentException {
		for (Node node : nodes) {
			if (node.getXCoordinate() == x && node.getYCoordinate() == y) {
				return node;
			}
		}
		throw new IllegalArgumentException("No node with coordinates " + x + "-" + y + ".");
	}
	
	@Override
	public List<Node> getNodes() {
		return nodes;
	}

	@Override
	public boolean hasNode(int x, int y) {
		try {
			getNode(x, y);
			return true; // found if no exception is thrown
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("  ");
		for (int i = 0; i < maxX; i++) {
			builder.append(i + " ");
		}

		int row = -1;
		int col = 0;
		for (Node node : nodes) {
			int x = node.getXCoordinate();
			int y = node.getYCoordinate();

			if (row != y) {
				row = y;
				col = 0;
				builder.append("\n" + row + " ");
			}

			while (x > col) {
				builder.append("  ");
				col++;
			}
			col++;

			if (!node.isMarked()) {
				builder.append('.');
			} else {
				builder.append(colors.get(node.getOccupantPlayerId()));
			}

			builder.append(' ');
		}

		return builder.toString();
	}

	/**
	 * Tells if the node is a boundary node. A boundary node is a node that is missing an at least one adjacent node horizontally or vertically.
	 * @param node The node to be checked if it is a boundary node.
	 * @return True if the given node is a boundary node.
	 */
	public boolean isBoundaryNode(Node node) {
		final int x = node.getXCoordinate();
		final int y = node.getYCoordinate();
		return !(hasNode(x - 1, y) && hasNode(x + 1, y) && hasNode(x, y - 1) && hasNode(x, y + 1));
	}

	private void computeMaxCoordinates() {
		maxX = 0;
		maxY = 0;
		for (Node node : nodes) {
			if (node.getXCoordinate() > maxX) {
				maxX = node.getXCoordinate();
			}
			if (node.getYCoordinate() > maxY) {
				maxY = node.getYCoordinate();
			}
		}
		// adjust for zero based coordinates
		maxX++;
		maxY++;
	}

}
