package hk.edu.polyu.comp.comp2021.jungle.model;

import java.util.HashMap;
import java.util.Map;

/**This is the class of Board
 * contains board information and eat function
 */
public class Board {
    /**
     * this is the number or columns
     */
    public static final int COL_NUM = 7;
    /**
     * this is the number of  rows
     */
    public static final int ROW_NUM = 9;
    /**
     * this is  the width of square
     */
    public static final int SQUARE_WIDTH = 8;
    /**
     * this is the height  of square
     */
    public static final int SQUARE_HEIGHT = 4;
    /**
     * this is the number of pieces
     */
    public static final int PIECE_NUM = 8;
    /**
     * This is the number of players
     */
    public static final int PLAYER_NUM = JungleGame.PLAYER_NUM;
    private Map <String, Piece> squarePieceMap;
    /**
     * this is the array of piece positions
     */
    public static final String PIECE_POSITION [] [] = {{"A3", "G1", "A1", "E3", "C3", "F2", "B2", "G3"}, {"G7", "A9", "G9", "C7", "E7", "B8", "F8", "A7"}};

    /**This is the constructor of Board class
     */
    public Board(){
        //use a Map to store <Position[String], Animal[Piece]> information
        squarePieceMap = new HashMap<>();

        //store the animal location from PIECE_POSITIONf
        for(int i = 1; i <= PLAYER_NUM; i++) {
            for (int j = 0; j < PIECE_NUM ; j++) {
                int rank = PIECE_NUM - j;
                String position = PIECE_POSITION[i - 1][j];
                squarePieceMap.put(position, new Piece(rank, i));
                squarePieceMap.get(position).setPosition(position);
            }
        }
        //set other location animal to be null
        for(int row = 0; row < ROW_NUM; row++){
            for(int col = 0; col < COL_NUM; col++){
                if(!squarePieceMap.containsKey((char)('A' + col) + (char)('1' + row) + ""))
                    squarePieceMap.put(((char)('A' + col) + (char)('1' + row) + ""), null);
            }
        }
    }

    /**This is the function returns the posion of a piece
     * @return postion of a piece
     * @param position position*/
    public Piece getPiece(String position){
        if (!squarePieceMap.containsKey(position))
            return null;
        return squarePieceMap.get(position);
    }

    /**This is the function returns whether the posion is Den
     * @param position: String of the postion
     * @param player: player id
     * @return whether the posion is Den*/
    public boolean isDens(String position, int player){ //0: playerX, 1: playerY
        int x = position.charAt(0) - 'A', y = position.charAt(1) - '1';
        if(player == 1 && x == COL_NUM/2 && y == 0)
            return true;
        else if(player == 2 && x == COL_NUM/2 && y == ROW_NUM - 1)
            return true;
        return false;
    }

    /**This is the function returns whether the posion is Trap
     * @param position: String of the postion
     * @param player: player id
     * @return whether the position is Trap*/
    public boolean isTrap(String position, int player){
        int x = position.charAt(0) - 'A', y = position.charAt(1) - '1';
        if(player == 1)
            if((y == 1 && x == COL_NUM /2) || (y == 0 && (x == COL_NUM/2 -1 || x == COL_NUM/2 + 1)))
                return true;
        if(player == 2)
            if((y == ROW_NUM - 2 && x == COL_NUM /2) || (y == ROW_NUM - 1 && (x == COL_NUM/2 -1 || x == COL_NUM/2 + 1)))
                return true;
        return false;
    }

    /**This is the function returns whether the posion is River
     * @param position: String of the postion
     * @return whether the posion is River*/
    public boolean isRiver(String position){
        int x = position.charAt(0) - 'A', y = position.charAt(1) - '1';
        if(x == COL_NUM / 2 - 2 || x == COL_NUM / 2 - 1 || x == COL_NUM / 2 + 1 || x == COL_NUM / 2 + 2)
            if(y == ROW_NUM / 2 - 1 || y == ROW_NUM / 2 || y == ROW_NUM / 2 + 1)
                return true;
        return false;
    }

    /**This is the function returns whether the posion is Land
     * @param position: String of the postion
     * @return whether the posion is Land*/
    public boolean isLand(String position){
        for(int i = 0; i <  PLAYER_NUM; i++){
            if(isDens(position, i) || isTrap(position, i) || isRiver(position))
                return false;
        }
        return true;
    }

    /**This is the function returns whether the posion is Land
     * @param p1: postion of the piece1
     * @param p2: postion of the piece2
     * @return whether the eat is Success*/
    public boolean eat(String p1, String p2){
        //if p2 contains animal
        Piece piece1 = getPiece(p1), piece2 = getPiece(p2);
        if(piece1.getOwner() != piece2.getOwner()) {
            if (isTrap(piece2.getPosition(), piece1.getOwner())) {
                //delete animal2 from board
                squarePieceMap.remove(p2);
                return true;
            }
            int rank1 = getPiece(p1).getRank(), rank2 = getPiece(p2).getRank();
            //if cannot eat: elephant and rat; or smaller rank
            if((rank1 == 1 && rank2 == 8) || ((rank1 >= rank2) && !(rank1 == 8 && rank2 == 1))){
                squarePieceMap.remove(p2);
                return true;
            }
        }
        return false;
    }

    /**
     * this  function gets  the  square piece map
     * @return Map
     */
    public Map<String, Piece> getSquarePieceMap(){
        return this.squarePieceMap;
    }

    /**
     * This function sets the  square  piece map
     * @param squarePieceMap squarePieceMap
     */
    public void setSquarePieceMap(Map<String, Piece> squarePieceMap){
        this.squarePieceMap = squarePieceMap;
    }
}
