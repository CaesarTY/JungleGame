package hk.edu.polyu.comp.comp2021.jungle.view.Scene;

import hk.edu.polyu.comp.comp2021.jungle.view.GameUI;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import static hk.edu.polyu.comp.comp2021.jungle.view.TextView.loadCheck;

/**
 * this is the EntryScene class
 */
public class EntryScene{

    private final int width = 600;
    private final int height = 800;
    private final int nBtnX = 150;
    private final int nBtnY = 400;
    private final int oBtnX = 350;
    private final int oBtnY = 400;

    private Group group = new Group();
    private Scene entryScene = new Scene(group ,width, height);
    private JungleGame model;
    private GameUI gameUI;

    /**
     * this is the constructor for  EntryScene class
     * @param game jungle game
     * @param gameUI game ui
     */
    public EntryScene(JungleGame game, GameUI gameUI) {
        this.model = game;
        this.gameUI = gameUI;
        entryScene.setFill(Color.WHITE);
        Button newGameBtn = new Button("New Game"); // press to start new game, icon is allowed
        newGameBtn.setLayoutX(nBtnX);
        newGameBtn.setLayoutY(nBtnY);
        newGameBtn.setOnAction((event) -> {
            System.out.println("new game button pressed");
            gameUI.getPrimaryStage().setScene(gameUI.getInfoScene().getScene());
        });
        Button oldGameBtn = new Button("Old Game"); // press to load gram from filepath
        oldGameBtn.setLayoutX(oBtnX);
        oldGameBtn.setLayoutY(oBtnY);
        oldGameBtn.setOnAction((event) -> {
            System.out.println("old game button pressed");
            // TODO load file
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Load Game");
            dialog.setHeaderText("Load Game");
            dialog.setContentText("Please enter file path:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(path -> System.out.println("File Path: " + path));

            if(result.isPresent()) {
                if(!result.get().equals("")){
                    if(loadGame(result.get())){
                        gameUI.getPrimaryStage().setScene(gameUI.getBoardScene().getScene());
                        gameUI.getBoardScene().start();
                        gameUI.getBoardScene().loadGameInitPiece();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Uh Oh!");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid file path, try again");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Uh Oh!");
                alert.setHeaderText(null);
                alert.setContentText("Invalid file path, try again");
                alert.showAndWait();
            }
        });
        ObservableList list1 = group.getChildren();
        list1.addAll(newGameBtn,oldGameBtn);

    }

    /**
     * this  function  get the  entryscene
     * @return entry scene
     */
    public Scene getScene(){
        return this.entryScene;
    }

    /**
     * this function loads the game
     * @param filePath file path string
     * @return true or false
     */
    public Boolean loadGame(String filePath){
        String load = new String();

            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("This file does not exist.Please enter the file path and name again:");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Uh Oh!");
                alert.setHeaderText(null);
                alert.setContentText("File does not exist, try again");
                alert.showAndWait();
            } else{
                try {
                    BufferedReader br = new BufferedReader(new FileReader(filePath));
                    load = br.readLine();
                    br.close();


                } catch (IOException e1) {
//                e1.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Uh Oh!");
                    alert.setHeaderText(null);
                    alert.setContentText("Cannot read file, try again");
                    alert.showAndWait();
                }
            }

        if (!loadCheck(load)){
            System.out.println("File broken");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uh Oh!");
            alert.setHeaderText(null);
            alert.setContentText("File is broken, try again");
            alert.showAndWait();
            return false;
        }
        else
            return model.loadGame(load);
    }


}
