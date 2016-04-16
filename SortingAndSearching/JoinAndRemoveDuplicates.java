package problemSolving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/****************************************************************************
 * Problem statement:
 * 
 * Give an O(N*log(N)) algorithm that joins two arrays of integers. The output
 * should not contain duplicates.
 * 
 ****************************************************************************/

/**
 * 
 * @author Mario Cervera
 *
 */
public class JoinAndRemoveDuplicates {

	
	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved efficiently by sorting the input arrays first. Then, the output array is
	 * built by subsequently adding the minimum element of each array, ignoring the duplicates. The complexity
	 * of the algorithm is O(N*log(N)) overall: O(N*log(N)) for the sorting step plus a linear sweep for
	 * creating the output array. 
	 * 
	 *****************************************************************************************************/ 
	
	public static List<Integer> joinAndRemoveDuplicates(int[] A, int[] B) {
    	
    	List<Integer> result = new ArrayList<Integer>();
    	
    	int i = 0;
    	int j = 0;
    	int N = A.length;
    	int M = B.length;
    	
    	// First, sort the input arrays
    	
    	Arrays.sort(A);
    	Arrays.sort(B);
    	
    	// Iterate the input arrays, adding the minimum element to the output array 
    	
    	while(i < N && j < M) {
    		
    		int ith = A[i];
    		int jth = B[j];
    		
    		if(ith < jth) {
    			result.add(ith);
    			i++;
    			
    			while(i < N && ith == A[i]) i++; //Ignore duplicates
    		}
    		else if(ith > jth) {
    			result.add(jth);
    			j++;
    			
    			while(j < M && jth == B[j]) j++;
    		}
    		else { //Same elements, add just one of them
    			
    			result.add(ith);
    			i++;
    			j++;
    			
    			while(i < N && ith == A[i]) i++;
    			while(j < M && jth == B[j]) j++;
    		}
    	}
    	
    	// One of the input arrays has not been completely traversed. Add the remaining
    	// elements to the output array
    	
    	if(i < N) {
    		for(int k = i; k < N; k++) {
    			if(result.get(result.size() - 1) != A[k]) { //Avoid duplicates
    				result.add(A[k]);
    			}
    		}
    	}
    	else if(j < M) {
    		for(int k = j; k < M; k++) {
				if(result.get(result.size() - 1) != B[k]) {
					result.add(B[k]);
				}
    		}
    	}
    	
    	return result;
    }
}
