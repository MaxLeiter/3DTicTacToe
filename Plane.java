public class Plane {

	private int cxDiff, cyDiff, czDiff, rxDiff, ryDiff, rzDiff;

	Coordinate a;

	/**
	 * Lines in a plane <br>
	 * lines[0-3] vertical <br>
	 * lines[4-7] horizontal <br>
	 * lines[8] y = x <br>
	 * lines[9] y = 3 - x <br>
	 * [8][ ][ ][9] <br>
	 * [ ][8][9][ ] <br>
	 * [ ][9][8][ ] <br>
	 * [9][ ][ ][8] <br>
	 **/
	public Line[] lines = new Line[10];

	/**
	 * Constructs a Plane of 10 lines. 
	 * <code>
	 * [a][b][ ][ ] <br>
	 * [c][ ][ ][ ] <br>
	 * [ ][ ][ ][ ] <br>
	 * [ ][ ][ ][ ] <br>
	 * </code>
	 **/
	public Plane(Coordinate a, Coordinate b, Coordinate c) {
		this.a = a;
		cxDiff = b.getX() - a.getX();
		cyDiff = b.getY() - a.getY();
		czDiff = b.getZ() - a.getZ();

		rxDiff = c.getX() - a.getX();
		ryDiff = c.getY() - a.getY();
		rzDiff = c.getZ() - a.getZ();

		for (int i = 0; i < lines.length; i++) {
			lines[i] = new Line();
		}

		// Here be dragons.
		for (int count = 0; count < lines.length; count++) {
			for (int lineIndex = 0; lineIndex < lines[count].getCoords().length; lineIndex++) {
				switch(count) {
				case 0: // vertical
				case 1:
				case 2:
				case 3:
					lines[count].setCoord(lineIndex, new Coordinate(a.getX() + lineIndex * rxDiff + (count % 4) * cxDiff, a.getY() + lineIndex * ryDiff + (count % 4) * cyDiff, a.getZ() + lineIndex * rzDiff + (count % 4) * czDiff));
					break;
				case 4: // horizontal
				case 5:
				case 6:
				case 7:
					lines[count].setCoord(lineIndex, new Coordinate(a.getX() + lineIndex * cxDiff + (count % 4) * rxDiff, a.getY() + lineIndex * cyDiff + count * ryDiff, a.getZ() + lineIndex * czDiff + (count % 4) * rzDiff));
					break;
				case 8: // y = x
					lines[count].setCoord(lineIndex, new Coordinate(a.getX() + lineIndex * rxDiff + lineIndex * cxDiff, a.getY() + lineIndex * ryDiff + lineIndex * cyDiff, a.getZ() + lineIndex * rzDiff + lineIndex * czDiff));
					break;
				case 9: // y = 3 - x
					lines[count].setCoord(lineIndex, new Coordinate(a.getX() + 3 * rxDiff + 3 * cxDiff - lineIndex * rxDiff - lineIndex * cxDiff, a.getY() + 3 * ryDiff + 3 * cyDiff - lineIndex * ryDiff - lineIndex * cyDiff, a.getZ() + 3 * rzDiff + 3 * czDiff - lineIndex * rzDiff - lineIndex * czDiff));
					break;
				default:
					// no-op
					System.err.println("Tried to define line outside of [0-9]");
				}

			}
		}
	}

	/**
	 * (r, c) -> (x, y, z)
	 * @param r row
	 * @param c column
	 * @return
	 */
	public Coordinate getXYZCoordinate(int r, int c) {
		Coordinate temp = new Coordinate(a.getX() + r * rxDiff + c * cxDiff, a.getY() + r * ryDiff + c * cyDiff, a.getZ() + r * rzDiff + c * czDiff);
		return temp;
	}


	/**
	 * Returns an array of <code>Line</code>'s. <br>
	 * Hopefully size 10, otherwise messed up.
	 * @return Line[10] lines
	 **/
	public Line[] getLines() {
		return lines;
	}


	/**
	 * Print 10 lines in a plane
	 */
	public void print() {
		for (int i = 0; i < lines.length; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < lines[i].getSize(); j++) {
				System.out.print("[" + lines[i].getCoords()[j] + "]   ");
			}
			System.out.println();
		}
	}
	
	public boolean hasWonLine() {
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines[0].getSize(); j++) {
				Board.Tile tile = Board.getTile(lines[i].getCoords()[j]);
			}
		}
		return false;
	}
}
