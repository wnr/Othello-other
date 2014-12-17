package kth.game.othello.player.movestrategy;

import java.util.ArrayList;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardMocker;
import kth.game.othello.rules.Rules;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RandomMoveStrategyTest {

	@Test
	public void testMove() {
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

		Board b = bm.mockBoardFromString(state);

		Rules r = Mockito.mock(Rules.class);

		MoveStrategy ms = new RandomMoveStrategy();
		Assert.assertNull(ms.move(player1Id, r, b));
		Mockito.when(r.hasValidMove(player1Id)).thenReturn(true);
		Assert.assertNull(ms.move(player1Id, r, b));
		Mockito.when(r.isMoveValid(player1Id, "4-2")).thenReturn(true);
		Assert.assertEquals("4-2", ms.move(player1Id, r, b).getId());

		Mockito.when(r.isMoveValid(player1Id, "5-3")).thenReturn(true);
		Mockito.when(r.isMoveValid(player1Id, "2-4")).thenReturn(true);
		Mockito.when(r.isMoveValid(player1Id, "3-5")).thenReturn(true);
		Assert.assertNotNull(ms.move(player1Id, r, b));

		ArrayList<String> possibleResult = new ArrayList<String>();
		possibleResult.add("4-2");
		possibleResult.add("5-3");
		possibleResult.add("2-4");
		possibleResult.add("3-5");
		Assert.assertTrue(possibleResult.contains(ms.move(player1Id, r, b).getId()));
	}

}
