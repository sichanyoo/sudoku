package sudoku.userinterface;

import javafx.scene.control.TextField;

//custom TextField object that extends TextField from java fx
public class SudokuTextField extends TextField {
    //x and y coordinate
    private final int x;
    private final int y;

    //constructor
    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //getters for x and y values
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //two overridden methods from TextField to fix weird error of
    //character duplication upon user input
    @Override
    public void replaceText(int i, int i1, String s) {
        if (!s.matches("[0-9]")) {
            super.replaceText(i, i1, s);
        }
    }
    @Override
    public void replaceSelection(String s) {
        if (!s.matches("[0-9]")) {
            super.replaceSelection(s);
        }
    }
}
