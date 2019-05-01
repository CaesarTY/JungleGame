package hk.edu.polyu.comp.comp2021.jungle.model;

/**This is the class of Piece
 * contains piece information
 */
public class Piece {
    /**
     * this is the number of animal types
     */
    public static final int ANIMAL_TYPE_NUM = 8;
    private static final String PIECE_NAME [] = {"Elephant", "Lion", "Tiger", "Leopard", "Wolf", "Dog", "Cat", "Rat"};
    private final int rank;
    private final int owner; // 1：player1; 2： player2
    private String position;

    /**This is the constructor of Piece class
     * @param rank: rank of the Piece
     * @param owner: owner of the Piece*/
    public Piece(int rank, int owner){
        this.rank = rank;
        this.owner = owner;
    }

    /**This is the function to set the position
     *@param position:position*/
    public void setPosition(String position){
        this.position = position;
    }

    /**This is the function to return rank
     *@return rank: rank*/
    public int getRank(){
        return this.rank;
    }

    /**This is the function to return owner
     *@return owner: owner*/
    public int getOwner(){
        return this.owner;
    }

    /**This is the function to return position
     *@return position: position*/
    public String getPosition(){ return this.position; }

    /**This is the function to return name
     *@return name: name*/
    public String getName(){
        return PIECE_NAME[ANIMAL_TYPE_NUM - rank];
    }


}
