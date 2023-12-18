package Interface;

import ResearchComputation.SudokuBoard;

public interface ITestSudoku {
	boolean checkValidSizes(SudokuBoard board);

	boolean checkValidRows(SudokuBoard board);

	boolean checkValidColumns(SudokuBoard board);

	boolean checkValidBoxes(SudokuBoard board);

	boolean testBoard(SudokuBoard board, int flags);
}
