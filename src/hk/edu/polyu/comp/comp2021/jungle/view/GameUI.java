package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.view.Scene.*;
import javafx.application.Application;
import javafx.stage.Stage;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;

/**
 * This is the GameUI class
 */
public class GameUI{

    private Stage pStage;

    private JungleGame model = new JungleGame();;

    private EntryScene entryScene;
    private InfoScene infoScene;
    private BoardScene boardScene;

    /**
     * this is the constructor for GameUI
     * @param primaryStage primaryStage
     */
    public GameUI(Stage primaryStage){
        this.pStage= primaryStage;
        entryScene = new EntryScene(model,this);
        infoScene = new InfoScene(model,this);
        boardScene = new BoardScene(model,this);
    }

    /**
     * this function gets the entry scene
     * @return entry scene
     */
    public EntryScene getEntryScene() {
        return entryScene;
    }

    /**
     * this  function gets  the info  scene
     * @return info scene
     */
    public InfoScene getInfoScene() {
        return infoScene;
    }

    /**
     * this function gets the board scene
     * @return board scene
     */
    public BoardScene getBoardScene() {
        return boardScene;
    }

    /**
     * this function gets  the primary  stage
     * @return primary stage
     */
    public Stage getPrimaryStage() {
        return pStage;
    }

    /**
     * this is  the main  function  that launches the  application
     * @param args args
     */
    public static void main(String args[]){
        Application.launch(args);
    }

}
