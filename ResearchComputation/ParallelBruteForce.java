package ResearchComputation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import Interface.IParallelBruteForce;
import Interface.pair;

public class ParallelBruteForce extends SudokuSolver implements IParallelBruteForce {

	public int[][] finalSolution;
	private int THREAD;
	private int numberOfSolutions = 0;

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
	public void solve() {

		ExecutorService executors = Executors.newFixedThreadPool(getTHREAD());
		pair<Integer, Integer> pos = findEmpty(board);
		for (int num = board.get_MIN_VALUE(); num <= board.get_MAX_VALUE(); num++) {
			int[][] matrix = copyMatrix(this.board.getBoard());
			SudokuBoard clonedBoard = new SudokuBoard(matrix);
			if (isValid(clonedBoard, num, pos)) {
				clonedBoard.setBoardData(pos.getFirst(), pos.getSecond(), num);
				Callable<SudokuBoard> runnableTask = () -> {
//					SequentialBackTracking tempSolver = new SequentialBackTracking(clonedBoard, false);
//					tempSolver.solve();
//					return tempSolver.getSolution();
					try {
						SequentialBruteForce tempSolver = new SequentialBruteForce(clonedBoard, false);
						tempSolver.solve();
						return tempSolver.getSolution();

					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				};
				Future<SudokuBoard> future = executors.submit(runnableTask);
				SudokuBoard result = null;
				try {
					result = future.get();
					numberOfSolutions++;
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				if (result != null) {
					board.printBoard(result, true);
				}
				System.out.printf("Added (%d, %d) value: {%d} %n", pos.getFirst(), pos.getSecond(), num);
				break;
			}
		}
		executors.shutdown();
		try {
			if (!executors.awaitTermination(500, TimeUnit.MILLISECONDS)) {
				executors.shutdownNow();
			}
		} catch (InterruptedException e) {
			executors.shutdown();
		}
		System.out.println("Is ExecutorSerivce is shutdown: " + executors.isShutdown());
	}

	@Override
	public SudokuBoard getSolution() {
		solution = new SudokuBoard(finalSolution);
		return solution;
	}

	public int[][] copyMatrix(int[][] original) {
		int rows = original.length;
		int cols = original[0].length;
		int[][] copy = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			System.arraycopy(original[i], 0, copy[i], 0, cols);
		}
		return copy;
	}

	public int getNumberOfSolutions() {
		return this.numberOfSolutions;
	}

	public int getTHREAD() {
		return this.THREAD;
	}

	public void setTHREAD(int tHREAD) {
		this.THREAD = tHREAD;
	}

}
