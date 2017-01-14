package problemSolving;

import java.util.List;

/****************************************************************************************************
 * Problem statement:
 * 
 * Given an ordered set of integers, which represent the weights of the vertices of a path graph,
 * calculate the maximum-weight Independent Set (IS); that is, a set of vertices of the graph, no
 * two of which are adjacent and whose total weight sum is maximum. The output must be a bit string,
 * where the ith bit must be set to 1 if the ith vertex is in the independent set, and to 0 otherwise.
 * 
 ****************************************************************************************************/

/**
 * @author Mario Cervera
 */
public class MWIndependentSet {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved applying dynamic programming. We store the boundary conditions (i.e., the
	 * IS of an empty graph is the empty set, and the IS of a graph with only one vertex is the vertex itself).
	 * Then, we can build a recurrence relation that calculates in linear time (using an one-dimensional array)
	 * all the other vertices that belong to the IS.
	 * 
	 *****************************************************************************************************/
	
	public static String mwIndependentSet(List<Integer> weights) {

		int N = weights.size();
		
		// Define an array to memoize the values of the subproblems

		long[] mwisTable = new long[N + 1]; // mwisTable[i] stores the value of the maximum weight independent set for the first i vertices

		// Boundary conditions

		mwisTable[0] = 0;
		mwisTable[1] = weights.get(0); // Index 1 represents the first vertex (whose index is 0 in the input array)

		// Recurrence relation

		for (int i = 2; i <= N; i++) {
			mwisTable[i] = Math.max(mwisTable[i - 1], mwisTable[i - 2] + weights.get(i-1));
		}

		// Reconstruct solution (bit string), which represents the vertices that are included in the independent set

		String bitString = "";

		int i = N;
		while (i >= 1) {
			if (i == 1) { // First vertex is included in the independent set
				bitString = "1" + bitString;
				i--;
			} 
			else {
				if (mwisTable[i - 2] + weights.get(i-1) >= mwisTable[i - 1]) { // ith vertex is included
					bitString = "01" + bitString;
					i -= 2;
				} else { // ith vertex is not included
					bitString = "0" + bitString;
					i--;
				}
			}
		}

		return bitString;
	}
}
