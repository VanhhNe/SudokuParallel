package Interface;

import ResearchComputation.SudokuBoard;

public interface ISudokuSolver {

	public boolean checkIfAllFilled(SudokuBoard board);

	public boolean checkIfRowFilled(SudokuBoard board, int indexOfRows);

	public int findEmptyFromRow(SudokuBoard board, int indexOfRows);

	public pair<Integer, Integer> findEmpty(SudokuBoard board);

	public boolean isEmpty(SudokuBoard board, int i, int j);

	public boolean isValidColumn(SudokuBoard board, int num, pair<Integer, Integer> pos);

	public boolean isValidRow(SudokuBoard board, int num, pair<Integer, Integer> pos);

	public boolean isValidBox(SudokuBoard board, int num, pair<Integer, Integer> pos);

	public boolean isValid(SudokuBoard board, int num, pair<Integer, Integer> pos);

	public boolean isUnique(SudokuBoard board, int num, pair<Integer, Integer> pos);

	public void setMode(MODES mode);

	public boolean getStatus();

	public SudokuBoard getSolution();

	void showProgressBar(SudokuBoard board, int recursionDepth, int interval);

	public void solve();

	public void setSolution(SudokuBoard solution);

	public SudokuBoard cloneValue(SudokuBoard board1, SudokuBoard board2);
}
