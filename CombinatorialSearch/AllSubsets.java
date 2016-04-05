package problemSolving;

import java.util.ArrayList;
import java.util.List;

/********************************************************************************************
 * Problem statement:
 * 
 * Implement an efficient algorithm for listing all subsets of an universal set of N numbers
 * 
 ********************************************************************************************/

public class AllSubsets {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying backtracking to iterate through all possible solutions of the
	 * search space. Each of these solutions represents a subset. This recursive approach implicitly builds
	 * a tree-based search space that is traversed using Depth-First Search. In this tree, the internal
	 * nodes represent partial solutions and the leaves represent complete solutions (subsets).
	 * 
	 *****************************************************************************************************/

	/*
	 * The backtracking algorithm. Subsets (i.e., the solutions) are represented as arrays of booleans.
	 */
	public static void backtrack(List<Boolean> solution, List<Integer> input) {

		if (solution == null) {
			solution = new ArrayList<Boolean>();
		}

		if (isSubset(solution, input)) {

			// If the current solution represents a subset, process it.

			processSolution(solution, input);

		}
		else {

			// The candidates for the next position of a solution are always  "true" and "false"

			boolean[] candidates = { true, false };

			for (boolean bool : candidates) {

				// Add candidate to the current subset

				solution.add(bool);

				// Apply backtracking to generate all permutations

				backtrack(solution, input);

				// Remove last candidate from the current subset (backtrack)

				solution.remove(solution.size() - 1);
			}
		}
	}

	/*
	 * A solution string is a permutation if it has the same length as the input string
	 */
	private static boolean isSubset(List<Boolean> solution, List<Integer> input) {

		return (solution.size() == input.size());
	}

	/*
	 * This method just prints a solution
	 */
	private static void processSolution(List<Boolean> solution, List<Integer> input) {

		String result = "{";

		for (int i = 0; i < solution.size(); i++) {

			if (solution.get(i)) {
				result += (input.get(i) + ",");
			}
		}

		// Remove last "," (when no empty set)

		if (result.length() > 1) {
			result = result.substring(0, result.length() - 1);
		}

		result += "}";

		System.out.println(result);
	}

}
