import java.util.ArrayList;
import java.util.List;

public class Board {
	/**
	 * The 4x4x4 representation of the board.
	 **/
	private static Tile[][][] board;


	/** Constants for board evaluation **/
	private static float c1 = 10,
			c2 = 100,
			c3 = 10000;

	/**
	 * The Plane[18] array.
	 * (0, 0, 0) = bottom left closest to you. Z up. X right. Y away. Because who cares about relative, right?
	 **/
	private Plane[] planes;

	/**
	 * Construct a board object
	 **/
	public Board() {
		board = new Tile[4][4][4];
		planes = new Plane[18];
		this.construct();
	}


	/**
	 * Fill the board with <code>Tile.BLANK</code>
	 **/
	private void construct() {

		// Horizontal
		planes[0] = new Plane(new Coordinate(0, 0, 0), new Coordinate (0, 1, 0), new Coordinate (1, 0, 0)); // first flat
		planes[1] = new Plane(new Coordinate(0, 0, 1), new Coordinate (0, 1, 1), new Coordinate (1, 0, 1)); // second bottom flat 
		planes[2] = new Plane(new Coordinate(0, 0, 2), new Coordinate (0, 1, 2), new Coordinate (1, 0, 2)); // third bottom flat
		planes[3] = new Plane(new Coordinate(0, 0, 3), new Coordinate (0, 1, 3), new Coordinate (1, 0, 3)); // fourth bottom flat (top)

		// Verticals
		planes[4] = new Plane(new Coordinate(0, 0, 0), new Coordinate (0, 1, 0), new Coordinate (0, 0, 1)); // vert left
		planes[5] = new Plane(new Coordinate(1, 0, 0), new Coordinate (1, 1, 0), new Coordinate (1, 0, 1)); // vert second from left
		planes[6] = new Plane(new Coordinate(2, 0, 0), new Coordinate (2, 1, 0), new Coordinate (2, 0, 1)); // vert third from left
		planes[7] = new Plane(new Coordinate(3, 0, 0), new Coordinate (3, 1, 0), new Coordinate (3, 0, 1)); // vert right

		// Vertical Depths (this is not a nomenclature course)
		planes[8] = new Plane(new Coordinate(0, 0, 0), new Coordinate (0, 0, 1), new Coordinate (1, 0, 0)); // vert closest to you
		planes[9] = new Plane(new Coordinate(0, 1, 0), new Coordinate (0, 1, 1), new Coordinate (1, 1, 0)); // vert second closest to you
		planes[10] = new Plane(new Coordinate(0, 2, 0), new Coordinate (0, 2, 1), new Coordinate (1, 2, 0)); // vert third closest
		planes[11] = new Plane(new Coordinate(0, 3, 0), new Coordinate (0, 3, 1), new Coordinate (1, 3, 0)); // vert farthest away

		// y = x
		planes[12] = new Plane(new Coordinate(0, 0, 0), new Coordinate (0, 1, 1), new Coordinate (1, 0, 0)); // y = x
		planes[13] = new Plane(new Coordinate(0, 3, 0), new Coordinate (0, 2, 1), new Coordinate (1, 3, 0)); // 3 - y = x

		// z = x
		planes[14] = new Plane(new Coordinate(0, 0, 0), new Coordinate (0, 1, 0), new Coordinate (1, 0, 1)); // z = x
		planes[15] = new Plane(new Coordinate(3, 0, 0), new Coordinate (3, 1, 0), new Coordinate (2, 0, 1)); // 3 - z = x

		// x = y = z
		planes[16] = new Plane(new Coordinate(0, 0, 0), new Coordinate (1, 1, 0), new Coordinate (0, 0, 1)); // x = y = z
		planes[17] = new Plane(new Coordinate(3, 0, 0), new Coordinate (2, 1, 0), new Coordinate (3, 0, 1)); // 3 - x = y = z

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				for (int z = 0; z < board[0][0].length; z++) {
					board[x][y][z] = Tile.BLANK;
				}
			}
		}

		/*	board[0][0][0] = Tile.X;
		board[1][0][0] = Tile.X;
		board[2][0][0] = Tile.O;
		board[3][0][0] = Tile.O;
		board[0][1][0] = Tile.X;
		board[2][1][0] = Tile.O;
		board[3][1][0] = Tile.O;
		board[0][2][0] = Tile.X;
		board[1][2][0] = Tile.O;
		board[2][2][0] = Tile.X;
		board[3][2][0] = Tile.X;
		board[2][3][0] = Tile.O;
		board[3][3][0] = Tile.X;*/

		board[0][0][0] = Tile.O;
		board[1][0][0] = Tile.O;
		board[2][0][0] = Tile.O;
		board[3][0][0] = Tile.O;

		System.out.println(evaluate());
	}


	/**
	 * Returns Coordinates of Tile.Blank's
	 * @return Coordinate[] of Tile.BLANK's
	 */
	public List<Coordinate> getValidMoves() {
		List<Coordinate> coords = new ArrayList<Coordinate>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				for (int k = 0; k < board[i].length; k++) {
					if (board[i][j][k].equals(Tile.BLANK)) {
						coords.add(new Coordinate(i, j, k));
					}
				}
			}
		}
		return coords;
	}

	public double evaluate() {
		double eval = 0.0d;
		for (int i = 0; i < planes.length; i++) {
			for (int j = 0; j < planes[i].getLines().length; j++) {
				int counter = 0;
				Tile prev = getTile(planes[i].getLines()[j].getCoords()[0]);

				if (prev == Tile.X) {
					counter = 1;
				} else if (prev == Tile.O) {
					counter = -1;
				}

				for (int k = 1; k < planes[i].getLines()[j].getCoords().length; k++) {
					Tile current = getTile(planes[i].getLines()[j].getCoords()[k]);

					if (current != Tile.BLANK) {
						if (prev == current) {
							if (prev == Tile.X) {
								counter++;
							} else if (prev == Tile.O) {
								counter--;
							}
						} else {
							break;
						}
					}

					prev = current;
				}


				switch (Math.abs(counter)) {
				case 0:
					eval += 0;
					break;
				case 1:
					eval += c1 * Math.signum(counter);
					break;
				case 2:
					eval += c2 * Math.signum(counter);
					break;
				case 3:
					eval += c3 * Math.signum(counter);
					break;
				case 4:
					if (counter > 0) {
						eval += Double.POSITIVE_INFINITY;
					} else if (counter < 0) {
						eval += Double.NEGATIVE_INFINITY;
					}
					break;
				}

			}
		}
		return eval;
	}

	private void print() {
		for (int i = 0; i < planes.length; i++) {
			System.out.println(i + " -----");
			planes[i].print();
		}
	}

	public static Tile getTile(Coordinate coord) {
		return board[coord.getX()][coord.getY()][coord.getZ()];
	}

	private boolean isWon() {
		for (int i = 0; i < planes.length; i++) {
			if (planes[i].hasWonLine()) {
				return true;
			}
		}
		return false;
	}

	public static enum Tile {
		X,
		O,
		BLANK
	}

}
