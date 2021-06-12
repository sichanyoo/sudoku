package sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sudoku.buildlogic.SudokuBuildLogic;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.UserInterfaceImpl;

import java.io.IOException;

//extends Application from java fx library
public class SudokuApplication extends Application {
    //declare new UI view
    private IUserInterfaceContract.View uiImpl;


    @Override
    public void start(Stage primaryStage) throws Exception{
        //instantiate a new UI view using default java FX window (primaryStage)
        uiImpl = new UserInterfaceImpl(primaryStage);
        try {
            //wire MVC together
            SudokuBuildLogic.build(uiImpl);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //main method to use in class Main
    public static void main(String[] args) {
        launch(args);
    }
}
