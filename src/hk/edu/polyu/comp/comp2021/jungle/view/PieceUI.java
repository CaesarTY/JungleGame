package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.model.Piece;
import hk.edu.polyu.comp.comp2021.jungle.view.Scene.BoardScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is the PieceUI class for GUI of a piece
 */
public class PieceUI{
    /**
     * This is the path to the elephant icon
     */
    public static final String ElephantIconPath = "./resources/elephant.png";
    /**
     * This is the path to the lion icon
     */
    public static final String LionIconPath = "./resources/lion.png";
    /**
     * This is the path to the tiger icon
     */
    public static final String TigerIconPath = "./resources/tiger.png";
    /**
     * This is the path to the leopard icon
     */
    public static final String LeopardIconPath = "./resources/leopard.png";
    /**
     * This is the path to the wolf icon
     */
    public static final String WolfIconPath = "./resources/wolf.png";
    /**
     * This is the path to the dog icon
     */
    public static final String DogIconPath = "./resources/dog.png";
    /**
     * This is the path to the cat icon
     */
    public static final String CatIconPath = "./resources/cat.png";
    /**
     * This is the path to the rat icon
     */
    public static final String RatIconPath = "./resources/rat.png";

    private String path;
    private final int imageHeight = 52;
    private final int imageWidth = 44;
    private final int rotate = 180;

    private Button button;
    private Piece piece;
    private BoardScene boardScene;

    /**
     * This is the constructor of PieceUI class
     * @param piece piece
     * @param boardScene board scene
     */
    public PieceUI(Piece piece, BoardScene boardScene){
        this.piece = piece;
        this.boardScene = boardScene;
        switch (this.piece.getRank()){
            case 8:
                this.path = this.ElephantIconPath;
                break;
            case 7:
                this.path = this.LionIconPath;
                break;
            case 6:
                this.path = this.TigerIconPath;
                break;
            case 5:
                this.path = this.LeopardIconPath;
                break;
            case 4:
                this.path = this.WolfIconPath;
                break;
            case 3:
                this.path = this.DogIconPath;
                break;
            case 2:
                this.path = this.CatIconPath;
                break;
            case 1:
                this.path = this.RatIconPath;
                break;
            default:
                this.path = this.ElephantIconPath;
        }
        Image icon = new Image(this.path);
        ImageView imageView = new ImageView(icon);
        imageView.setFitHeight(imageHeight);
        imageView.setFitWidth(imageWidth);
        // set rotate
        if(this.piece.getOwner()==2){
            imageView.setRotate(imageView.getRotate() + rotate);
        }
        button = new Button("", imageView);
        button.setStyle("-fx-background-color: #ffffff; ");
        button.setOnAction((event) ->  {
            if (boardScene.getSelectedPiece() == boardScene.getTemp() && boardScene.getSelectedPieceOpponent() == boardScene.getTemp() && boardScene.getSelectedSquare() == -1) { // piece to square, choose first piece aka own piece
                if(piece.getOwner() == boardScene.currentTurn()) {
                    boardScene.setSelectedPiece(piece);
                    changeButtonColor("ff0000");
                    System.out.println("owner: " + piece.getOwner() +" selected piece position: " + piece.getPosition() + " square: " + boardScene.getSelectedSquare());
//                            boardScene.movePiece();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Uh Oh!");
                    alert.setHeaderText(null);
                    alert.setContentText("That's not your piece!");
                    alert.showAndWait();
                }
            } else if (boardScene.getSelectedPiece() != boardScene.getTemp() && boardScene.getSelectedSquare() == -1) { // piece to piece, choose second piece aka opponent piece
                if(piece.getOwner() != boardScene.currentTurn()) { // someone elses piece
                    boardScene.setSelectedPieceOpponent(piece);
                    changeButtonColor("ff0000"); //piece.getPieceUI().
                    System.out.println("\t selected piece position: " + boardScene.getSelectedPiece().getPosition() + " previous piece position: " + boardScene.getSelectedPieceOpponent().getPosition());
                    boardScene.movePiece();
                }
                else if (piece.getOwner() == boardScene.currentTurn() && boardScene.getSelectedPiece() == piece){ // click on current piece again, user get to choose another
                    boardScene.clearSelectedPiece();
                    boardScene.clearSelectedPieceOpponent();
                    boardScene.clearSelectedSquare();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Uh Oh!");
                    alert.setHeaderText(null);
                    alert.setContentText("You can't eat your own piece!");
                    alert.showAndWait();
                }

            }
        });
    }

    /**
     * This function returns button
     * @return button
     */
    public Button getPiece() {
        return button;
    }

    /**
     * This function return this piece class field ot pieceui
     * @return  return piece
     */
    public Piece getPieceClass() {
        return this.piece;
    }

    /**
     * This function changes the button color
     * @param color color
     */
    public void changeButtonColor(String color) {
        button.setStyle("-fx-background-color: #"+color+"; ");
    }

}
