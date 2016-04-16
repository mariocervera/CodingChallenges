package problemSolving;

import java.util.ArrayDeque;
import java.util.Deque;

/****************************************************************************
 * Problem statement:
 * 
 * Determine whether the parentheses, square brackets, and curly braces of a
 * String are balanced and properly nested.
 * 
 ****************************************************************************/ 

/**
 * 
 * @author Mario Cervera
 *
 */
public class BalancedString {

	
	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem is solved using a Stack. When the String is being processed, each opening symbol entails
	 * a "push" operation and each closing symbol entails a "pop" operation. The String will be balanced if
	 * the Stack is empty at the end of the processing (and no error is found during the analysis).
	 * 
	 *****************************************************************************************************/ 
	
	public static boolean isBalanced(String s) {

		Deque<Character> symbolStack = new ArrayDeque<Character>();

		for (int i = 0; i < s.length(); i++) { // Processing the input string ...

			char c = s.charAt(i);

			if (c == '(' || c == '[' || c == '{') { // If the character is an opening symbol --> push

				symbolStack.addFirst(c);
				
			}
			else if (c == ')' || c == ']' || c == '}') { // If the character is a closing symbol ...

				if (symbolStack.isEmpty()) { // Error: the stack is empty
					
					return false;
				}
				else {
					
					Character c2 = (Character) symbolStack.removeFirst(); //Pop operation

					if ((c == '(' && c2 != ')') ||
							(c == '[' && c2 != ']') ||
							(c == '{' && c2 != '}')) { // Error: no matching symbol in the stack
						
						return false;
					}
				}
			}
		}

		if (symbolStack.isEmpty()) { // No error and empty stack --> balanced String
			
			return true;
		}

		return false;
	}
}
