package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * This move strategy will make the first valid move it can find for a player.
 * 
 * @author Henrik Hygerth
 */
public class NaiveMoveStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "NaiveMoveStrategy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		if (rules.hasValidMove(playerId)) {
			for (Node node : board.getNodes()) {
				if (rules.isMoveValid(playerId, node.getId())) {
					return node;
				}
			}
		}

		return null;
	}

}
