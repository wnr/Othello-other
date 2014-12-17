package kth.game.othello;

import java.util.List;
import java.util.Random;

import kth.game.othello.player.Player;
import kth.game.othello.rules.Rules;

/**
 * This class is responsible for tracking the player in turn for a turn-based game.
 * 
 * @author Henrik Hygerth
 */
public class PlayerSwitcher {

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
	 */
	public void switchToNextPlayer() {
		for (int i = 1; i <= players.size(); i++) {
			int index = (playerInTurn + i) % numPlayers;
			String playerId = players.get(index).getId();
			if (rules.hasValidMove(playerId)) {
				playerInTurn = index;
				return;
			}
		}
		playerInTurn = NO_PLAYER_IN_TURN;
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
