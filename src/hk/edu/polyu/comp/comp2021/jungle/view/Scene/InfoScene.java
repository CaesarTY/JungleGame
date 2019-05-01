package hk.edu.polyu.comp.comp2021.jungle.view.Scene;

import hk.edu.polyu.comp.comp2021.jungle.view.GameUI;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * This is the InfoScene class for the information scene
 */
public class InfoScene {

    private final int width = 600;
    private final int height = 800;
    private final int playerXX = 100;
    private final int playerXY = 400;
    private final int playerYX = 300;
    private final int playerYY = 400;
    private final int sBtnX = 250;
    private final int sBtnY = 600;
    private final int bBtnX = 20;
    private final int bBtnY = 20;

    private Group group = new Group();
    private Scene infoScene = new Scene(group ,width, height);
    private JungleGame model;
    private GameUI gameUI;

    /**
     * This is the constructor of InfoScene class
     * @param game jungle game
     * @param gameUI game ui
     */
    public InfoScene(JungleGame game, GameUI gameUI) {
        this.model = game;
        this.gameUI = gameUI;
        TextField playerX = new TextField();
        playerX.setPromptText("player 1 name");
        playerX.setLayoutX(playerXX);
        playerX.setLayoutY(playerXY);
        TextField playerY = new TextField();
        playerY.setPromptText("player 2 name");
        playerY.setLayoutX(playerYX);
        playerY.setLayoutY(playerYY);
        Button startGameBtn = new Button("Go"); // press to load gram from filepath
        startGameBtn.setLayoutX(sBtnX);
        startGameBtn.setLayoutY(sBtnY);
        startGameBtn.setOnAction((event) -> {
            // TODO validate name is different
            System.out.println("GO button pressed");
            if(playerX.getText()!=null && !playerX.getText().isEmpty() && playerY.getText()!=null && !playerY.getText().isEmpty()) {
//                    model.getPlayer()[0].setName(playerX.getText());
//                    model.getPlayer()[1].setName(playerY.getText());
                model.initializeGame(playerX.getText(), playerY.getText());
                System.out.println("player 1: "+ model.getPlayer()[0].getName()+", player 2: "+ model.getPlayer()[1].getName());
                gameUI.getPrimaryStage().setScene(gameUI.getBoardScene().getScene());
                gameUI.getBoardScene().start();
            } else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Uh Oh!");
                alert.setHeaderText(null);
                alert.setContentText("Please input all the fields, thanks!");
                alert.showAndWait();
            }
        });
        Button backBtn1 = new Button("Back");
        backBtn1.setLayoutX(bBtnX);
        backBtn1.setLayoutY(bBtnY);
        backBtn1.setOnAction((event) -> {
            System.out.println("back button1 pressed");
            gameUI.getPrimaryStage().setScene(gameUI.getEntryScene().getScene());
        });
        ObservableList list2 = group.getChildren();
        list2.addAll(startGameBtn,playerX,playerY,backBtn1);
    }

    /**
     * This function returns the infoscene Scene
     * @return infoscene
     */
    public Scene getScene(){
        return this.infoScene;
    }
}
