package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import kth.game.othello.player.Player;
import kth.game.othello.rules.Rules;

/**
 * This class is responsible for tracking the player in turn for a turn-based game.
 * Is observable for player skipping events, which happens when some players cannot make a move and therefore are skipped.
 * 
 * @author Henrik Hygerth
 */
public class PlayerSwitcher extends Observable {

	private static final int NO_PLAYER_IN_TURN = -1;

	private List<Player> players;
	private Rules rules;
	private int numPlayers;
	private int playerInTurn;

	/**
	 * @param players the list of players included in the game
	 * @param rules the rules that the game is using
	 */
	public PlayerSwitcher(List<Player> players, Rules rules) {
		this.players = players;
		this.rules = rules;
		numPlayers = players.size();
		setStartingPlayer();
	}

	/**
	 * @param players the list of players included in the game
	 * @param rules the rules that the game is using
	 * @param playerId the id of the player that should make the first move
	 */
	public PlayerSwitcher(List<Player> players, Rules rules, String playerId) {
		this.players = players;
		this.rules = rules;
		numPlayers = players.size();
		setStartingPlayer(playerId);
	}

	/**
	 * Returns the player in turn.
	 * 
	 * @return the player in turn, or null if no player can make a move
	 */
	public Player getPlayerInTurn() {
		if (playerInTurn == NO_PLAYER_IN_TURN) {
			return null;
		}
		return players.get(playerInTurn);
	}

	/**
	 * Proceeds to the next player that can make a valid move.
	 * Can notify observers if players has been skipped, with the list of player id's skipped.
	 *
	 * @param notify True means that the method should notify player skips.
	 * @return All skipped players that cannot make a move.
	 */
	public List<String> switchToNextPlayer(boolean notify) {
		List<String> skipped = new ArrayList<String>();

		for (int i = 1; i <= players.size(); i++) {
			int index = (playerInTurn + i) % numPlayers;
			String playerId = players.get(index).getId();
			if (rules.hasValidMove(playerId)) {
				playerInTurn = index;

				if(notify && !skipped.isEmpty()) {
					setChanged();
					notifyObservers(skipped);
				}

				return skipped;
			}
			skipped.add(playerId);
		}
		playerInTurn = NO_PLAYER_IN_TURN;
		return new ArrayList<String>();
	}

	public List<String> switchToNextPlayer() {
		return switchToNextPlayer(false);
	}


	/**
	 * Randomly selects a player in turn.
	 */
	private void setStartingPlayer() {
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		playerInTurn = random.nextInt(numPlayers + 1) % numPlayers;
	}

	/**
	 * Sets a specific player in turn.
	 * 
	 * @param playerId the id of the desired player
	 */
	private void setStartingPlayer(String playerId) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getId().equals(playerId)) {
				playerInTurn = i;
			}
		}
	}

}
