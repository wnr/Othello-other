package kth.tournament;

import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreItem;
import kth.tournament.match.Match;
import kth.tournament.presenter.ResultItem;
import kth.tournament.presenter.ResultPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The responsibility of this class is to host a Othello tournament.
 *
 * @author Mattias Harrysson
 * @author Henrik Hygerth
 */
public class OthelloTournament implements Tournament {

	private Score score;
	private List<Player> players;
	private List<Match> matches;
	private ResultPresenter resultPresenter;

	/**
	 * @param score the total score for each player in the tournament
	 * @param players the tournament contestant
	 * @param matches the tournament matches to be played
	 * @param resultPresenter the present of the tournament result
	 */
	public OthelloTournament(Score score, List<Player> players, List<Match> matches, ResultPresenter resultPresenter) {
		this.score = score;
		this.players = players;
		this.matches = matches;
		this.resultPresenter = resultPresenter;
	}

	@Override
	public void start() {
		for (Match match : matches) {
			match.start();
		}
		presentScore();
	}

	private void presentScore() {
		List<ResultItem> results = new ArrayList<ResultItem>();
		for (ScoreItem item : score.getPlayersScore()) {
			results.add(new ResultItem(getPlayerNameFromId(item.getPlayerId()), item.getScore()));
		}
		resultPresenter.present(results);
	}

	/**
	 * Returns player name from specified player id.
	 *
	 * @param playerId the player id to search after
	 * @return the player name
	 */
	private String getPlayerNameFromId(String playerId) {
		String result = null;
		for (Player player : players) {
			if (player.getId() == playerId) {
				result = player.getName();
				break;
			}
		}
		return result;
	}

}
