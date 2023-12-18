package ResearchComputation;

import java.io.IOException;

import Interface.MODES;

public class MainRunner {
	static TestSudoku testValid;

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		testValid = new TestSudoku();
		String fileName = "";
		MODES MODE = null;
		int kernel = 4;
		if (args.length >= 2) {
			fileName = args[0];
			int option = Integer.parseInt(args[1]);
			System.out.println("Option model: " + option);
			switch (option) {
			case 0:
				MODE = MODES.SEQUENTIAL_BACKTRACKING;
				break;
			case 1:
				MODE = MODES.SEQUENTIAL_BRUTEFORCE;
				break;
			case 2:
				MODE = MODES.PARALLEL_BRUTEFORCE;
				break;
			default:
				MODE = MODES.SEQUENTIAL_BACKTRACKING;
				break;
			}
		}
		if (args.length == 3) {
			kernel = Integer.parseInt(args[2]);
			System.out.println(MODE);
		}
		printTitle();
		if (MODE.equals(MODES.SEQUENTIAL_BACKTRACKING)) {
			runSequentialBackTracking(fileName);
		} else if (MODE.equals(MODES.SEQUENTIAL_BRUTEFORCE)) {
			runSequentialBruteForce(fileName);
		} else if (MODE.equals(MODES.PARALLEL_BRUTEFORCE)) {
			runParallelBruteForce(fileName, kernel);
		} else {
			System.out.print("Incorrect Option for Algorithm!");
		}
	}

	public static void runSequentialBruteForce(String fileName) throws CloneNotSupportedException {
		SudokuBoard board;
		try {
			board = new SudokuBoard(fileName);
			System.out
					.println("************************************* INPUT GRID *************************************");
			System.out.println("Initialzie empty cells: " + board.get_INIT_NUM_EMPTY_CELLS());
			System.out.println("BOX_SIZE: " + board.get_BOX_SIZE());
			board.printBoard(board);
			System.out.println("Total number cells: " + board.getNumTotalCells());
			SequentialBruteForce solver = new SequentialBruteForce(board, true);
			long begin = System.currentTimeMillis();
			solver.solve();
			long end = System.currentTimeMillis();
			System.out.println("Print in main");
			double time = (double) (end - begin) / 1e3;
			board.printBoard(solver.getSolution());
			System.out.println("[Solved in " + time + " seconds]");
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

	public static void runSequentialBackTracking(String fileName) throws IOException {
		SudokuBoard board = new SudokuBoard(fileName);
		System.out.println("************************************* INPUT GRID *************************************");
		System.out.println("Initialzie empty cells: " + board.get_INIT_NUM_EMPTY_CELLS());
		board.printBoard(board);
		SequentialBackTracking solver = new SequentialBackTracking(board, true);
		long begin = System.currentTimeMillis();
		solver.solve();
		long end = System.currentTimeMillis();
		double time = (double) (end - begin) / 1e3;
		System.out.println("************************************* OUTPUT GRID *************************************");
		board.printBoard(solver.getSolution(), true);
		System.out.println("[Solved in " + time + " seconds]");
	}

	public static void runParallelBruteForce(String fileName, int kernel) throws CloneNotSupportedException {
		SudokuBoard board;
		try {
			board = new SudokuBoard(fileName);
			System.out
					.println("************************************* INPUT GRID *************************************");
			System.out.println("Initialzie empty cells: " + board.get_INIT_NUM_EMPTY_CELLS());
			System.out.println("BOX_SIZE: " + board.get_BOX_SIZE());
			board.printBoard(board);
			System.out.println("Total number cells: " + board.getNumTotalCells());
			ParallelBruteForce solver = new ParallelBruteForce(board, true, kernel);
			System.out.println("************************************* OUTPUT GRID *************************************");
			long begin = System.currentTimeMillis();
			solver.solve();
			long end = System.currentTimeMillis();
//			System.out.println("Print in main");
			double time = (double) (end - begin) / 1e3;
			System.out.println("Total number of solution: " + solver.getNumberOfSolutions());
			System.out.println("[Solved in " + time + " seconds]");
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

	public static void printTitle() {
		System.out.println("\n"
				+ "███████╗██╗   ██╗██████╗  ██████╗ ██╗  ██╗██╗   ██╗    ███████╗ ██████╗ ██╗    ██╗   ██╗███████╗██████╗ \n"
				+ "██╔════╝██║   ██║██╔══██╗██╔═══██╗██║ ██╔╝██║   ██║    ██╔════╝██╔═══██╗██║    ██║   ██║██╔════╝██╔══██╗\n"
				+ "███████╗██║   ██║██║  ██║██║   ██║█████╔╝ ██║   ██║    ███████╗██║   ██║██║    ██║   ██║█████╗  ██████╔╝\n"
				+ "╚════██║██║   ██║██║  ██║██║   ██║██╔═██╗ ██║   ██║    ╚════██║██║   ██║██║    ╚██╗ ██╔╝██╔══╝  ██╔══██╗\n"
				+ "███████║╚██████╔╝██████╔╝╚██████╔╝██║  ██╗╚██████╔╝    ███████║╚██████╔╝███████╗╚████╔╝ ███████╗██║  ██║\n"
				+ "╚══════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝ ╚═════╝     ╚══════╝ ╚═════╝ ╚══════╝ ╚═══╝  ╚══════╝╚═╝  ╚═╝\n");
	}
}
