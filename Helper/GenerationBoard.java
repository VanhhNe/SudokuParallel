package Helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GenerationBoard {
	private static Random rand = new Random();
	private int board[][];
	private static int size = 9;
	private static int numBlock = 3;
	private int boardHide[][];

	public GenerationBoard() {
		this.board = new int[size][size];
		this.boardHide = new int[size][size];
	}

	public GenerationBoard(int[][] board, int[][] hideBoard) {
		this.board = board;
		this.board = hideBoard;
	}

	public void generateBoard() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// Gen row 1, 2, 3
				if (i == 0) {
					this.board[i][j] = j + 1;
				} else if (i < numBlock) {
					int temp = board[i - 1][j] + numBlock;
					if (temp > size) {
						board[i][j] = temp - size;
					} else {
						board[i][j] = temp;
					}
				}
				// Gen row 4 - n
				else {
					if ((j + 1) % numBlock != 0) {
						board[i][j] = board[i - numBlock][j + 1];
					} else {
						board[i][j] = board[i - numBlock][j - numBlock + 1];
					}
				}
			}
		}
	}

	void printArray2D(int[][] boardSudoku) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ((j + 1) % numBlock == 0) {
					System.out.printf("%-5d", boardSudoku[i][j]);
				} else {
					System.out.printf("%-3d", boardSudoku[i][j]);
				}
			}
			if ((i + 1) % numBlock == 0) {
				System.out.printf("%n%n");
			} else {
				System.out.printf("%n");

			}
		}
	}

	public void shuffleRow() {
		int ithBlock;
		for (int i = 0; i < size; i += numBlock) {
			int ranNum = rand.nextInt(numBlock);
			ithBlock = i / numBlock;
			swapRows(i, ithBlock * numBlock + ranNum);
		}
	}

	private void swapRows(int r1, int r2) {
		int rowTemp[] = board[r1];
		board[r1] = board[r2];
		board[r2] = rowTemp;
	}

	public void shuffleCols() {
		int ithBlock;
		for (int j = 0; j < size; j += numBlock) {
			int ranNum = rand.nextInt(numBlock);
			ithBlock = j / numBlock;
			swapCols(j, ithBlock * numBlock + ranNum);
		}
	}

	private void swapCols(int c1, int c2) {
		int colTemp;
		for (int i = 0; i < size; i++) {
			colTemp = board[i][c1];
			board[i][c1] = board[i][c2];
			board[i][c2] = colTemp;
		}
	}

	public void shuffleBlockRows() {
		for (int i = 0; i < numBlock; i++) {
			int ranNum = rand.nextInt(numBlock);
			swapBlockRows(i, ranNum);
		}
	}

	private void swapBlockRows(int br1, int br2) {
		for (int i = 0; i < numBlock; i++) {
			swapRows(br1 * numBlock + i, br2 * numBlock + i);
		}
	}

	public void shuffleBlockCols() {
		for (int i = 0; i < numBlock; i++) {
			int ranNum = rand.nextInt(numBlock);
			swapBlockCols(i, ranNum);
		}
	}

	private void swapBlockCols(int bc1, int bc2) {
		for (int i = 0; i < numBlock; i++) {
			swapCols(bc1 * numBlock + i, bc2 * numBlock + i);
		}
	}

	public void hideCell(double rate) {
		// Coppy board to boardHine
		for (int k = 0; k < size; k++) {
			for (int l = 0; l < size; l++) {
				if (board[k][l] != 0) {
					boardHide[k][l] = board[k][l];
				}
			}
		}

		// Hide Cell
		for (int i = 0; i < size * size * rate; i++) {
			int row = rand.nextInt(size);
			int col = rand.nextInt(size);
			boardHide[row][col] = 0;
		}
	}

	public int[][] getBoard() {
		return this.board;
	}

	public int[][] getHideBoard() {
		return this.boardHide;
	}
}
