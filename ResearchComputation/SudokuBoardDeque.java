package ResearchComputation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import Interface.ISudokuBoardDeque;

public class SudokuBoardDeque implements ISudokuBoardDeque {
	Deque<SudokuBoard> boardDeque;

	public SudokuBoardDeque() {
		boardDeque = new ArrayDeque<SudokuBoard>();
	}

	public SudokuBoardDeque(SudokuBoard board) {
		boardDeque = new ArrayDeque<SudokuBoard>();
		boardDeque.add(board);
	}

	@Override
	public int size() {
		return boardDeque.size();
	}

	@Override
	public SudokuBoard front() {
		return boardDeque.peekFirst();
	}

	@Override
	public SudokuBoard back() {
		return boardDeque.peekLast();
	}

	@Override
	public void popFront() {
		boardDeque.pollFirst();
	}

	@Override
	public void popBack() {
		boardDeque.pollLast();
	}

	@Override
	public void pushFront(SudokuBoard board) {
		boardDeque.offerFirst(board);
	}

	@Override
	public SudokuBoard get(int i) {
		return new ArrayList<>(boardDeque).get(i);
	}

	@Override
	public void pushBack(SudokuBoard board) {
		boardDeque.offerLast(board);
	}
}
