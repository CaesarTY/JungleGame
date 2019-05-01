package hk.edu.polyu.comp.comp2021.jungle.view;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the ApplicationUI class for launching GUI
 */
public class ApplicationUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        GameUI gameUI= new GameUI(primaryStage);

        // stage
        primaryStage.setTitle("THE JUNGLE GAME");
        primaryStage.setScene(gameUI.getEntryScene().getScene());
        primaryStage.show();
    }
}
