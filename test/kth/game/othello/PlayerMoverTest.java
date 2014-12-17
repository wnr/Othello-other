package kth.game.othello;

import kth.game.othello.player.Player;
import kth.game.othello.rules.Rules;

import org.junit.Test;
import org.mockito.Mockito;

public class PlayerMoverTest {

	@Test(expected=IllegalStateException.class)
	public void notComputerInTurnTest() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getType()).thenReturn(Player.Type.HUMAN);
		PlayerSwitcher playerSwitcher = Mockito.mock(PlayerSwitcher.class);
		Mockito.when(playerSwitcher.getPlayerInTurn()).thenReturn(player);

		PlayerMover playerMover = new PlayerMover(null, null, null, null);
		playerMover.move(playerSwitcher);
	}

	@Test(expected=IllegalArgumentException.class)
	public void wrongPlayerInTurnTest() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getId()).thenReturn("id");
		PlayerSwitcher playerSwitcher = Mockito.mock(PlayerSwitcher.class);
		Mockito.when(playerSwitcher.getPlayerInTurn()).thenReturn(player);

		PlayerMover playerMover = new PlayerMover(null, null, null, null);
		playerMover.move(playerSwitcher, "wrongId", null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void invalidMoveTest() {
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getId()).thenReturn("id");
		PlayerSwitcher playerSwitcher = Mockito.mock(PlayerSwitcher.class);
		Mockito.when(playerSwitcher.getPlayerInTurn()).thenReturn(player);
		Rules rules = Mockito.mock(Rules.class);
		Mockito.when(rules.isMoveValid(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

		PlayerMover playerMover = new PlayerMover(null, null, null, rules);
		playerMover.move(playerSwitcher, "id", "wrongNodeId");
	}

}