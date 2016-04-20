package problemSolving;

/*************************************************************************************************
 * Problem statement:
 * 
 * A zero-indexed array A consisting of N integers is given. An equilibrium index of this array
 * is any integer P such that 0 <= P < N and the sum of elements of lower indices is equal to the
 * sum of elements of higher indices; that is:
 * 
 * A[0] + A[1] + ... + A[P-1] = A[P+1] + ... + A[N-2] + A[N-1]
 * 
 * Sum of zero elements is assumed to be equal to 0.
 * 
 * Write a function that, given a zero-indexed array A consisting of N integers, returns any of
 * its equilibrium indices. The function should return -1 if no equilibrium index exists.
 * 
 *************************************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class EquilibriumIndex {
	
	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying dynamic programming. We can define an array of size N to store
	 * all the partial sums. The i-th element of this array will contain the sum of the elements from 0 to
	 * i (from the input array A). Using the array of partial sums we can easily search for equilibrium
	 * indexes in O(N) time.
	 * 
	 *****************************************************************************************************/

	public static int equilibriumIndex(int[] A) {

		int N = A.length;

		if (N == 0)
			return -1;

		long[] sums = new long[N];

		// Calculate all the sums (recurrence relation)

		sums[0] = A[0];

		for (int i = 1; i < N; i++) {

			sums[i] = sums[i - 1] + A[i];
		}

		// Look for equilibrium index

		long lowSum = 0, highSum = 0;

		for (int i = 0; i < N; i++) {

			if (i == 0) {
				lowSum = 0;
				highSum = sums[N - 1] - sums[i];
			}
			else if (i == N - 1) {
				lowSum = sums[i - 1];
				highSum = 0;
			}
			else {
				lowSum = sums[i - 1];
				highSum = sums[N - 1] - sums[i];
			}

			if (lowSum == highSum) {
				return i;
			}
		}

		return -1;
	}
}
