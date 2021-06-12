package sudoku.buildlogic;

import sudoku.computationlogic.GameLogic;
import sudoku.persistence.LocalStorageImpl;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

//class that wires MVC together
public class SudokuBuildLogic {

    //build function that wires MVC together
    //so essentially:

    //sudoku.userinterface is the VIEW portion of MVC, except for its logic subfolder
    //sudoku.problemdomain is the MODEL portion of MVC, that holds data of the game as well as structure of the game grid and state
    //sudoku.userinterface.logic.ControlLogic is the CONTROLLER portion of MVC, that mediates interaction between VIEW and MODEL
    //      and ControlLogic controller uses functions from sudoku.computationlogic to generate new game boards and check if game is complete or not

    //and the application entry point is the Main class, using launch function from SudokuApplication class

    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try {
            initialState = storage.getGameData();
        } catch (IOException e) {
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }

        IUserInterfaceContract.EventListener uiLogic
                = new ControlLogic(storage, userInterface);

        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
