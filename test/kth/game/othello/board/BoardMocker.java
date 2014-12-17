package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

/**
 * This class mocks up boards with nodes from a string representing a board state.
 * 
 * @author Henrik Hygerth
 */
public class BoardMocker {

	/**
	 * Mocks a board with a state described in a string
	 * @param state the state which the board should have
	 * @return the board
	 */
	public Board mockBoardFromString(String state) {
		List<Node> nodes = mockNodesFromString(state);
		Board board = Mockito.mock(Board.class);
		Mockito.when(board.getNodes()).thenReturn(nodes);
		for (Node node : nodes) {
			Mockito.when(board.getNode(node.getXCoordinate(), node.getYCoordinate())).thenReturn(node);
		}
		return board;
	}

	/**
	 * Creates all the nodes required to describe a specified state
	 * @param state the specified state
	 * @return the nodes
	 */
	public List<Node> mockNodesFromString(String state) {
		List<Node> nodes = new ArrayList<Node>();
		int x = 0;
		int y = 0;

		for (int i = 0; i < state.length(); i++) {
			char cell = state.charAt(i);
			
			if (cell == ' ') {
				continue;
			}

			if (cell == '\n') {
				y++;
				x = 0;
				continue;
			}
			
			Node node = Mockito.mock(Node.class);

			if (cell == '.') {
				Mockito.when(node.getOccupantPlayerId()).thenReturn("");
				Mockito.when(node.isMarked()).thenReturn(false);
			} else {
				Mockito.when(node.getOccupantPlayerId()).thenReturn(String.valueOf(cell));
				Mockito.when(node.isMarked()).thenReturn(true);
			}

			Mockito.when(node.getXCoordinate()).thenReturn(x);
			Mockito.when(node.getYCoordinate()).thenReturn(y);
			Mockito.when(node.getId()).thenReturn(x + "-" + y);

			nodes.add(node);

			x++;
		}
		return nodes;
	}

	/**
	 * Converts a board to a string
	 * @param board the board that is printed
	 * @return the string
	 */
	public String boardToString(Board board) {
		return nodesToString(board.getNodes());
	}

	/**
	 * Converts nodes to a string
	 * @param nodes the nodes
	 */
	public String nodesToString(List<Node> nodes) {
		int xMax = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			if (!node.isMarked()) {
				sb.append('.');
			} else {
				sb.append(node.getOccupantPlayerId());
			}
			if (i < nodes.size() - 1 && nodes.get(i + 1).getXCoordinate() < xMax) {
				xMax = 0;
				sb.append('\n');
			} else {
				xMax = node.getXCoordinate();
				sb.append(' ');
			}
		}
		return sb.toString();
	}
}
