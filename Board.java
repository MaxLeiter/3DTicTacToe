
public class Board {
	private Tile[][][] board;

	public Board() {
		board = new Tile[4][4][4];
		this.construct();
		this.print();
	}

	private void construct() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				for (int z = 0; z < board[0][0].length; z++) {
					board[x][y][z] = Tile.BLANK;
				}
			}
		}
	}

	private void print() {

	}

	public static enum Tile {
		X,
		O,
		BLANK
	}

}
