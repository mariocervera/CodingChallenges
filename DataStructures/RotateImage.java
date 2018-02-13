package problemSolving;

/*******************************************************************************************
 * Problem statement:
 * 
 * You are given an N x N 2D matrix representing an image. Rotate the image by 90 degrees
 * (clockwise). You need to do this in place (that is, without using any extra storage).
 * 
 *******************************************************************************************/

/**
 * @author Mario Cervera
 */
public class RotateImage {

	/*******************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved recursively, by rotating the exterior rows and columns, and proceeding
	 * inwards until reaching the base case: the center of the matrix. In other words, we can rotate one
	 * concentric square at a time. The specific square that is rotated in each recursive invocation is
	 * established by an "offset" parameter.
	 * 
	 *******************************************************************************************************/
	
	public static void rotate90degrees(int[][] a, int offset) {
		
		if(offset >= a.length/2) {
			return;
		}
		
		for(int i = offset; i < a.length-offset-1; i++) {
			
			// Rotate from top to right
			
			int aux = a[i][a.length-offset-1];
			a[i][a.length-offset-1] = a[offset][i];
			
			// Rotate from right to bottom
			
			int aux2 = a[a.length-offset-1][a.length-1-i];
			a[a.length-offset-1][a.length-1-i] = aux;
			
			// Rotate from bottom to left
			
			int aux3 = a[a.length-1-i][offset];
			a[a.length-1-i][offset] = aux2;
			
			// Rotate from left to top
			
			a[offset][i] = aux3;
			
		}
		
		rotate90degrees(a, ++offset);
	}
}
