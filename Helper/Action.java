package Helper;

public class Action {
	protected String type;
	protected int number;
	protected int row;
	protected int col;

	public Action(String type, int number, int row, int col) {
		this.type = type;
		this.number = number;
		this.row = row;
		this.col = col;
	}
}
