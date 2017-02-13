package problemSolving;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/****************************************************************************
 * Problem statement:
 * 
 * Given a set of 2D points, calculate the cost of the shortest cycle that visits
 * each point exactly once. The distance between two points is defined as their
 * Euclidean distance.
 * 
 ****************************************************************************/

/**
 * @author Mario Cervera
 */
public class TravelingSalesman {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This class implements a solution that applies dynamic programming. Each subproblem A[S,j] stores the cost
	 * of the shortest cycle that goes from point 1 to point j and traverses exactly the points that are contained
	 * in set S. This set does not specify the exact order of these points. This allows the algorithm to run in
	 * O(2^n) time (where n is the number of points). Considering that the problem is NP-Complete, this is a huge
	 * improvement over naive brute-force search, which takes O(n!) time.
	 * 
	 *****************************************************************************************************/
	
	private static int N = 0;
	
	public static float travelingSalesman(Point[] points) {
		
		N = points.length;

		// Compute all-pairs of distances
		
		float[][] distance = new float[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = i; j < N; j++) {
				if(i == j) {
					distance[i][j] = 0F;
				}
				else {
					distance[i][j] = distance(points[i], points[j]);
					distance[j][i] = distance[i][j];
				}
			}
		}
			
		// Define two tables to cache the subproblems (it is only necessary to remember current and previous iterations).
		//
		// Key -> A subset of points S, represented as an integer. The position of each bit (in the integer's binary representation)
		// that is set to 1 represents an index of the "points" array.
		// 
		// Value -> An array storing the costs of the optimal paths for each destination included in S.
		
		Map<Integer, float[]> dpTablePrev = new HashMap<Integer, float[]>();
		Map<Integer, float[]> dpTableCurrent = new HashMap<Integer, float[]>();
		
		// Base case
		
		Integer baseCaseSet = new Integer(1);
		float[] baseCaseValue = new float[N];
		for(int i = 0; i < N; i++) {
			baseCaseValue[i] = Float.MAX_VALUE;
		}
		baseCaseValue[0] = 0;
		
		dpTableCurrent.put(baseCaseSet, baseCaseValue);

		//  Recurrence relation
		
		for(int m = 2; m <= N; m++) { // For each size of the subsets
			
			// Transfer subproblems (only those of the previous iteration are needed)
			
			dpTablePrev.clear();
			dpTablePrev = dpTableCurrent;
			dpTableCurrent = new HashMap<Integer, float[]>();
			
			// Iterate the subsets of size m-1 and increment their size in one element
			
			Set<Integer> subsetsPrevIteration  = dpTablePrev.keySet();
			
			for(Integer subsetPrevIt : subsetsPrevIteration) {
				
				int posMSB = getPositionOfMSB(subsetPrevIt);
				
				for(int i = posMSB+1; i < N; i++) { // New subsets are created by moving 1 bit towards the left
					
					Integer aux = Integer.highestOneBit((int)Math.pow(2,i));
					
					Integer newSubset = subsetPrevIt | aux;
					
					// Get the positions of the bits that are set to 1
					
					List<Integer> positionsOf1Bits = get1Positions(newSubset);
					
					for(Integer j : positionsOf1Bits) { // For each possible destination (contained in the subset) ...
						
						if(j != 0) { // ... other than the source point
							
							// Look for the best last hop
							
							Float min = Float.MAX_VALUE;
							
							for(Integer k : positionsOf1Bits) {
								
								if(!k.equals(j)) { // Cycles not allowed
									
									List<Integer> reducedSet = getReducedSet(positionsOf1Bits, j);
									float[] costsPaths = dpTablePrev.get(toIntegerFromBitPositions(reducedSet));
									if(costsPaths != null) {
										Float partialTourLength = costsPaths[k];
										Float totalTourLength = partialTourLength + distance[k][j];
										if(totalTourLength < min) {
											min = totalTourLength;
										} 
									}
								}
							}
							
							// Update the current subproblem
							
							float[] costsPaths = dpTableCurrent.get(newSubset);
							if(costsPaths == null) {
								costsPaths = new float[N];
								for(int l = 0; l < N; l++) {
									costsPaths[l] = Float.MAX_VALUE;
								}
							}
							costsPaths[j] = min;
							dpTableCurrent.put(newSubset, costsPaths);
						}
					}
				}
			}
		}

		// Get the best closed tour
		
		List<Integer> fullSet = new ArrayList<Integer>();
		for(int i = 0; i < N; i++) {
			fullSet.add(i);
		}
		
		Float min = Float.MAX_VALUE;
		
		for(int j = 1; j < N; j++) {
			
			float[] costsPaths = dpTableCurrent.get(toIntegerFromBitPositions(fullSet));
			if(costsPaths != null) {
				Float partialTourLength = costsPaths[j];
				Float totalTourLength = partialTourLength + distance[j][0];
				if(totalTourLength < min) {
					min = totalTourLength;
				}
			}
		}
		
		// Return result
		
		return min;
	}
	
	/*
	 * Distance between two points
	 */
	private static float distance(Point p1, Point p2) {
		
		Float difXs = p1.getX() - p2.getX();
		Float difYs = p1.getY() - p2.getY();
		
		return (float) Math.sqrt(difXs*difXs + difYs*difYs);
	} 
	
	/*
	 * Returns the position of the most significant bit (that is, the leftmost bit)
	 */
	private static int getPositionOfMSB(int n) {
	    
		int mask = Integer.highestOneBit((int)Math.pow(2, N+1));
		
		int posBit = N+1;
		
		do {
			mask = mask >> 1;
			posBit--;
		}
		while((n & mask) == 0);
		
		return posBit;
	}
	
	/*
	 * Returns the positions of the bits that are set to 1
	 */
	private static List<Integer> get1Positions(int n) {
	    
		List<Integer> result = new ArrayList<Integer>();
		
		int mask = new Integer(1);
		int posBit = 0;
		
		while(posBit <= N) {
			
			if((n & mask) != 0) {
				result.add(posBit);
			}
			
			mask = mask << 1;
			posBit++;
		}
		
		return result;
	}
	
	/*
	 * Returns the integer that has the specified positions (bitwise) set to 1
	 */
	private static Integer toIntegerFromBitPositions(List<Integer> positions) {
	    
		Integer result = new Integer(0);
		
		for(Integer i : positions) {
			
			Integer x = Integer.highestOneBit((int)Math.pow(2, i));
			
			result |= x;
		}
		
		return result;
	}
	
	/*
	 * Returns the set that is given as input with the "j" element removed
	 */
	private static List<Integer> getReducedSet(List<Integer> subset, Integer j) {
		
		List<Integer> result = new ArrayList<Integer>();
		
		for(Integer i : subset) {
			if(!i.equals(j)) {
				result.add(i);
			}
		}
		
		return result;
	}
}

/**
 * A representation of 2D points
 * 
 * @author Mario Cervera
 */
class Point {
	
	private float x;
	private float y;
	
	public Point(float x, float y) {
		
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "(" + this.getX() + ", " + this.getY() + ")";
	}
}
