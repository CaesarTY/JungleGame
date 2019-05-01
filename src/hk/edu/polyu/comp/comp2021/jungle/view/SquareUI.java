package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.view.Scene.BoardScene;
import javafx.scene.layout.StackPane;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;

/**
 * This is the SuqareUI class for GUI of Square
 */
public class SquareUI {

    private final int width = 65;
    private final int height = 65;

    private JungleGame model;
    private StackPane square = new StackPane();
    private int id;
    private BoardScene boardScene;

    /**
     * This is the constructor of SquareUI
     * @param game jungle game
     * @param id id of square
     * @param boardScene boardscene
     */
    public SquareUI(JungleGame game, int id, BoardScene boardScene) {
        this.model = game;
        this.id = id;
        this.boardScene = boardScene;
        this.square.setPrefSize(width, height);
        this.square.setOnMouseClicked((event) -> {
            if (boardScene.getSelectedPiece()!=null) {
                boardScene.setSelectedSquare(id);
                System.out.println("\t selected piece position: " + boardScene.getSelectedPiece().getPosition() + " square: " + boardScene.getSelectedSquare());
                boardScene.movePiece();
            } else{
                System.out.println("square chosen before piece!");
            }
        });

    }

    /**
     * This fucntion adds Piece to Square
     * @param piece piece
     */
    public void addPiece(PieceUI piece) {
        this.square.getChildren().add(piece.getPiece());
    }

    /**
     * This function removes Piece from Square
     */
    public void removePiece(){
        this.square.getChildren().clear();
    }

    /**
     * This function returns square
     * @return square
     */
    public StackPane getSquare() {
        return this.square;
    }

    /**
     * This function returns square id
     * @return id
     */
    public int getSquareId () {
        return this.id;
    }
}
