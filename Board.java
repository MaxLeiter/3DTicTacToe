import java.util.ArrayList;
import java.util.List;

public class Board {
	/**
	 * The 4x4x4 representation of the board.
	 **/
	private Tile[][][] board;


	/** Constants for board evaluation **/
	private static float CONSTANT_ONE_IN_ROW = 1,
			CONSTANT_TWO_IN_ROW = 100,
			CONSTANT_THREE_IN_ROW = 10000;

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
	 * Constructs board from tiles
	 * @param Tile[][][] array of tiles
	 */
	public Board(Tile[][][] tiles, Plane[] plane) {
		this.board = tiles;
		this.planes = plane;
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
					board[x][y][z] = Tile.B;
				}
			}
		}

	}

	/**
	 * Makes a move
	 * @param x
	 * @param y
	 * @param z
	 * @param tile
	 * @return new Board
	 * @throws InvalidMoveException
	 */
	public Board move(Coordinate move, Tile tile) throws InvalidMoveException {
		if (!isSquareBlank(move)) {
			throw new InvalidMoveException();
		}
		Board newBoard = this.copy();
	//	System.out.println("Move: " + move + ", Tile: "  + tile);

		newBoard.board[move.getX()][move.getY()][move.getZ()] = tile;
		return newBoard;
	}


	/**
	 * Returns if Tile at x, y, z is Blank
	 * @param x
	 * @param y
	 * @param z
	 * @return if Tile == Tile.B
	 */
	private boolean isSquareBlank(Coordinate square) {
		Tile tile = this.getTile(new Coordinate(square.getX(), square.getY(), square.getZ()));
		return tile == Tile.B;
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
					Coordinate coord = new Coordinate(i, j, k);
					if (isSquareBlank(coord)) {
						coords.add(coord);
					}
				}
			}
		}
		return coords;
	}

	private double evaluatePlane(Plane plane) {
		double eval = 0.0d;
		for (int j = 0; j < plane.getLines().length; j++) {
			int xCounter = 0;
			int oCounter = 0;
			
			for (int k = 0; k < plane.getLines()[j].getSize(); k++) {
				Tile rover = getTile(plane.getLines()[j].getCoords()[k]);

				if (rover == Tile.X) {
					xCounter++;
				} else if (rover == Tile.O) {
					oCounter++;
				}

			}


			if (xCounter != 0 && oCounter != 0) {
				xCounter = oCounter = 0;
			}

			int counter = xCounter != 0 ? xCounter : -oCounter;

			switch (Math.abs(counter)) {
			case 0:
				eval += 0;
				break;
			case 1:
				eval += CONSTANT_ONE_IN_ROW * Math.signum(counter);
				break;
			case 2:
				eval += CONSTANT_TWO_IN_ROW * Math.signum(counter);
				break;
			case 3:
				eval += CONSTANT_THREE_IN_ROW * Math.signum(counter);
				break;
			case 4:
				if (counter > 0) {
					eval = Double.POSITIVE_INFINITY;
				} else if (counter < 0) {
					eval = Double.NEGATIVE_INFINITY;
				}
				break;
			}
		}
		return eval;
	}

	public void printTiles(int plane) {
		for (int j = 0; j < planes[plane].getLines()[0].getSize(); j++) {
			for (int i = 0; i < planes[plane].getLines()[j].getCoords().length; i++) {
				System.out.print(this.getTile(planes[plane].getLines()[j].getCoords()[i]) + " ");
				if ((i + 1) % 4 == 0) {
					System.out.println();
				}
			}
		}
		System.out.println();
	}

	public boolean isWon() {
		for (int i = 0; i < planes.length; i++) {
			for (int j = 0; j < planes[i].getLines().length; j++) {
				int counter = 0;
				Board.Tile previous = getTile(planes[i].getLines()[j].getCoords()[0]); // first tile
				for (int k = 1; k < planes[i].getLines()[0].getSize(); k++) {
					Board.Tile tile = getTile(planes[i].getLines()[j].getCoords()[k]);
					if (tile.equals(Board.Tile.X) || tile.equals(Board.Tile.O)) {
						if (tile.equals(previous)) {
							counter++;
							previous = tile;
						} else {
							break;
						}
					} else {
						break;
					}
					if (counter == 3) {
						return true;
					}
				}
				counter = 0;
			}
		}
		return false;
	}

	public double evaluate() {
		double eval = 0.0d;
		for (int i = 0; i < planes.length; i++) {
			eval += evaluatePlane(planes[i]);
		}
		return eval;
	}

	public void print() {
		for (int i = 0; i <= 3; i++) {
			System.out.println(i + " -----");
			this.printTiles(i);
		}
	}

	public Tile getTile(Coordinate coord) {
		return board[coord.getX()][coord.getY()][coord.getZ()];
	}

	public static enum Tile {
		X,
		O,
		B
	}


	static class InvalidMoveException extends Exception {
		public InvalidMoveException() {
			super("Invalid move made. Square is not blank");
		}
	}

	public Board copy() {
		Tile[][][] newBoard = new Tile[4][4][4];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				for (int k = 0; k < board[i][j].length; k++) {
					newBoard[i][j][k] = board[i][j][k];
				}
			}
		}

		return new Board(newBoard, planes);
	}
}

