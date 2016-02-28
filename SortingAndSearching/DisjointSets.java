package problemSolving;

import java.util.Arrays;

/****************************************************************************
 * Problem statement:
 * 
 * Give an efficient algorithm to determine whether two arrays are disjoint.
 * 
 ****************************************************************************/

public class DisjointSets {

	/****************************************************************************
	 * Solution:
	 * 
	 * We can sort one of the two input arrays (say A) and, then, iterate the
	 * other array (say B) to determine whether the elements of B appear in A. We
	 * can apply binary search on A for this purpose. If N is the size of A and M
	 * the size of B, the complexity of this algorithm is O(N+M log N). This is
	 * because we get O(N log N) for the sorting step plus O(M log N) for the cost
	 * of iterating B and carrying out binary search on A. 
	 * 
	 ****************************************************************************/
	
	public static boolean areDisjoint(int[] a, int[] b) {
		
		// First, sort one of the arrays
		
		Arrays.sort(a);
		
		// Apply binary search to check disjointness in logarithmic time
		
		for(int i = 0; i < b.length; i++) {
			
			if(binarySearch(a, b[i], 0, a.length-1)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean binarySearch(int[] a, int key, int low, int high) {
		
		if(low > high) return false;
		
		int middle = (low + high) / 2;
		
		if(a[middle] == key) return true;
		
		if(a[middle] > key) {
			
			return binarySearch(a, key, low, middle-1);
		}
		else {
			return binarySearch(a, key, middle+1, high);
		}
	}
}
