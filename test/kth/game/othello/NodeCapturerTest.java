package kth.game.othello;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardMocker;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeCapturer;
import kth.game.othello.board.NodeFinder;
import kth.game.othello.player.Player;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class NodeCapturerTest {

	@Test
	public void nodesToCaptureInDirection() {
		final int rows = 8;
		final int cols = 8;
		String p1Id = "w";
		String p2Id = "b";
		Player player1 = Mockito.mock(Player.class);
		Player player2 = Mockito.mock(Player.class);
		Mockito.when(player1.getId()).thenReturn(p1Id);
		Mockito.when(player2.getId()).thenReturn(p2Id);

		String state = 	". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . w . . .\n" +
						". . . w w . . .\n" +
						". . . b w . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n";
		BoardMocker bm = new BoardMocker();

		Board board = bm.mockBoardFromString(state);
		
		Mockito.when(board.getMaxX()).thenReturn(cols);
		Mockito.when(board.getMaxY()).thenReturn(rows);

		Mockito.when(board.hasNode(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

		NodeFinder nf = new NodeFinder();
		NodeCapturer nc = new NodeCapturer(nf);

		Node from = Mockito.mock(Node.class);
		Mockito.when(from.getXCoordinate()).thenReturn(5);
		Mockito.when(from.getYCoordinate()).thenReturn(4);

		Node direction = Mockito.mock(Node.class);
		Mockito.when(direction.getOccupantPlayerId()).thenReturn(p1Id);
		Mockito.when(direction.getXCoordinate()).thenReturn(4);
		Mockito.when(direction.getYCoordinate()).thenReturn(4);
		Assert.assertEquals(1, nc.getNodesToCaptureInDirection(board, p2Id, from, direction).size());
		
		Mockito.when(direction.getXCoordinate()).thenReturn(4);
		Mockito.when(direction.getYCoordinate()).thenReturn(3);
		Assert.assertEquals(0, nc.getNodesToCaptureInDirection(board, p2Id, from, direction).size());

		Mockito.when(from.getXCoordinate()).thenReturn(3);
		Mockito.when(from.getYCoordinate()).thenReturn(2);

		Mockito.when(direction.getXCoordinate()).thenReturn(3);
		Mockito.when(direction.getYCoordinate()).thenReturn(3);
		Assert.assertEquals(1, nc.getNodesToCaptureInDirection(board, p2Id, from, direction).size());
		
		Mockito.when(direction.getXCoordinate()).thenReturn(4);
		Mockito.when(direction.getYCoordinate()).thenReturn(3);
		Assert.assertEquals(0, nc.getNodesToCaptureInDirection(board, p2Id, from, direction).size());
	}

	@Test
	public void getNodesToCapture() {
		final int rows = 8;
		final int cols = 8;
		String p1Id = "w";
		String p2Id = "b";
		Player player1 = Mockito.mock(Player.class);
		Player player2 = Mockito.mock(Player.class);
		Mockito.when(player1.getId()).thenReturn(p1Id);
		Mockito.when(player2.getId()).thenReturn(p2Id);

		String state = 	". . . . . . . .\n" +
				". . . . . . . .\n" +
				". . . . w . . .\n" +
				". . . w w . . .\n" +
				". . . b w . . .\n" +
				". . . . . . . .\n" +
				". . . . . . . .\n" +
				". . . . . . . .\n";
		BoardMocker bm = new BoardMocker();

		Board board = bm.mockBoardFromString(state);

		Mockito.when(board.getMaxX()).thenReturn(cols);
		Mockito.when(board.getMaxY()).thenReturn(rows);

		Mockito.when(board.hasNode(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

		NodeFinder nf = new NodeFinder();
		NodeCapturer nc = new NodeCapturer(nf);

		Node place = Mockito.mock(Node.class);
		Mockito.when(place.getXCoordinate()).thenReturn(5);
		Mockito.when(place.getYCoordinate()).thenReturn(4);
		Mockito.when(place.getId()).thenReturn("5-4");

		Assert.assertEquals(1, nc.getNodesToCapture(board, p2Id, "5-4", false).size());

		Mockito.when(place.getXCoordinate()).thenReturn(5);
		Mockito.when(place.getYCoordinate()).thenReturn(4);
		Mockito.when(place.getId()).thenReturn("4-4");

		Assert.assertEquals(0, nc.getNodesToCapture(board, p2Id, "4-4", false).size());
	}

}
