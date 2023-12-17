package ResearchComputation;

import Interface.MODES;
import Interface.pair;

public class SequentialBackTracking extends SudokuSolver {
	SudokuBoard board;

	public SequentialBackTracking(SudokuBoard board, boolean printMessage) {
		this.board = board;
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
			showProgressBar(board, recurtionDepth, 5);
		}
		if (checkIfAllFilled(board)) {
			solvered = true;
			solution = board;
			return solvered;
		}
		pair<Integer, Integer> emptyCellPos = findEmpty(board);
		int row = emptyCellPos.getFirst();
		int col = emptyCellPos.getSecond();

		for (int num = board.get_MIN_VALUE(); num <= board.get_MAX_VALUE(); num++) {
			if (isValid(board, num, emptyCellPos)) {
				board.setBoardData(row, col, num);
				if (isUnique(board, num, emptyCellPos)) {
					num = board.get_BOARD_SIZE();
				}
				if (solve_kernel()) {
					solvered = true;
					return solvered;
				} else {
					board.setBoardData(row, col, board.get_EMPTY_CELL_VALUE());
				}
			}
		}
		recurtionDepth++;
		solvered = false;
		return solvered;
	}
}
