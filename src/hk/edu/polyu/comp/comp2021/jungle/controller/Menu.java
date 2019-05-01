package hk.edu.polyu.comp.comp2021.jungle.controller;

import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import hk.edu.polyu.comp.comp2021.jungle.view.View;

/**This is the class of  Menu
 * contain controller
 */
public class Menu {

    private JungleGame model;
    private View view;

    /**This is the constructor of Menu class
     * @param model: junglegame
     * @param view: view
     */
    public Menu(JungleGame model, View view){
        this.model = model;
        this.view = view;
        view.start();
    }

}
