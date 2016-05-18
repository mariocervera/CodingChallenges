package problemSolving;

import java.util.ArrayList;
import java.util.List;

/********************************************************************************************
 * Problem statement:
 * 
 * Given a grid of size NxM and a starting position, return a path that visits each cell of
 * the grid exactly once and uses only the moves that are allowed for the knight piece of the
 * Chess game.
 * 
 ********************************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class KnightTour {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying backtracking. This brute-force approach for the knight's tour
	 * problem is impractical on all but the smallest grids. Nonetheless, I use it here for illustration
	 * purposes, since it is a nice example of a backtracking implementation. Other solutions are based
	 * on divide and conquer algorithms and neural networks.
	 * 
	 * A general rule of thumb (extracted from Steven Skiena's book "The Algorithm Design Manual") is:
	 * 
	 * "Combinatorial searches, when augmented with tree pruning techniques, can be used to find the
	 * optimal solution of small optimization problems. How small depends upon the specific problem, but
	 * typical size limits are somewhere between 15 <= n <= 50 items".
	 * 
	 *****************************************************************************************************/
	
	private static boolean solutionFound = false;

	// The size of the grid
	
	private static final int N = 6;
	private static final int M = 6;

	/*
	 * The only public method of this class. Returns a matrix that represents a chess board. Each cell
	 * contains a number that represents a step in the knight's tour
	 */
	public static int[][] getTour(int x, int y) {

		Position startingPosition = new Position(x, y);

		List<Position> tour = new ArrayList<Position>();
		tour.add(startingPosition);

		// Keep track of visited positions using a matrix

		boolean[][] visited = new boolean[N][M];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				visited[i][j] = false;
			}
		}

		// Mark starting position as visited

		visited[x][y] = true;

		// Invoke backtracking algorithm to find solution

		solutionFound = false;
		backtrack(tour, visited);

		// Build the resulting matrix: each cell contains a number that represents a step in the knight's tour

		int[][] result = new int[N][M];
		int size = tour.size();
		for (int i = 0; i < size; i++) {
			Position currentPos = tour.get(i);
			result[currentPos.getX()][currentPos.getY()] = i + 1;
		}

		return result;
	}

	/*
	 * The backtracking algorithm (brute force)
	 */
	private static void backtrack(List<Position> solution, boolean[][] visited) {

		if (isSolution(solution)) {

			// The current tour represents a valid solution --> finish backtracking

			solutionFound = true;
			return;
		}
		else {

			// Obtain the list of candidate positions for the next move

			List<Position> candidates = getCandidates(solution, visited);

			for (Position p : candidates) {

				// Perform the next move (add the candidate position to the partial solution)

				solution.add(p);
				visited[p.getX()][p.getY()] = true;

				// Apply backtracking to generate all possible tours

				backtrack(solution, visited);

				if (solutionFound) {
					return;
				}
				else {

					// Undo move (remove position from solution)

					solution.remove(p);
					visited[p.getX()][p.getY()] = false;
				}
			}
		}
	}

	/*
	 * A solution is a tour of size: rows * columns
	 */
	private static boolean isSolution(List<Position> solution) {

		return (solution.size() >= N * M);
	}

	/*
	 * This method obtains the list of candidate positions for the next move
	 * (based on the last position of the current solution)
	 */
	private static List<Position> getCandidates(List<Position> solution, boolean[][] visited) {

		List<Position> candidates = new ArrayList<Position>();

		Position lastPosition = solution.get(solution.size() - 1);

		int x = lastPosition.getX();
		int y = lastPosition.getY();

		if (x - 1 >= 0 && y - 2 >= 0 && !visited[x - 1][y - 2])
			candidates.add(new Position(x - 1, y - 2));

		if (x + 1 < N && y - 2 >= 0 && !visited[x + 1][y - 2])
			candidates.add(new Position(x + 1, y - 2));

		if (x + 2 < N && y - 1 >= 0 && !visited[x + 2][y - 1])
			candidates.add(new Position(x + 2, y - 1));

		if (x + 2 < N && y + 1 < M && !visited[x + 2][y + 1])
			candidates.add(new Position(x + 2, y + 1));

		if (x + 1 < N && y + 2 < M && !visited[x + 1][y + 2])
			candidates.add(new Position(x + 1, y + 2));

		if (x - 1 >= 0 && y + 2 < M && !visited[x - 1][y + 2])
			candidates.add(new Position(x - 1, y + 2));

		if (x - 2 >= 0 && y + 1 < M && !visited[x - 2][y + 1])
			candidates.add(new Position(x - 2, y + 1));

		if (x - 2 >= 0 && y - 1 >= 0 && !visited[x - 2][y - 1])
			candidates.add(new Position(x - 2, y - 1));

		return candidates;
	}
}

class Position {

	private int x;
	private int y;

	public Position(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
