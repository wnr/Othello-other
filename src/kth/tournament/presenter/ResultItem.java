package kth.tournament.presenter;

/**
 * A representation of a element in a result.
 *
 * @author Mattias Harrysson
 */
public class ResultItem {

	private String name;
	private int score;

	/**
	 * @param name the name of the contestant
	 * @param score the score for the contestant
	 */
	public ResultItem(String name, int score) {
		this.name = name;
		this.score = score;
	}

	/**
	 * @return the name of the contestant
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the score for the contestant
	 */
	public int getScore() {
		return score;
	}

}
