package Main;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import Helper.*;
import javax.swing.JFrame;

public class SudokuSolver {

	public static final int GRID_SIZE = 9;
	public static JFrame frame;
	public static PanelSolver panel;
	private static GenerationBoard genBoard;
	private static int[][] grid;
	private static int[][] temp;
	private static Random ran = new Random();
	private static int level = 2;

	public static void main(String[] args) {
		grid = new int[9][9];
		temp = new int[9][9];
		genBoard = new GenerationBoard();
		frame = new JFrame();
		frame.setResizable(true);
		setSizeandLocationFrame();
		frame.setTitle("Sudoku Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new PanelSolver();
		frame.setContentPane(panel);
		frame.setVisible(true);
	}

	public static void setSizeandLocationFrame() {
		int frameWidth = 800;
		int frameHeight = 800;

		int offsetX = -100;
		int offsetY = -200;

		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		int locationX = (screenWidth - frameWidth) / 2 + offsetX;
		int locationY = (screenHeight - frameHeight) / 2 + offsetY;

		frame.setSize(frameWidth, frameHeight);
		frame.setLocation(locationX, locationY);
	}

	private static boolean isNumberInRow(int[][] board, int number, int row) {
		for (int i = 0; i < GRID_SIZE; i++) {
			if (board[row][i] == number) {
				return true;
			}
		}
		return false;
	}

	private static boolean isNumberInColumn(int[][] board, int number, int col) {
		for (int i = 0; i < GRID_SIZE; i++) {
			if (board[i][col] == number) {
				return true;
			}
		}
		return false;
	}

	private static boolean isNumberInBox(int[][] board, int number, int row, int col) {
		int localBoxRow = row - row % 3;
		int localBoxCol = col - col % 3;
		for (int i = localBoxRow; i < localBoxRow + 3; i++) {
			for (int j = localBoxCol; j < localBoxCol + 3; j++) {
				if (board[i][j] == number) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isValidPlacement(int[][] board, int number, int row, int col) {
		return !isNumberInRow(board, number, row) && !isNumberInColumn(board, number, col)
				&& !isNumberInBox(board, number, row, col);
	}

	private static boolean solveBoard(int[][] board) {
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				if (board[row][col] == 0) {
					for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {
						if (isValidPlacement(board, numberToTry, row, col)) {
							board[row][col] = numberToTry;
							if (solveBoard(board)) {
								return true;
							} else {
								board[row][col] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	public static void generateBoard(int level) {
		double rate;
		String tempLevel;
		switch (level) {
		case 2:
			tempLevel = "easy";
			System.out.println(tempLevel);
			rate = 0.5;
			break;
		case 3:
			tempLevel = "median";
			System.out.println(tempLevel);
			rate = 0.6;
			break;
		case 4:
			tempLevel = "hard";
			System.out.println(tempLevel);
			rate = 0.75;
			break;
		default:
			tempLevel = "medium";
			System.out.println(tempLevel);
			rate = 0.75;
			break;
		}
		genBoard.generateBoard();
		genBoard.shuffleRow();
		genBoard.shuffleCols();
		genBoard.shuffleBlockRows();
		genBoard.shuffleBlockCols();
		genBoard.hideCell(rate);
		grid = genBoard.getBoard();
		temp = genBoard.getHideBoard();

	}

	public static void newName() {
		generateBoard(level);
		if (solveBoard(grid)) {
			System.out.println("OK!!!" + level);
		}

		panel.setArray(grid, temp);
		panel.setTextLabel();
	}

//	public static void newName() {
//		int k = 0;
//		ArrayList<Integer> randomNumber = getRandomNum();
//
//		for (int i = 0; i < GRID_SIZE; i++) {
//			for (int j = 0; j < GRID_SIZE; j++) {
//				grid[i][j] = 0;
//				if ((j + 2) % 2 == 0 && ((i + 2) % 2) == 0) {
//					grid[i][j] = randomNumber.get(k);
//					k++;
//					if (k == 9) {
//						k = 0;
//					}
//				}
//			}
//		}
//
//		if (solveBoard(grid)) {
//			System.out.println("OK!!!" + level);
//		}
//
//		int rann = ran.nextInt(level);
//		int c = 0;
//		for (int i = 0; i < 9; i++) {
//			for (int j = 0; j < 9; j++) {
//				temp[i][j] = 0;
//				if (c < rann) {
//					c++;
//					continue;
//				} else {
//					rann = ran.nextInt(level);
//					c = 0;
//					temp[i][j] = grid[i][j];
//				}
//			}
//		}
//		panel.setArray(grid, temp);
//		panel.setTextLabel();
//	}

	private static ArrayList<Integer> getRandomNum() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (Integer i = 1; i < 10; i++) {
			numbers.add(i);
		}
		Collections.shuffle(numbers);
		return numbers;
	}

	public static void setLevel(int lev) {
		level = lev;
	}

	public static int getLevel() {
		return level;
	}

	public JFrame getFrame() {
		return frame;
	}
}
