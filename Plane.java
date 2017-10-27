public class Plane {
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
		int cxDiff = b.getX() - a.getX();
		int cyDiff = b.getY() - a.getY();
		int czDiff = b.getZ() - a.getZ();

		int rxDiff = c.getX() - a.getX();
		int ryDiff = c.getY() - a.getY();
		int rzDiff = c.getZ() - a.getZ();

		// Here be dragons.
		for (int count = 0; count < lines.length; count++) {
			for (int lineIndex = 0; lineIndex < lines[count].getSize(); lineIndex++) {
				switch(count) {
				case 0: // vertical
				case 1:
				case 2:
				case 3:
					lines[count].setCoord(lineIndex, new Coordinate(a.getX() + lineIndex * rxDiff + count * cxDiff, a.getY() + lineIndex * ryDiff + count * cyDiff, a.getZ() + lineIndex * rzDiff + count * czDiff));
					break;
				case 4: // horizontal
				case 5:
				case 6:
				case 7:
					lines[count].setCoord(lineIndex, new Coordinate(a.getX() + lineIndex * cxDiff + count * rxDiff, a.getY() + lineIndex * cyDiff + count * ryDiff, a.getZ() + lineIndex * czDiff + count * rzDiff));
					break;
				case 8: // y = x
					lines[count].setCoord(lineIndex, new Coordinate(a.getX() + lineIndex * rxDiff + lineIndex * cxDiff, a.getY() + lineIndex * ryDiff + lineIndex * cyDiff, a.getZ() + lineIndex * rzDiff + lineIndex * czDiff));
					break;
				case 9: // y = 3 - x
					lines[count].setCoord(lineIndex, new Coordinate(a.getX() + 3 * rxDiff - lineIndex * rxDiff - lineIndex * cxDiff, a.getY() + 3 * ryDiff - lineIndex * ryDiff - lineIndex * cyDiff, a.getZ() + 3 * rzDiff - lineIndex * rzDiff - lineIndex * czDiff));
					break;
				default:
					// no-op
					System.err.println("Tried to define line outside of [0-9]");
				}

			}
		}
	}

	public Coordinate getXYZCoordinate(int r, int c) {
		return null;
	}


	/**
	 * Returns an array of <code>Line</code>'s. <br>
	 * Hopefully size 10, otherwise messed up.
	 * @return Line[10] lines
	 **/
	public Line[] getLines() {
		return null;
	}
}
