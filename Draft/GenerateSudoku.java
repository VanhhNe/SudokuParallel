package Draft;



import java.util.Random;
import java.util.Scanner;

public class GenerateSudoku {
    static Random rand = new Random();
    static int board[][];
    static int n;
    static int numBlock;

    static int board2[][];
    static int boardHide[][];

    static void generateBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Gen row 1, 2, 3 
                if (i == 0) {
                    board[i][j] = j + 1;
                } else if (i < numBlock) {
                    int temp = board[i - 1][j] + numBlock;
                    if (temp > n) {
                        board[i][j] = temp - n;    
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

    static void printArray2D(int[][] boardSudoku) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
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

    static void shuffleRow() {
        int ithBlock;
        for (int i = 0; i < n; i+=numBlock) {
            int ranNum = rand.nextInt(numBlock);
            ithBlock = i / numBlock;
            swapRows(i, ithBlock * numBlock + ranNum);
        }
    }

    private static void swapRows(int r1, int r2) {
        int rowTemp[] = board[r1];
        board[r1] = board[r2];
        board[r2] = rowTemp;
    }

    static void shuffleCols() {
        int ithBlock;
        for (int j = 0; j < n; j+=numBlock) {
            int ranNum = rand.nextInt(numBlock);
            ithBlock = j / numBlock;
            swapCols(j, ithBlock * numBlock + ranNum);
        }
    }

    private static void swapCols(int c1, int c2) {
        int colTemp;
        for (int i = 0; i < n; i++) {
            colTemp = board[i][c1];
            board[i][c1] = board[i][c2];
            board[i][c2] = colTemp;
        }
    }

    static void shuffleBlockRows() {
        for (int i = 0; i < numBlock; i++) {
            int ranNum = rand.nextInt(numBlock);
            swapBlockRows(i, ranNum);
        }   
    }

    private static void swapBlockRows(int br1, int br2) {
        for (int i = 0; i < numBlock; i++) {
            swapRows(br1 * numBlock + i, br2 * numBlock + i);
        }
    }

    static void shuffleBlockCols() {
        for (int i = 0; i < numBlock; i++) {
            int ranNum = rand.nextInt(numBlock);
            swapBlockCols(i, ranNum);
        }   
    }

    private static void swapBlockCols(int bc1, int bc2) {
        for (int i = 0; i < numBlock; i++) {
            swapCols(bc1 * numBlock + i, bc2 * numBlock + i);
        }
    }

    static void hideCell(double rate) {
        boardHide = new int[n][n];
        // Coppy board to boardHine
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < n; l++) {
                if (board[k][l] != 0) {
                    boardHide[k][l] = board[k][l];
                }
            }
        }

        // Hide Cell
        for (int i = 0; i < n * n * rate; i++) {
            int row = rand.nextInt(n);
            int col = rand.nextInt(n);
            boardHide[row][col] = 0;
        }
    }


    private static boolean isNumberInRow(int[][] board, int number, int row) {
		for (int i = 0; i < n; i++) {
			if (board[row][i] == number) {
				return true;
			}
		}
		return false;
	}

	private static boolean isNumberInColumn(int[][] board, int number, int col) {
		for (int i = 0; i < n; i++) {
			if (board[i][col] == number) {
				return true;
			}
		}
		return false;
	}

	private static boolean isNumberInBox(int[][] board, int number, int row, int col) {
		int localBoxRow = row - row % numBlock;
		int localBoxCol = col - col % numBlock;
		for (int i = localBoxRow; i < localBoxRow + numBlock; i++) {
			for (int j = localBoxCol; j < localBoxCol + numBlock; j++) {
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

	private static int solveBoard(int[][] board, int solutionCount) {
        // System.out.printf("solutionCount: %d %n", solutionCount + 1);
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (board[row][col] == 0) {
					for (int numberToTry = 1; numberToTry <= n; numberToTry++) {
						if (isValidPlacement(board, numberToTry, row, col)) {
							board[row][col] = numberToTry;
                            int cache = solveBoard(board, solutionCount);
                            if (cache > 1) {
                                return cache;
                            }
							if (cache > solutionCount) {
								solutionCount = cache;
                                for (int k = 0; k < n; k++) {
                                    for (int l = 0; l < n; l++) {
                                        if (board[k][l] != 0) {
                                            board2[k][l] = board[k][l];
                                        }
                                    }
                                }
                                board[row][col] = 0;
							} else {
                                board[row][col] = 0;
                            }
						}
					}
					return solutionCount;
				}
			}
		}
		return solutionCount + 1;
	}

    private static boolean solveBoard(int[][] board) {
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (board[row][col] == 0) {
					for (int numberToTry = 1; numberToTry <= n; numberToTry++) {
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


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Size Sudoku: ");
        n = 25;
        numBlock = (int) Math.sqrt(n);
        board = new int[n][n];
        board2 = new int[n][n];

        // System.out.println(2 / 3);

        double rate = 0.75;
        
        generateBoard();
        shuffleRow();
        shuffleCols();
        shuffleBlockRows();
        shuffleBlockCols();
        printArray2D(board);
        System.out.println("---------------");

        int solutionCount;
        // int num = 0;
        while (true) {
            

            hideCell(rate);

            printArray2D(boardHide);

            solutionCount = solveBoard(boardHide, 0);

            System.out.println("solutionCount: " + solutionCount);
            if (solutionCount == 1) {
                System.out.println("Done");
                break;
            }
            // solveBoard(board, solutionCount);
            // System.out.printf("%d", num++);
        }

        printArray2D(board);
        System.out.println("-------------------\n");
        printArray2D(board2);
    }
}
