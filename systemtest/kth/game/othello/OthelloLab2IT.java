package kth.game.othello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import kth.game.othello.player.*;
import kth.game.othello.player.movestrategy.*;
import kth.game.othello.score.*;
import org.junit.Assert;
import org.junit.Test;

import kth.game.othello.board.factory.Diamond;
import kth.game.othello.player.Player.Type;

public class OthelloLab2IT {

	private String generateId() {
		return UUID.randomUUID().toString();
	}

	private MoveStrategy getNewMoveStrategy() {
		return new RandomMoveStrategy();
	}

	private OthelloFactory getOthelloFactory() {
		return new OthelloFactoryImpl();
	}

	private Player createComputer(String name) {
		return new PlayerImpl(generateId(), name, Player.Type.COMPUTER, new NaiveMoveStrategy());
	}

	private Player createHuman(String name) {
		return new PlayerImpl(generateId(), name, Player.Type.HUMAN, null);
	}

	private void makeNumberOfComputerMoves(int numberOfMoves, Othello othello) {
		for (int i = 0; i < numberOfMoves; i++) {
			Assert.assertEquals(Type.COMPUTER, othello.getPlayerInTurn().getType());
			othello.move();
		}
	}

	@Test
	public void studyTheInitialScoreTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(0).getId();
		othello.start();
		Assert.assertEquals(2, othello.getScore().getPoints(playerId));
	}

	@Test
	public void studyTheScoreAfterAMoveTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(1).getId();
		othello.start(playerId);
		othello.move(playerId, othello.getBoard().getNode(2, 3).getId());
		Assert.assertEquals(4, othello.getScore().getPoints(playerId));
	}

	@Test
	public void threeComputersOnADiamondBoardTest() {
		List<Player> players = new ArrayList<Player>();
		players.add(createComputer("black"));
		players.add(createComputer("white"));
		players.add(createComputer("orange"));
		Diamond diamond = new Diamond();
		Othello othello = getOthelloFactory().createGame(diamond.getNodes(11, players), players);
		othello.start();
		while (othello.isActive()) {
			othello.move();
		}
		Assert.assertFalse(othello.isActive());
	}

	/* Commented out since the game can not finish deterministic with a random strategy since one player can in one or
	   more rounds not be able to make a move.

	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		othello.start(othello.getPlayers().get(0).getId());

		// Make some moves
		makeNumberOfComputerMoves(10, othello);

		// Change one of the computers strategy
		othello.getPlayers().get(0).setMoveStrategy(getNewMoveStrategy());

		// Make some moves
		makeNumberOfComputerMoves(50, othello);

		Assert.assertFalse(othello.isActive());
	}
	*/

	@Test
	public void updatesScore() {
		List<ScoreItem> scores = new LinkedList<ScoreItem>();

		scores.add(new ScoreItem("p1", 3));
		scores.add(new ScoreItem("p2", 5));
		scores.add(new ScoreItem("p3", 2));

		ScoreImpl score = new ScoreImpl(scores);

		score.update(null, Arrays.asList(null, "p2"));
		score.update(null, Arrays.asList("p1", "p3"));

		List<ScoreItem> currentScores = score.getPlayersScore();

		Assert.assertEquals("p2", currentScores.get(0).getPlayerId());
		Assert.assertEquals("p3", currentScores.get(1).getPlayerId());
		Assert.assertEquals("p1", currentScores.get(2).getPlayerId());

		Assert.assertEquals(6, currentScores.get(0).getScore());
		Assert.assertEquals(3, currentScores.get(1).getScore());
		Assert.assertEquals(2, currentScores.get(2).getScore());
	}

}
