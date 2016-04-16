package problemSolving;

/******************************************************************************************
 * Problem statement:
 * 
 * You are given an array of N numbers, each of which may be positive, negative, or zero.
 * Give an efficient algorithm to identify the index positions i and j to the maximum sum
 * of the i-th through j-th numbers.
 * 
 ******************************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class MaximumIntervalSum {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved in O(N) time by making clever use of various pointers. Nonetheless, for
	 * illustration purposes, I provide below a Dynamic Programming version of the solution. The algorithm
	 * runs in O(N^2) time. It uses a two-dimensional matrix to store all the possible sums of intervals.
	 * Then, the algorithm searches the maximum value of the matrix to determine the indexes.
	 * 
	 *****************************************************************************************************/
	
	public static void maximumIntervalSum(int[] numbers) {
		
		int N = numbers.length;
		
		int[][] matrix = new int[N][N];
		
		//Matrix initialization
		
		for(int i = 0; i < N; i++) {
			
			for(int j = 0; j < N; j++) {
				
				matrix[i][j] = Integer.MIN_VALUE;
			}
		}
		
		//Boundary conditions
		
		for(int i = 0; i < N; i++) {
			
			matrix[i][i] = numbers[i];
		}
		
		//Fill rest of the matrix (recurrence relation)
		
		for(int i = 0; i < N; i++) {
			
			for(int j = i + 1; j < N; j++) {
				
				matrix[i][j] = matrix[i][j-1] + numbers[j];
			}
		}
		
		//Find the maximum value of the matrix
		
		int max = Integer.MIN_VALUE;
		int max_i = -1;
		int max_j = -1;
		
		for(int i = 0; i < N; i++) {
			
			for(int j = 0; j < N; j++) {
				
				if(matrix[i][j] > max) {
					
					max = matrix[i][j];
					max_i = i;
					max_j = j;
				}
			}
		}
		
		// Print solution
		
		System.out.println("Maximum sum: " + max);
		System.out.println("Positions: i = " + max_i + ", j = " + max_j);
	}
	
}
