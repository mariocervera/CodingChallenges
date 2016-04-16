package problemSolving;

/******************************************************************************************
 * Problem statement:
 * 
 * Given a set of coin denominators, find the minimum number of coins to make a certain
 * amount of change.
 * 
 ******************************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class CoinsChange {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying dynamic programming. We store the boundary condition (i.e., zero
	 * coins are needed to make zero amount of money) in an array, and, then, we use this value to calculate
	 * the number of coins needed for all amounts of money from 0 to N (our target amount). Each of these
	 * amounts is represented by a position in the array.
	 * 
	 *****************************************************************************************************/
	
	public static int minimumNumberCoins(int amount, int[] coinDenom) {
		
		// Each position of the array stores the minimum number of coins needed to
		// obtain the amount of change that is represented by the index of the array.
		
		int[] numberOfCoins = new int[amount + 1];
		
		// Base case (boundary condition)
		
		numberOfCoins[0] = 0;
		
		// Array initialization
		
		for(int i = 1; i <= amount; i++) {
			
			numberOfCoins[i] = Integer.MAX_VALUE; 
		}
		
		// For each amount from 1 to N ...
		
		for(int i = 1; i <= amount; i++) {
			
			// For each coin denominator ...
			
			for(int j = 0; j < coinDenom.length; j++) {
				
				int coin = coinDenom[j];
				
				// Update mininum value, if appropriate
				
				if(coin <= i && numberOfCoins[i - coin] + 1 < numberOfCoins[i]) {
					
					numberOfCoins[i] = numberOfCoins[i - coin] + 1;
				}
			}
		}
		
		return numberOfCoins[amount];
	}
}
