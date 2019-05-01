package hk.edu.polyu.comp.comp2021.jungle.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PieceTest {
    Piece newPiece = new Piece(8, 1);

    @Test
    public void checkThePosition(){
        newPiece.setPosition("A1");
        assertEquals(newPiece.getPosition(), "A1");
    }

    @Test
    public void checkTheRank(){
        assertEquals(newPiece.getRank(),  8);
    }

    @Test
    public void checkTheOwner(){
        assertEquals(newPiece.getOwner(), 1);
    }

    @Test
    public void checkTheName(){
        assertEquals(newPiece.getName(), "Elephant");
    }
}
