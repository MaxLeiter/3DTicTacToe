public class Line
{
	
	
	private Coordinate[] coordinates = new Coordinate[4];

	/**
	 * Constructs a Line object with 4 coordinates. <br>
	 * <code>a</code> must be on the perimiter of the plane. a and b must be first two Coordinates on the line.<br>
	 * <code>
	 * [a][b][ ][ ] <br>
	 * [ ][ ][ ][ ] <br>
	 * [ ][ ][ ][ ] <br>
	 * [ ][ ][ ][ ] <br>
	 * </code>
	 **/
	public Line(Coordinate a, Coordinate b) {
		int xDiff = b.getX() - a.getX();
		int yDiff = b.getY() - b.getY();
		int zDiff = b.getZ() - b.getZ();

		for (int i = 0; i < 4; i++) {
			coordinates[i] = new Coordinate(a.getX() + xDiff * i, a.getY() + yDiff * i, a.getZ() + zDiff * i);
		}
	}
	
	/**
	 * Size of the coordinates array
	 * @return int coordinates.length
	 */
	public int getSize() {
		return this.coordinates.length;
	}
	
	/**
	 * Set a Coordinate in coordinates[]
	 * @param index 0 based indexing
	 * @param value Coordinate
	 */
	public void setCoord(int index, Coordinate value) {
		this.coordinates[index] = value;
	}
	
	/**
	 * The coordinates array
	 * @return Line[] coordinates
	 */
	public Coordinate[] getCoords() {
		return this.coordinates;
	}
}
