import java.util.List;

public class Game {
	private Board.Tile ComputerTile = Board.Tile.X; // TODO: Set in constructor when you start game
	private Board.Tile AdversaryTile = Board.Tile.O; // TODO: Set in constructor when you start game
	private static boolean debug = false;

	public static void main(String[] args) {
		Game game = new Game();
		try {
			Coordinate x = game.start(Board.Tile.X, 4);
			// if (debug) {
			System.out.printf("board[%d][%d][%d] = Tile.X", x.getX(), x.getY(), x.getZ());
			// }
		} catch (Board.InvalidMoveException e) {
			e.printStackTrace();
		}
	}

	public Coordinate start(Board.Tile turn, int maxDepth) throws Board.InvalidMoveException {
		double bestScore = Double.NEGATIVE_INFINITY;
		Coordinate bestMove = null;
		Board board = new Board();
		List<Coordinate> moves = board.getValidMoves();
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		for (Coordinate move : moves) {
			Board temp = board.move(move, turn);
			double score = minimax(getNextTile(turn), temp, maxDepth, alpha, beta);
			if (debug) {
				System.out.println("score: " + score);
				System.out.println("bestScore: " + bestScore);
			}

			if (score > bestScore) {
				bestScore = score;
				bestMove = move;
				System.out.println("Set");
				if (Double.isInfinite(bestScore)) {
					break;
				}
			}

		}
		return bestMove;
	}

	/**
	 * Returns the best move to be made using alpha-beta
	 * 
	 * @param tile
	 * @param board
	 * @param depth
	 * @return utility value
	 */
	public double minimax(Board.Tile tile, Board board, int maxDepth, double alpha, double beta) {
		double score = 0;
		if (tile == ComputerTile) {
			score = max(tile, board, maxDepth, alpha, beta);
		} else {
			score = min(tile, board, maxDepth, alpha, beta);
		}
		return score;
	}

	/**
	 * Returns utility value. Minimize opponent.
	 * 
	 * @param board
	 * @return utility value
	 */

	private double min(Board.Tile tile, Board board, int depth, double alpha, double beta) {
		double eval = board.evaluate();

		if (Double.isInfinite(eval) || depth <= 0) {
			return eval;
		}

		List<Coordinate> moves = board.getValidMoves();

		double lowestMax = Double.POSITIVE_INFINITY; // need to maximize this
		for (int index = 0; index < moves.size(); index++) {
			try {
				Coordinate move = moves.get(index);
				Board temp = board.move(move, tile);
				double max = max(getNextTile(tile), temp, depth - 1, alpha, beta);
				if (max < lowestMax) {
					lowestMax = max;
				}
				if (lowestMax <= alpha) {
					return lowestMax;
				}
				beta = Math.min(beta, lowestMax);
			} catch (Board.InvalidMoveException e) {
				e.printStackTrace();
			}
		}

		return lowestMax;
	}

	private double max(Board.Tile tile, Board board, int depth, double alpha, double beta) {
		double eval = board.evaluate();

		if (Double.isInfinite(eval) || depth <= 0) {
			return eval;
		}

		List<Coordinate> moves = board.getValidMoves();

		double largestMin = Double.NEGATIVE_INFINITY; // need to minimize this
		Coordinate bestMinMove = null; // for debugging purposes

		for (int index = 0; index < moves.size(); index++) {
			try {
				Coordinate move = moves.get(index);
				Board temp = board.move(move, tile);
				double min = min(getNextTile(tile), temp, depth - 1, alpha, beta);
				if (min > largestMin) { // largestMin = Math.max(largestMin, min);
					largestMin = min;
					bestMinMove = move;
				}
				if (largestMin >= beta) {
					return largestMin;
				}
				alpha = Math.max(alpha, largestMin);
			} catch (Board.InvalidMoveException e) {
				e.printStackTrace();
			}
		}

		if (debug) {
			System.out.println("coords: " + bestMinMove);
		}
		return largestMin;
	}

	private Board.Tile getNextTile(Board.Tile tile) {
		return (tile == ComputerTile) ? AdversaryTile : ComputerTile;
	}

}
