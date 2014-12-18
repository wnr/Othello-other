package kth.game.othello;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.NodeImpl;
import kth.game.othello.board.NodeSwapperImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeCapturer;
import kth.game.othello.board.NodeFinder;
import kth.game.othello.board.NodeSwapper;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.player.movestrategy.NaiveMoveStrategy;
import kth.game.othello.player.movestrategy.RandomMoveStrategy;
import kth.game.othello.rules.Rules;
import kth.game.othello.rules.RulesImpl;
import kth.game.othello.score.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * A factory for producing Othello games.
 *
 * @author Mattias Harrysson
 * @author Henrik Hygerth
 */
public class OthelloFactoryImpl implements OthelloFactory {

	private static final String PLAYER1_NAME = "Player 1";
	private static final String PLAYER2_NAME = "Player 2";

	@Override
	public Othello createComputerGame() {
		Player player1 = createComputerPlayer(PLAYER1_NAME, new NaiveMoveStrategy());
		Player player2 = createComputerPlayer(PLAYER2_NAME, new RandomMoveStrategy());
		return createGame(Arrays.asList(player1, player2));
	}

	@Override
	public Othello createHumanGame() {
		Player player1 = createHumanPlayer(PLAYER1_NAME);
		Player player2 = createHumanPlayer(PLAYER2_NAME);
		return createGame(Arrays.asList(player1, player2));
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		Player player1 = createHumanPlayer(PLAYER1_NAME);
		Player player2 = createComputerPlayer(PLAYER2_NAME, new RandomMoveStrategy());
		return createGame(Arrays.asList(player1, player2));
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		return createGame(createNodes(nodesData), players);
	}

	/**
	 * Creates Othello game with a default board from given list of players.
	 * 
	 * @param players a list of players in the game
	 * @return othello game
	 */
	private Othello createGame(List<Player> players) {
		return createGame(createNodes(players.get(0), players.get(1), 8, 8), players);
	}

	/**
	 * Creates Othello game.
	 *
	 * @param nodes a list of nodes to use as board
	 * @param players a list of players in the game
	 * @return othello game
	 */
	private Othello createGame(List<NodeImpl> nodes, List<Player> players) {
		String id = generateId();
		BoardImpl board = createBoard(nodes, players);
		NodeFinder nodeFinder = new NodeFinder();
		NodeCapturer nodeCapturer = new NodeCapturer(nodeFinder);
		Rules rules = new RulesImpl(board, nodeCapturer);
		NodeSwapper nodeSwapper = new NodeSwapperImpl(nodes);
		PlayerMover playerMover = new PlayerMover(new MoveHistory(), nodeSwapper, board, rules);
		Score score = createScore(board, nodes, players);
		return new OthelloImpl(id, board, rules, playerMover, score, players);
	}

	/**
	 * Creates a human player with specified name.
	 *
	 * @param name the name of the player
	 * @return human player
	 */
	private PlayerImpl createHumanPlayer(String name) {
		return new PlayerImpl(generateId(), name, Player.Type.HUMAN, null);
	}

	/**
	 * Creates a computer player with specified name.
	 *
	 * @param name the name of the player
	 * @return computer player
	 */
	private PlayerImpl createComputerPlayer(String name, MoveStrategy moveStrategy) {
		return new PlayerImpl(generateId(), name, Player.Type.COMPUTER, moveStrategy);
	}

	/**
	 * Creates list of nodes.
	 *
	 * @param player1 the first player
	 * @param player2 the second player
	 * @param rows the number of rows
	 * @param cols the number of columns
	 * @return list of nodes
	 */
	private List<NodeImpl> createNodes(Player player1, Player player2, int rows, int cols) {
		List<NodeImpl> nodes = new ArrayList<NodeImpl>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == 3 && j == 3) {
					nodes.add(new NodeImpl(j, i, player1.getId()));
				} else if (i == 3 && j == 4) {
					nodes.add(new NodeImpl(j, i, player2.getId()));
				} else if (i == 4 && j == 3) {
					nodes.add(new NodeImpl(j, i, player2.getId()));
				} else if (i == 4 && j == 4) {
					nodes.add(new NodeImpl(j, i, player1.getId()));
				} else {
					nodes.add(new NodeImpl(j, i));
				}
			}
		}
		return nodes;
	}

	/**
	 * Creates list of nodes.
	 *
	 * @param nodesData the nodes to read from
	 * @return list of nodes
	 */
	private List<NodeImpl> createNodes(Set<NodeData> nodesData) {
		List<NodeImpl> nodes = new ArrayList<NodeImpl>();
		for (NodeData nodeData : nodesData) {
			int x = nodeData.getXCoordinate();
			int y = nodeData.getYCoordinate();
			String occupantPlayerId = nodeData.getOccupantPlayerId();
			if (occupantPlayerId == null) {
				nodes.add(new NodeImpl(x, y));
			} else {
				nodes.add(new NodeImpl(x, y, occupantPlayerId));
			}
		}

		// nodesData can have any order
		Collections.sort(nodes);

		return nodes;
	}

	/**
	 * Creates a board from a list of nodes and players.
	 *
	 * @param nodes the playing field
	 * @param players the players
	 * @return the board
	 */
	private BoardImpl createBoard(List<NodeImpl> nodes, List<Player> players) {
		HashMap<String, Character> colors = new HashMap<String, Character>();
		// assign a ASCII char for each player to use in the board string representation
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			colors.put(player.getId(), (char) (97 + i));
		}
		return new BoardImpl((List<Node>) (Object) nodes, colors);
	}

	/**
	 * Creates a list of score items from specified list of players.
	 *
	 * @param players a list of players for the game
	 * @return the list of score items
	 */
	private List<ScoreItem> createScoreItems(List<Player> players) {
		List<ScoreItem> items = new ArrayList<ScoreItem>();
		for (Player player : players) {
			ScoreItem item = new ScoreItem(player.getId(), players.size());
			items.add(item);
		}
		return items;
	}

	/**
	 * Creates score that observers to all specified nodes and tracks each specified player.
	 *
	 * @param nodes the nodes to observe
	 * @param players the players to track
	 * @return new score instance
	 */
	private Score createScore(BoardImpl board, List<NodeImpl> nodes, List<Player> players) {
		ScoreStrategy scoreStrategy = new OthelloScoreStrategy(board);
		ScoreImpl score = new ScoreImpl(createScoreItems(players), scoreStrategy);
		for (NodeImpl node : nodes) {
			node.addObserver(score);
		}
		return score;
	}

	/**
	 * Generates a unique id.
	 *
	 * @return unique id
	 */
	private String generateId() {
		return UUID.randomUUID().toString();
	}

}
