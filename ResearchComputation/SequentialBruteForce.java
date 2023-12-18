package ResearchComputation;

import Interface.MODES;
import Interface.pair;

public class SequentialBruteForce extends SudokuSolver {
	public SudokuBoard solutionBoard;

	public SequentialBruteForce(SudokuBoard board, boolean printMessage) throws CloneNotSupportedException {
		this.solutionBoard = new SudokuBoard(board.getBoard());
		this.board = board;
		mode = MODES.SEQUENTIAL_BRUTEFORCE;

		if (printMessage) {
			System.out.println("Sequential Sudoku solver using brute force algorithm starts, please wait ...");
		}
	}

	public int[][] copyMatrix(int[][] original) {
		int rows = original.length;
		int cols = original[0].length;

		int[][] copy = new int[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				copy[i][j] = original[i][j];
			}
		}

		return copy;
	}

	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	@Override
	public void solve() {
		solve_kernel(0, 0);
	}

	public void solve_kernel(int row, int col) {
//		board.printBoard(board);
		if (solvered) {
//			solution.printBoard(solution);
			return;
		}
		if (mode == MODES.SEQUENTIAL_BRUTEFORCE) {
			showProgressBar(solutionBoard, recurtionDepth, 5);
		}

		int BOARD_SIZE = solutionBoard.get_BOARD_SIZE();
		int abs_index = row * BOARD_SIZE + col;
		if (abs_index >= solutionBoard.getNumTotalCells()) {
			solvered = true;
			solution = new SudokuBoard(solutionBoard.getBoard());
			return;
		}
		int row_next = (abs_index + 1) / BOARD_SIZE;
		int col_next = (abs_index + 1) % BOARD_SIZE;
		if (!isEmpty(solutionBoard, row, col)) {
			solve_kernel(row_next, col_next);
		} else {
			for (int num = board.get_MIN_VALUE(); num <= solutionBoard.get_MAX_VALUE(); num++) {
				pair<Integer, Integer> pos = new pair<>(row, col);
				if (isValid(solutionBoard, num, pos)) {
					solutionBoard.setBoardData(row, col, num);
					if (isUnique(solutionBoard, num, pos)) {
						num = solutionBoard.get_MAX_VALUE() + 1;
					}
					solve_kernel(row_next, col_next);
					solutionBoard.resetCell(row, col);
				}
			}
		}
		recurtionDepth++;
	}
}
