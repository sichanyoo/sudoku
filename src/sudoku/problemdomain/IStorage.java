package sudoku.problemdomain;

import java.io.IOException;

//getter and setter methods for game data (board data)
//used in sudoku.userinterface.logic.ControlLogic
public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
