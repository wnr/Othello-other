package kth.game.othello;

import java.util.List;

import kth.game.othello.board.BoardMocker;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeFinder;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class NodeFinderTest {

	@Test
	public void getNodeFromId() {
		NodeFinder nf = new NodeFinder();

		BoardMocker bm = new BoardMocker();

		String state = 	". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . w b . . .\n" +
						". . . b w . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n";

		List<Node> nodes = bm.mockNodesFromString(state);

		Assert.assertEquals("4-4", nf.getNodeFromId(nodes, "4-4").getId());
		Assert.assertEquals("0-7", nf.getNodeFromId(nodes, "0-7").getId());
	}

	@Test
	public void getAdjacentMarkedNodes() {
		NodeFinder nf = new NodeFinder();
		BoardMocker bm = new BoardMocker();
		
		String state = 	". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . w b . . .\n" +
						". . . b w . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n";

		List<Node> nodes = bm.mockNodesFromString(state);

		Node node = Mockito.mock(Node.class);
		Mockito.when(node.getXCoordinate()).thenReturn(3);
		Mockito.when(node.getYCoordinate()).thenReturn(2);
		Assert.assertEquals(2, nf.getAdjacentMarkedNodes(nodes, node).size());

		Mockito.when(node.getXCoordinate()).thenReturn(3);
		Mockito.when(node.getYCoordinate()).thenReturn(3);
		Assert.assertEquals(3, nf.getAdjacentMarkedNodes(nodes, node).size());
	}

	@Test
	public void getAdjacentOpponentNodes() {
		NodeFinder nf = new NodeFinder();
		String player1Id = "w";

		BoardMocker bm = new BoardMocker();

		String state = 	". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . w b . . .\n" +
						". . . b w . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n";

		List<Node> nodes = bm.mockNodesFromString(state);

		Node node = Mockito.mock(Node.class);
		Mockito.when(node.getXCoordinate()).thenReturn(3);
		Mockito.when(node.getYCoordinate()).thenReturn(2);
		Assert.assertEquals(1, nf.getAdjacentOpponentNodes(nodes, player1Id, node).size());

		Mockito.when(node.getXCoordinate()).thenReturn(3);
		Mockito.when(node.getYCoordinate()).thenReturn(3);
		Assert.assertEquals(2, nf.getAdjacentOpponentNodes(nodes, player1Id, node).size());
	}
}
