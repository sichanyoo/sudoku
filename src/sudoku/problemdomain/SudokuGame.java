package sudoku.problemdomain;
import sudoku.computationlogic.SudokuUtilities;
import sudoku.constants.GameState;

import java.io.Serializable;

//virtual representation of a sudoku game, board data + game state
//Serializable implemented to make writing game data to disk easier
public class SudokuGame implements Serializable {
    //enum for readability
    private final GameState gameState;
    //sudoku board data using 9 by 9 int 2D array
    private final int[][] gridState;

    //sudoku board boundary (9) constant for readability
    public static final int GRID_BOUNDARY = 9;

    //constructor
    public SudokuGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    //game state getter
    public GameState getGameState() {
        return gameState;
    }

    //grid state (board data) getter, data made immutable by returning copy
    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }
}
