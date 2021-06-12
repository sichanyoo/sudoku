package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameGenerator {
    //return a new 9 x 9 game grid
    public static int [][] getNewGameGrid() {
        return unsolveGame(getSolvedGame());
    }

    //method to create a sudoku puzzle from fully filled and valid 9 x 9 sudoku game
    private static int[][] unsolveGame(int[][] solvedGame) {
        //random number generator
        Random random = new Random(System.currentTimeMillis());

        //solvable boolean
        boolean solvable = false;
        //new 9x9 grid
        int [][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        //while unsolvable, repeat algorithm
        while (solvable == false) {
            //copy solvedGame array to solvableArraay
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            //while loop index (i.e., number of integers removed from board)
            int index = 0;
            //randomly delete 40 numbers from sudoku board
            while (index < 40) {
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                //if randomly chosen (x, y) on board is not empty
                //then empty it, and increase the index
                if (solvableArray[xCoordinate][yCoordinate] != 0) {
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }

            //then copy modified solvableArray to toBeSolved array
            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);

            //then see if modified (emptied) array can be solved
            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
        }
        //once solvable sudoku puzzle is created, return the grid
        return solvableArray;
    }

    //backtracking algorithm to generate a fully filled and valid 9 x 9 sudoku board
    private static int[][] getSolvedGame() {
        //random number generator
        Random random = new Random(System.currentTimeMillis());
        //get a new 9x9 grid
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];


        for (int value = 1; value <= GRID_BOUNDARY; value++) {
            //number of times the value is put on board
            int allocations = 0;
            //number of times allocation is interrupted due to invalid board
            //this is used to start over allocation of specific value in 1-9 range
            int interrupt = 0;
            //fail safe variable for starting over the whole process
            int attempts = 0;

            //instantiate new allocTracker for current value from 1-9 range
            List<Coordinates> allocTracker = new ArrayList<>();

            //allocate each value (1-9) nine times on board
            while (allocations < GRID_BOUNDARY) {
                //if allocation is interrupted more than 200 times
                if (interrupt > 200) {
                    //reset allocations of current value
                    //and not the whole board
                    allocTracker.forEach(coord -> {
                        newGrid[coord.getX()][coord.getY()] = 0;
                    });

                    //reset interupt, allocations, clear allocTracker
                    //then increase attempt
                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    //if attempted over 500 times, that means the problem
                    //isn't just the current value but something went wrong
                    //with allocation of previous value as to make board unsolvable
                    //in this case, start over the whole thing
                    if (attempts > 500) {
                        //clear grid, reset attempt to 0, reset
                        //for loop counter (value) to 1
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }

                //if  interrupt < 200
                //get a new x, y coordinate on board
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                //if (x, y) on board is empty (i.e., == 0)
                if (newGrid[xCoordinate][yCoordinate] == 0) {
                    //then set that tile's integer to value
                    newGrid[xCoordinate][yCoordinate] = value;

                    //if the updated grid is invalid sudoku board
                    if (GameLogic.sudokuIsInvalid(newGrid)) {
                        //increase interrupt counter by 1, then
                        //change tile's integer back to 0
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    } else {
                        //if board is valid, then increase allocations counter by 1,
                        //then add (x, y) coordinate to allocTracker list
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }
        }
        //return newly made fully filled and valid sudoku board
        return newGrid;
    }

    //helper method to clear the 9 x 9 grid, all element to zero's
    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}
