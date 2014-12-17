package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardMocker;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PassiveMoveStrategyTest {

	@Test
	public void testMove() {
		String player1Id = "w";
		String player2Id = "b";
		String state = 	". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . b b b . .\n" +
						". . . w w w . .\n" +
						". . . w w . . .\n" +
						". . . w . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n";

		BoardMocker bm = new BoardMocker();
		Board b = bm.mockBoardFromString(state);

		Rules r = Mockito.mock(Rules.class);

		@SuppressWarnings("unchecked") // Suppress warning because we know it is mocked and not a real List<Node>
		List<Node> nodesToSwap1 = Mockito.mock(List.class);
		@SuppressWarnings("unchecked")
		List<Node> nodesToSwap2 = Mockito.mock(List.class);
		@SuppressWarnings("unchecked")
		List<Node> nodesToSwap3 = Mockito.mock(List.class);

		Mockito.when(nodesToSwap1.size()).thenReturn(1);
		Mockito.when(nodesToSwap2.size()).thenReturn(3);
		Mockito.when(nodesToSwap3.size()).thenReturn(1);

		MoveStrategy ms = new PassiveMoveStrategy();

		Assert.assertNull(ms.move(player1Id, r, b));
		Mockito.when(r.hasValidMove(player1Id)).thenReturn(true);
		Mockito.when(r.hasValidMove(player2Id)).thenReturn(true);
		Assert.assertNull(ms.move(player2Id, r, b));
		Mockito.when(r.isMoveValid(player2Id, "5-4")).thenReturn(true);
		Mockito.when(r.isMoveValid(player2Id, "3-6")).thenReturn(true);
		Mockito.when(r.getNodesToSwap(player2Id, "5-4")).thenReturn(nodesToSwap1);
		Mockito.when(r.getNodesToSwap(player2Id, "3-6")).thenReturn(nodesToSwap2);
		Assert.assertEquals("5-4", ms.move(player2Id, r, b).getId());
		Mockito.when(r.isMoveValid(player2Id, "4-5")).thenReturn(true);
		Mockito.when(r.getNodesToSwap(player2Id, "4-5")).thenReturn(nodesToSwap3);
		Assert.assertEquals("4-5", ms.move(player2Id, r, b).getId());
	}

}
