package kth.game.othello.score;

import kth.game.othello.PlayerSwitcher;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Changed the score of players according to the following:
 * - When a player loses a node: -1 point.
 * - When a player occupies a node: 1 point.
 * - When a player cannot move (and gets skipped): -2 points.
 * - When a player occupies a boundary node: 2 points.
 */
public class OthelloScoreStrategy implements ScoreStrategy {
    private BoardImpl board;

    /**
     * Creates the score strategy.
     * @param board The board that the score will operate on.
     */
    public OthelloScoreStrategy(BoardImpl board) {
        this.board = board;
    }

    @Override
    public List<String> updateScores(List<ScoreItem> scores, Observable observable, Object args) {
        if(observable instanceof NodeImpl) {
            return onNodeChanged(scores, (NodeImpl)observable, ((List<String>)args));
        } else if(observable instanceof PlayerSwitcher) {
            return onPlayersSkipped(scores, (List<String>)args);
        } else {
            throw new RuntimeException("Unsupported observable.");
        }
    }

    /**
     * Updates player scores due to a node swap.
     * @param scores The score items for this game.
     * @param node The node that has been change.
     * @param args A list with first element as the previous occupying player id and the second element as the next occupying player id.
     * @return A list of all player id's that had their score changed.
     */
    private List<String> onNodeChanged(List<ScoreItem> scores, Node node, List<String> args) {
        List<String> changes = args;
        String prevPlayerId = changes.get(0);
        String nextPlayerId = changes.get(1);

        int score = 1;

        if(board.isBoundaryNode(node)) {
            score = 2;
        }

        List<String> updatedScores = new ArrayList<String>();
        for (int i = 0; i <  scores.size(); i++) {
            ScoreItem item = scores.get(i);
            if (item.getPlayerId().equals(prevPlayerId) && item.getScore() > 0) {
                scores.set(i, new ScoreItem(item.getPlayerId(), item.getScore() - score));
                updatedScores.add(prevPlayerId);
            } else if (item.getPlayerId().equals(nextPlayerId)) {
                scores.set(i, new ScoreItem(item.getPlayerId(), item.getScore() + score));
                updatedScores.add(nextPlayerId);
            }
        }
        return updatedScores;
    }

    /**
     * Updates player scores due to player skips (unable to move).
     * @param scores The score items for this game.
     * @param skippedPlayers A list of player id's that couldn't move and therefore has been skipped.
     * @return A list of all player id's that had their score changed.
     */
    private List<String> onPlayersSkipped(List<ScoreItem> scores, List<String> skippedPlayers) {
        for(int i = 0; i < scores.size(); i++) {
            ScoreItem item = scores.get(i);
            if(skippedPlayers.contains(item.getPlayerId())) {
                scores.set(i, new ScoreItem(item.getPlayerId(), item.getScore() - 2));
            }
        }

        return skippedPlayers;
    }
}
