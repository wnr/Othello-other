package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * Describes a player for a turn-based game.
 *
 * @author Mattias Harrysson
 * @author Henrik Hygerth
 */
public class PlayerImpl implements Player {

	private String id;
	private String name;
	private Type type;
	private MoveStrategy moveStrategy;

	/**
	 * @param id the player id
	 * @param name the player name
	 * @param type the type of player
	 * @param moveStrategy the move strategy to use
	 */
	public PlayerImpl(String id, String name, Type type, MoveStrategy moveStrategy) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.moveStrategy = moveStrategy;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public MoveStrategy getMoveStrategy() {
		return moveStrategy;
	}

	@Override
	public void setMoveStrategy(MoveStrategy moveStrategy) {
		this.moveStrategy = moveStrategy;
	}

}
