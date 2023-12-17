package Interface;

public class pair<T, E> {
	private T first;
	private E second;

	public pair(T first, E second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public E getSecond() {
		return second;
	}
}
