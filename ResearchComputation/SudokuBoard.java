package ResearchComputation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

import Interface.ISudokuBoard;

public class SudokuBoard implements ISudokuBoard, Cloneable {
	private int[][] Board;
	private int _BOX_SIZE;
	private int _BOARD_SIZE;
	private int _MIN_VALUE = 1;
	private int _MAX_VALUE = 9;
	private int _INIT_NUM_EMPTY_CELLS;
	private static int _EMPTY_CELL_VALUE = 0;

	public SudokuBoard(int board_size) {
		this.Board = new int[board_size][board_size];
		this._BOARD_SIZE = board_size;
		this._BOX_SIZE = (int) Math.sqrt(_BOARD_SIZE);
		this._MAX_VALUE = this._BOARD_SIZE;
		_INIT_NUM_EMPTY_CELLS = _BOARD_SIZE * _BOARD_SIZE;
	}

	public SudokuBoard(int[][] board) {
		this.Board = readInput(board);
	}

	public SudokuBoard(String fileName) throws IOException {
		this.Board = readInput(fileName);
	}

	@Override
	public int[][] readInput(String fileName) throws IOException {
		int[][] sudokuBoard = null;
		int num_empty_cells = 0;

		try (BufferedReader inputFile = new BufferedReader(new FileReader(fileName))) {
			// Read _BOARD_SIZE from the file
			_BOARD_SIZE = Integer.parseInt(inputFile.readLine());
			_BOX_SIZE = (int) Math.sqrt(_BOARD_SIZE);
			_MAX_VALUE = _BOARD_SIZE;
			sudokuBoard = new int[_BOARD_SIZE][_BOARD_SIZE];
			// Initialize the sudokuBoard
			for (int row = 0; row < _BOARD_SIZE; ++row) {
				String[] values = inputFile.readLine().split(" ");
				for (int col = 0; col < _BOARD_SIZE; ++col) {
					int value = Integer.parseInt(values[col]);
					sudokuBoard[row][col] = value;
					num_empty_cells += (value == 0 ? 1 : 0);
				}
			}

		} catch (IOException e) {
			// Handle exceptions that might occur during file operations
			System.err.println("Error: " + e.getMessage());
			System.exit(1);
		}

		// Additional variables like _INIT_NUM_EMPTY_CELLS can be set here
		_INIT_NUM_EMPTY_CELLS = num_empty_cells;
		return sudokuBoard;
	}

	@Override
	public void writeOutput(SudokuBoard solutionBoard, String fileName) throws IOException {
		int[][] solution = solutionBoard.getBoard();
		int BOARD_SIZE = solutionBoard.get_BOARD_SIZE();
		int BOX_SIZE = solutionBoard.get_BOX_SIZE();

		try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileName))) {
			Formatter formatter = new Formatter(outputFile);
			int digit = (int) Math.log(10) + 1;

			for (int r = 0; r < BOARD_SIZE; r++) {
				for (int c = 0; c < BOARD_SIZE; c++) {
					formatter.format("%" + digit + "d", solutionBoard.getBoardData(r, c));

					if (c != BOARD_SIZE - 1) {
						formatter.format(" ");
					}

					if (c % BOX_SIZE == (BOX_SIZE - 1)) {
						if (c != BOARD_SIZE - 1) {
							formatter.format(" ");
						}
					}
				}
				if (r != BOARD_SIZE - 1) {
					formatter.format("%n");
					if (r % BOX_SIZE == (BOX_SIZE - 1)) {
						formatter.format("%n");
					}
				}
			}
			formatter.close();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public SudokuBoard(SudokuBoard anotherSudokuBoard) {
		this.Board = anotherSudokuBoard.getBoard();
		this._BOX_SIZE = anotherSudokuBoard.get_BOX_SIZE();
		this._BOARD_SIZE = anotherSudokuBoard.get_BOARD_SIZE();
		this._MIN_VALUE = anotherSudokuBoard.get_MIN_VALUE();
		this._MAX_VALUE = anotherSudokuBoard.get_MAX_VALUE();
		this._INIT_NUM_EMPTY_CELLS = anotherSudokuBoard.get_INIT_NUM_EMPTY_CELLS();
	}

	public SudokuBoard() {
		// TODO Auto-generated constructor stub
	}

	public void resetCell(int row, int col) {
		this.Board[row][col] = _EMPTY_CELL_VALUE;
	}

	public void setBoardData(int row, int col, int num) {
		this.Board[row][col] = num;
	}

	public int getBoardData(int row, int col) {
		return this.Board[row][col];
	}

	public int at(int row, int col) {
		return this.Board[row][col];
	}

	public int getNumTotalCells() {
		return _BOARD_SIZE * _BOARD_SIZE;
	}

	public int getNumEmptyCells() {
		int n = 0;
		for (int i = 0; i < _BOARD_SIZE; ++i) {
			for (int j = 0; j < _BOARD_SIZE; ++j) {
				n += (this.at(i, j) == 0 ? 1 : 0);
			}
		}
		return n;
	}

	public ArrayList<Integer> getNumberInRow(int idxRow) {
		ArrayList<Integer> numberInRow = new ArrayList<Integer>();
		for (int col = 0; col < _BOARD_SIZE; col++) {
			int num = this.Board[idxRow][col];
			if (num == _EMPTY_CELL_VALUE) {
				continue;
			}
			numberInRow.add(num);
		}
		return numberInRow;
	}

	public ArrayList<Integer> getNumberInCol(int idxCol) {
		ArrayList<Integer> numberInCol = new ArrayList<Integer>();
		for (int row = 0; row < _BOARD_SIZE; row++) {
			int num = this.Board[row][idxCol];
			if (num == _EMPTY_CELL_VALUE) {
				continue;
			}
			numberInCol.add(num);
		}
		return numberInCol;
	}

	@Override
	public void printBoard(SudokuBoard board, boolean solved) {
		String ANSI_RESET = "\u001B[0m";
		String ANSI_RED = "\u001B[31m";
		String ANSI_GREEN = "\u001B[32m";
		String ANSI_YELLOW = "\u001B[33m";
		int[][] grid = board.getBoard();
		int[][] initGrid = this.Board;
		for (int i = 0; i < board._BOARD_SIZE; ++i) {
			if (i % board._BOX_SIZE == 0 && i != 0) {
				String s1 = "---";
				String s2 = s1.repeat(board._BOX_SIZE) + " + ";
				System.out.print(s2.repeat(board._BOX_SIZE - 1) + s1.repeat(board._BOX_SIZE) + "\n");
			}

			for (int j = 0; j < board._BOARD_SIZE; ++j) {
				if (j % board._BOX_SIZE == 0 && j != 0) {
					System.out.print("  | ");
				}
				if (grid[i][j] == initGrid[i][j]) {
					if (j == board._BOARD_SIZE - 1) {
						System.out.printf("%s%2d%n%s", ANSI_GREEN, grid[i][j], ANSI_RESET);
					} else if (j % board._BOX_SIZE == board._BOX_SIZE - 1) {
						System.out.printf("%s%2d%s", ANSI_GREEN, grid[i][j], ANSI_RESET);
					} else {
						System.out.printf("%s%2d%s ", ANSI_GREEN, grid[i][j], ANSI_RESET);
					}
				} else {
					if (j == board._BOARD_SIZE - 1) {
						System.out.printf("%2d%n", grid[i][j]);
					} else if (j % board._BOX_SIZE == board._BOX_SIZE - 1) {
						System.out.printf("%2d", grid[i][j]);
					} else {
						System.out.printf("%2d ", grid[i][j]);
					}
				}
			}
		}
	}

	/*----------------------- DEFAULT GETTER-SETTER METHOD -----------------------*/
	public int[][] getBoard() {
		return Board;
	}

	public void setBoard(int[][] board) {
		Board = board;
	}

	public int get_BOX_SIZE() {
		return _BOX_SIZE;
	}

	public void set_BOX_SIZE(int _BOX_SIZE) {
		this._BOX_SIZE = _BOX_SIZE;
	}

	public int get_BOARD_SIZE() {
		return _BOARD_SIZE;
	}

	public void set_BOARD_SIZE(int _BOARD_SIZE) {
		this._BOARD_SIZE = _BOARD_SIZE;
	}

	public int get_MIN_VALUE() {
		return _MIN_VALUE;
	}

	public void set_MIN_VALUE(int _MIN_VALUE) {
		this._MIN_VALUE = _MIN_VALUE;
	}

	public int get_MAX_VALUE() {
		return _MAX_VALUE;
	}

	public void set_MAX_VALUE(int _MAX_VALUE) {
		this._MAX_VALUE = _MAX_VALUE;
	}

	public int get_INIT_NUM_EMPTY_CELLS() {
		return _INIT_NUM_EMPTY_CELLS;
	}

	public void set_INIT_NUM_EMPTY_CELLS(int _INIT_NUM_EMPTY_CELLS) {
		this._INIT_NUM_EMPTY_CELLS = _INIT_NUM_EMPTY_CELLS;
	}

	public int get_EMPTY_CELL_VALUE() {
		return _EMPTY_CELL_VALUE;
	}

	public void set_EMPTY_CELL_VALUE(int _EMPTY_CELL_VALUE) {
		this._EMPTY_CELL_VALUE = _EMPTY_CELL_VALUE;
	}

	@Override
	public int[][] readInput(int[][] matrix) {
		_BOARD_SIZE = matrix.length;
		_BOX_SIZE = (int) Math.sqrt(_BOARD_SIZE);
		_MAX_VALUE = _BOARD_SIZE;
		int numberEmptyCell = 0;
		int n = matrix.length;
		int[][] newBoard = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				newBoard[i][j] = matrix[i][j];
				numberEmptyCell += ((matrix[i][j] == 0) ? 1 : 0);
			}
		}
		_INIT_NUM_EMPTY_CELLS = numberEmptyCell;
		return newBoard;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public void printBoard(int[][] grid) {
		if (grid == null) {
			return;
		}
		int BOARD_SIZE = grid.length;
		int BOX_SIZE = (int) Math.sqrt(BOARD_SIZE);
		// TODO Auto-generated method stub
		for (int i = 0; i < BOARD_SIZE; ++i) {
			if (i % BOX_SIZE == 0 && i != 0) {
				String s1 = "---";
				String s2 = s1.repeat(BOX_SIZE) + " + ";
				System.out.print(s2.repeat(BOX_SIZE - 1) + s1.repeat(BOX_SIZE) + "\n");
			}

			for (int j = 0; j < BOARD_SIZE; ++j) {
				if (j % BOX_SIZE == 0 && j != 0) {
					System.out.print("  | ");
				}

				if (j == BOARD_SIZE - 1) {
					System.out.printf("%2d%n", grid[i][j]);
				} else if (j % BOX_SIZE == BOX_SIZE - 1) {
					System.out.printf("%2d", grid[i][j]);
				} else {
					System.out.printf("%2d ", grid[i][j]);
				}
			}
		}
	}

	@Override
	public void printBoard(SudokuBoard board) {
		int[][] grid = board.getBoard();

		for (int i = 0; i < board._BOARD_SIZE; ++i) {
			if (i % board._BOX_SIZE == 0 && i != 0) {
				String s1 = "---";
				String s2 = s1.repeat(board._BOX_SIZE) + " + ";
				System.out.print(s2.repeat(board._BOX_SIZE - 1) + s1.repeat(board._BOX_SIZE) + "\n");
			}

			for (int j = 0; j < board._BOARD_SIZE; ++j) {
				if (j % board._BOX_SIZE == 0 && j != 0) {
					System.out.print("  | ");
				}

				if (j == board._BOARD_SIZE - 1) {
					System.out.printf("%2d%n", grid[i][j]);
				} else if (j % board._BOX_SIZE == board._BOX_SIZE - 1) {
					System.out.printf("%2d", grid[i][j]);
				} else {
					System.out.printf("%2d ", grid[i][j]);
				}
			}
		}

	}
}
