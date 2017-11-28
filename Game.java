import java.util.List;

public class Game {
	private Board.Tile ComputerTile = Board.Tile.X; // TODO: Set in constructor when you start game
	private Board.Tile AdversaryTile = Board.Tile.O; // TODO: Set in constructor when you start game
	private static boolean debug = false;
	public static void main(String[] args) {
		Game game = new Game();
		try {
			Coordinate x = game.start(Board.Tile.X, 3);
			//if (debug) {
				System.out.printf("board[%d][%d][%d] = Tile.X", x.getX(), x.getY(), x.getZ());
			//}
		} catch (Board.InvalidMoveException e) {
			e.printStackTrace();
		}
	}

	public Coordinate start(Board.Tile turn, int maxDepth) throws Board.InvalidMoveException {
		double bestScore = Double.NEGATIVE_INFINITY;
		Coordinate bestMove = null;
		Board board = new Board();
		List<Coordinate> moves = board.getValidMoves();
		for (Coordinate move : moves) {
			System.out.println("We going");
			Board temp = board.move(move, turn);
			double score = minimax(getNextTile(turn), temp, maxDepth);
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
	 * Returns the best move to be made
	 * 
	 * @param tile
	 * @param board
	 * @param depth
	 * @return
	 */
	public double minimax(Board.Tile tile, Board board, int maxDepth) {
		double score = 0;
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
		double eval = board.evaluate();
		if (Double.isInfinite(eval) || depth <= 0) {
			return eval;
		}
		List<Coordinate> moves = board.getValidMoves();

		double lowestMax = Double.POSITIVE_INFINITY; // need to maximize this
		Coordinate bestMaxMove = null;
		for (int index = 0; index < moves.size(); index++) {
			try {
				Coordinate move = moves.get(index);
				Board temp = board.move(move, tile);
				double max = max(getNextTile(tile), temp, depth - 1);
				if (max < lowestMax) {
					lowestMax = max;
					bestMaxMove = move;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lowestMax;
	}

	private double max(Board.Tile tile, Board board, int depth) {
		double eval = board.evaluate();
		if (Double.isInfinite(eval) || depth <= 0) {
			return eval;
		}

		List<Coordinate> moves = board.getValidMoves();

		double largestMin = Double.NEGATIVE_INFINITY; // need to minimize this
		Coordinate bestMinMove = null;
		for (int index = 0; index < moves.size(); index++) {
			try {
				Coordinate move = moves.get(index);
				Board temp = board.move(move, tile);
				double min = min(getNextTile(tile), temp, depth - 1);
				if (min > largestMin) {
					largestMin = min;
					bestMinMove = move;
				}
			} catch (Exception e) {
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
