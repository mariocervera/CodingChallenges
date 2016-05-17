package problemSolving;

/********************************************************************************************
 * Problem statement:
 * 
 * You are situated in a N dimensional grid at position X1, X2, ..., Xn. The sizes of the
 * dimensions of the grid are D1, D2, ..., Dn. In one step, you can walk one step ahead or
 * behind in any one of the N dimensions. In how many ways can you take M steps such that you
 * do not leave the grid at any point?
 * 
 * Note: this problem extends Grid Walking (one dimension) - See: GridWalking_OneDimension.java 
 * 
 ********************************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class GridWalking_Ndimensional {

	/*******************************************************************************************************************
	 * Solution:
	 * 
	 * To solve this problem, it is good to start by simplifying the problem to one dimension and then extend the solution.
	 * Have a look at GridWalking_OneDimension.java for a solution for one-dimensional grids. Let's now assume that we have
	 * already calculated a matrix paths(d,k), which stores the number of possible paths for each dimension "d" (individually)
	 * and for each number of steps "k" (from 0 to M). The next step is to combine the dimensions. Suppose, for example,
	 * that we want to integrate the paths of dimensions 1 and 2. If the total number of steps is 3 (M = 3), this means
	 * that we have to consider four cases:
	 *   
	 *   1) paths(1,0) and paths(2,3) --> 0 steps are performed in dimension 1, and 3 steps are performed in dimension 2.
	 *   2) paths(1,1) and paths(2,2) --> 1 step is performed in dimension 1, and 2 steps are performed in dimension 2.
	 *   3) paths(1,2) and paths(2,1) --> 2 steps are performed in dimension 1, and 1 step is performed in dimension 2.
	 *   4) paths(1,3) and paths(2,0) --> 3 steps are performed in dimension 1, and 0 steps are performed in dimension 2.
	 *   
	 * For example, in case 2), we obtain three possible sequences: {d1,d2,d2}, {d2,d1,d2}, and {d2,d2,d1}. This means that
	 * we take one step in dimension 1 and two steps in dimension 2, in any order. Here is where binomial coefficients C(n,k)
	 * come into the picture. Binomial coefficients count "in how many ways we can select k elements out of n elements". In
	 * this example, we need to calculate C(3,1) = 3. In general, the number of paths considering dimension 1 and dimension
	 * 2 will be the sum from i=0 to i=M of C(M,i) * paths(1,i) * paths(2,M-i). Applying a dynamic programming algorithm, we
	 * can calculate this recurrence relation, and, thus, obtain the final result for the N dimensions of the grid.
	 * 
	 * Note that the number of possible paths in a given grid grows very fast. For this reason, the result
	 * is calculated modulo 1000000007. Additionally, the algorithm considers the following constraints:
	 * 
	 * 1 <= N <= 10
	 * 1 <= M <= 300
	 * 2 <= Di <= 100
	 * 1 <= Xi <= Di
	 * 
	 * The arrays "dimension" and "position" must be of size N+1. Thus, the size of the first dimension will be stored at
	 * index 1 of the array "dimension" (same for positions).
	 * 
	 ***********************************************************************************************************************/
	
	private static long p = 1000000007L;

	public static long countWalks(int[] dimension, int N, int M, int[] position) {

		// First, calculate binomial coefficients (applying the recurrence of
		// Pascal's triangle)

		long[][] choose = new long[310][310];

		for (int i = 0; i < 310; i++) {

			choose[i][0] = choose[i][i] = 1;

			for (int j = 1; j < i; j++) {

				choose[i][j] = (choose[i - 1][j - 1] + choose[i - 1][j]) % p;
			}
		}

		// **************************************************************************************
		// ****          Calculate number of paths in each dimension (individually)          ****
		// **** This part corresponds to the problem solved in GridWalking_OneDimension.java ****
		// **************************************************************************************

		long[][] paths = new long[N + 1][M + 1]; // Dimension - paths for 1..M steps

		for (int m = 0; m <= M; m++)
			paths[0][m] = 1;

		for (int d = 1; d < dimension.length; d++) {

			int dimSize = dimension[d];

			long[][] matrix = new long[M + 1][dimSize + 1];

			// Base cases

			for (int m = 0; m <= M; m++)
				matrix[m][0] = 1;

			for (int n = 1; n <= dimSize; n++) {

				matrix[0][n] = 1;

				if (n == 1 || n == dimSize) {
					matrix[1][n] = 1;
				} 
				else {
					matrix[1][n] = 2;
				}
			}

			// Recurrence relation

			for (int m = 2; m <= M; m++) {
				for (int n = 1; n <= dimSize; n++) {

					long left = 0, right = 0;

					if (n > 1) {
						left = matrix[m - 1][n - 1];
					}
					if (n < dimSize) {
						right = matrix[m - 1][n + 1];
					}

					matrix[m][n] = ((left % p) + (right % p)) % p;
				}
			}

			// Copy result

			for (int m = 0; m <= M; m++)
				paths[d][m] = matrix[m][position[d]];
		}

		// *******************************************************************
		// **** Use the "paths" array to merge dimensions. Use C(n,k) % p ****
		// *******************************************************************

		long[][] pathsIntegration = new long[N + 1][M + 1];

		// Base cases

		for (int m = 0; m <= M; m++)
			pathsIntegration[0][m] = 1L;

		for (int n = 0; n <= N; n++)
			pathsIntegration[n][0] = 1L;

		for (int m = 1; m <= M; m++)
			pathsIntegration[1][m] = paths[1][m];

		// Recurrence relation

		for (int d = 2; d < dimension.length; d++) {
			for (int m = 1; m <= M; m++) {

				long result = 0;

				for (int i = 0; i <= m; i++) {

					long binomialMod = choose[m][i];
					long operand1 = pathsIntegration[d - 1][i];
					long operand2 = paths[d][m - i];

					long prod1 = (binomialMod * operand1) % p;
					long prod2 = (prod1 * operand2) % p;

					result += prod2;
				}

				pathsIntegration[d][m] = result % p;
			}
		}

		return pathsIntegration[N][M];
	}
}
