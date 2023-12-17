package ResearchComputation;

import java.util.ArrayList;

import Interface.ISudokuSolver;
import Interface.MODES;
import Interface.pair;

public class SudokuSolver implements ISudokuSolver {

	SudokuBoard board;
	boolean solvered = false;
	public SudokuBoard solution;
	int recurtionDepth = 0;
	int currentNumEmptyCells;
	MODES mode;

	@Override
	public boolean checkIfAllFilled(SudokuBoard board) {
		// TODO Auto-generated method stub
		for (int i = 0; i < board.get_BOARD_SIZE(); i++) {
			for (int j = 0; j < board.get_BOARD_SIZE(); j++) {
				if (isEmpty(board, i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean checkIfRowFilled(SudokuBoard board, int indexOfRows) {
		// TODO Auto-generated method stub
		for (int j = 0; j < board.get_BOARD_SIZE(); j++) {
			if (isEmpty(board, indexOfRows, j)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int findEmptyFromRow(SudokuBoard board, int indexOfRows) {
		// TODO Auto-generated method stub
		int emptyCellColIdx = -1;
		for (int j = 0; j < board.get_BOARD_SIZE(); j++) {
			if (isEmpty(board, indexOfRows, j)) {
				emptyCellColIdx = j;
				return emptyCellColIdx;
			}
		}
		return emptyCellColIdx;
	}

	@Override
	public pair<Integer, Integer> findEmpty(SudokuBoard board) {
		pair<Integer, Integer> posEmptyCell = null;
		boolean stop = false;
		for (int i = 0; i < board.get_BOARD_SIZE(); i++) {
			for (int j = 0; j < board.get_BOARD_SIZE(); j++) {
				if (isEmpty(board, i, j)) {
					posEmptyCell = new pair<>(i, j);
					stop = true;
					break;
				}
			}
			if (stop) {
				break;
			}
		}
		return posEmptyCell;
	}

	@Override
	public boolean isEmpty(SudokuBoard board, int i, int j) {
		// TODO Auto-generated method stub
		return (board.at(i, j) == board.get_EMPTY_CELL_VALUE());
	}

	@Override
	public boolean isValidColumn(SudokuBoard board, int num, pair<Integer, Integer> pos) {
		// TODO Auto-generated method stub
		for (int i = 0; i < board.get_BOARD_SIZE(); i++) {
			if ((i != pos.getFirst()) && (board.at(i, pos.getSecond()) == num)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isValidRow(SudokuBoard board, int num, pair<Integer, Integer> pos) {
		// TODO Auto-generated method stub
		for (int j = 0; j < board.get_BOARD_SIZE(); j++) {
			if ((j != pos.getSecond()) && (board.at(pos.getFirst(), j) == num)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isValidBox(SudokuBoard board, int num, pair<Integer, Integer> pos) {
		// TODO Auto-generated method stub
		int BOX_SIZE = board.get_BOX_SIZE();
		int box_x = (int) Math.floor(pos.getFirst() / BOX_SIZE);
		int box_y = (int) Math.floor(pos.getSecond() / BOX_SIZE);

		for (int i = box_x * BOX_SIZE; i < box_x * BOX_SIZE + BOX_SIZE; i++) {
			for (int j = box_y * BOX_SIZE; j < box_y * BOX_SIZE + BOX_SIZE; j++) {
				if ((i != pos.getFirst() && j != pos.getSecond()) && (board.at(i, j) == num)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isValid(SudokuBoard board, int num, pair<Integer, Integer> pos) {
		return isValidRow(board, num, pos) && isValidColumn(board, num, pos) && isValidBox(board, num, pos);
	}

	@Override
	public boolean isUnique(SudokuBoard board, int num, pair<Integer, Integer> pos) {
		// TODO Auto-generated method stub
		int BOX_SIZE = board.get_BOX_SIZE();
		int BOARD_SIZE = board.get_BOARD_SIZE();
		int localRow = pos.getFirst() % BOX_SIZE;
		int localCol = pos.getSecond() % BOX_SIZE;

		int boxX = (int) Math.floor(pos.getFirst() / BOX_SIZE);
		int boxY = (int) Math.floor(pos.getSecond() / BOX_SIZE);

		for (int i = ((localRow == 0) ? 1 : 0); i < BOX_SIZE; i++) {
			if (i == localRow) {
				continue;
			}
			int tempRow = boxX * BOX_SIZE + i;
			ArrayList<Integer> numbersInRow = board.getNumberInRow(tempRow);
			if (!numbersInRow.contains(num)) {
				return false;
			}
		}

		for (int j = ((localCol == 0) ? 1 : 0); j < BOX_SIZE; j++) {
			if (j == localCol) {
				continue;
			}
			int tempCol = boxY * BOX_SIZE + j;
			ArrayList<Integer> numbersInCol = board.getNumberInCol(tempCol);
			if (!numbersInCol.contains(num)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void setMode(MODES mode) {
		this.mode = mode;
	}

	@Override
	public boolean getStatus() {
		// TODO Auto-generated method stub
		return solvered;
	}

	@Override
	public SudokuBoard getSolution() {
		// TODO Auto-generated method stub
		return solution;
	}

	@Override
	public void showProgressBar(SudokuBoard board, int recursionDepth, int interval) {
		if (recursionDepth == 0) {
			currentNumEmptyCells = board.get_INIT_NUM_EMPTY_CELLS();
			printProcessBar2((double) (board.get_INIT_NUM_EMPTY_CELLS() - currentNumEmptyCells)
					/ board.get_INIT_NUM_EMPTY_CELLS());
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else {
			if (board.getNumEmptyCells() < currentNumEmptyCells) {
				currentNumEmptyCells = board.getNumEmptyCells();
				printProcessBar2((double) (board.get_INIT_NUM_EMPTY_CELLS() - currentNumEmptyCells)
						/ board.get_INIT_NUM_EMPTY_CELLS());
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void printProcessBar2(double percentage) {
		int BAR_WIDTH = 20;
		System.out.print("[");

		int pos = (int) (BAR_WIDTH * percentage);
		for (int i = 0; i < BAR_WIDTH; ++i) {
			if (i < pos) {
				System.out.print("=");
			} else if (i == pos) {
				System.out.print(">");
			} else {
				System.out.print(" ");
			}
		}

		System.out.print("] " + (int) (percentage * 100.0) + "%\r");
//		System.out.flush();
	}

	@Override
	public void solve() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSolution(SudokuBoard solution) {
		// TODO Auto-generated method stub
		this.solution = solution;
	}

	@Override
	public SudokuBoard cloneValue(SudokuBoard solution, SudokuBoard board) {
		int n = board.get_BOARD_SIZE();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int data = board.getBoard()[i][j];
				solution.setBoardData(i, j, data);
			}
		}
		return solution;
	}

}
