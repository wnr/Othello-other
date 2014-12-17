package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * This move strategy will make moves that captures as many opponent nodes as possible in one move.
 *
 * @author Henrik Hygerth
 */
public class AggressiveMoveStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "AggressiveMoveStrategy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		int max = 0;
		Node result = null;

		if (rules.hasValidMove(playerId)) {
			for (Node node : board.getNodes()) {
				if (rules.isMoveValid(playerId, node.getId())) {
					int tmp = rules.getNodesToSwap(playerId, node.getId()).size();
					if (max <= tmp) {
						max = tmp;
						result = node;
					}
				}
			}
		}

		return result;
	}

}
