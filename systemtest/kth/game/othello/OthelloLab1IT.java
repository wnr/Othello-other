package kth.game.othello;

import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;

import org.junit.Assert;
import org.junit.Test;

public class OthelloLab1IT {

	private Object getNumberOfOccupiedNodes(Othello othello) {
		int occupiedNodesCounter = 0;
		for (Node node : othello.getBoard().getNodes()) {
			if (node.isMarked()) {
				occupiedNodesCounter++;
			}
		}
		return occupiedNodesCounter;
	}

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

	private Player getHumanPlayer(Othello othello) {
		if (othello.getPlayers().get(0).getType() == Type.COMPUTER) {
			return othello.getPlayers().get(1);
		} else {
			return othello.getPlayers().get(0);
		}
	}

	@Test
	public void someMovesBetweenAComputerAndHumanTest() {
		Othello othello = getOthelloFactory().createHumanVersusComputerGame();
		Player human = getHumanPlayer(othello);
		othello.start(human.getId());
		makeAHumanMove(othello, human);
		othello.move();
		makeAHumanMove(othello, human);
		othello.move();
		makeAHumanMove(othello, human);
		othello.move();
		Assert.assertEquals(10, getNumberOfOccupiedNodes(othello));
	}

	@Test
	public void someMovesBetweenTwoHumansTest() {
		Othello othello = getOthelloFactory().createHumanGame();
		Player player1 = othello.getPlayers().get(0);
		Player player2 = othello.getPlayers().get(1);
		othello.start(player1.getId());
		makeAHumanMove(othello, player1);
		makeAHumanMove(othello, player2);
		makeAHumanMove(othello, player1);
		makeAHumanMove(othello, player2);
		makeAHumanMove(othello, player1);
		makeAHumanMove(othello, player2);
		Assert.assertEquals(10, getNumberOfOccupiedNodes(othello));
	}

	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		othello.start(othello.getPlayers().get(0).getId());
		while (othello.isActive()) {
			Assert.assertEquals(Type.COMPUTER, othello.getPlayerInTurn().getType());
			othello.move();
		}
	}

}
