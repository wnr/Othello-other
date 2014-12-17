package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardImplTest {

	@Test
	public void validGetNode() {
		List<Node> nodes = new ArrayList<Node>();

		Node n1 = Mockito.mock(Node.class);
		Mockito.when(n1.getId()).thenReturn("0-0");
		Mockito.when(n1.getXCoordinate()).thenReturn(0);
		Mockito.when(n1.getYCoordinate()).thenReturn(0);

		Node n2 = Mockito.mock(Node.class);
		Mockito.when(n2.getId()).thenReturn("2-4");
		Mockito.when(n2.getXCoordinate()).thenReturn(2);
		Mockito.when(n2.getYCoordinate()).thenReturn(4);

		Node n3 = Mockito.mock(Node.class);
		Mockito.when(n3.getId()).thenReturn("7-7");
		Mockito.when(n3.getXCoordinate()).thenReturn(7);
		Mockito.when(n3.getYCoordinate()).thenReturn(7);

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (x == 0 && y == 0) {
					nodes.add(n1);
				} else if (x == 2 && y == 4) {
					nodes.add(n2);
				} else if (x == 7 && y == 7) {
					nodes.add(n3);
				} else {
					Node filler = Mockito.mock(Node.class);
					Mockito.when(filler.getId()).thenReturn("" + x + "-" + y);
					Mockito.when(filler.getXCoordinate()).thenReturn(x);
					Mockito.when(filler.getYCoordinate()).thenReturn(y);
					nodes.add(filler);
				}
			}
		}

		Board board = new BoardImpl(nodes, null);

		Assert.assertEquals(board.getNode(0, 0).getId(), n1.getId());
		Assert.assertEquals(board.getNode(2, 4).getId(), n2.getId());
		Assert.assertEquals(board.getNode(7, 7).getId(), n3.getId());
	}

	@Test(expected=IllegalArgumentException.class)
	public void invalidGetNode() {
		List<Node> nodes = new ArrayList<Node>();
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				Node filler = Mockito.mock(Node.class);
				Mockito.when(filler.getXCoordinate()).thenReturn(x);
				Mockito.when(filler.getYCoordinate()).thenReturn(y);
				nodes.add(filler);
			}
		}

		Board board = new BoardImpl(nodes, null);
		board.getNode(8, 3);
	}

	@Test
	public void correctMaxCoordinates() {
		Node n1 = Mockito.mock(Node.class);
		Mockito.when(n1.getXCoordinate()).thenReturn(10);
		Mockito.when(n1.getYCoordinate()).thenReturn(3);

		Node n2 = Mockito.mock(Node.class);
		Mockito.when(n2.getXCoordinate()).thenReturn(5);
		Mockito.when(n2.getYCoordinate()).thenReturn(20);

		Node n3 = Mockito.mock(Node.class);
		Mockito.when(n3.getXCoordinate()).thenReturn(2);
		Mockito.when(n3.getYCoordinate()).thenReturn(2);

		List<Node> nodes = Arrays.asList(n1, n2, n3);

		Board board = new BoardImpl(nodes, null);
		Assert.assertEquals(board.getMaxX(), 11); // non-zero based
		Assert.assertEquals(board.getMaxY(), 21);
	}

}