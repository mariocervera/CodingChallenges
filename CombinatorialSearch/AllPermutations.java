package problemSolving;

import java.util.ArrayList;
import java.util.List;

/***********************************************************************************
 * Problem statement:
 * 
 * Write a function to find all permutations of the letters in a particular string
 * 
 ***********************************************************************************/

public class AllPermutations {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying backtracking to iterate through all possible solutions of
	 * the search space. Each of these solutions represents a permutation. At each step in the backtracking
	 * algorithm, a single character is added to the solution string. This recursive approach implicitly
	 * builds a tree-based search space that is traversed using Depth-First Search. In this tree, the
	 * internal nodes represent partial solutions and the leaves represent complete solutions (permutations).
	 * 
	 *****************************************************************************************************/

	public static void backtrack(String solution, String input) {

		if (isPermutation(solution, input)) {

			// If the current solution represents a permutation, print it.

			System.out.println(solution);
			
		}
		else {

			// Obtain the list of candidate characters to add to the solution string

			List<Character> candidates = getCandidates(solution, input);

			for (Character c : candidates) {

				// Add character to the solution string

				solution += c;

				// Apply backtracking to generate all permutations

				backtrack(solution, input);

				// Remove character from solution string (backtrack)

				solution = solution.substring(0, solution.length() - 1);
			}
		}
	}

	/*
	 * A solution string is a permutation if it has the same length as the input string
	 */
	private static boolean isPermutation(String solution, String input) {

		return (solution.length() == input.length());
	}

	/*
	 * This method obtains a set of characters that represent candidates for the
	 * next position in the solution string
	 */
	private static List<Character> getCandidates(String solution, String input) {

		List<Character> candidates = new ArrayList<Character>();

		// Initialize the candidates list to the characters of the input string

		char[] inputArray = input.toCharArray();

		for (char c : inputArray) {
			candidates.add(c);
		}

		// Remove the characters that are already present in the solution

		for (int i = 0; i < solution.length(); i++) {

			char c = solution.charAt(i);

			candidates.remove(new Character(c));
		}

		return candidates;
	}
}
