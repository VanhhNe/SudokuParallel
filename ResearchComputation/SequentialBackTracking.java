package ResearchComputation;

import Interface.MODES;
import Interface.pair;

public class SequentialBackTracking extends SudokuSolver {
	SudokuBoard solutionBoard;

	public SequentialBackTracking(SudokuBoard board, boolean printMessage) {
		this.board = board;
		this.solutionBoard = new SudokuBoard(board.getBoard());
		mode = MODES.SEQUENTIAL_BACKTRACKING;
		if (printMessage) {
			System.out.println("Sequential Sudoku solver using backtracking algorithm starts, please wait ...");
		}
	}

	@Override
	public void solve() {
		solve_kernel();
	}

	public boolean solve_kernel() {
		if (solvered) {
			return solvered;
		}
		if (mode == MODES.SEQUENTIAL_BACKTRACKING) {
			showProgressBar(solutionBoard, recurtionDepth, 5);
		}
		if (checkIfAllFilled(solutionBoard)) {
			solvered = true;
			solution = solutionBoard;
			return solvered;
		}
		pair<Integer, Integer> emptyCellPos = findEmpty(solutionBoard);
		int row = emptyCellPos.getFirst();
		int col = emptyCellPos.getSecond();

		for (int num = solutionBoard.get_MIN_VALUE(); num <= solutionBoard.get_MAX_VALUE(); num++) {
			if (isValid(solutionBoard, num, emptyCellPos)) {
				solutionBoard.setBoardData(row, col, num);
				if (isUnique(solutionBoard, num, emptyCellPos)) {
					num = solutionBoard.get_BOARD_SIZE();
				}
				if (solve_kernel()) {
					solvered = true;
					return solvered;
				} else {
					solutionBoard.setBoardData(row, col, solutionBoard.get_EMPTY_CELL_VALUE());
				}
			}
		}
		recurtionDepth++;
		return solvered;
	}
}
