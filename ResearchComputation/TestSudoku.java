package ResearchComputation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Interface.ITestSudoku;

public class TestSudoku implements ITestSudoku {
	private static final int SIZES_VALID = 1;
	private static final int ROWS_VALID = 2;
	private static final int COLUMNS_VALID = 4;
	private static final int BOXES_VALID = 8;

	@Override
	public boolean checkValidSizes(SudokuBoard board) {
		// TODO Auto-generated method stub
		int BOARD_SIZE = board.get_BOARD_SIZE();
		int ROW_SIZE = board.getBoard().length;
		int COL_SIZE = board.getBoard()[0].length;
		if ((ROW_SIZE != BOARD_SIZE) | (COL_SIZE != BOARD_SIZE)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkValidRows(SudokuBoard board) {
		// TODO Auto-generated method stub
		for (int row = 0; row < board.get_BOARD_SIZE(); row++) {
			List<Integer> valueInRows = new ArrayList<>();
			for (int col = 0; col < board.get_BOARD_SIZE(); col++) {
				int val = board.getBoardData(row, col);
				if (val == board.get_EMPTY_CELL_VALUE()) {
					continue;
				}
				if (valueInRows.contains(val)) {
					return false;
				}
				valueInRows.add(val);
			}
		}
		return true;
	}

	@Override
	public boolean checkValidColumns(SudokuBoard board) {
		// TODO Auto-generated method stub
		for (int col = 0; col < board.get_BOARD_SIZE(); col++) {
			Set<Integer> valuesInCols = new HashSet<>();
			for (int row = 0; row < board.get_BOARD_SIZE(); row++) {
				int val = board.getBoardData(row, col);
				if (val == board.get_EMPTY_CELL_VALUE()) {
					continue;
				}
				if (!valuesInCols.add(val)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean checkValidBoxes(SudokuBoard board) {
		// TODO Auto-generated method stub
		int BOX_SIZE = board.get_BOX_SIZE();
		for (int box_x = 0; box_x < BOX_SIZE; box_x++) {
			for (int box_y = 0; box_y < BOX_SIZE; box_y++) {
				Set<Integer> valuesInBox = new HashSet<>();
				for (int m = 0; m < BOX_SIZE; m++) {
					for (int n = 0; n < BOX_SIZE; n++) {
						int val = board.getBoardData(box_x * BOX_SIZE + m, box_y * BOX_SIZE + n);
						if (val == board.get_EMPTY_CELL_VALUE()) {
							continue;
						}
						if (!valuesInBox.add(val)) {
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean testBoard(SudokuBoard board, int flags) {
		if (!(expect(flags, SIZES_VALID) == checkValidSizes(board))) {
			throw new RuntimeException("+++ ERROR: The dimension of the Sudoku board is not valid! +++\n");
		}

		if (!(expect(flags, ROWS_VALID) == checkValidRows(board))) {
			throw new RuntimeException("+++ ERROR: Some rows in the Sudoku board contain duplicate numbers! +++\n");
		}

		if (!(expect(flags, COLUMNS_VALID) == checkValidColumns(board))) {
			throw new RuntimeException("+++ ERROR: Some columns in the Sudoku board contain duplicate numbers! +++\n");
		}
		if (!(expect(flags, BOXES_VALID) == checkValidBoxes(board))) {
			throw new RuntimeException("+++ ERROR: Some boxes in the Sudoku board contain duplicate numbers! +++\n");
		}

		System.out.println("\u001B[96mThis is a valid Sudoku board!\u001B[0m");
		return true;
	}

	private static boolean expect(int flags, int flag) {
		return (flags & flag) == flag;
	}

}
