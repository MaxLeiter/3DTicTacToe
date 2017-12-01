import java.util.Scanner;

public class Game {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Board board = new Board();
		System.out.println("Welcome to 4D Tic Tac Toe.");
		System.out.println("Player tile is O. AI has first move.");
		System.out.println("Player: input coordinates as `x <space> y <space> z`");

		while (board.getValidMoves().size() > 0 && !board.isWon()) {
			Coordinate move;
			Board.Tile tile;
			move = aiMove(board);
			tile = Board.Tile.X; // computer
			testMove(board, move, tile);

			try {
				board = board.move(move, tile);
				System.out.println("AI moved " + move);
			} catch (Board.InvalidMoveException e) {
				e.printStackTrace();
			}

			tile = Board.Tile.O; // player
			boolean success = false;
			if (board.isWon()) {
				System.out.println("AI won.");
				return;
			}

			do {
				move = playerMove(board, scanner);
				success = testMove(board, move, tile);
				if (!success) {
					System.out.println("Invalid move. Try again.");
				}
			} while (!success);

			try {
				board = board.move(move, tile);
				System.out.println("Player moved " + move);
			} catch (Board.InvalidMoveException e) {
				e.printStackTrace();
			}
			if (board.isWon()) {
				System.out.println("Player Won");
			}
			board.print();
		}

	}

	public static boolean testMove(Board board, Coordinate move, Board.Tile tile) {
		try {
			board.move(move, tile);
			return true;
		} catch (Board.InvalidMoveException e) {
			return false;
		}
	}

	public static Coordinate aiMove(Board board) {
		AI ai = new AI();
		Coordinate x = null;
		try {
			x = ai.start(board, Board.Tile.X, 2);
		} catch (Board.InvalidMoveException e) {
			e.printStackTrace();
		}
		return x;
	}

	public static Coordinate playerMove(Board board, Scanner scanner) {
		System.out.print("Coordinate to move: ");
		String move = "";
		int x = 0, y = 0, z = 0;

		if (scanner.hasNextLine()) {
			move = scanner.nextLine();
			String[] parsed = move.split(" ");
			try {
				x = Integer.parseInt(parsed[0]);
				y = Integer.parseInt(parsed[1]);
				z = Integer.parseInt(parsed[2]);
				if ((x > 3 || x < 0) || (y > 3 || y < 0) || (z > 3 || z < 0)) {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid move.");
				playerMove(board, scanner);
			}

		} else {
			System.err.print("Invalid input");
			playerMove(board, scanner);
		}
		return new Coordinate(x, y, z);
	}
}
