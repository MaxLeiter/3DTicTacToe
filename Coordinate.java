public class Coordinate {
	/**
	 * int x, y, z;
	 **/
	private int x, y, z;

	/**
	 * Construct a Coordinate object
	 * @param x
	 * @param y
	 * @param z
	 **/
	public Coordinate(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Get the X coordinate
	 * @return x
	 **/
	public int getX() {
		return x;
	}

	/**
	 * Get the Y coordinate
	 * @return y
	 **/
	public int getY() {
		return y;
	}

	/**
	 * Get the Z coordinate
	 * @return z
	 **/
	public int getZ() {
		return z;
	}

	@Override
	public String toString() {
		return getX() + " " + getY() + " " + getZ();
	}
	
	public boolean isCorner() {
		return (x == 3 || x == 0 && y == 3 || y == 0 && z == 3 || z == 0);
	}

}
