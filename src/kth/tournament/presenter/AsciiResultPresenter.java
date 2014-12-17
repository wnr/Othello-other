package kth.tournament.presenter;

import java.util.List;

/**
 * The responsibility of this class is to present the outcome of a tournament to standard output.
 *
 * @author Mattias Harrysson
 */
public class AsciiResultPresenter implements ResultPresenter {

	@Override
	public void present(List<ResultItem> results) {
		System.out.print("Tournament Score: ");
		for (ResultItem result : results) {
			System.out.print(result.getName() + " (" + result.getScore() + ") ");
		}
	}

}
