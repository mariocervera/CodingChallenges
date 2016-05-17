package problemSolving;

/********************************************************************************************
 * Problem statement:
 * 
 * You are situated in a one dimensional grid of size N at position k. In one step, you can walk
 * one step ahead or behind. In how many ways can you take M steps such that you do not leave
 * the grid at any point?
 * 
 ********************************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class GridWalking_OneDimension {
	
	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying dynamic programming. If we are at position k, the number of
	 * walks can be calculated by adding the walks from position k-1 and k+1 (considering that one step has
	 * already been taken when we arrive at these positions). Thus, we can define the following recurrence
	 * relation: A[m,k] = A[m-1,k-1] + A[m-1,k+1], where A[m,k] represents the number of walks that can be
	 * performed in "m" steps when we are at position "k".
	 * 
	 * Note that the number of possible walks in a given grid grows very fast. This can easily cause integer
	 * overflows. For the sake of clarity, the solution that is presented here does not take overflows into
	 * consideration.
	 * 
	 *****************************************************************************************************/

	public static int countWalks(int N, int M, int k) {
		
		if(N < 2 || M < 0) return 0;
		
		int[][] A = new int[M+1][N+1];
		
		//Base cases
		
		for(int m = 0; m <= M; m++) A[m][0] = 0; 
		
		for(int n = 1; n <= N; n++) {
			
			A[0][n] = 0;
			
			if(n == 1  || n == N) {
				A[1][n] = 1;
			}
			else {
				A[1][n] = 2;
			}
		}
		
		// Recurrence relation
		
		for(int m = 2; m <= M; m++) {
			for(int n = 1; n <= N; n++) {
				
				int left = 0, right = 0;
				
				if(n > 1) {
					left = A[m-1][n-1];
				}
				if(n < N) {
					right = A[m-1][n+1];
				}
				
				A[m][n] = left + right; 
			}
		}
		
		return A[M][k];
	}
}
