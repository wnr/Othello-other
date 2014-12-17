package kth.game.othello.board;

import java.util.Arrays;
import java.util.Observable;

/**
 * Describes a node in a Othello board. It will inform all observers when a new player is assigned to this
 * node.
 * 
 * @author Henrik Hygerth
 * @author Mattias Harrysson
 */
public class NodeImpl extends Observable implements Node, Comparable<Node> {
	
	private String id;
	private String occupantId;
	private int x;
	private int y;
	private boolean marked;
	
	/**
	 * Construct a empty node.
	 *
	 * @param x the x-coordinate on the board
	 * @param y the y-coordinate on the board
	 */
	public NodeImpl(int x, int y) {
		this.id = x + "-" + y;
		this.x = x;
		this.y = y;
		occupantId = null;
		marked = false;
	}

	/**
	 * Construct a node occupied by a player.
	 *
	 * @param x the x-coordinate on the board
	 * @param y the y-coordinate on the board
	 * @param playerId the occupying player id
	 */
	public NodeImpl(int x, int y, String playerId) {
		this.id = x + "-" + y;
		this.x = x;
		this.y = y;
		mark(playerId);
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getOccupantPlayerId() {
		return occupantId;
	}

	@Override
	public int getXCoordinate() {
		return x;
	}

	@Override
	public int getYCoordinate() {
		return y;
	}

	@Override
	public boolean isMarked() {
		return marked;
	}

	@Override
	public int compareTo(Node node) {
		// row-major comparison
		final int x2 = node.getXCoordinate();
		final int y2 = node.getYCoordinate();
		if (x == x2 && y == y2) {
			return 0;
		} else if (y > y2 || (y == y2 && x > x2)) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * Sets node state to be occupied by specified player id. Observers will be notified of this change and receive the
	 * previous player id (if any, or else null) and the current occupying player id.
	 *
	 * @param playerId the player to occupy this node
	 */
	public void mark(String playerId) {
		setChanged();
		notifyObservers(Arrays.asList(occupantId, playerId));

		occupantId = playerId;
		marked = true;
	}

	/**
	 * Sets node free from any occupying player.
	 */
	public void unmark() {
		setChanged();
		notifyObservers(Arrays.asList(occupantId, occupantId));

		occupantId = null;
		marked = false;
	}

}