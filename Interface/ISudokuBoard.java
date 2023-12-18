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

	public void printBoard(SudokuBoard board, boolean solved);

	public void printBoard(int[][] board);
}
