package problemSolving;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/****************************************************************************
 * Problem statement:
 * 
 * Reverse the words in a sentence. For example, "My name is Chris" becomes
 * "Chris is name My".
 * 
 ****************************************************************************/

public class ReverseWords {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem can be solved iterating the words of the input String and storing them in a Stack.
	 * Then, the elements of the Stack can be popped one at a time to compose the reversed sentence. The
	 * complexity of the algorithm is linear with the number of words of the input String.
	 * 
	 *****************************************************************************************************/ 
	
	public static String reverseWords(String sentence) {
	
		Deque<String> stack = new ArrayDeque<String>();
		
		StringTokenizer st = new StringTokenizer(sentence);
		
		//Store words in the stack
		
		while(st.hasMoreTokens()) {
			
			String word = st.nextToken();
			stack.addFirst(word);
		}
		
		//Compose reversed sentence
		
		String result = "";
		
		while(!stack.isEmpty()) {
			
			result += stack.removeFirst() + " ";
		}
		
		return result.substring(0, result.length() - 1);
	}
	
	
	
}
