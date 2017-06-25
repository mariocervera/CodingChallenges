package problemSolving;

import java.util.ArrayList;
import java.util.List;

/********************************************************************************************
 * Problem statement:
 * 
 * A crossword grid is provided to you, along with a set of words that need to be filled into
 * the grid. The cells in the grid are initially either + signs or - signs. Cells marked with
 * a + have to be left as they are. Cells marked with a - need to be filled up with an
 * appropriate character.
 * 
 * Example of empty grid:
 * 
 * +-++++++++
 * +-++++++++
 * +-++++++++
 * +-----++++
 * +-+++-++++
 * +-+++-++++
 * +++++-++++
 * ++------++
 * +++++-++++
 * +++++-++++
 * 
 * Example of the solution after placing the words: london, delhi, iceland, and ankara:
 * 
 * +L++++++++
 * +O++++++++
 * +N++++++++
 * +DELHI++++
 * +O+++C++++
 * +N+++E++++
 * +++++L++++
 * ++ANKARA++
 * +++++N++++
 * +++++D++++
 * 
 ********************************************************************************************/

/**
 * @author Mario Cervera
 */
public class CrosswordPuzzle {

	/*****************************************************************************************************
	 * Solution:
	 * 
	 * This problem, due to its small size, can be solved by applying brute force via backtracking.
	 * 
	 *****************************************************************************************************/

	private static boolean finished = false;

	/**
	 * This method solves the grid recursively.
	 * 
	 * @param inputGrid, the input grid without words
	 * @param outputGrid, the solution to the problem
	 * @param words, the words that must be placed in the grid
	 * @param i, starting row
	 * @param j, starting column
	 */
	public static void crossWord(Grid inputGrid, Grid outputGrid, List<GridWord> words, int i, int j) {

		if(allWordsInGrid(words)) { // Is the grid solved? If so, print solution and return
			System.out.print(outputGrid.toString());
			finished = true;
			return;
		}

		if(i > 9 && j > 9) { // Have we reached the end of the grid? If so, return
			return;
		}

		// Recursion

		if (inputGrid.getCell(i, j) == '+') { // Easy case: just transfer "+" symbol to the output grid
			outputGrid.setCell(i, j, '+');
		}
		else { // Set new word (if one fits)

			List<GridWord> candidateWords = getCandidates(inputGrid, outputGrid, words, i, j);
			for (GridWord w : candidateWords) {

				// Make move

				int backupI = i;
				int backupJ = j;
				List<int[]> backupModifiedCells;

				if (w.isHorizontal()) {
					backupModifiedCells = setHorizontalWord(outputGrid, w.getWord(), i, j);
				}
				else {
					backupModifiedCells = setVerticalWord(outputGrid, w.getWord(), i, j);
				}

				w.setInGrid(true);

				// Backtrack

				if (j < 9) {
					crossWord(inputGrid, outputGrid, words, i, j + 1);
				}
				else if (i < 9) {
					crossWord(inputGrid, outputGrid, words, i + 1, 0);
				}

				// Unmake move

				w.setInGrid(false);
				i = backupI;
				j = backupJ;
				for (int c = 0; c < backupModifiedCells.size(); c++) {
					outputGrid.setCell(backupModifiedCells.get(c)[0], backupModifiedCells.get(c)[1], '+');
				}

				// Finished?

				if (finished) return;
			}
		}

		// Next cell

		if (j < 9) {
			crossWord(inputGrid, outputGrid, words, i, j + 1);
		} else if (i < 9) {
			crossWord(inputGrid, outputGrid, words, i + 1, 0);
		}
	}
	
	/*
	 * This method checks whether all words have been correctly placed in the grid
	 */
	private static boolean allWordsInGrid(List<GridWord> words) {
		for (GridWord word : words) {
			if (!word.isInGrid()) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * This method returns the words that fit in position (i,j) either horizontally or vertically (or both)
	 */
	private static List<GridWord> getCandidates(Grid inputGrid, Grid outputGrid, List<GridWord> words, int i, int j) {

		List<GridWord> result = new ArrayList<GridWord>();

		for (GridWord w : words) {
			if (!w.isInGrid()) {
				if (fitsHorizontally(inputGrid, outputGrid, w, i, j)
						|| fitsVertically(inputGrid, outputGrid, w, i, j)) {
					result.add(w);
				}
			}
		}

		return result;
	}
	
	/*
	 * This method checks whether a given word fits horizontally at position (i,j)
	 */
	private static boolean fitsHorizontally(Grid inputGrid, Grid outputGrid, GridWord word, int i, int j) {

		if(j + word.getWord().length() - 1 > 9) { // The word falls out of the grid
			return false;
		}

		if (j > 0 && inputGrid.getCell(i, j - 1) == '-') { // Middle position (there is space to the left)
			return false;
		}

		// Check whether there are enough '-' symbols. Beware of existing letters

		int k = 0;
		for (; k < word.getWord().length(); k++) {
			char wordC = word.getWord().charAt(k);
			char gridCinput = inputGrid.getCell(i, j + k);
			char gridCoutput = outputGrid.getCell(i, j + k);

			if (gridCinput == '+') {
				return false;
			}
			if (gridCoutput != '+' && gridCoutput != wordC) {
				return false;
			}
		}

		if (j + k <= 9 && inputGrid.getCell(i, j + k) == '-') { // There is extra space, so the word does not fit perfectly
			return false;
		}

		word.setHorizontal(true);
		return true;
	}
	
	/*
	 * This method checks whether a given word fits vertically at position (i,j)
	 */
	private static boolean fitsVertically(Grid inputGrid, Grid outputGrid, GridWord word, int i, int j) {

		if (i + word.getWord().length() - 1 > 9) { // The word falls out of the grid
			return false;
		}

		if (i > 0 && inputGrid.getCell(i - 1, j) == '-') { // Middle position
			return false;
		}

		// Check whether there are enough '-' symbols. Beware of existing letters

		int k = 0;
		for (; k < word.getWord().length(); k++) {
			char wordC = word.getWord().charAt(k);
			char gridCinput = inputGrid.getCell(i + k, j);
			char gridCoutput = outputGrid.getCell(i + k, j);

			if (gridCinput == '+') {
				return false;
			}
			if (gridCoutput != '+' && gridCoutput != wordC) {
				return false;
			}
		}

		if (i + k <= 9 && inputGrid.getCell(i + k, j) == '-') { // There is extra space, so the word does not fit perfectly
			return false;
		}

		word.setHorizontal(false);
		return true;
	}
	
	/*
	 * This method transfers a word to the output grid (horizontally). Returns
	 * the cells that are modified.
	 */
	private static List<int[]> setHorizontalWord(Grid outputGrid, String word, int i, int j) {

		List<int[]> modifiedCells = new ArrayList<int[]>();

		for (int k = 0; k < word.length(); k++) {
			if (outputGrid.getCell(i, j+k) == '+') {
				int[] cell = {i, j+k};
				modifiedCells.add(cell);
			}
			outputGrid.setCell(i, j+k, word.charAt(k));
		}

		return modifiedCells;
	}

	/*
	 * This method transfers a word to the output grid (vertically) Returns the
	 * cells that are modified.
	 */
	private static List<int[]> setVerticalWord(Grid outputGrid, String word, int i, int j) {

		List<int[]> modifiedCells = new ArrayList<int[]>();

		for (int k = 0; k < word.length(); k++) {
			if (outputGrid.getCell(i+k, j) == '+') {
				int[] cell = {i+k, j};
				modifiedCells.add(cell);
			}
			outputGrid.setCell(i+k, j, word.charAt(k));
		}

		return modifiedCells;
	}
}

/**
 * This class represents the Grid
 * 
 * @author Mario Cervera
 */
class Grid {

	private char[][] grid;
	private int size;

	public Grid(int size) {
		this.grid = new char[size][size];
		this.size = size;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				grid[i][j] = '+';
			}
		}
	}

	public char getCell(int i, int j) {
		return this.grid[i][j];
	}

	public void setCell(int i, int j, char c) {
		this.grid[i][j] = c;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				s += grid[i][j];
			}
			s += "\n";
		}
		return s;
	}
}

/**
 * This class represents a word to the placed in the grid
 * 
 * @author Mario Cervera
 */
class GridWord {

	private String word;
	private boolean inGrid;
	private boolean horizontal;

	public GridWord(String word) {
		this.word = word;
		this.inGrid = false;
		this.horizontal = false;
	}

	public String getWord() {
		return word;
	}

	public boolean isInGrid() {
		return inGrid;
	}

	public void setInGrid(boolean inGrid) {
		this.inGrid = inGrid;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
}