package problemSolving;

/****************************************************************************
 * Problem statement:
 * 
 * Use the partitioning idea of Quicksort to give an algorithm that finds the
 * median element of an array of N integers in expected O(N) time.
 * 
 ****************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class QuickSelectMedian {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem is a specific instance of the Quickselect algorithm, which finds the kth smallest element
	 * in an unordered list. In this case, we want to find the (n/2)th smallest element (i.e., the median). 
	 * Quickselect uses the same approach as quicksort to partition data into elements that are less than
	 * or greater than a selected pivot. However, instead of recursing into both sides, Quickselect only
	 * recurses into one side – the side that contains the element that is being searched. This reduces the
	 * average complexity from O(n log n) to O(n).
	 * 
	 *****************************************************************************************************/ 
	
	public static int quickSelectMedian(int[] a, int low, int high) {
		
		if(low >= high) return a[high];
		
		// Carry out the partition, in the same way as Quicksort
		
		int pivot = partition(a, low, high);
		
		// Recurse either into the lower or the higher part of the array
		// depending on the position of the pivot
		
		if(pivot > a.length / 2) {
			
			return quickSelectMedian(a, low, pivot - 1);
		}
		else if(pivot < a.length / 2) {
			
			return quickSelectMedian(a, pivot + 1, high);
		}
		else {
			return a[pivot];
		}	
		
	}
	
	private static int partition(int[] a, int low, int high) {
		
		int pivot = high;
		int firstHigh = low;
		
		for(int i = low; i <= high; i++) {
			
			if(a[i] < a[pivot]) {
				
				swap(a, i, firstHigh);
				firstHigh++;
			}
		}
		
		swap(a, pivot, firstHigh);
		
		return firstHigh;
	}
	
	private static void swap(int[] a, int i, int j)  {
		
		int aux = a[i];
		a[i] = a[j];
		a[j] = aux;
	}
}
