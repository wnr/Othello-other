package kth.game.othello;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.player.Player;
import kth.game.othello.rules.Rules;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PlayerSwitcherTest {

	private List<Player> create2Players() {
		Player player1 = Mockito.mock(Player.class);
		Player player2 = Mockito.mock(Player.class);
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);

		Mockito.when(player1.getId()).thenReturn("foo");
		Mockito.when(player2.getId()).thenReturn("bar");

		return players;
	}

	private List<Player> create3Players() {
		Player player1 = Mockito.mock(Player.class);
		Player player2 = Mockito.mock(Player.class);
		Player player3 = Mockito.mock(Player.class);
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player3);

		Mockito.when(player1.getId()).thenReturn("foo");
		Mockito.when(player2.getId()).thenReturn("bar");
		Mockito.when(player3.getId()).thenReturn("foobar");

		return players;
	}

	@Test
	public void switchToNextPlayer3Players() {
		List<Player> players = create3Players();

		Player p1 = players.get(0);
		Player p2 = players.get(1);
		Player p3 = players.get(2);

		Rules rules = Mockito.mock(Rules.class);
		Mockito.when(rules.hasValidMove(p1.getId())).thenReturn(true);
		Mockito.when(rules.hasValidMove(p2.getId())).thenReturn(true);
		Mockito.when(rules.hasValidMove(p3.getId())).thenReturn(true);

		List<String> skipped;
		PlayerSwitcher ps = new PlayerSwitcher(players, rules, p1.getId());
		Assert.assertEquals(p1, ps.getPlayerInTurn());
		skipped = ps.switchToNextPlayer();
		Assert.assertEquals(p2, ps.getPlayerInTurn());
		Assert.assertEquals(0, skipped.size());
		skipped = ps.switchToNextPlayer();
		Assert.assertEquals(p3, ps.getPlayerInTurn());
		Assert.assertEquals(0, skipped.size());
		skipped = ps.switchToNextPlayer();
		Assert.assertEquals(p1, ps.getPlayerInTurn());
		Assert.assertEquals(0, skipped.size());
		Mockito.when(rules.hasValidMove(p2.getId())).thenReturn(false);
		Mockito.when(rules.hasValidMove(p3.getId())).thenReturn(false);
		skipped = ps.switchToNextPlayer();
		Assert.assertEquals(p1, ps.getPlayerInTurn());
		Assert.assertEquals(2, skipped.size());
		Assert.assertTrue(skipped.contains(p2.getId()));
		Assert.assertTrue(skipped.contains(p3.getId()));
		Mockito.when(rules.hasValidMove(p1.getId())).thenReturn(false);
		Mockito.when(rules.hasValidMove(p3.getId())).thenReturn(true);
		skipped = ps.switchToNextPlayer();
		Assert.assertEquals(p3, ps.getPlayerInTurn());
		Assert.assertEquals(1, skipped.size());
		Assert.assertTrue(skipped.contains(p2.getId()));
		Mockito.when(rules.hasValidMove(p3.getId())).thenReturn(false);
		skipped = ps.switchToNextPlayer();
		Assert.assertEquals(null, ps.getPlayerInTurn());
		Assert.assertEquals(0, skipped.size());
	}

	@Test
	public void switchToNextPlayer2Players() {
		List<Player> players = create2Players();

		Player p1 = players.get(0);
		Player p2 = players.get(1);

		Rules rules = Mockito.mock(Rules.class);
		Mockito.when(rules.hasValidMove(p1.getId())).thenReturn(true);
		Mockito.when(rules.hasValidMove(p2.getId())).thenReturn(true);

		PlayerSwitcher ps = new PlayerSwitcher(players, rules, p1.getId());
		Assert.assertEquals(p1, ps.getPlayerInTurn());
		ps.switchToNextPlayer();
		Assert.assertEquals(p2, ps.getPlayerInTurn());
		ps.switchToNextPlayer();
		Assert.assertEquals(p1, ps.getPlayerInTurn());
		Mockito.when(rules.hasValidMove(p2.getId())).thenReturn(false);
		ps.switchToNextPlayer();
		Assert.assertEquals(p1, ps.getPlayerInTurn());
	}

	@Test
	public void chosenPlayerStartsGame() {
		List<Player> players = create2Players();

		Player p1 = players.get(0);

		PlayerSwitcher ps = new PlayerSwitcher(players, null, p1.getId());

		Assert.assertEquals(p1, ps.getPlayerInTurn());
	}

	@Test
	public void randomPlayerStartsGame() {
		List<Player> players = create2Players();

		Player p1 = players.get(0);
		Player p2 = players.get(1);

		PlayerSwitcher ps = new PlayerSwitcher(players, null);

		Player playerInTurnId = ps.getPlayerInTurn();
		assert(playerInTurnId.equals(p1) || playerInTurnId.equals(p2));
	}

}
