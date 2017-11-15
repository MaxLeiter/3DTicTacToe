import java.util.ArrayList;
import java.util.List;

import Board.Tile;

public class Game {
	Board.Tile AITile = Board.Tile.O; // TODO: Set in constructor when you start game
	Board.Tile AdversaryTile = Board.Tile.X; // TODO: Set in constructor when you start game

	public void minimax(Board board) {
    	
	}

	/**
	 * Returns utility value
	 * @param board
	 * @return
	 */
    private double min(Board board) {
    	Board tempBoard = board.copy();
    	ArrayList<Coordinate> moves = (ArrayList<Coordinate>) board.getValidMoves();
    	double[] evals = new double[moves.size()]; // evals is parallel with moves list
    	
    	for (int i = 0; i < evals.length; i++) {
    		evals[i] = tempBoard.move(moves.get(i), AITile).evaluate();
    		
    	}
    	
    	double evaluate = board.move(new Coordinate(), AITile);
    	if (board.evaluate() == Double.POSITIVE_INFINITY) {
    		return evaluate;
    	}
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (evalMove(list.get(i)) < min) {
                min = evalMove(list.get(i));
                index = i;
            }
        }
        return list.get(index);
    }

    private double max(Board board) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (evalMove(list.get(i)) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

}
