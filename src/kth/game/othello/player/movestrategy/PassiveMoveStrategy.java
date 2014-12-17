package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * This move strategy will make moves that captures as few opponent nodes as possible (at least one node) in one move.
 *
 * @author Henrik Hygerth
 */
public class PassiveMoveStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "PassiveMoveStrategy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		int min = Integer.MAX_VALUE;
		Node result = null;

		if (rules.hasValidMove(playerId)) {
			for (Node node : board.getNodes()) {
				if (rules.isMoveValid(playerId, node.getId())) {
					int temp = rules.getNodesToSwap(playerId, node.getId()).size();
					if (min >= temp) {
						min = temp;
						result = node;
					}
				}
			}
		}

		return result;
	}

}
