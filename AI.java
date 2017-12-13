import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AI {
	private static boolean DEBUG = false;
	private static boolean MOVE_ORDER = false;

	public Coordinate start(Board board, Board.Tile ComputerTile, int maxDepth) throws Board.InvalidMoveException {
		double bestScore = Double.NEGATIVE_INFINITY;
		Coordinate bestMove = null;
		List<Coordinate> moves = board.getValidMoves();
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		for (Coordinate move : moves) { // this is the first step of minimax - maximize us; max of mins
			Board temp = board.move(move, ComputerTile);
			double score = min(Board.getNextTile(ComputerTile), temp, maxDepth, alpha, beta);

			if (score >= bestScore) {
				bestScore = score;
				bestMove = move;
			}



			if (DEBUG) {
				System.out.println("score: " + score);
				System.out.println("bestScore: " + bestScore);
			}
		}
		return bestMove;
	}


	/**
	 * Returns utility value. Minimize opponent.
	 * 
	 * @param board
	 * @return utility value
	 */

	private double min(Board.Tile tile, Board board, int depth, double alpha, double beta) {
		double eval = board.evaluate(Board.getNextTile(tile));

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
			boards = orderMoves(boards, tile);
		}

		double lowestMax = Double.POSITIVE_INFINITY; // need to minimize this
		for (int index = 0; index < boards.size(); index++) {
			Board temp = boards.get(index);
			double max = max(Board.getNextTile(tile), temp, depth - 1, alpha, beta);
			if (max < lowestMax) {
				lowestMax = max;
				System.out.println("lowestMax set to " + lowestMax);
			}
			if (lowestMax <= alpha) {
				return lowestMax;
			}
			beta = Math.min(beta, lowestMax);
		}

		return lowestMax;
	}

	private double max(Board.Tile tile, Board board, int depth, double alpha, double beta) {
		double eval = board.evaluate(tile);

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
			boards = orderMoves(boards, tile);
		}

		double largestMin = Double.NEGATIVE_INFINITY; // need to maximize this
		for (int index = 0; index < boards.size(); index++) {
			Board temp = boards.get(index);
			double min = min(Board.getNextTile(tile), temp, depth - 1, alpha, beta);
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

	private List<Board> orderMoves(List<Board> boards, Board.Tile tile) {
		boards.sort(new Comparator<Board>() {
			@Override
			public int compare(Board first, Board second) {
				if (first.evaluate(tile) < second.evaluate(tile)) {
					return 1;
				} else if (first.evaluate(tile) > second.evaluate(tile)) {
					return -1;
				} else {
					return 0; // Same score
				}
			}
		});
		return boards;
	}

}