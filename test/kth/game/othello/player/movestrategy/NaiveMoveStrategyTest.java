package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardMocker;
import kth.game.othello.rules.Rules;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class NaiveMoveStrategyTest {

	@Test
	public void testMove() {
		String player1Id = "w";
		String player2Id = "b";
		BoardMocker bm = new BoardMocker();

		String state = 	". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . w b . . .\n" +
						". . . b w . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n" +
						". . . . . . . .\n";

		Board b = bm.mockBoardFromString(state);

		Rules r = Mockito.mock(Rules.class);

		MoveStrategy ms = new NaiveMoveStrategy();
		Assert.assertNull(ms.move(player1Id, r, b));
		Mockito.when(r.hasValidMove(player1Id)).thenReturn(true);
		Assert.assertNull(ms.move(player1Id, r, b));
		Mockito.when(r.isMoveValid(player1Id, "4-2")).thenReturn(true);
		Assert.assertEquals("4-2", ms.move(player1Id, r, b).getId());

		Mockito.when(r.hasValidMove(player2Id)).thenReturn(true);
		Assert.assertNull(ms.move(player2Id, r, b));
		Assert.assertEquals("4-2", ms.move(player1Id, r, b).getId());
		Mockito.when(r.isMoveValid(player2Id, "3-2")).thenReturn(true);
		Mockito.when(r.isMoveValid(player1Id, "3-5")).thenReturn(true);
		Assert.assertEquals("4-2", ms.move(player1Id, r, b).getId());
		Assert.assertEquals("3-2", ms.move(player2Id, r, b).getId());
	}

}
