package problemSolving;

/*********************************************************************************************
 * Problem statement:
 * 
 * The longest common substring of two strings X and Y is the longest string that appears as
 * a run of consecutive letters in both strings. For example, the longest common substring of
 * "photograph" and "tomography" is "ograph". Let N = |X| and M = |Y|. Give a O(N*M) dynamic
 * programming algorithm for longest common substring.
 * 
 *********************************************************************************************/

public class LongestCommonSubstring {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * The algorithm below stores in a two-dimensional matrix the lengths of all the possible common
	 * substrings. Each position [i][j] stores the maximum length of the common substring ending in the
	 * i-th and j-th positions (from X and Y, respectively). Once the matrix is filled, the algorithm
	 * searches the maximum value in order to build the resulting substring.  
	 * 
	 *****************************************************************************************************/
	
	public static void longestCommonSubstring(String X, String Y) {
		
		int N = X.length();
		int M = Y.length();
		
		int[][] matrix = new int[N + 1][M + 1];
		
		// Matrix initialization
		
		for(int i = 0; i <= N; i++) {
			
			for(int j = 0; j <= M; j++) {
				
				matrix[i][j] = 0;
			}
		}
		
		// Fill the matrix. Recurrence relation. 

		for(int i = 1; i <= N; i++) {
			
			char c1 = X.charAt(i - 1);
			
			for(int j = 1; j <= M; j++) {
				
				char c2 = Y.charAt(j - 1);
				
				if(c1 != c2) {
					
					//Reset counter when characters differ
					
					matrix[i][j] = 0;
				}
				else {
					//Increase length of common substring
					
					matrix[i][j] = matrix[i-1][j-1] + 1;
				}
			}
		}
		
		// Traverse the matrix to locate maximum substring length and position
		
		int max = 0, max_i = 0, max_j = 0;
		
		for(int i = 1; i <= N; i++) {

			for(int j = 1; j <= M; j++) {
				
				if(matrix[i][j] > max) {
					
					max = matrix[i][j];
					max_i = i;
					max_j = j;
				}
			}
		}
		
		// Build the substring and print the result
		
		String result = "";
		
		while(max_i > 0 && max_j > 0 && matrix[max_i][max_j] != 0) {
			
			result += X.charAt(max_i - 1);
			
			max_i--;
			max_j--;
		}
		
		StringBuilder sb = new StringBuilder(result);
		System.out.println(sb.reverse());
	}
}
