package kth.tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import kth.game.OthelloGame;
import kth.game.OthelloGameFactory;
import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreImpl;
import kth.game.othello.score.ScoreItem;
import kth.game.othello.score.ScoreStrategy;
import kth.tournament.match.Match;
import kth.tournament.match.OthelloMatch;
import kth.tournament.presenter.AsciiResultPresenter;
import kth.tournament.presenter.ResultPresenter;

/**
 * A factory for producing Othello tournaments.
 *
 * @author Henrik Hygerth
 * @author Mattias Harrysson
 */
public class OthelloTournamentFactory implements TournamentFactory {

	private OthelloGameFactory gameFactory;

	/**
	 * @param gameFactory the othello game factory to use for each match
	 */
	public OthelloTournamentFactory(OthelloGameFactory gameFactory) {
		this.gameFactory = gameFactory;
	}

	@Override
	public OthelloTournament createTournament(List<Player> players) {
		List<Match> matches = createGames(players);
		Score score = createScore(matches, players);
		ResultPresenter resultPresenter = new AsciiResultPresenter();
		return new OthelloTournament(score, players, matches, resultPresenter);
	}

	/**
	 * Creates a list of games to be played by the specified contestants in a tournament.
	 *
	 * @param players the contestant
	 * @return list of games
	 */
	private List<Match> createGames(List<Player> players) {
		List<Match> matches = new ArrayList<Match>();
		OthelloFactory factory = new OthelloFactoryImpl();
		for (Player playerA : players) {
			for (Player playerB : players) {
				if (playerA.getId().equals(playerB.getId())) {
					continue;
				}
				Othello othello = createClassicOthello(factory, playerA, playerB);
				OthelloGame game = gameFactory.createGame(othello);
				matches.add(new OthelloMatch(game, playerA.getId()));
			}
		}
		return matches;
	}

	/**
	 * Creates classic Othello game between two players.
	 *
	 * @param factory the Othello factory to use to construct the Othello game
	 * @param playerA the first player
	 * @param PlayerB the second player
	 * @return classic Othello game
	 */
	private Othello createClassicOthello(OthelloFactory factory, Player playerA, Player PlayerB) {
		List<Player> players = Arrays.asList(playerA, PlayerB);
		return factory.createGame(createNodesForSquareBoard(8, players), players);
	}

	/**
	 * Creates nodes for a square board.
	 * @param size the size of the board
	 * @param players the players on the board
	 * @return the nodes
	 */
	private Set<NodeData> createNodesForSquareBoard(int size, List<Player> players) {
		Square square = new Square();
		return square.getNodes(size, players);
	}

	/**
	 * Creates score that observes all list of upcoming game matches, the score tracks the score for each participating
	 * player.
	 *
	 * @param matches the games to observe
	 * @param players the participating players
	 * @return new score instance
	 */
	private Score createScore(List<Match> matches, List<Player> players) {
		ScoreStrategy scoreStrategy = new TournamentScoreStrategy();
		ScoreImpl score = new ScoreImpl(createScoreItems(players), scoreStrategy);
		for (Match match : matches) {
			match.addObserver(score);
		}
		return score;
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
			ScoreItem item = new ScoreItem(player.getId(), 0);
			items.add(item);
		}
		return items;
	}

}
