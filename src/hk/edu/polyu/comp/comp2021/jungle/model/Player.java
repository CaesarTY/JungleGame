package hk.edu.polyu.comp.comp2021.jungle.model;

/**This is the class of Player
 * contains player information
 */
public class Player {

    private String name;
    private int pieceNum;

    /**This is the constructor of Piece class
     */
    public Player(){
        pieceNum = Piece.ANIMAL_TYPE_NUM;
    }

    /**This is the function to set the name
     *@param name:name
     *@return true or false
     */
    public boolean setName(String name){
        if(name == null)
            return false;
        this.name = name;
        return true;
    }

    /**This is the function to return name
     *@return name: name*/
    public String getName() {
        return name;
    }

    /**This is the function to return PieceNum
     *@return PieceNum: PieceNum*/
    public int getPieceNum(){
        return pieceNum;
    }

    /**This is the function to decrease PieceNum
     */
    public void decreasePieceNum(){
        pieceNum--;
    }
}
