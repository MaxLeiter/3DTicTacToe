
public class Board {
	/**
	 * The 4x4x4 representation of the board.
	 **/
	private Tile[][][] board;

	/**
	 * Construct a board object
	 **/
	public Board() {
		board = new Tile[4][4][4];
		this.construct();
		this.print();
	}

	
	/**
	 * Fill the board with <code>Tile.BLANK</code>
	 **/
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
