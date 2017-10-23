
public class Board {
	private Tile[][][] board;

	public Board() {
		board = new Tile[4][4][4];
	}
	
	public Tile[][][] getBoard() {
		return board;
	}
	
	public static enum Tile {
		X,
		O,
		BLANK
	}

}
