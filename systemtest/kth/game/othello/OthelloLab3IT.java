package kth.game.othello;

import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreItem;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class OthelloLab3IT {

	private OthelloFactory getOthelloFactory() {
		return new OthelloFactoryImpl();
	}

	private void makeAHumanMove(Othello othello, Player human) {
		for (Node node : othello.getBoard().getNodes()) {
			if (othello.isMoveValid(human.getId(), node.getId())) {
				othello.move(human.getId(), node.getId());
				return;
			}
		}
		throw new IllegalStateException();
	}

	private class GameFinishedObserver implements Observer {
		private int callbacks = 0;

		public GameFinishedObserver() {
			this.callbacks = 0;
		}

		@Override
		public void update(Observable observable, Object o) {
			callbacks++;
		}

		public int getCallbacks() {
			return callbacks;
		}
	}

	private class MoveObserver implements Observer {
		private List<List<Node>> moves;

		public MoveObserver() {
			moves = new LinkedList<List<Node>>();
		}

		@Override
		public void update(Observable observable, Object o) {
			moves.add((List<Node>) o);
		}

		public List<List<Node>> getMoves() {
			return moves;
		}
	}

	@Test
	public void studyTheBoardAfterUndoTest() {
		Othello othello = getOthelloFactory().createHumanGame();

		List<Player> players = othello.getPlayers();
		othello.start(players.get(0).getId());

		List<Node> startNodes = new LinkedList<Node>();
		for (Node node : othello.getBoard().getNodes()) {
			int x = node.getXCoordinate();
			int y = node.getYCoordinate();
			if ((x == 3 || x == 4) && (y == 3 || y == 4)) {
				startNodes.add(node);
			}
		}

		final int moves = 10;

		for (int i = 0; i < moves; i++) {
			Player player = players.get(i % 2);
			makeAHumanMove(othello, player);
		}

		for (int i = 0; i < moves; i++) {
			othello.undo();
		}

		List<Node> currentNodes = new LinkedList<Node>();
		for (Node node : othello.getBoard().getNodes()) {
			int x = node.getXCoordinate();
			int y = node.getYCoordinate();
			if (node.isMarked() && (x == 3 || x == 4) && (y == 3 || y == 4)) {
				Assert.assertNotNull(node.getOccupantPlayerId());
				currentNodes.add(node);
			} else {
				Assert.assertNull(node.getOccupantPlayerId());
			}
		}

		Assert.assertEquals(startNodes, currentNodes);
	}

	@Test
	public void studyTheScoreAfterUndoTest() {
		Othello othello = getOthelloFactory().createHumanGame();

		List<Player> players = othello.getPlayers();
		othello.start(players.get(0).getId());

		final int moves = 10;

		for (int i = 0; i < moves; i++) {
			Player player = players.get(i % 2);
			makeAHumanMove(othello, player);
		}

		for (int i = 0; i < moves; i++) {
			othello.undo();
		}

		Score score = othello.getScore();
		for (ScoreItem item : score.getPlayersScore()) {
			Assert.assertEquals(item.getScore(), players.size());
		}
	}

	@Test
	public void studyPlayerInTurnAfterUndoTest() {
		Othello othello = getOthelloFactory().createHumanGame();

		List<Player> players = othello.getPlayers();
		othello.start(players.get(0).getId());

		final int moves = 10;

		for (int i = 0; i < moves; i++) {
			Player player = players.get(i % 2); // 0, 1, 0, ...
			makeAHumanMove(othello, player);
		}

		Assert.assertEquals(players.get(0).getId(), othello.getPlayerInTurn().getId());
		for (int i = 0; i < moves; i++) {
			Player player = players.get((i + 1) % 2); // 1, 0, 1, ...
			othello.undo();
			Assert.assertEquals(player.getId(), othello.getPlayerInTurn().getId());
		}
	}

	@Test
	public void studyCallbackAfterGameEndedTest() {
		Othello othello = getOthelloFactory().createComputerGame();

		GameFinishedObserver finishedObserver = new GameFinishedObserver();
		othello.addGameFinishedObserver(finishedObserver);

		othello.start(othello.getPlayers().get(0).getId());
		while (othello.isActive()) {
			Assert.assertEquals(0, finishedObserver.getCallbacks());
			othello.move();
		}
		Assert.assertEquals(1, finishedObserver.getCallbacks());
	}

	@Test
	public void studyCallbackWhenMoveTest() {
		Othello othello = getOthelloFactory().createComputerGame();

		MoveObserver moveObserver = new MoveObserver();
		othello.addMoveObserver(moveObserver);

		List<List<Node>> moves = new LinkedList<List<Node>>();

		othello.start();
		while (othello.isActive()) {
			moves.add(othello.move());
		}

		List<List<Node>> observedMoves = moveObserver.getMoves();
		for (int i = 0; i < moves.size(); i++) {
			Assert.assertEquals(moves.get(i), observedMoves.get(i));
		}
	}

}