package kth.game.othello.score;

import kth.game.othello.PlayerSwitcher;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class OthelloScoreStrategy implements ScoreStrategy {
    private BoardImpl board;

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
