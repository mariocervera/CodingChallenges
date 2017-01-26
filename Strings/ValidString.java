package problemSolving;

import java.util.Arrays;

/******************************************************************************************
 * Problem statement:
 * 
 * A "valid" string is a string S such that, for all distinct characters in S, each such
 * character occurs the same number of times in S.
 * 
 * For example, "aabb" is a valid string because the frequency of both characters a and b is 2,
 * whereas "aabbc" is not a valid string because the frequency of characters a, b, and c is not
 * the same.
 * 
 * Watson gives a string S to Sherlock and asks him to remove some characters from the string
 * such that the new string is a "valid" string.
 * 
 * Sherlock wants to know from you if it is possible to be done with less than or equal to one
 * removal.
 *  
 ******************************************************************************************/

/**
 * @author Mario Cervera
 */
public class ValidString {
	
	/****************************************************************************************
	 * Solution:
	 * 
	 * We can define an array of 26 counters that stores the frequency of appearance of each
	 * character, and, then, iterate this array to check whether we can obtain a valid string
	 * in the given number of operations.
	 * 
	 ****************************************************************************************/

	public static boolean validString(String S) {

		// Array holding letter counters

		int[] counters = new int[26];

		int len = S.length();

		for (int i = 0; i < len; i++) {
			char c = S.charAt(i);
			counters[c - 'a']++;
		}

		// Sort the counters

		Arrays.sort(counters);

		// Look for the first value that is != 0

		int counterFirstChar = 0;
		int i = 0;
		while (counterFirstChar == 0 && i < 26) {
			counterFirstChar = counters[i];
			i++;
		}
		
		// If the counters are all 0, the string is valid

		if (counterFirstChar == 0) { 
			return true;
		}
		else {

			boolean wildcardUsed = false; // I can afford one change

			if (counterFirstChar == 1 && i < 26 && counterFirstChar != counters[i]) {
				
				// If the first counter is 1 and the next is not one, I must use the wildcard
				
				counterFirstChar = counters[i];
				wildcardUsed = true;
				i++;
			}

			int j = i;
			for (; j < 26; j++) { // Iterate the counters ...

				if (counters[j] != counterFirstChar) { // When one differs ...

					if (wildcardUsed || Math.abs(counters[j] - counterFirstChar) > 1) {
						return false;
					}
					else if (Math.abs(counters[j] - counterFirstChar) == 1) {
						wildcardUsed = true;
					}
				}
			}

			if (j == 26) { // If the counter array is fully traversed, the string is valid
				return true;
			}
		}

		return false;
	}
}
