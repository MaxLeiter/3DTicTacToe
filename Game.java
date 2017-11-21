import java.util.ArrayList;

public class Game {
	private Board.Tile ComputerTile = Board.Tile.X; // TODO: Set in constructor when you start game
	private Board.Tile AdversaryTile = Board.Tile.O; // TODO: Set in constructor when you start game

	public static void main(String[] args) {
		Game game = new Game();
		try {
			System.out.println(game.start(Board.Tile.X, 1));
		} catch (Board.InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Coordinate start(Board.Tile turn, int maxDepth) throws Board.InvalidMoveException {
		double bestScore = 0;
		Coordinate bestMove = null;
		Board board = new Board();
		ArrayList<Coordinate> moves = (ArrayList<Coordinate>) board.getValidMoves();
		for (Coordinate move : moves) {
			double score = minimax(turn, board, maxDepth);
			System.out.println("score: " + score);
			System.out.println("bestScore: " + bestScore);

			if (score > bestScore) {
				bestScore = score;
				bestMove = move;
				if (Double.isInfinite(bestScore)) {
					break;
				}
			}
		}
		return bestMove;
	}

	/**
	 * Returns the best move to be made
	 * 
	 * @param tile
	 * @param board
	 * @param depth
	 * @return
	 */
	public double minimax(Board.Tile tile, Board board, int maxDepth) {
		double score = 0;
		if (board.isWon()) {
			return score;
		}
		if (tile == ComputerTile) {
			score = max(tile, board, maxDepth);
		} else {
			score = min(tile, board, maxDepth);
		}
		return score;
	}

	/**
	 * Returns utility value. Minimize opponent.
	 * 
	 * @param board
	 * @return
	 */

	private double min(Board.Tile tile, Board board, int depth) {
		System.out.println("MIN: " + tile);

		ArrayList<Coordinate> moves = (ArrayList<Coordinate>) board.getValidMoves();

		double eval = board.evaluate();
		if (Double.isInfinite(eval) || depth == 0) {
			return eval;
		}

		for (int move = 0; move < moves.size(); move++) {
			try {
				Board temp = board.move(moves.get(move), tile);
				double max = max(getNextTile(tile), temp, depth - 1);
				System.out.println("max: " + max);
				return max;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return eval;
	}

	private double max(Board.Tile tile, Board board, int depth) {
		System.out.println("MAX: " + tile);
		ArrayList<Coordinate> moves = (ArrayList<Coordinate>) board.getValidMoves();
		System.out.println(moves);
		double eval = board.evaluate();
		if (Double.isInfinite(eval) || depth == 0 ) {
			return eval;
		}

		for (int move = 0; move < moves.size(); move++) {
			try {
				Board temp = board.move(moves.get(move), tile);
				double min = min(getNextTile(tile), temp, depth - 1);
				System.out.println("min: " + min);
				return min;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return eval;
	}

	private Board.Tile getNextTile(Board.Tile tile) {
		return (tile == ComputerTile) ? AdversaryTile : ComputerTile;
	}

}
