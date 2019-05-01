package hk.edu.polyu.comp.comp2021.jungle;

import hk.edu.polyu.comp.comp2021.jungle.controller.Menu;
import hk.edu.polyu.comp.comp2021.jungle.view.*;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;

/**
 * This is the  Application Class
 */
public class Application {

    /**
     * This is the main class
     * @param args args
     */
    public static void main(String[] args){
        JungleGame game = new JungleGame();

        // launch GUI
        javafx.application.Application.launch(ApplicationUI.class);

        // start playing the game
        View view = new TextView(game);
        Menu controller = new Menu(game, view);
    }

}
