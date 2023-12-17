package ResearchComputation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Interface.IParallelBruteForce;
import Interface.pair;

public class ParallelBruteForce extends SudokuSolver implements IParallelBruteForce {

	private SudokuBoardDeque boardDeque;
	public int[][] finalSolution;
	private static int THREAD = 4;

	public ParallelBruteForce() {
		// TODO Auto-generated constructor stub
	}

	public ParallelBruteForce(SudokuBoard board, boolean printMessage, int THREAD) {
		this.board = board;
		this.THREAD = THREAD;
		if (printMessage) {
			System.out.println("Parallel Sudoku solver using brute force algorithm starts, please wait ...");
		}
	}

	@Override
	public void bootstrap() {
		// TODO Auto-generated method stub
		if (boardDeque.size() == 0) {
			return;
		}
		SudokuBoard board = boardDeque.front();
		if (checkIfAllFilled(board)) {
			return;
		}

		pair<Integer, Integer> emptyCellPos = findEmpty(board);
		int row = emptyCellPos.getFirst();
		int col = emptyCellPos.getSecond();

		for (int num = board.get_MIN_VALUE(); num <= board.get_MAX_VALUE(); num++) {
			if (isValid(board, num, emptyCellPos)) {
				board.setBoardData(row, col, num);
				boardDeque.pushBack(board);
			}
		}
		boardDeque.popFront();
	}

	@Override
	public void bootstrap(SudokuBoardDeque boardDeque, int indexOfRows) {
		// TODO Auto-generated method stub
		if (boardDeque.size() == 0) {
			return;
		}
		while (!checkIfRowFilled(boardDeque.front(), indexOfRows)) {
			SudokuBoard board = boardDeque.front();
			int emptyCellColIdx = findEmptyFromRow(board, indexOfRows);

			for (int num = board.get_MIN_VALUE(); num <= board.get_MAX_VALUE(); num++) {
				pair<Integer, Integer> emptyCellPos = new pair<Integer, Integer>(indexOfRows, emptyCellColIdx);

				if (isValid(board, num, emptyCellPos)) {
					board.setBoardData(indexOfRows, emptyCellColIdx, num);
					boardDeque.pushBack(board);
				}
			}
		}
		boardDeque.popFront();
	}

	@Override
	public void solve() {
		ExecutorService executors = Executors.newFixedThreadPool(THREAD);
		for (int num = board.get_MIN_VALUE(); num <= board.get_MAX_VALUE(); num++) {
			int[][] matrix = copyMatrix(this.board.getBoard());
			SudokuBoard clonedBoard = new SudokuBoard(matrix);
			if (isValid(clonedBoard, 0, 0, num)) {
				executors.submit(() -> solveBruteForcePar(clonedBoard, 0, 0));
			}
		}
		executors.shutdown();
	}

	private boolean isValid(SudokuBoard clonedBoard, int i, int j, int num) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void solveBruteForcePar(SudokuBoard board, int row, int col) {
		if (solvered) {
			return;
		}

		int BOARD_SIZE = board.get_BOARD_SIZE();
		int abs_index = row * BOARD_SIZE + col;

		if (abs_index >= board.getNumTotalCells()) {
			solvered = true;
			finalSolution = copyMatrix(board.getBoard());
			return;
		}

		int row_next = (abs_index + 1) / BOARD_SIZE;
		int col_next = (abs_index + 1) % BOARD_SIZE;
		if (!isEmpty(board, row, col)) {
			solveBruteForcePar(board, row_next, col_next);
		} else {
			for (int num = board.get_MIN_VALUE(); num <= board.get_MAX_VALUE(); num++) {
				pair<Integer, Integer> pos = new pair<>(row, col);
				if (isValid(board, num, pos)) {
					board.setBoardData(row, col, num);

					if (isUnique(board, num, pos)) {
						num = board.get_MAX_VALUE() + 1;
					}
					SudokuBoard local_board = new SudokuBoard(BOARD_SIZE);
					local_board.setBoardData(row, col, num);
					solveBruteForcePar(local_board, row_next, col_next);
					board.resetCell(row, col);
				}
			}
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

	@Override
	public void solveKernel1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void solveKernel2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void solveBruteForceSeq(SudokuBoard board, int row, int col) {
		// TODO Auto-generated method stub

	}

}
