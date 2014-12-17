package kth.demo;

import java.util.Arrays;
import java.util.List;

import kth.game.AsciiOthelloGameFactory;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.Player.Type;
import kth.game.othello.player.movestrategy.AggressiveMoveStrategy;
import kth.game.othello.player.movestrategy.NaiveMoveStrategy;
import kth.game.othello.player.movestrategy.PassiveMoveStrategy;
import kth.game.othello.player.movestrategy.RandomMoveStrategy;
import kth.tournament.OthelloTournamentFactory;
import kth.tournament.Tournament;
import kth.tournament.TournamentFactory;

/**
 * Starts an Othello game tournament between four different computers on a classic board using ASCII.
 *
 * @author Henrik Hygerth
 * @author Mattias Harrysson
 */
public class Demo9 {

	/**
	 * Demo entry point.
	 */
	public static void main(String[] args) {
		Type type = Type.COMPUTER;
		Player naivePlayer = new PlayerImpl("p1", "Naive Player", type, new NaiveMoveStrategy());
		Player randomPlayer = new PlayerImpl("p2", "Random Player", type, new RandomMoveStrategy());
		Player aggressivePlayer = new PlayerImpl("p3", "Aggressive Player", type, new AggressiveMoveStrategy());
		Player passivePlayer = new PlayerImpl("p4", "Passive Player", type, new PassiveMoveStrategy());
		List<Player> players = Arrays.asList(naivePlayer, randomPlayer, aggressivePlayer, passivePlayer);

		TournamentFactory factory = new OthelloTournamentFactory(new AsciiOthelloGameFactory());
		Tournament tournament = factory.createTournament(players);
		tournament.start();
	}

}
