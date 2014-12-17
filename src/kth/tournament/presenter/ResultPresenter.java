package kth.tournament.presenter;

import java.util.List;

/**
 * The responsibility of this class is to present the outcome of a tournament.
 *
 * @author Mattias Harrysson
 */
public interface ResultPresenter {

	/**
	 * Presents the tournament outcome.
	 *
	 * @param results the list of results to present
	 */
	public void present(List<ResultItem> results);

}
