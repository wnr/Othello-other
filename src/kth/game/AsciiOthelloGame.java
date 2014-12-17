package kth.game;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * This class represents the game loop for running a ASCII Othello game.
 *
 * @author Mattias Harrysson
 */
public class AsciiOthelloGame extends Observable implements OthelloGame, Observer {

	protected Othello othello;
	protected List<Player> players;
	private Scanner scanner;

	/**
	 * @param othello the Othello game
	 * @param scanner the human input scanner
	 */
	public AsciiOthelloGame(Othello othello, Scanner scanner) {
		this.othello = othello;
		this.othello.addGameFinishedObserver(this);
		this.scanner = scanner;
		players = othello.getPlayers();
	}

	@Override
	public void start() {
		othello.start();
		run();
	}

	@Override
	public void start(String playerId) {
		othello.start(playerId);
		run();
	}

	/**
	 * Runs the game until no more moves can be made.
	 */
	private void run() {
		printStartMessage();
		while (othello.isActive()) {
			printGameState();
			Player movingPlayer = othello.getPlayerInTurn();
			if (movingPlayer.getType() == Player.Type.COMPUTER) {
				othello.move();
			} else if (othello.hasValidMove(movingPlayer.getId())) {
				try {
					othello.move(movingPlayer.getId(), getHumanMove());
				} catch (IllegalArgumentException e) {
					printErrorMessage(e.getMessage());
				}
			}
		}
		printGameState();
		printEndMessage();
	}

	private void printStartMessage() {
		System.out.println("**** Othello: Game start ****");
		System.out.println();
		int vs = players.size() - 1;
		for (Player player : players) {
			System.out.print(player.getName() + (vs > 0 ? " vs " : ". "));
			vs--;
		}
		System.out.println(othello.getPlayerInTurn().getName() + " is first to move.");
	}

	private void printErrorMessage(String message) {
		System.out.println("Error: " + message);
	}

	private void printGameState() {
		System.out.println();
		printScore();
		System.out.print("\n\n");

		System.out.println(othello.getBoard());
	}

	private void printEndMessage() {
		System.out.println();
		System.out.println("**** Othello: Game Ended ****");
	}

	/**
	 * Read and return input from a human player.
	 *
	 * @return node id to make move to
	 */
	private String getHumanMove() {
		System.out.println();
		System.out.println("Enter move");
		System.out.print("> ");

		int x = getNextInputInt();
		int y = getNextInputInt();

		scanner.nextLine(); // consume \n
		System.out.println();

		return "" + x + "-" + y;
	}

	/**
	 * Returns next integer from input scanner.
	 *
	 * @return next input integer, or -1 if invalid input
	 */
	private int getNextInputInt() {
		if (scanner.hasNextInt()) {
			return scanner.nextInt();
		} else {
			scanner.next(); // skip
			return -1;
		}
	}

	/**
	 * Prints current score for all participating players.
	 */
	private void printScore() {
		System.out.print("Score: ");
		for (ScoreItem item : othello.getScore().getPlayersScore()) {
			System.out.print(getPlayerNameFromId(item.getPlayerId()) + " (" + item.getScore() + ") ");
		}
	}

	/**
	 * Returns player name from specified player id.
	 *
	 * @param playerId the player id to search after
	 * @return the player name
	 */
	private String getPlayerNameFromId(String playerId) {
		String result = null;
		for (Player player : othello.getPlayers()) {
			if (player.getId() == playerId) {
				result = player.getName();
				break;
			}
		}
		return result;
	}

	@Override
	public void update(Observable observable, Object object) {
		setChanged();
		notifyObservers(othello.getScore());
	}

}