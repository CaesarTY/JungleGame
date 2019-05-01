package hk.edu.polyu.comp.comp2021.jungle.view.Scene;

import hk.edu.polyu.comp.comp2021.jungle.model.Board;
import hk.edu.polyu.comp.comp2021.jungle.model.Piece;
import hk.edu.polyu.comp.comp2021.jungle.view.GameUI;
import hk.edu.polyu.comp.comp2021.jungle.view.PieceUI;
import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;
import hk.edu.polyu.comp.comp2021.jungle.view.SquareUI;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.util.Optional;

import static hk.edu.polyu.comp.comp2021.jungle.view.TextView.loadCheck;

/**
 * This is the BoardScene Class
 */
public class BoardScene {
    /**
     * This is the number of rows
     */
    public static final int NumRows = 9;
    /**
     * This is the number of columns
     */
    public static final int NumCols = 7;
    /**
     * This is the width
     */
    private final int width = 600;
    /**
     * This is the height
     */
    private final int height = 800;
    /**
     * This is the number of pieces
     */
    private final int pieceNumber = 16;
    /**
     * This is the X coordinate of background view
     */
    private final int bgViewX = 61;
    /**
     * This is  the Y coordinate of background view
     */
    private final int bgViewY =95;
    /**
     * This is the height of background view
     */
    private final int bgViewHeight = 600;
    /**
     * This is the width of background view
     */
    private final int bgViewWidth = 800;
    /**
     * This is the font size
     */
    private final int fontSize = 20;
    /**
     * This is the X coordinate of welcome  text
     */
    private final int wTextX = 135;
    /**
     * This  is the Y coordinate of welcome  text
     */
    private final int wTextY = 70;
    /**
     * This is the Y coordinate of turn text
     */
    private final int tTextX = 100;
    /**
     * This is the X coordinate of turn text
     */
    private final int tTextY = 720;
    /**
     * This is the x coordinate of save button
     */
    private final int sBtnX = 100;
    /**
     * this is the y coordinate of save button
     */
    private final int sBtnY = 750;
    /**
     * This is the x coordinate of save button
     */
    private final int lBtnX = 200;
    /**
     * this is the y coordinate of save button
     */
    private final int lBtnY = 750;
    /**
     * this is the x coordinate of exit button
     */
    private final int eBtnX = 300;
    /**
     * this is the  y coordinate of exit button
     */
    private final int eBtnY = 750;
    /**
     * this is  the x coordinate of grid pane
     */
    private final int gPaneX = 65;
    /**
     * this is the y coordinate of  grid pane
     */
    private final int gPaneY = 99;
    /**
     * this is the width of grid pane
     */
    private final int gPaneWidth = 463;
    /**
     * this is the height of grid pane
     */
    private final int gPaneHeight = 590;
    /**
     * this is temp1 for  transition between position and id
     */
    private final int temp1 = 65;
    /**
     * this is temp2 for transition between position and id
     */
    private final int temp2 = 57;


    private Group group = new Group();
    private Scene boardScene = new Scene(group,width, height);
    private JungleGame model;
    private GameUI gameUI;
    private GridPane gridPane = new GridPane();
    private SquareUI[][] squareUIs = new SquareUI[7][9];
    private PieceUI[] pieceUIS = new PieceUI[pieceNumber];
    private Text turnText = new Text();

    private Piece temp = new Piece(-1,-1);
    private Piece selectedPiece = temp;
    private Piece selectedPieceOpponent = temp;
    private int selectedSquareID = -1;

    /**
     * this is  the  constructor  for BoardScene
     * @param game jungle game
     * @param gameUI game  ui
     */
    public BoardScene(JungleGame game, GameUI gameUI) {
        this.model = game;
        this.gameUI = gameUI;
        this.initGridPane();
        this.initSquares();
        this.initPiece();

        Image background = new Image("./resources/background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setX(bgViewX);
        backgroundView.setY(bgViewY);
        backgroundView.setFitHeight(bgViewHeight);
        backgroundView.setFitWidth(bgViewWidth);
        backgroundView.setPreserveRatio(true);
        Text welcomeText = new Text();
        welcomeText.setFont(new Font(fontSize));
        welcomeText.setX(wTextX);
        welcomeText.setY(wTextY);
        welcomeText.setText("WELCOME TO THE JUNGLE GAME");

        turnText.setFont(new Font(fontSize));
        turnText.setX(tTextX);
        turnText.setY(tTextY);
        turnText.setText("Turn: ");
        Button saveBtn = new Button("Save Game"); // press to load gram from filepath
        saveBtn.setLayoutX(sBtnX);
        saveBtn.setLayoutY(sBtnY);
        saveBtn.setOnAction((event) -> {
            System.out.println("save button pressed");
            saveGame();
        });
        Button loadBtn = new Button("load Game"); // press to load gram from filepath
        loadBtn.setLayoutX(lBtnX);
        loadBtn.setLayoutY(lBtnY);
        loadBtn.setOnAction((event) -> {
            System.out.println("load button pressed");
            if (!model.isSaved()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Unsaved Changes");
                alert.setHeaderText(null);
                alert.setContentText("Current game has unsaved changes, sure to load without saving?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    load();
                } else {
                }
            }else {
                load();
            }

        });
        Button exitBtn = new Button("Exit Game"); // press to load gram from filepath
        exitBtn.setLayoutX(eBtnX);
        exitBtn.setLayoutY(eBtnY);
        exitBtn.setOnAction((event) -> {
            System.out.println("exit button pressed");
            gameUI.getPrimaryStage().close();
        });
        ObservableList list3 = group.getChildren();
        list3.addAll(backgroundView,saveBtn,exitBtn,loadBtn,turnText,welcomeText,gridPane); //backBtn2,
    }
    /**
     * this  function  gets board scene
     * @return boardscene
     */
    public Scene getScene(){
        return this.boardScene;
    }

    /**
     * this function gets  temp piece
     * @return temp piece
     */
    public Piece getTemp(){
        return this.temp;
    }

    /**
     * this function initiates gridpanes
     */
    public void initGridPane() {
        for (int i = 0; i < NumCols; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(cc);
        }
        for (int j = 0; j < NumRows; j++) {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rc);
        }
        gridPane.setLayoutX(gPaneX);
        gridPane.setLayoutY(gPaneY);
        gridPane.setPrefSize(gPaneWidth,gPaneHeight);
        gridPane.setGridLinesVisible(false);
    }

    public void load(){
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
    }
    /**This is the function to save Game
     * @param filePath: String of the game savefile path
     * */
    public void save(String filePath){
        //check saveStatus
        String[] Patharray = filePath.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< Patharray.length-1;i++){
            sb.append(Patharray[i]+"/");
        }
        String Path = sb.toString();
        File newFile = new File(Path);
        if  (!newFile.exists()  && !newFile.isDirectory()){
            if (newFile.mkdir()==false){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Uh Oh!");
                alert.setHeaderText(null);
                alert.setContentText("Invalid file path, try again");
                alert.showAndWait();
            }
        }

        File file =new File(filePath);

        if (file.exists())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uh Oh!");
            alert.setHeaderText(null);
            alert.setContentText("This file exists, try again");
            alert.showAndWait();

        } else{
            try {
                String save = model.saveGame();
                Boolean b = file.createNewFile();
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                pw.write(save);
                pw.close();

                if(b)  { // file created
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Saved");
                    alert.setHeaderText(null);
                    alert.setContentText("Game Saved");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Save Game");
                    alert.setHeaderText(null);
                    alert.setContentText("Save Game Error");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    /**
     * this is the function to save game
     */
    public void saveGame(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Save Game");
        dialog.setHeaderText("Save Game");
        dialog.setContentText("Please enter file path:");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            String filepath = result.get();
            save(filepath);
        }
    }
    /**
     * this  function initiates square
     */
    public void initSquares() {
        // initialize the squares
        for (int i = 0; i < NumCols; i++) {
            for (int j = 0; j < NumRows; j++) {
                SquareUI square = new SquareUI(model,9*i+j,this);
                this.squareUIs[i][j] = square;
                this.gridPane.add(square.getSquare(), i,j);
            }
        }
    }

    /**
     * this function initiates pieces
     */
    public void initPiece() {
        for (int i = 0; i < Board.PLAYER_NUM; i++){
            for (int j = 0; j < Board.PIECE_NUM; j++) {
                // 1. get piece from model
                // 2. create new pieceUI
                // 3. put piece on square
                PieceUI newTemp = new PieceUI(model.getBoard().getPiece(Board.PIECE_POSITION[i][j]), this);
                this.pieceUIS[i*Board.PIECE_NUM+j]=newTemp;
                getSquareFromId(this.positionToId(Board.PIECE_POSITION[i][j])).addPiece(newTemp);
//                model.getBoard().getPiece(Board.PIECE_POSITION[i][j]).setPieceUI(newTemp);
            }
        }
    }

    /**
     * this function loads the pieces in the correct place
     */
    public void loadGameInitPiece(){
        for(int a = 0; a < NumCols; a++){
            for (int b = 0;b< NumRows;b++){
                SquareUI squareUI = squareUIs[NumCols-a-1][b];
                squareUI.removePiece();
                Piece piece = model.getBoard().getSquarePieceMap().get((char)('G' - a) +""+ (char)('9' - b) + "");
                if(piece!=null) {
                    PieceUI pieceUI = getPieceUIFromPiece(piece);
                    pieceUI.getPieceClass().setPosition((char)('G' - a) +""+ (char)('9' - b) + "");
                    squareUI.addPiece(pieceUI);
                }
            }
        }
    }

    /**
     * this  function gets square from id
     * @param id id
     * @return square
     */
    public SquareUI getSquareFromId(int id) {
        int j = id % 9;
        int i = id / 9;
        return this.squareUIs[i][j];
    }

    /**
     * this function gtes id from position
     * @param position position
     * @return id
     */
    public int positionToId(String position) {
        int i = (int)position.charAt(0) - temp1;
        int j = temp2 - (int)position.charAt(1);
        return 9 * i + j;
    }

    /**
     * this function gets position from id
     * @param id id
     * @return position
     */
    public String idToPosition(int id) {
        int i = id / 9;
        int j = id % 9;
        return String.valueOf((char)(i + temp1)) +String.valueOf((char)(temp2 - j));
    }

    /**
     * this function gets the pieceui from piece
     * @param p piece
     * @return pieceui
     */
    public PieceUI getPieceUIFromPiece(Piece p){
        return pieceUIS[8-p.getRank()+(p.getOwner()-1)*8];
    }

    /**
     * this function starts a turn
     */
    public void start() {
        if(!model.isWin()) {
            int id = model.getNextTurn();
            System.out.println("Turn: Player " + id + " -- " + model.getPlayer()[id - 1].getName());
        }
    }

    /**
     * this function returns the current turn  player id
     * @return current turn player id
     */
    public int currentTurn() {return model.getNextTurn();}

    /**
     * this  function sets the selected piece
     * @param piece selected piece
     */
    public void setSelectedPiece(Piece piece) { this.selectedPiece = piece; }

    /**
     * This function returns the selected piece
     * @return selected piece
     */
    public Piece getSelectedPiece() {
        return this.selectedPiece;
    }

    /**
     * This function sets the selected opponent piece
     * @param piece id for piece
     */
    public void setSelectedPieceOpponent(Piece piece) { this.selectedPieceOpponent = piece; }

    /**
     * this  function returns  the selected opponent piece
     * @return selected opponent piece
     */
    public Piece getSelectedPieceOpponent() {
        return this.selectedPieceOpponent;
    }

    /**
     * this function sets the selected  square id
     * @param squareUIID id for suqare UI
     */
    public void setSelectedSquare(int squareUIID) {
        this.selectedSquareID = squareUIID;
    }

    /**
     * this  function returns the  selected square
     * @return selected square
     */
    public int getSelectedSquare() {
        return this.selectedSquareID;
    }

    /**
     * this function clears the selected piece
     */
    public void clearSelectedPiece() {
        if(this.selectedPiece!=temp){
            getPieceUIFromPiece(this.selectedPiece).changeButtonColor("ffffff");// this.selectedPiece.getPieceUI().changeButtonColor("ffffff");
        }
        this.selectedPiece = temp;
    }

    /**
     * this function clears the selected opponent  piece
     */
    public void clearSelectedPieceOpponent() {
        if(this.selectedPieceOpponent!=temp){
            getPieceUIFromPiece(this.selectedPieceOpponent).changeButtonColor("ffffff");// this.selectedPieceOpponent.getPieceUI().changeButtonColor("ffffff");
        }
        this.selectedPieceOpponent = temp;
    }

    /**
     * this function clears the  selected  square id
     */
    public void clearSelectedSquare() {
        this.selectedSquareID = -1;
    }

    /**
     * this function moves a piece
     */
    public void movePiece() {
        // 1. validate
        // 2. move / don't move
        // 3. clear selected
        // 4. call start()
        boolean move = this.validate();
        if (move) {
            if (this.selectedPiece!=temp&&this.selectedSquareID!=-1&&this.selectedPieceOpponent==temp){ // move piece to square
                // piece add to new square
                getSquareFromId(this.selectedSquareID).addPiece(getPieceUIFromPiece(this.selectedPiece)); //this.selectedPiece.getPieceUI()
                // piece remove from old square, its automatic!
//                getSquareFromId(this.selectedSquareID).removePiece();

                // piece setposition
                this.selectedPiece.setPosition(idToPosition(this.selectedSquareID));
                checkWin();

            } else if(this.selectedPiece!=temp&&this.selectedSquareID==-1&&this.selectedPieceOpponent!=temp) { // move piece onto another piece
                int newSquareId =  this.positionToId(this.selectedPieceOpponent.getPosition());
                // remove 2nd piece from square
                getSquareFromId(newSquareId).removePiece();
                // 1st piece add to square
                getSquareFromId(newSquareId).addPiece(getPieceUIFromPiece(this.selectedPiece));
                // 1st piece removed from old square, its automatic!
                // 1st piece setposition and 2nd piece declare dead
                this.selectedPiece.setPosition(idToPosition(newSquareId));
                checkWin();
            }
//            if(model.isWin()){
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("GAME OVER");
//                alert.setHeaderText("WE HAVE A WINNER");
//                alert.setContentText("Congratulations "+model.getPlayer()[model.getWinnerID()].getName()+", You are the winner!");
//                alert.showAndWait();
//            }
            this.clearSelectedPiece();
            this.clearSelectedSquare();
            this.clearSelectedPieceOpponent();
            model.switchTurn();
            model.switchTurn();
            this.start();

        } else {
            System.out.println(this.selectedPiece.getPosition()+" "+(this.selectedPieceOpponent==temp)+" "+this.selectedSquareID);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uh Oh!");
            alert.setHeaderText(null);
            alert.setContentText("Invalid movement, try again");
            alert.showAndWait();

            this.clearSelectedPiece();
            this.clearSelectedSquare();
            this.clearSelectedPieceOpponent();
        }

    }

    /**
     * this function validates a move
     * @return validate boolean
     */
    public boolean validate(){
        // validate from model
        // this.selectedSquareID change into command, eg: A3
        // this.selectedPiece
        // parameters: from:
        if (this.selectedPiece!=temp&&this.selectedSquareID!=-1&&this.selectedPieceOpponent==temp){
            String p1 = this.selectedPiece.getPosition();
            String p2 = this.idToPosition(this.selectedSquareID);
            boolean move = model.movePosition(p1,p2);
            return move;
        } else if(this.selectedPiece!=temp&&this.selectedSquareID==-1&&this.selectedPieceOpponent!=temp) {
            String p1 = this.selectedPiece.getPosition();
            String p2 = this.selectedPieceOpponent.getPosition();
            boolean  move =  model.movePosition(p1,p2);
            return move;

        }
        return true;
    }

    /**
     * This function checks the winning status
     */
    public void checkWin(){
        if(model.isWin()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("GAME OVER");
            alert.setHeaderText("WE HAVE A WINNER");
            alert.setContentText("Congratulations "+model.getPlayer()[model.getWinnerID()-1].getName()+", You are the winner!");
            alert.showAndWait();
        }
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
