package kth.tournament;

import kth.game.othello.player.Player;

import java.util.List;

/**
 * A factory for producing tournaments.
 *
 * @author Mattias Harrysson
 * @author Henrik Hygerth
 */
public interface TournamentFactory {

	/**
	 * Creates a tournament.
	 *
	 * @param players the players to contest in the tournament
	 * @return the tournament
	 */
	public Tournament createTournament(List<Player> players);

}
