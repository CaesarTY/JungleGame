package hk.edu.polyu.comp.comp2021.jungle.model;

import java.util.HashMap;

/**This is the class of JungleGame
 * contains three main functions save load game and move position
 * also contains some fields indicate the status of game
 */
public class JungleGame {

    /**
     * this is the number of players
     */
    public static final int PLAYER_NUM = 2;
    private Player[] player = new Player[PLAYER_NUM];
    private Board board;
    private int nextTurn; // 1: Player1; 2: Player2
    private boolean saveStatus; // false: unsaved; true: saved
    private boolean winStatus; // false: unfinished; true: finished
    private int winnerID;

    /**This is the constructor of Board class
     */
    public JungleGame(){
        for(int i = 0; i < PLAYER_NUM; i++)
            player[i] = new Player();
        board = new Board();
        saveStatus = true;
    }

    /**This is the function to initialize Game
     * @param player1Name: String of the player1
     * @param player2Name: String of the player2
     */
    public void initializeGame(String player1Name, String player2Name){
        getPlayer()[0].setName(player1Name);
        getPlayer()[1].setName(player2Name);
        saveStatus = false;
        nextTurn = 1;
        winStatus = false;
    }

    /**This is the function to load Game
     * @param fileString: String of the game content
     * @return whether the load is Success*/
    public boolean loadGame(String fileString){
        //if current game unsaved, return false
        //update current game
        //check position validation
        board.setSquarePieceMap(new HashMap<>());
        String[] fileContent = fileString.split("#");
        player[0].setName(fileContent[0].split("/")[0]);
        player[1].setName(fileContent[0].split("/")[1]);
        this.setTurn(Integer.valueOf(fileContent[0].split("/")[2]));

        for (int i =1; i < fileContent.length; i++){
            String[] pieceinfo = fileContent[i].split("/");
            if (pieceinfo.length == 3){
                String postion = pieceinfo[0];
                int owner = Integer.valueOf(pieceinfo[1]);
                int rank = Integer.valueOf(pieceinfo[2]);
                board.getSquarePieceMap().put(postion, new Piece(rank, owner));
//                System.out.println("here");
                board.getSquarePieceMap().get(postion).setPosition(postion);
            }

        }
        for(int row = 0; row < Board.ROW_NUM; row++){
            for(int col = 0; col < Board.COL_NUM; col++){
                if(!board.getSquarePieceMap().containsKey((char)('A' + col) +""+ (char)('1' + row) + ""))
                    board.getSquarePieceMap().put(((char)('A' + col) +""+ (char)('1' + row) + ""), null);
            }
        }
        return true;
    }

    /**This is the function to save Game
     * @return whether the save is Success*/
    public String saveGame(){
        //return the content need to be stored
        String list = "";
        list += player[0].getName() + '/' + player[1].getName() + '/' + String.valueOf(nextTurn) + "#"; // The form is like owner1/owner2
        for(int row = 0; row < Board.ROW_NUM; row++) {
            for (int col = 0; col < Board.COL_NUM; col++) {
                String postion = (char) ('A' + col) + "" + (char) ('1' + row) + "";
                if (board.getSquarePieceMap().get(postion) != null) {
                    Piece p = board.getSquarePieceMap().get(postion);
                    list += postion + '/' + String.valueOf(p.getOwner()) + '/' + String.valueOf(p.getRank())+"#"; //The form is like: A1/2/1
                }
            }
        }
        this.saveStatus = true;
        return list;
    }

    /**This is the function to move piece
     * @param p1: String of the position of piece p1
     * @param p2: String of the position of piece p2
     * @return whether the move is Success*/
    public boolean movePosition(String p1, String p2){
        //check whether the p1 has animal and animal belongs to this.nextTurn
        if(board.getPiece(p1) == null || board.getPiece(p1).getOwner() != nextTurn) {
            return false;
        }

        int curRank = board.getPiece(p1).getRank();
        int curPlayer = board.getPiece(p1).getOwner();

        //check if it's its own dens
        if (board.isDens(p2, curPlayer))
            return false;

        // vertical move
        if (p1.charAt(0) == p2.charAt(0)) {
            //special case: lion or tiger cross the river vertically
            if (Math.abs(p1.charAt(1) - p2.charAt(1)) != 1) {
                char a = p1.charAt(0), b = p1.charAt(1), c = p2.charAt(1);
                if ((a == 'B' || a == 'C' || a == 'E' || a == 'F') && ((b == '3' && c == '7') || (c == '3' && b == '7')) && (curRank == 6 || curRank == 7)) {
                    //check if there is a rat in the river
                    for (char ch = '4'; ch < '7'; ch = (char) (ch + 1)) {
                        String str = a + ch + "";
                        if (board.getPiece(str) != null) return false;
                    }
                    if (board.getPiece(p2) != null) {
                        //smaller than animal in the position p2
                        if (!board.eat(p1, p2))
                            return false;
                    }
                } else return false;
            }
            else {
                //special case: rat move vertically in the river
                if  (board.isRiver(p1) && board.isRiver(p2)) {
                    if (board.getPiece(p2) != null && board.getPiece(p2).getRank() != 1) return false;
                }
                else if (board.isRiver(p1) || board.isRiver(p2)) {
                    if (curRank != 1) return false;
                    else {
                        //cannot attack other animal from the water square/land
                        if(board.getPiece(p2) != null)
                            return false;
                    }
                }
                // general case vertical move
                else {
                    if(board.getPiece(p2) != null) {
                        //smaller than animal in the position p2
                        if (!board.eat(p1, p2))
                            return false;
                    }
                }

            }
        }

        //all cases for horizontal movement
        else if (Math.abs(p1.charAt(0) - p2.charAt(0)) == 1 && p1.charAt(1) == p2.charAt(1)) {
            //special case: rat go cross the river horizontally
            if  (board.isRiver(p1) && board.isRiver(p2)) {
                if (board.getPiece(p2) != null && board.getPiece(p2).getRank() != 1)
                    return false;
            }
            else if (board.isRiver(p1) || board.isRiver(p2)) {
                if (curRank != 1) return false;
                else {
                    //cannot attack other animals from the water square/land
                    if(board.getPiece(p2) != null)
                        return false;
                }
            }
            else {
               if(board.getPiece(p2) != null) {
                    //smaller than animal in the position p2
                   if (!board.eat(p1, p2))
                       return false;

                }
            }
        }
        // special case for the lion and tiger cross horizontally
        else if (Math.abs(p1.charAt(0) - p2.charAt(0)) == 3 && p1.charAt(1) == p2.charAt(1) && (curRank == 6 || curRank == 7)) {
            char a = p1.charAt(0), b= p2.charAt(0), c = p2.charAt(1);
            if ((a == 'A' || a == 'D' || a == 'G') && (c == '4' || c == '5' || c == '6') ) {
                //check if there is a rat in the river
                if (a > b) {
                    char temp = a;
                    a = b;
                    b = temp;
                }
                for (char ch = (char)(a + 1); ch < b; ch = (char)(ch + 1)) {
                    String str = ch + "" + c + "";
                    if (board.getPiece(str) != null) return false;
                }
                if(board.getPiece(p2) != null) {
                    //smaller than animal in the position p2
                    if (!board.eat(p1, p2))
                        return false;

                }
            }
            else return false;
        }
        //invalid movement
        else return false;

        switchTurn();
        this.saveStatus = false;
        if(board.getPiece(p2) != null)
            getPlayer()[nextTurn - 1].decreasePieceNum();

        //refresh the position
        board.getSquarePieceMap().put(p2,board.getPiece(p1));
        board.getPiece(p1).setPosition(p2);
        board.getSquarePieceMap().remove(p1);

        //if the winner enter his opponent's dens
        if (board.isDens(p2, nextTurn) || player[nextTurn - 1].getPieceNum() == 0) {
            this.winStatus = true;
            setWinnerID(curPlayer);
        }
        return true;
    }

    /**This is the function to swich the turn*/
    public void switchTurn(){
        if(nextTurn == 1)
            nextTurn = 2;
        else
            nextTurn = 1;
    }

    /**This is the function to set the turn
     *@param PlayerID: ID of the player
     *@return true or false*/
    public boolean setTurn(int PlayerID){
        if(PlayerID < 1 || PlayerID > PLAYER_NUM)
            return false;
        this.nextTurn = PlayerID;
        return true;
    }

    /**This is the function to return player
     *@return Player: player*/
    public Player[] getPlayer (){ return player;}

    /**This is the function to return board
     *@return Board: board*/
    public Board getBoard() {
        return board;
    }

    /**This is the function to return nextturn
     *@return nextturn: nextturn*/
    public int getNextTurn(){
        return nextTurn;
    }

    /**This is the function to return saveStatus
     *@return saveStatus: saveStatus*/
    public boolean isSaved(){
        return saveStatus;
    }

    /**This is the function to return winStatus
     *@return winStatus: winStatus*/
    public boolean isWin(){
        return winStatus;
    }

    /**This is the function to set the wining player
     *@param winnerID: ID of the wining player
     *@return true or false*/
    public boolean setWinnerID(int winnerID){
        if (winnerID < 1 || winnerID > PLAYER_NUM)
            return false;
        this.winnerID = winnerID;
        return true;
    }

    /**This is the function to return the wining player
     *@return winnerID: the wining player*/
    public int getWinnerID(){
        return winnerID;
    }

    /**This is the function to set the saveStatus
     *@param saveStatus:saveStatus*/
    public void setSaveStatus(boolean saveStatus){
        this.saveStatus = saveStatus;
    }

}
