package problemSolving;

/*******************************************************************************************
 * Problem statement:
 * 
 * Consider the following data compression technique. We have a table of M text strings,
 * each at most K in length. We want to encode a data string D of length N using as few
 * text strings as possible. For example, if our table contains (a, ba, abab, b) and the
 * data string is bababbaababa, the best way to encode it is (b,abab,ba,abab,a) for a total
 * of five code words. Give an O(N*M*K) algorithm to find the length of the best encoding.
 * You may assume that every string has at least one encoding in terms of the table.
 * 
 *******************************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class EncodingLength {

	/*******************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying dynamic programming. We declare an array A whose i-th element
	 * stores the length of the best encoding for the substring ending at i. By defining a base case - i.e.,
	 * the empty string needs zero words to be encoded (A[0]=0) - we can define a recurrence relation to
	 * calculate the rest of the elements of the array. The solution to the problem resides in A[N].
	 * 
	 *******************************************************************************************************/
	
	public static int encodingLength(String stringToEncode, String[] textStrings) {
		
		int N = stringToEncode.length();
		
		int[] A = new int[N + 1];
		
		// Initialize array
		
		for(int i = 0; i <= N; i++) A[i] = Integer.MAX_VALUE - 1;
		
		// Base case (boundary condition: empty string needs zero words to be encoded)
		
		A[0] = 0;
		
		// Fill array (recurrence relation)
		
		for(int i = 1; i <= N; i++) {
			
			for(int j = 0; j < i; j++) {
				
				if(A[j] + 1 < A[i]) { //Possible improvement ...
					
					if(availableTextString(stringToEncode, textStrings, i, j)) {
						
						A[i] = A[j] + 1;
					}
				}
			}
		}
		
		// Return the result
		
		return A[N];
	}
	
	/*
	 * This method checks whether the substring that goes from the j-th to the i-th position of the
	 * input data string matches any of the words contained in the "textStrings" array.
	 */
	private static boolean availableTextString(String stringToEncode, String[] textStrings, int i, int j) {
		
		String s = stringToEncode.substring(j, i);
		
		for(int k = 0; k < textStrings.length; k++) {
			
			if(textStrings[k].equals(s)) return true;
		}
		
		return false;
	}
}
