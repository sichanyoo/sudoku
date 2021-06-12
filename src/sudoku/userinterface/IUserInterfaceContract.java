package sudoku.userinterface;
import sudoku.problemdomain.SudokuGame;

public interface IUserInterfaceContract {
    //listener that listens to new user input on sudoku board and pop up
    //window click
    //acts as controller in this game application from MVC architecture
    interface EventListener {
        void onSudokuInput(int x, int y, int input);
        void onDialogClick();
    }

    //UI view
    interface View {
        //sets listener for the view
        void setListener(IUserInterfaceContract.EventListener listener);
        //updates a tile
        void updateSquare(int x, int y, int input);
        //updates whole board
        void updateBoard(SudokuGame game);
        //show win popup
        void showDialog(String message);
        //show error popup
        void showError(String message);
    }
}
