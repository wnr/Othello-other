package kth.game.othello.rules;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeCapturer;

/**
 * This class represents the rules for a game
 * 
 * @author Henrik Hygerth
 */
public class RulesImpl implements Rules {

	private Board board;
	private NodeCapturer nodeCapturer;

	public RulesImpl(Board board, NodeCapturer nodeCapturer) {
		this.board = board;
		this.nodeCapturer = nodeCapturer;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return nodeCapturer.getNodesToCapture(board, playerId, nodeId, true);
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return !nodeCapturer.getNodesToCapture(board, playerId, nodeId, false).isEmpty();
	}

	@Override
	public boolean hasValidMove(String playerId) {
		for (Node node : board.getNodes()) {
			if (!node.isMarked() && isMoveValid(playerId, node.getId())) {
				return true;
			}
		}
		return false;
	}

}
