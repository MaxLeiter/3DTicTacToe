import java.util.Scanner;

public class Game {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Board board = new Board();

		System.out.println("Welcome to 4D Tic Tac Toe.");
		Board.Tile playerTile = getPlayerTile(scanner);

		System.out.println("Player: input coordinates as `x <space> y <space> z`");

		while (board.getValidMoves().size() > 0 && !board.isWon()) {
			Board.Tile currentTile;
			if (playerTile == Board.Tile.O) {

				currentTile = Board.Tile.X;
				board = aiMove(board, currentTile);
				if (board.isWon()) {
					System.out.println(currentTile + " won!");
					break;
				}
				currentTile = Board.Tile.O;
				board = playerMove(board, scanner, currentTile);
				if (board.isWon()) {
					System.out.println(currentTile + " won!");
					break;
				}
			} else {
				currentTile = Board.Tile.X;
				board = playerMove(board, scanner, currentTile);
				if (board.isWon()) {
					System.out.println(currentTile + " won!");
					break;
				}
				currentTile = Board.Tile.O;
				board = aiMove(board, currentTile);
				if (board.isWon()) {
					System.out.println(currentTile + " won!");
					break;
				}
			}

		}
		if (board.getValidMoves().isEmpty()) {
			System.out.println("Draw. All spaces filled.");
		}

		if (board.isWon()) {
			System.out.println("Game over.");
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

	public static Board aiMove(Board board, Board.Tile aiTile) {
		AI ai = new AI();
		Coordinate move = null;

		try {
			move = ai.start(board, aiTile, 2);
		} catch (Board.InvalidMoveException e) {
			e.printStackTrace();
		}
		// testMove(board, move, aiTile);

		try {
			board = board.move(move, aiTile);
			board.print();
			System.out.println("AI moved " + move);
		} catch (Board.InvalidMoveException e) {
			e.printStackTrace();
		}

		return board;

	}

	public static Board playerMove(Board board, Scanner scanner, Board.Tile playerTile) {
		System.out.print("Coordinate to move: ");
		String input = "";
		int x = 0, y = 0, z = 0;

		if (scanner.hasNextLine()) {
			input = scanner.nextLine();
			String[] parsed = input.split(" ");
			try {
				x = Integer.parseInt(parsed[0]);
				y = Integer.parseInt(parsed[1]);
				z = Integer.parseInt(parsed[2]);
				if ((x > 3 || x < 0) || (y > 3 || y < 0) || (z > 3 || z < 0)) {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid move.");
				return playerMove(board, scanner, playerTile);
			}

		} else {
			System.err.print("Invalid input");
			return playerMove(board, scanner, playerTile);
		}
		Coordinate move;
		boolean success = false;
		do {
			move = new Coordinate(x, y, z);

			success = testMove(board, move, playerTile);
			if (!success) {
				System.out.println("Invalid move. Try again.");
				return playerMove(board, scanner, playerTile);
			}
		} while (!success);

		try {
			board = board.move(move, playerTile);
			board.print();
			System.out.println("Player moved " + move);
		} catch (Board.InvalidMoveException e) {
			e.printStackTrace();
		}
		return board;
	}

	public static Board.Tile getPlayerTile(Scanner scanner) {
		System.out.print("Player tile is (X / O): ");
		String tile = "";
		if (scanner.hasNextLine()) {
			tile = scanner.nextLine();
			if (tile.toUpperCase().equals("X")) {
				return Board.Tile.X;
			} else if (tile.toUpperCase().equals("O")) {
				return Board.Tile.O;
			} else {
				System.out.println("Invalid Tile. Must be X or O.");
				getPlayerTile(scanner);
			}
		}
		return null;
	}
}
