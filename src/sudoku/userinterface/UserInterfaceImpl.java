package sudoku.userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.constants.GameState;
import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

import java.util.HashMap;

//EventHandler<KeyEvent> is what actually tells the program a key
//has been pressed on the keyboard
public class UserInterfaceImpl implements IUserInterfaceContract.View,
        EventHandler<KeyEvent> {

    //background window of the application, from java fx
    private final Stage stage;
    //similar to a <div> tag from HTML
    private final Group root;


    //hash map used to keep track of 81 different text fields of sudoku window
    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;
    //a new listener for handling input (dialog click, new sudoku board input)
    private IUserInterfaceContract.EventListener listener;

    //application window coordinate constants
    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_AND_Y = 576;
    //application window color and title constants
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 0, 0);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    private static final String SUDOKU = "Sudoku";

    //constructor for view
    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    //helper method to make constructor concise
    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        stage.show();
    }

    ////////////////////////////////////////////////////////
    /*5 methods below are another layer of helper methods for
    creating new elements in this.root for UI*/
    ///////////////////////////////////////////////////////

    //creates lines and adds to root (board)
    private void drawGridLines(Group root) {
        //xAndY is constant for where first lines are drawn (origin)
        //index is multiplied by 64 (pixels) each time to set lines apart
        int xAndY = 114;
        int index = 0;
        while (index < 8) {

            //set thickness
            int thickness;
            //make lines that divide squares within the board thicker
            if (index == 2 || index == 5) {
                thickness = 3;
            } else {
                thickness = 2;
            }

            //get vertical line
            Rectangle verticalLine = getLine(
                    xAndY + 64 * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
            );

            //get horizontal line
            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    BOARD_X_AND_Y
            );

            //add both lines to root (div)
            root.getChildren().addAll(
                    verticalLine,
                    horizontalLine
            );

            //increase index for next pair of lines
            index++;
        }
    }

    //helper method for drawGridLines()
    private Rectangle getLine(double x,
                              double y,
                              double height,
                              double width) {
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;
    }

    //draw 81 sudoku text fields
    private void drawTextFields(Group root) {
        //origin of where first text field is drawn
        final int xOrigin = 50;
        final int yOrigin = 50;

        //multiplier for indices to set text fields apart on baord
        final int xAndYDelta = 64;

        //O(n^2) loop for 81 fields on 9 x 9 sudoku board
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                //get x, y coordinate for text field
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;

                //instantiate a new text field object
                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);

                //style text field using helper method
                styleSudokuTile(tile, x, y);

                //where user input is actually "listened" to
                //since "this" extends EventHandler of java fx,
                //it can be passed to TextField method setOnKeyPressed
                tile.setOnKeyPressed(this);

                //add new sudoku text field to hash map with its coordinate (x, y position on board)
                //as the key
                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);

                //add sudoku text field to div (board space)
                root.getChildren().add(tile);
            }
        }
    }

    //helper method for styling each sudoku text field in drawTextFields()
    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        Font numberFont = new Font(32);

        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY);
    }

    //draws and adds sudoku board space to root (div)
    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);

        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);

        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        root.getChildren().addAll(boardBackground);
    }

    //sudoku title for application, below the board area
    private void drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    //the application window background
    //changes/sets stage (not the root (div))
    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    ////////////////////////////////////////////////////////
    //implementing methods from IUserInterfaceContract.View
    ////////////////////////////////////////////////////////

    //sets listener for the view object
    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    //update a specific sudoku text field with x, y coordinate to new input
    @Override
    public void updateSquare(int x, int y, int input) {
        //retrieve sudoku text field from hash map using coordinates object
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x, y));

        //change user input integer to string
        String value = Integer.toString(input);

        //if user input 0, change it to empty string
        if (value.equals("0")) value = "";

        //change the text of sudoku text field to user's input
        tile.textProperty().setValue(value);
    }

    //update the whole board to SudokuGame object passed to the method
    @Override
    public void updateBoard(SudokuGame game) {
        //loop for updating all 81 sudoku text tiles
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                //get current sudoku text field from hashmap using x, y coordinates
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));

                //get the corresponding sudoku text field string from game
                //parameter passed to method, by getting immutable copy of game data
                String value = Integer.toString(
                        game.getCopyOfGridState()[xIndex][yIndex]
                );

                //if string is 0, change it to empty string
                if (value.equals("0")) value = "";

                //then set current sudoku text field to retrieved string
                tile.setText(
                        value
                );

                //if board is being generated for a new game, set pre-filled sudoku text fields
                //a bit transparent, and disable them from user interaction
                if (game.getGameState() == GameState.NEW) {
                    if (value.equals("")) {
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    //show game win dialog and wait for click
    //upon click, handle using listener object
    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listener.onDialogClick();
    }

    //show game error dialog and wait for click
    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }

    //called when a key on keyboard is pressed
    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getText().matches("[0-9]")) {
                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource());
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, event.getSource());
            } else {
                ((TextField) event.getSource()).setText("");
            }
        }
        event.consume();
    }

    //helper method for handle() for readability
    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value
        );
    }
}
