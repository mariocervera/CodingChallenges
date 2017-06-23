package problemSolving;

/********************************************************************************************
 * Problem statement:
 * 
 * Find the number of ways that a given integer X can be expressed as the sum of the Nth power
 * of unique, natural numbers.
 * 
 * Example:
 * 
 * For X = 100 and N = 2 the answer is 3 because there are 3 different ways to add up to 100
 * using powers of 2:
 * 
 * 100^2
 * 
 * 6^2 + 8^2
 * 
 * 1^2 + 3^2 + 4^2 + 5^2 + 7^2
 * 
 ********************************************************************************************/

/**
 * @author Mario Cervera
 */
public class PowerSum {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying dynamic programming. We store partial results in a bidimensional
	 * table A. Each cell A(i,j) stores the number of way we can add up to i using the first j powers of N.
	 * 
	 *****************************************************************************************************/

	public static int powerSum(int X, int N) {

		//  Initialize table
		
		int maxPowerBase = (int) Math.pow(X, (double)1/N);
		int[][] A = new int[X+1][maxPowerBase+1];
		
		for(int i = 0; i <= X; i++) { // all values of X
			for(int j = 0; j <= maxPowerBase; j++) { // all possible bases
				A[i][j] = 0;
			}
		}
		
		// Base case: the number of ways to add up to 0 is always 1.
		
		for(int j = 0; j <= maxPowerBase; j++) {
			A[0][j] = 1;
		}
		
		// Recurrence relation: fill the table iteratively. In each iteration, we build the solution to a 
		// larger subproblem by adding up two cases:
		// 
		// - the last j-th power is included in the solution.
		// - the last j-th power is NOT included.

		for(int x = 1; x <= X; x++) { 
			for(int j = 1; j <= maxPowerBase; j++) {
				int power = (int)Math.pow(j, N);
				if(x >= power) {
					A[x][j] = A[x][j-1] + A[x-power][j-1];
				}
				else {
					A[x][j] = A[x][j-1];
				}
			}
		}
		
		// Return the result, which lies in the last cell of the table.
		
		return A[X][maxPowerBase];
	}
}
