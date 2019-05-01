package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.model.JungleGame;

import java.io.*;
import java.util.Scanner;

/**This is the class of TextView
 * to handle txt view
 */
public class TextView implements View{
    private JungleGame model;
    private Scanner input = new Scanner(System.in);
    /**This is the constructor of TextView class
     * @param model: junglegame
     */
    public TextView(JungleGame model){
        this.model = model;
    }
    /**This is the function to return game
     *@return game: game
     */
    public JungleGame getGame() {
        return this.model;
    }
    /**This is the function to start game
     */
    @Override
    public void start(){
        System.out.println("======== Welcome to the Jungle Game ========");

        //start new game or load a saved game
        switch(chooseMenu()){
            case "START":
                //input two player names
                String[] playerName = new String [2];
                for(int i = 0; i < model.PLAYER_NUM; i++) {
                    while(true) {
                        System.out.print("Please input the name of Player " + (i + 1) + ": ");
                        playerName[i] = input.nextLine();
                        if(playerName[i] != null)
                            break;
                        System.out.println("Invalid player name. Please enter again.");
                    }
                }
                model.initializeGame(playerName[0], playerName[1]);
                System.out.println("Start new game successfully.");
                printBoard();
                break;
            case "LOAD":
                //input filepath
                while(true){
                    System.out.print("Please input the file path: ");
                    if(loadGame(input.nextLine()))
                        break;
                    System.out.println("Invalid file path. Please enter again.");
                }
                System.out.println("Open a saved game successfully.");
                printBoard();
                break;
            }

        //user input command in turn
        while(!model.isWin()){
            //print next turn 's player
            int id = model.getNextTurn();
            if (id != 0)
                System.out.println("Turn: Player " + id + " -- " + model.getPlayer()[id - 1].getName());
            System.out.print("Please input a command\n(save [filePath]; open [filePath]; move [fromPosition] [toPosition]) : ");
            handleCommand(input.nextLine());
        }

        //print winner information
        System.out.println("======== Game Over ========");
        int id = model.getWinnerID();
        System.out.println("Winner -- Player " + id + " " + model.getPlayer()[id - 1].getName());

    }

    /**This is the function to choose menu
     * @return menu item
     */
    public String chooseMenu(){
        String res = "";
        while(true){
            System.out.print("1. Start a new game:\n2.Load a saved game:\nPlease choose (1 or 2): ");
            String temp = input.nextLine();
            if(temp.length() == 1 && (temp.equals("1") || temp.equals("2"))){
                switch(temp){
                    case "1":
                        res = "START";
                        break;
                    case "2":
                        res = "LOAD";
                        break;
                }
                return res;
            }
            System.out.println("Invalid choice. Please enter again.");
        }
    }

    /**This is the function to save Game
     * @param filePath: String of the game savefile path
     * @return whether the save is Success*/
    public boolean saveGame(String filePath){
        //check saveStatus
        String[] Patharray = filePath.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< Patharray.length-1;i++){
            sb.append(Patharray[i]+"/");
        }
        String Path = sb.toString();
        File newFile = new File(Path);
        if  (!newFile .exists()  && !newFile .isDirectory()){
            if (newFile .mkdir()==false){
                return false;
            }
        }
        String choice;
        String save = model.saveGame();

        File file =new File(filePath);
        if (file.exists()){
            System.out.println("This file already exists. ");
            return false;
        }
//        while(file.exists())
//        {
//            System.out.println("This file already exists. Please enter 0 to change the file name or enter 1 to delete the original file:");
//
//            choice = input.nextLine();
//            while(!choice.equals("0") && !choice.equals("1") ) {
//                System.out.println("Invalid input. Please enter again");
//            }
//            if(choice.equals("1")) break;
//            else
//            {
//                System.out.println("Please enter the filePath of the saving file:");
//                filePath = input.nextLine();
//                file =new File(filePath);
//            }
//        }
        try {
            file.createNewFile();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.write(save);
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Your game has already been saved");
        return true;
    }

    /**This is the function to check command
     * @param fileString: String of the command
     * @return whether the command is valid*/
    public static boolean loadCheck(String fileString){
        String[] fileContent = fileString.split("#");
        if (fileContent.length<=2) {
            return false;
        }
        String[] playerstring = fileContent[0].split("/");
        if (playerstring.length!=3||!(playerstring[2].equals("1")||playerstring[2].equals("2"))){
            return false;
        }
        for (int i = 1; i<fileContent.length;i++){
            String[] Piece =  fileContent[i].split("/");
            if (Piece.length != 3) {
                return false;
            }
            if (Piece[0].charAt(0)<'A' && Piece[0].charAt(0)>'Z') {
                return false;
            }
            if (Piece[0].charAt(1)<'0' && Piece[0].charAt(0)>'9'){
                return false;
            }
            if (Piece[1].charAt(0)<'0' && Piece[0].charAt(0)>'9'){
                return false;
            }
            if (Piece[2].charAt(0)<'0' && Piece[0].charAt(0)>'9'){
                return false;
            }

        }
        return true;

    }

    /**This is the function to load Game
     * @param filePath: String of the game savefile path
     * @return whether the load is Success*/
    public boolean loadGame(String filePath){
        String load = new String();
        if (!model.isSaved()){
            model.setSaveStatus(true);
            loadGame(filePath);
//            System.out.println("Your haven't save your current game yet, please save it first.");
//            saveGame(input.nextLine());
//            return false;
            return true;
        }

        else {

//            System.out.println("Please enter the file path and name of the game you want to load: ");
//            String filestr = view.getInput();
            File file = new File(filePath);

            while (!file.exists()) {
                System.out.println("This file does not exist.Please enter the file path and name again:");
                filePath = input.nextLine();
                file = new File(filePath);
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                load = br.readLine();
                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        if (!loadCheck(load)){
            System.out.println("File broken");
//            System.exit(0);
            return false;
        }
        else
            return model.loadGame(load);
    }

    /**This is the function to handle command
     * @param command: String of the command
     * @return whether the command is handled*/
    public boolean handleCommand(String command) {
        if (command == null || command.length() <= 5) {
            System.out.println("Invalid command.");
            return false;
        }
        switch (command.substring(0, 5)) {
            case "save ":
                //pase the file path to the saveGame()
                if(saveGame(command.substring(5))){
                    System.out.println("Save the game successfully.");
                    return true;
                }
                break;
            case "open ":
                //pase the file path to the loadGame()
                if(loadGame(command.substring(5))) {
                    System.out.println("Open a saved game successfully.");
                    printBoard();
                    return true;
                }
                break;
            case "move ":
                //format mast be "p1 p2"
                //if length incorrect or not separated by single space
                if (command.substring(5).length() == 5 && command.substring(7, 8).charAt(0) == ' ') {
                    String p1 = command.substring(5, 7), p2 = command.substring(8, 10);
                    boolean ValidationP1 = p1.charAt(0) >= 'A' && p1.charAt(0) <= 'G' && p1.charAt(1) >= '1' && p1.charAt(1) <= '9';
                    boolean ValidationP2 = p2.charAt(0) >= 'A' && p2.charAt(0) <= 'G' && p2.charAt(1) >= '1' && p2.charAt(1) <= '9';
                    if (ValidationP1 && ValidationP2)
                        if (model.movePosition(p1, p2)){
                            printBoard();
                            return true;
                        }
                }
                System.out.println("Invalid move position.");
                printBoard();
                return false;
        }
        System.out.println("Invalid command. Please enter again.");
        return false;
    }

    /**This is the function to print the board
     */
    @Override
    public void printBoard(){
        int COL_NUM = model.getBoard().COL_NUM;
        int ROL_NUM = model.getBoard().ROW_NUM;
        int SQUARE_WIDTH = model.getBoard().SQUARE_WIDTH;
        int SQUARE_HEIGHT = model.getBoard().SQUARE_HEIGHT;

        //print the player Y
        for(int col = 0; col < COL_NUM / 2; col++)
                System.out.print("         ");
        System.out.print("   Player 2\n  ");

        //print the first line
        for(int col = 0; col < COL_NUM; col++)
            System.out.print("---------");
        System.out.print("-\n  ");

        //print the board
        for(int row = ROL_NUM; row > 0; row--){

            //print first-half square height
            for(int i = 0; i < SQUARE_HEIGHT / 2 - 1; i++) {
                for (int col = 0; col < COL_NUM; col++) {
                    System.out.print("|");
                    String position = (char) ('A' + col) + (row + "");
                    if (model.getBoard().isRiver(position))
                        System.out.print("~~~~~~~~");
                    else if(model.getBoard().isTrap(position, 1) || model.getBoard().isTrap(position, 2))
                        System.out.print("++++++++");
                    else if(model.getBoard().isDens(position, 1) || model.getBoard().isDens(position, 2))
                        System.out.print("........");
                    else
                        System.out.print("        ");
                }
                System.out.print("|\n" + row +" ");
            }

            //print the row number and animal
            for(int col = 0; col < COL_NUM; col++){
                String position = (char) ('A' + col) + (row + "");
                System.out.print("|");
                if(getNameByPosition(position) != null)
                    System.out.print(getNameByPosition(position));
                else if(model.getBoard().isRiver(position))
                    System.out.print("~~~~~~~~");
                else if(model.getBoard().isTrap(position, 1) || model.getBoard().isTrap(position, 2))
                    System.out.print("++++++++");
                else if(model.getBoard().isDens(position, 1) || model.getBoard().isDens(position, 2))
                    System.out.print("........");
                else
                    System.out.print("        ");
            }

            //print the rest of the square height
            System.out.print("|\n  ");
            for(int i = 0; i < SQUARE_HEIGHT / 2; i++) {
                for (int col = 0; col < COL_NUM; col++) {
                    System.out.print("|");
                    String position = (char) ('A' + col) + (row + "");
                    if (model.getBoard().isRiver(position))
                        System.out.print("~~~~~~~~");
                    else if(model.getBoard().isTrap(position, 1) || model.getBoard().isTrap(position, 2))
                        System.out.print("++++++++");
                    else if(model.getBoard().isDens(position, 1) || model.getBoard().isDens(position, 2))
                        System.out.print("........");
                    else
                        System.out.print("        ");
                }
                System.out.print("|\n  ");
            }
            for(int col = 0; col < COL_NUM; col++)
                System.out.print("---------");
            System.out.print(" \n  ");
        }

        //print the column number
        for(int col = 0; col < COL_NUM; col++)
            System.out.print("    " + (char)('A' + col) + "    ");
        System.out.print(" \n  ");

        //print the player X
        for(int col = 0; col < COL_NUM / 2; col++)
            System.out.print("         ");
        System.out.println(" Player 1");
        System.out.println(" ~ : River\n . : Dens\n + : Trap");
    }

    /**This is the function to return name
     *@return name: name
     * @param position  position of piece*/
    public String getNameByPosition(String position){
        if(model.getBoard().getPiece(position) == null) return null;
        String res = "";
        String name = model.getBoard().getPiece(position).getName();
        for(int i = 0; i < (model.getBoard().SQUARE_WIDTH - name.length()) / 2; i++)
            res += " ";
        res += name;
        for(int i = 0; i < (model.getBoard().SQUARE_WIDTH - name.length() + 1) / 2; i++)
            res += " ";
        return res;
    }
}
