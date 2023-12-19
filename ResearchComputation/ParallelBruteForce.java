package ResearchComputation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import Interface.IParallelBruteForce;
import Interface.MODES;
import Interface.pair;

public class ParallelBruteForce extends SudokuSolver implements IParallelBruteForce {

	public SudokuBoardDeque boardDeque;
	public int[][] finalSolution;
	private int THREAD;
	private int numberOfSolutions = 0;
	private boolean endService = false;
	private SudokuBoard solutionBoard;

	public ParallelBruteForce() {
		// TODO Auto-generated constructor stub
	}

	public ParallelBruteForce(SudokuBoard board, boolean printMessage, int THREAD) {
		this.solutionBoard = new SudokuBoard(board.getBoard());
		this.boardDeque = new SudokuBoardDeque(solutionBoard);
		this.board = board;
		this.THREAD = THREAD;
		if (printMessage) {
			System.out.println("Parallel Sudoku solver using brute force algorithm starts, please wait ...");
		}
	}

	public void bootstrap() {
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
				boardDeque.pushBack(new SudokuBoard(board.getBoard()));
			}
		}
		boardDeque.popFront();
	}

//	public void boostrap(SudokuBoardDeque boardDeque, int indexOfRows) {
//		if (boardDeque.size() == 0) {
//			return;
//		}
//
//		while (!checkIfRowFilled(boardDeque.front(), indexOfRows)) {
//			SudokuBoard board = boardDeque.front();
//			int emptyCellColIndex = findEmptyFromRow(board, indexOfRows);
//
//			for (int num = board.get_MIN_VALUE(); num <= board.get_MAX_VALUE(); num++) {
//				pair<Integer, Integer> pos = new pair<Integer, Integer>(indexOfRows, emptyCellColIndex);
//				if (isValid(board, num, pos)) {
//					board.setBoardData(pos.getFirst(), pos.getSecond(), num);
//					boardDeque.pushBack(new SudokuBoard(board));
//				}
//			}
//			boardDeque.popFront();
//		}
//	}

	@Override
	public void solve() {
		ExecutorService executors = Executors.newFixedThreadPool(getTHREAD());
		List<Callable<SequentialBruteForce>> callabeTasks = new ArrayList<>();
		int num_bootstraps = this.THREAD;
		for (int i = 0; i < num_bootstraps; i++) {
			bootstrap();
		}
		int numberOfBoards = boardDeque.size();
		System.out.println("Number of board after bootstrap: " + numberOfBoards);
		for (int i = 0; i < numberOfBoards; i++) {
			SudokuBoard tempBoard = boardDeque.get(i);
			int[][] matrix = copyMatrix(tempBoard.getBoard());
			SudokuBoard clonedBoard = new SudokuBoard(matrix);
			Callable<SequentialBruteForce> runnableTask = () -> {
				try {
					SequentialBruteForce tempSolver = new SequentialBruteForce(clonedBoard, false);
					tempSolver.setMode(MODES.PARALLEL_BRUTEFORCE);
					tempSolver.solve();
					return tempSolver;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			};
			callabeTasks.add(runnableTask);
		}
		List<Future<SequentialBruteForce>> result = null;
		try {
			result = executors.invokeAll(callabeTasks);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result == null) {
			executors.shutdown();
			return;
		}
		for (Future<SequentialBruteForce> future : result) {
			try {
				SequentialBruteForce tempSolver = future.get();

				if (tempSolver.getStatus()) {
					for (Future<SequentialBruteForce> otherFuture : result) {

						otherFuture.cancel(true);
					}
					board.printBoard(tempSolver.getSolution(), true);
					numberOfSolutions++;
					break;
				}
			} catch (ExecutionException | InterruptedException e) {
				e.printStackTrace(); // Handle exceptions from task execution
			}
		}
//		for (int i = 0; i < result.size(); i++) {
//			Future<SequentialBruteForce> tempSolver = result.get(i);
//			try {
//				if (tempSolver.get().getStatus()) {
//					if (!solvered) {
//						board.printBoard(tempSolver.get().getSolution(), true);
//						System.out.println("*****************");
//					}
//					solvered = true;
//					numberOfSolutions++;
////					break;
//				}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
		executors.shutdown();
		try {
			if (!executors.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
				executors.shutdownNow();
			}
		} catch (InterruptedException e) {
			executors.shutdown();
		}
		System.out.println("Is ExecutorSerivce shutdown: " + executors.isShutdown());
	}

//	@Override
//	public void solve() {
//		ExecutorService executors = Executors.newFixedThreadPool(getTHREAD());
//		List<Callable<SudokuBoard>> callabeTasks = new ArrayList<>();
//		pair<Integer, Integer> pos = findEmpty(board);
//		for (int num = board.get_MIN_VALUE(); num <= board.get_BOARD_SIZE(); num++) {
//			int[][] matrix = copyMatrix(this.board.getBoard());
//			SudokuBoard clonedBoard = new SudokuBoard(matrix);
//			if (isValid(clonedBoard, num, pos)) {
//				System.out.printf("Added (%d, %d) value: {%d} %n", pos.getFirst(), pos.getSecond(), num);
//				clonedBoard.set_MAX_VALUE(clonedBoard.get_BOARD_SIZE());
//				clonedBoard.setBoardData(pos.getFirst(), pos.getSecond(), num);
//				Callable<SudokuBoard> runnableTask = () -> {
////					SequentialBackTracking tempSolver = new SequentialBackTracking(clonedBoard, false);
////					tempSolver.setMode(MODES.PARALLEL_BRUTEFORCE);
////					tempSolver.solve();
////					return tempSolver.getSolution();
//					try {
//						SequentialBruteForce tempSolver = new SequentialBruteForce(clonedBoard, false);
//						tempSolver.setMode(MODES.PARALLEL_BRUTEFORCE);
//						tempSolver.solve();
//						return tempSolver.getSolution();
//
//					} catch (CloneNotSupportedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						return null;
//					}
//				};
//				callabeTasks.add(runnableTask);
//			}
//		}
//		SudokuBoard result = null;
//		try {
//			result = executors.invokeAny(callabeTasks);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		if (result == null) {
//			executors.shutdown();
//			return;
//		}
//		numberOfSolutions++;
//		board.printBoard(result, true);
//		executors.shutdown();
//		try {
//			if (!executors.awaitTermination(100, TimeUnit.MILLISECONDS)) {
//				executors.shutdownNow();
//			}
//		} catch (InterruptedException e) {
//			executors.shutdown();
//		}
//		System.out.println("Is ExecutorSerivce is shutdown: " + executors.isShutdown());
//	}

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

	@Override
	public void solve_k1() {
		// TODO Auto-generated method stub

	}

	public boolean isEndService() {
		return endService;
	}

	public void setEndService(boolean endService) {
		this.endService = endService;
	}

}
