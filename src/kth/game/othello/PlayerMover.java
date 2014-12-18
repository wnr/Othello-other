package kth.game.othello;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeSwapper;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.rules.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * The responsibility of this class is to perform moves for players on a board such that they conform with a set of
 * board rules. It must inform all observers when a move has been made or if there was no valid move and the turn was
 * passed.
 *
 * @author Mattias Harrysson
 */
public class PlayerMover extends Observable {

	private MoveHistory moveHistory;
	private NodeSwapper nodeSwapper;
	private Board board;
	private Rules rules;

	/**
	 * @param moveHistory the storage to push performed moves to
	 * @param nodeSwapper the node swapper that will update the board for a valid move
	 * @param board the board to perform moves on
	 * @param rules the rules to follow when validating moves
	 */
	public PlayerMover(MoveHistory moveHistory, NodeSwapper nodeSwapper, Board board, Rules rules) {
		this.moveHistory = moveHistory;
		this.nodeSwapper = nodeSwapper;
		this.board = board;
		this.rules = rules;
	}

	/**
	 * Performs a move for a computer, but only if it is the computer in turn and can make a valid move.
	 *
	 * @param playerSwitcher the player switcher for changing the turn to next player
	 * @return the nodes that where swapped for this move, including the node where the player made the move
	 * @throws IllegalStateException if there is not a computer in turn
	 */
	public List<Node> move(PlayerSwitcher playerSwitcher) throws IllegalStateException {
		Player player = playerSwitcher.getPlayerInTurn();

		if (player.getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Computer is not in turn.");
		}

		MoveStrategy moveStrategy = player.getMoveStrategy();
		Node node = moveStrategy.move(player.getId(), rules, board);
		if (node != null) {
			return move(playerSwitcher, player.getId(), node.getId());
		}

		List<Node> nodesToSwap = new ArrayList<Node>();
		registerMove(playerSwitcher, nodesToSwap, null, null);

		return nodesToSwap;
	}

	/**
	 * Performs a move for a specified player to a specified node, but only if it is a valid move in the current state.
	 *
	 * @param playerSwitcher the player switcher for changing the turn to next player
	 * @param playerId the moving player id
	 * @param nodeId the node id to place the node
	 * @return the nodes that where swapped for this move, including the node where the player made the move
	 * @throws IllegalArgumentException if the move is not valid, or if the player is not in turn
	 */
	public List<Node> move(PlayerSwitcher playerSwitcher, String playerId, String nodeId) throws IllegalArgumentException {
		Player player = playerSwitcher.getPlayerInTurn();

		if (!(player.getId().equals(playerId))) {
			throw new IllegalArgumentException("Player '" + playerId + "' is not in turn.");
		}

		if (!rules.isMoveValid(playerId, nodeId)) {
			throw new IllegalArgumentException("Invalid move.");
		}

		List<Node> nodesToSwap =  rules.getNodesToSwap(playerId, nodeId);
		registerMove(playerSwitcher, nodesToSwap, playerId, nodeId);

		return nodesToSwap;
	}

	/**
	 * Undo one move.
	 *
	 * @param playerSwitcher the player switcher for changing the turn to previous player
	 * @param players the players in the game
	 */
	public void undo(PlayerSwitcher playerSwitcher, List<Player> players) {
		if (moveHistory.hasMoves()) {
			nodeSwapper.copy(moveHistory.popLastMoves());
			// loop until we hit the player before this move
			for (int i = 0; i < players.size() - 1; i++) {
				playerSwitcher.switchToNextPlayer(false);
			}
		}
	}

	/**
	 * Undo all previously made moves.
	 */
	public void undoAll() {
		moveHistory.clear();
	}

	/**
	 * Registers the move by updating the state of the board.
	 *
	 * @param playerSwitcher the player switcher
	 * @param nodesToSwap the nodes swapped in this move
	 * @param playerId the moving player id
	 * @param nodeId the node id where player placed a new node
	 */
	private void registerMove(PlayerSwitcher playerSwitcher, List<Node> nodesToSwap, String playerId, String nodeId) {
		moveHistory.pushNewMoves(nodesToSwap);
		nodeSwapper.swap(nodesToSwap, playerId, nodeId);
		playerSwitcher.switchToNextPlayer(true);

		setChanged();
		notifyObservers(nodesToSwap);
	}

}
