package Interface;

import ResearchComputation.SudokuBoard;
import ResearchComputation.SudokuBoardDeque;

public interface IParallelBruteForce {
	public void bootstrap();

	public void bootstrap(SudokuBoardDeque boardDeque, int indexOfRows);

	public void solve();

	public void solveKernel1();

	public void solveKernel2();

	public void solveBruteForceSeq(SudokuBoard board, int row, int col);

	public void solveBruteForcePar(SudokuBoard board, int row, int col);
}
