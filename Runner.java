
public class Runner {
	public static void main(String args[]) {
		Board b = new Board();
		Plane p = new Plane(new Coordinate(0, 0, 0), new Coordinate (0, 1, 0), new Coordinate (1, 0, 0));
		p.print();
	}
}
