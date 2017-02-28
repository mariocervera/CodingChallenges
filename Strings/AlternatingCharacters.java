package problemSolving;

/****************************************************************************************************
 * Problem statement:
 * 
 * A string T always consists of two distinct alternating characters, for example "xyxyx" or "yxyxy".
 * 
 * Given a string S over the English alphabet, convert it into a string T that only contains two alternating
 * characters. This can be done by removing characters from S. For example, if S is equal to "dabdaccbcd" and
 * you delete all occurrences of characters "c" and "d", then S becomes T = "abab".
 * 
 * Report the maximum possible length of T.
 * 
 ****************************************************************************************************/

/**
 * @author Mario Cervera
 */
public class AlternatingCharacters {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * A possible solution consists of iterating over all pairs of characters. Observe that there are only
	 * (26*25) / 2 = 325 possibilities, which is, in effect, a (somewhat high) constant value. Then, you can
	 * scan S in linear time for each pair of characters and store the maximum length of T that you find.
	 * The time complexity of this solution is O(|S|)
	 * 
	 *****************************************************************************************************/
	
	public static int alternatingCharacters(String s) {
		
		int len = s.length();
		
		// Initialize a bit array to keep track of which characters exist in S and which characters do not.
		
		boolean[] charExists = new boolean[26];

		for (int i = 0; i < 26; i++)
			charExists[i] = false;

		for (int i = 0; i < len; i++) {
			int charValue = s.charAt(i) - 'a';
			charExists[charValue] = true;
		}
		
		// Iterate all pairs of possible characters and report maximum length of T

		int maxLen = 0;

		for (int i = 0; i < 26; i++) {
			for (int j = i + 1; j < 26; j++) {

				if (charExists[i] && charExists[j]) { // Only if both characters appear in S ...

					int currentLen = 0;
					boolean valid = true;

					int lastCharValue = -1;
					int firstIndex = -1;

					// Get the first appearance (in S) of "i" or j""
					
					for (int k = 0; k < len && lastCharValue == -1; k++) {
						int aux = s.charAt(k) - 'a';
						if (aux == i || aux == j) {
							lastCharValue = aux;
							currentLen++;
							firstIndex = k;
						}
					}

					if (lastCharValue == -1) {
						valid = false;
					}
					
					// Iterate the characters of S, keeping track of the last character ("i" or "j") that you see (to check
					// whether they appear in consecutive positions)

					for (int k = firstIndex + 1; k < len && valid; k++) {

						int currentCharValue = s.charAt(k) - 'a';

						if (currentCharValue == i || currentCharValue == j) {

							if (lastCharValue == currentCharValue) {
								valid = false;
							}
							else {
								currentLen++;
							}

							lastCharValue = currentCharValue;
						}
					}
					
					// Update maximum length

					if (valid && currentLen > maxLen) {
						maxLen = currentLen;
					}
				}
			}
		}

		return maxLen;
	}
}
