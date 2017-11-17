import java.util.ArrayList;

public class Game {
	private Board.Tile ComputerTile = Board.Tile.O; // TODO: Set in constructor when you start game
	private Board.Tile AdversaryTile = Board.Tile.X; // TODO: Set in constructor when you start game

	public static void main(String[] args) {
		Game game = new Game();
		System.out.println(game.start(Board.Tile.X, 15));
	}

	public double start(Board.Tile turn, int maxDepth) {
		return minimax(turn, new Board(), maxDepth);
	}

	/**
	 * Returns the best move to be made
	 * @param tile
	 * @param board
	 * @param depth
	 * @return
	 */
	public double minimax(Board.Tile tile, Board board, int depth) {
		double score = 0;
		if (board.isWon() || depth == 0) {
			return score;
		}

		if (tile == ComputerTile) { 
			score = max(tile, board, depth);
		} else {
			score = min(tile, board, depth);
		}
		return score;
	}

	/**
	 * Returns utility value. Minimize opponent.
	 * @param board
	 * @return
	 */

	// will take list of boards, and for each board, { call move() for that board, call max on that set of moves }

	private double min(Board.Tile tile, Board board, int depth) {
		ArrayList<Coordinate> moves = (ArrayList<Coordinate>) board.getValidMoves();
		Board[] boards = new Board[moves.size()];

		double eval = board.evaluate();
		if (Double.isInfinite(eval) || depth == 0) {
			return eval;
		}

		for (int move = 0; move < moves.size(); move++) {
			try {
				Board temp = board.move(moves.get(move), tile);
				boards[move] = temp;
				return max(getNextTile(tile), temp, depth - 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return eval;
	}

	// will take list of boards, and for each board, { call move() for that board, call min on that set of moves }
	private double max(Board.Tile tile, Board board, int depth) {
		ArrayList<Coordinate> moves = (ArrayList<Coordinate>) board.getValidMoves();
		Board[] boards = new Board[moves.size()];

		double eval = board.evaluate();
		if (Double.isInfinite(eval) || depth == 0) {
			return eval;
		}	

		for (int move = 0; move < moves.size(); move++) {
			try {
				Board temp = board.move(moves.get(move), tile);
				boards[move] = temp;
				return min(getNextTile(tile), temp, depth - 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return eval;
	}

	private Board.Tile getNextTile(Board.Tile tile) {
		return (tile  == ComputerTile) ? AdversaryTile : ComputerTile;
	}

}
