package problemSolving;

/********************************************************************************************
 * Problem statement:
 * 
 * How many different ways can you make change for an amount N, given a list C of M coin
 * denominators? Assume that there is an infinite amount of coins available per denominator.
 * 
 * Example:
 * 
 * For N=4 and C={1,2,3} there are four solutions {1,1,1,1}, {1,1,2}, {2,2}, and {1,3}.
 * 
 ********************************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class CoinsWays {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying dynamic programming. We store partial results in a bidimensional
	 * matrix A. Each cell (m,n) of this matrix indicates the number of ways we can obtain the amount "n"
	 * using only the first "m" coins. The result of the problem lies in A[M][N].
	 * 
	 *****************************************************************************************************/
	
	public static long coinWays(int N, int[] C) {
		
        int M = C.length;
        
        // Matrix to store partial results
        
        long[][] A = new long[M+1][N+1];
        
        // Boundary conditions
        
        for(int m = 0; m <= M; m++) {
            A[m][0] = 1;
        }
        
        // Recurrence relation
        // A[m][n]: The number of ways we can make change for "n" using only the first "m" coins is equal to:
        //             - the number of ways we can make change for "n" using only the first "m-1" coins, plus ...
        //             - the number of ways we can make change for "n-C[m]" using the first "m" coins.
        
        for(int m = 1; m <= M; m++) {
            for(int n = 1; n <= N; n++) {
            
                if(n >= C[m-1]) {
                    A[m][n] = A[m-1][n] + A[m][n-C[m-1]];
                }
                else {
                    A[m][n] = A[m-1][n];
                }
            }
        }
        
        return A[M][N];
	}
}
