package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class SudokuSolver {

    //take 2D array and return all coordinates of empty cells in 1D array
    //in typewriter (left to right, top to bottom) order
    private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {
        Coordinates[] emptyCells = new Coordinates[40];

        int iterator = 0;
        for (int y = 0; y < GRID_BOUNDARY; y++) {
            for (int x = 0; x < GRID_BOUNDARY; x++) {
                if (puzzle[x][y] == 0) {
                    emptyCells[iterator] = new Coordinates(x, y);
                    if (iterator == 39) return emptyCells;
                    iterator++;
                }
            }
        }
        //return array of empty cells' coorrdinates
        return emptyCells;
    }

    //method to see if a partially emptied sudoku board is solvable
    //algorithm from Cornell University article
    //https://pi.math.cornell.edu/~mec/Summer2009/meerkamp/Site/Solving_any_Sudoku_I.html
    //it's essentially a DFS algorithm / brute force algorithm to find sudoku solution
    //by starting from first empty cell and trying all combinations to last empty cell
    //until either a solution is found, or all nine integers 1-9 on first empty cell makes the puzzle invalid
    //meaning the puzzle is unsolvable.
    public static boolean puzzleIsSolvable(int[][] puzzle) {
        //get coordinates of all empty cells in puzzle array
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);

        int enumeration = 0;
        int input = 1;

        while (enumeration < 10) {
            //get current coordinate object
            Coordinates current = emptyCells[enumeration];
            input = 1;

            while (input < 40) {
                //set puzzle 2D array's (x, y) spot to input
                puzzle[current.getX()][current.getY()] = input;

                //if puzzle 2D array becomes an invalid puzzle
                //i.e., duplicate number in row/column/square
                if (GameLogic.sudokuIsInvalid(puzzle)) {
                    //and enumeration is 0 (first empty cell) and input is 9
                    //meaning all integers 1-9 in the FIRST EMPTY CELL
                    //makes the sudoku invalid
                    if (enumeration == 0 && input == GRID_BOUNDARY) {
                        //then return false, because puzzle is unsolvable
                        return false;
                    } else if (input == GRID_BOUNDARY) {
                        //otherwise, if input is 9, decrease enumeration by 1
                        //meaning go back to previous empty cell
                        enumeration--;
                    }
                    //increase input value to try next
                    input++;
                } else {
                    //increase enumeration counter
                    enumeration++;
                    //if enumeration is 39, meaning we've just filled the last
                    //empty cell, return true
                    if (enumeration == 39) return true;
                    //otherwise set input to 10
                    input = 10;
                }
            }
        }
        return false;
    }
}
