import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AI {
	private Board.Tile ComputerTile = Board.Tile.X; // TODO: Set in constructor when you start game
	private Board.Tile AdversaryTile = Board.Tile.O; // TODO: Set in constructor when you start game
	private static boolean DEBUG = true;
	private static boolean MOVE_ORDER = false;

	public Coordinate start(Board board, Board.Tile turn, int maxDepth) throws Board.InvalidMoveException {
		double bestScore = Double.NEGATIVE_INFINITY;
		Coordinate bestMove = null;
		List<Coordinate> moves = board.getValidMoves();
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		for (Coordinate move : moves) {
			Board temp = board.move(move, turn);
			double score = minimax(getNextTile(turn), temp, maxDepth, alpha, beta);
			if (DEBUG) {
				System.out.println("score: " + score);
				System.out.println("bestScore: " + bestScore);
			}

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

		List<Coordinate> coords = board.getValidMoves();
		List<Board> boards = new ArrayList<>();
		for (Coordinate move : coords) {
			try {
				boards.add(board.move(move, tile));
			} catch (Board.InvalidMoveException e) {
				e.printStackTrace();
			}
		}

		if (MOVE_ORDER) {
			boards = orderMoves(boards);
		}

		double lowestMax = Double.POSITIVE_INFINITY; // need to maximize this
		for (int index = 0; index < boards.size(); index++) {
			Board temp = boards.get(index);
			double max = max(getNextTile(tile), temp, depth - 1, alpha, beta);
			if (max < lowestMax) {
				lowestMax = max;
			}
			if (lowestMax <= alpha) {
				return lowestMax;
			}
			beta = Math.min(beta, lowestMax);
		}
		return lowestMax;
	}

	private double max(Board.Tile tile, Board board, int depth, double alpha, double beta) {
		double eval = board.evaluate();

		if (Double.isInfinite(eval) || depth <= 0) {
			return eval;
		}

		List<Coordinate> coords = board.getValidMoves();
		List<Board> boards = new ArrayList<>();
		for (Coordinate move : coords) {
			try {
				boards.add(board.move(move, tile));
			} catch (Board.InvalidMoveException e) {
				e.printStackTrace();
			}
		}

		if (MOVE_ORDER) {
			boards = orderMoves(boards);
		}

		double largestMin = Double.NEGATIVE_INFINITY; // need to minimize this
		for (int index = 0; index < boards.size(); index++) {
			Board temp = boards.get(index);
			double min = min(getNextTile(tile), temp, depth - 1, alpha, beta);
			if (min > largestMin) { // largestMin = Math.max(largestMin, min);
				largestMin = min;
			}
			if (largestMin >= beta) {
				return largestMin;
			}
			alpha = Math.max(alpha, largestMin);

		}

		return largestMin;
	}

	private Board.Tile getNextTile(Board.Tile tile) {
		return (tile == ComputerTile) ? AdversaryTile : ComputerTile;
	}

	private List<Board> orderMoves(List<Board> boards) {
		boards.sort(new Comparator<Board>() {
			@Override
			public int compare(Board first, Board second) {
				if (first.evaluate() < second.evaluate()) {
					return 1;
				} else if (first.evaluate() > second.evaluate()) {
					return -1;
				} else {
					return 0; // Same score
				}
			}
		});
		return boards;
	}

}