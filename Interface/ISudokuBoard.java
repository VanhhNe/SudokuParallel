package Interface;

import java.io.IOException;
import java.util.ArrayList;

import ResearchComputation.SudokuBoard;

public interface ISudokuBoard {
	public int[][] readInput(String fileName) throws IOException;

	public int[][] readInput(int[][] matrix);

	public void writeOutput(SudokuBoard solutionBoard, String fileName) throws IOException;

	public void setBoardData(int row, int col, int num);

	public int getBoardData(int row, int col);

	public int at(int row, int col);

	public int getNumTotalCells();

	public int getNumEmptyCells();

	public ArrayList<Integer> getNumberInRow(int idxRow);

	public ArrayList<Integer> getNumberInCol(int idxCol);

	public void printBoard(SudokuBoard board);

	public void printBoard(int[][] board);

	public int indexInCoverMatrix(int row, int col, int num);

	int createBoxConstraints(int[][] coverMatrix, int header);

	int createColumnConstraints(int[][] coverMatrix, int header);

	int createRowConstraints(int[][] coverMatrix, int header);

	int createCellConstraints(int[][] coverMatrix, int header);

	void createCoverMatrix(int[][] coverMatrix);

	void convertToCoverMatrix(int[][] coverMatrix);

}
