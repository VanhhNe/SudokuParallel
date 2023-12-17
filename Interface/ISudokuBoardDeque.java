package Interface;

import ResearchComputation.SudokuBoard;

public interface ISudokuBoardDeque {
	public int size();

	public SudokuBoard front();

	public SudokuBoard back();

	public SudokuBoard get(int i);

	public void popFront();

	public void popBack();

	public void pushFront(SudokuBoard board);

	public void pushBack(SudokuBoard board);
}
