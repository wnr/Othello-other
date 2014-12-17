package kth.game.othello;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.rules.Rules;
import kth.game.othello.score.Score;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This class represents an classic Othello game.
 *
 * @author Mattias Harrysson
 * @author Henrik Hygerth
 */
public class OthelloImpl implements Othello, Observer {

	private String id;
	private Board board;
	private Rules rules;
	private PlayerMover playerMover;
	private Score score;
	private PlayerSwitcher playerSwitcher;
	private List<Player> players;
	private List<Observer> gameFinishedObservers;
	private boolean finished;

	/**
	 * @param id the globally unique id for this Othello
	 * @param board the board to play on
	 * @param rules the rules to follow
	 * @param playerMover the player mover that performs moves on the board
	 * @param score the score tracker
	 * @param players the players that are playing
	 */
	public OthelloImpl(String id, Board board, Rules rules, PlayerMover playerMover, Score score, List<Player> players) {
		this.id = id;
		this.board = board;
		this.rules = rules;
		this.playerMover = playerMover;
		this.score = score;
		this.players = players;
		gameFinishedObservers = new LinkedList<Observer>();
		playerMover.addObserver(this);
	}

	@Override
	public void addGameFinishedObserver(Observer observer) {
		gameFinishedObservers.add(observer);
	}

	@Override
	public void addMoveObserver(Observer observer) {
		playerMover.addObserver(observer);
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return rules.getNodesToSwap(playerId, nodeId);
	}

	@Override
	public Player getPlayerInTurn() {
		return playerSwitcher.getPlayerInTurn();
	}

	@Override
	public List<Player> getPlayers() {
		return this.players;
	}

	@Override
	public Score getScore() {
		return score;
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return rules.hasValidMove(playerId);
	}

	@Override
	public boolean isActive() {
		for (Player player : getPlayers()) {
			if (hasValidMove(player.getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return rules.isMoveValid(playerId, nodeId);
	}

	@Override
	public List<Node> move() throws IllegalStateException {
		return playerMover.move(playerSwitcher);
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		return playerMover.move(playerSwitcher, playerId, nodeId);
	}

	@Override
	public void start() {
		playerSwitcher = new PlayerSwitcher(getPlayers(), rules);
		playerMover.undoAll();
		finished = false;
	}

	@Override
	public void start(String playerId) {
		playerSwitcher = new PlayerSwitcher(getPlayers(), rules, playerId);
		playerMover.undoAll();
		finished = false;
	}

	@Override
	public void undo() {
		playerMover.undo(playerSwitcher, getPlayers());
	}

	@Override
	public void update(Observable observable, Object object) {
		if (observable instanceof PlayerMover && !finished && !isActive()) {
			finished = true;
			for (Observer observer : gameFinishedObservers) {
				observer.update(null, null);
			}
		}
	}

}