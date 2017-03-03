package problemSolving;

/**********************************************************************************************
 * Problem statement:
 * 
 * A gene is represented as a string of length n (where n is divisible by 4) composed of the
 * letters A, C, G, and T. It is considered to be steady if each of the four letters occurs
 * exactly n/4 times. For example, AAGTGCCT is a steady gene.
 * 
 * The problem involves the conversion of a given String S (of length n divisible by 4) into
 * a steady gene. To do this, we can only choose one (maybe empty) substring of S and replace
 * it with any string of the same length. This substring must be the shortest possible.
 * 
 * For example, we can convert GAAATAAA into an steady gene by replacing the substring AAATA with
 * TTCCG to obtain GTTCCGAA. The length of the substring is 5, which is optimal.
 *
 **********************************************************************************************/

/**
 * @author Mario Cervera
 */
public class SteadyGene {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * Using the count (in S) of each character, we can calculate the exact number of times each character
	 * must appear in the final substring. To find the shortest possible such substring, we can use a sliding
	 * window that traverses S in O(n) time.
	 * 
	 *****************************************************************************************************/ 
	
	public static int steadyGene(String s) {
		
		int n = s.length();
		
		if(n % 4 != 0) return -1; // Incorrect input

		// Initialize array of counters

		int[] charCounts = new int[4];
		charCounts[0] = 0; // A
		charCounts[1] = 0; // C
		charCounts[2] = 0; // G
		charCounts[3] = 0; // T

		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			charCounts[getIndex(c)]++;
		}

		// Calculate number of time each character must appear in the final substring

		int steadyValue = n / 4;

		for (int i = 0; i <= 3; i++) {
			if (charCounts[i] - steadyValue > 0) {
				charCounts[i] -= steadyValue;
			}
			else {
				charCounts[i] = 0;
			}
		}

		// Initialize counters of the sliding window

		int[] charInWindow = new int[4]; // A, C, G, T
		charInWindow[0] = 0;
		charInWindow[1] = 0;
		charInWindow[2] = 0;
		charInWindow[3] = 0;

		int posIni = -1;
		int posFin = -1;

		// Step 1: set the initial position of the pointers

		for (int i = 0; i < n && posIni == -1 && posFin == -1; i++) {
			char currentChar = s.charAt(i);
			if (charCounts[getIndex(currentChar)] > 0) {
				posIni = i;
				posFin = i;
				charInWindow[getIndex(currentChar)]++;
			}
		}

		// Step 2: find the first window containing all of the required characters

		boolean windowFull = false;

		for (int i = posIni + 1; i < n && !windowFull; i++) {
			char currentChar = s.charAt(i);
			int indexCurrentChar = getIndex(currentChar);

			if (charCounts[indexCurrentChar] == 0) continue;  // This char is not relevant

			// Include char in window

			charInWindow[indexCurrentChar]++;
			posFin = i;

			windowFull = windowFull(charInWindow, charCounts);
		}

		if (!windowFull) return 0;

		// Step 3: Traverse entire string trying to reduce the window length

		int minLength = posFin - posIni + 1;

		for (int i = posFin + 1; i < n; i++) {

			char currentChar = s.charAt(i);
			int indexCurrentChar = getIndex(currentChar);

			if (charCounts[indexCurrentChar] == 0) continue; // This char is not relevant

			posFin = i;

			char charIni = s.charAt(posIni);

			if (charIni == currentChar) { // Update posIni

				boolean updated = false;

				do {
					posIni++;

					char c = s.charAt(posIni);
					int indexC = getIndex(c);

					if (charCounts[indexC] == 0) continue; // This char is not relevant
					
					if (charInWindow[indexC] > charCounts[indexC]) {
						charInWindow[indexC]--;
					}
					else {
						updated = true;
					}
				}
				while (!updated);
			}
			else { // Update window counts

				charInWindow[getIndex(currentChar)]++;
			}

			// Update minimum length

			if ((posFin - posIni + 1) < minLength) {
				minLength = posFin - posIni + 1;
			}
		}

		return minLength;
	}
	
	/*
	 * Returns the array index of a given character
	 */
	private static int getIndex(char c) {

		if (c == 'A') return 0;
		if (c == 'C') return 1;
		if (c == 'G') return 2;

		return 3;
	}

	/*
	 * Checks whether the sliding window contains all of the required characters
	 */
	private static boolean windowFull(int[] charInWindow, int[] charCounts) {

		for (int i = 0; i <= 3; i++) {
			if (charInWindow[i] < charCounts[i])
				return false;
		}

		return true;
	}
}