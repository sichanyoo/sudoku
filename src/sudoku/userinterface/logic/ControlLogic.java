package sudoku.userinterface.logic;

import sudoku.computationlogic.GameLogic;
import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;

import java.io.IOException;

//controller that mediates interaction between UI and backend of the game (computation logic)
public class ControlLogic implements IUserInterfaceContract.EventListener {

    //interfaces used to connect controller to view
    private IStorage storage;
    private IUserInterfaceContract.View view;

    //constructor
    public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }

    //handle user's input on a sudoku text field
    @Override
    public void onSudokuInput(int x, int y, int input) {
        //when user enters or deletes an input on sudoku text field
        //write it to game storage (game storage = source of truth as in actual game data)
        try {
             SudokuGame gameData = storage.getGameData();
             int[][] newGridState = gameData.getCopyOfGridState();
             newGridState[x][y] = input;

             //make a new SudokuGame object with updated grid
             gameData = new SudokuGame(
                     GameLogic.checkForCompletion(newGridState),
                     newGridState
             );

             //update local storage of game data
             storage.updateGameData(gameData);
             //after updating local storage, update view as well
             view.updateSquare(x, y, input);

             //if game state is complete, then show win pop up
             if (gameData.getGameState() == GameState.COMPLETE) {
                 view.showDialog(Messages.GAME_COMPLETE);
             }


        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    //handle pop up dialog click
    @Override
    public void onDialogClick() {
        try {
            //update storage first before updating UI
            storage.updateGameData(
                    GameLogic.getNewGame()
            );
            //now update UI
            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            view.showError(Messages.ERROR);
        }
    }
}
