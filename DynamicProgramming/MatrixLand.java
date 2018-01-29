package problemSolving;

/********************************************************************************************
 * Problem statement:
 * 
 * You are playing a matrix-based game with the following setup and rules:
 * 
 *  - You are given a matrix A with n rows and m columns. Each cell contains an integer. When a
 *  player passes a cell their score increases by the number written in that cell and the
 *  number in the cell becomes 0.
 *  
 *  - The player starts from any cell in the first row and can move left, right or down.
 *  
 *  - The game is over when the player reaches the last row and stops moving.
 *  
 *  The challenge is to find the maximum-weight path. An example can be found in the image of
 *  the link below. The solution in this example is 37.
 *  
 *  https://www.dropbox.com/s/228xzu7ccd2j0ta/MatrixLandExample.png?dl=0
 * 
 ********************************************************************************************/

/**
 * @author Mario Cervera
 */
public class MatrixLand {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * The fact that the player can only make progress by going left, right, and down (and not up) establishes
	 * a well-defined ordering that suggests the possibility to use dynamic programming. This is, in fact, the
	 * case.
	 * 
	 * Observe that we can create a bi-dimensional table "dp" where each position [x,y] stores the maximum sum
	 * of numbers that you can get when finishing the path at [x,y]. The following image
	 * 
	 * https://www.dropbox.com/s/ow9amqyvgm5n7b6/MatrixLandSol.png?dl=0
	 * 
	 * illustrates that the general way to arrive at [x,y] (and obtain the maximum score you can get) is from
	 * an intermediate position [x-1,i] in the previous row.
	 * 
	 * This suggests the following recurrence relation: 
	 * 
	 * dp[x][y] = max(mslit[x][y] + msr[x][y+1], msrit[x][y] + msl[x][y-1])
	 * 
	 * which uses four additional tables. These tables have the following semantics:
	 * 
	 * - msl[x,y] : the maximum score that you can get by moving only to the left from [x,y]
	 * - msr[x,y] : the maximum score that you can get by moving only to the right from [x,y]
	 * - mslit[x,y] : the maximum score that you can get by moving only to the left from [x,y] also including the
	 * position above [x,y], that is [x-1][y]
	 * - msrit[x,y] : the maximum score that you can get by moving only to the right from [x,y] also including the
	 * position above [x,y], that is [x-1][y]
	 * 
	 * Further information at:
	 * 
	 * https://www.hackerrank.com/contests/w35/challenges/matrix-land/problem
	 * 
	 *****************************************************************************************************/

	static int matrixLand(int[][] A, int n, int m) {
    	
    	int[][] dp = new int[n+1][m+1];
    	int[][] msl = new int[n+2][m+2];
    	int[][] msr = new int[n+2][m+2];
    	int[][] mslit = new int[n+2][m+2];
    	int[][] msrit = new int[n+2][m+2];
    	
    	for(int i = 1; i <= n; i++) {
    		
    		// Maximum sum left
    		
    		for(int j = 1; j <= m; j++) {
    			msl[i][j] = Math.max(msl[i][j-1] + A[i][j], 0);
        	}
    		
    		// Maximum sum right
    		
    		for(int j = m; j > 0; j--) {
    			msr[i][j] = Math.max(msr[i][j+1] + A[i][j], 0);
        	}
    		
    		// Final result of previous row
    		
    		for(int j = 1; j <= m; j++) {
    			dp[i-1][j] = Math.max(mslit[i-1][j] + msr[i-1][j+1], msrit[i-1][j] + msl[i-1][j-1]);
        	}
    		
    		// Maximum sum left including top
    		
    		mslit[i][1] = dp[i-1][1] + A[i][1];
    		for(int j = 2; j <= m; j++) {
    			mslit[i][j] = Math.max(mslit[i][j-1] + A[i][j], dp[i-1][j] + msl[i][j-1] + A[i][j]);
        	}
    		
    		// Maximum sum right including top
    		
    		msrit[i][m] = dp[i-1][m] + A[i][m];
    		for(int j = m-1; j > 0; j--) {
    			msrit[i][j] = Math.max(msrit[i][j+1] + A[i][j], dp[i-1][j] + msr[i][j+1] + A[i][j]);
    		}
    	}
    	
    	// Fill last row of dp table
    	
    	for(int j = 1; j <= m; j++) {
			dp[n][j] = Math.max(mslit[n][j] + msr[n][j+1], msrit[n][j] + msl[n][j-1]);
    	}
    	
    	// Calculate result (maximum value of last row)
    	
    	int max = dp[n][1];
        for(int j = 2; j <= m; j++){
            max = Math.max(max, dp[n][j]);
        }
    	
        return max;
    }
}
