package Draft;

import java.util.Random;

public class MultiThreading {

    private static final int BOARD_SIZE = 9;
    private int[][] board;

    public MultiThreading(int[][] board) {
        this.board = board;
    }

    public void solve() {
        // Create a thread for each possible value in the first empty cell
        for (int num = 1; num <= BOARD_SIZE; num++) {
            int[][] clonedBoard = cloneBoard(); // Create a copy of the board for each thread
            if (isValid(clonedBoard, 0, 0, num)) {
                // Start a new thread to explore this possibility
                Thread thread = new Thread(() -> solveParallel(clonedBoard, 0, 0));
                thread.start();
            }
        }
    }

    private void solveParallel(int[][] board, int row, int col) {
        if (row == BOARD_SIZE - 1 && col == BOARD_SIZE) {
            // Solution found
            printBoard(board);
            System.exit(0); // Terminate all threads
        }

        if (col == BOARD_SIZE) {
            // Move to the next row
            row++;
            col = 0;
        }

        if (board[row][col] != 0) {
            // Cell is already filled, move to the next one
            solveParallel(board, row, col + 1);
            return;
        }

        for (int num = 1; num <= BOARD_SIZE; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;

                // Recursively explore the next cell
                solveParallel(cloneBoard(board), row, col + 1);

                // Backtrack
                board[row][col] = 0;
            }
        }
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        // Check if 'num' is not already in the current row, column, and subgrid
        return !usedInRow(board, row, num) && !usedInCol(board, col, num) && !usedInSubgrid(board, row - row % 3, col - col % 3, num);
    }

    private boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInCol(int[][] board, int col, int num) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInSubgrid(int[][] board, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + startRow][col + startCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[][] cloneBoard() {
        int[][] clonedBoard = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(board[i], 0, clonedBoard[i], 0, BOARD_SIZE);
        }
        return clonedBoard;
    }

    private int[][] cloneBoard(int[][] source) {
        int[][] clonedBoard = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(source[i], 0, clonedBoard[i], 0, BOARD_SIZE);
        }
        return clonedBoard;
    }

    private void printBoard(int[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // int[][] sudokuBoard = {
        //     {5, 3, 0, 0, 7, 0, 0, 0, 0},
        //     {6, 0, 0, 1, 9, 5, 0, 0, 0},
        //     {0, 9, 8, 0, 0, 0, 0, 6, 0},
        //     {8, 0, 0, 0, 6, 0, 0, 0, 3},
        //     {4, 0, 0, 8, 0, 3, 0, 0, 1},
        //     {7, 0, 0, 0, 2, 0, 0, 0, 6},
        //     {0, 6, 0, 0, 0, 0, 2, 8, 0},
        //     {0, 0, 0, 4, 1, 9, 0, 0, 5},
        //     {0, 0, 0, 0, 8, 0, 0, 7, 9}
        // };

        // MultiThreading solver = new MultiThreading(sudokuBoard);
        // solver.solve();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + availableProcessors);
    }
}


// class RunnableDemo implements Runnable {
// 	static Random rand = new Random();
// 	private Thread t;
// 	private String threadName;
// 	RunnableDemo (String name) {
// 		threadName = name;
// 		System.out.println("Create " + threadName);
// 	}

// 	@Override
// 	public void run() {
// 		System.out.println("Running " + threadName);
// 		try {
// 			int n = rand.nextInt(100);
// 			while (n % 10 != 0) {
// 				System.out.println("Running " + threadName + ": get number " + n);
// 				n = rand.nextInt(100);
// 				Thread.sleep(50);
// 			}
// 		} catch (InterruptedException e) {
// 			System.out.println("Thread " + threadName + " interrupted");
// 		}
// 		System.out.println("Thread " + threadName + " exiting");
// 	}

// 	public void start() {
// 		System.out.println("Starting " + threadName);
// 		if (t == null) {
// 			t = new Thread(this, threadName);
// 			t.start();
// 		}
// 	}
// }

// public class MultiThreading {

// 	public static void main(String args[]) {
// 		System.out.println("Main thread running...");
// 		RunnableDemo r1 = new RunnableDemo("Thread1-1-HR-Dat");
// 		r1.start();

// 		RunnableDemo r2 = new RunnableDemo("Thread-2-Send-Email");
// 		r2.start();

// 		System.out.println("Main thread stopped");
// 	}
// }	