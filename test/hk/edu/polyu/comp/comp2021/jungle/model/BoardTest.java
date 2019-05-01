package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.view.TextView;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BoardTest {
    private Board board;
    @org.junit.Before
    public void setUp() throws Exception {
        Board board = new Board();
    }

    @Test
    public void testBoardConstructor(){
        Board board = new Board();
        assert true;
    }
    @Test
    public void testBoardgetGetPiece(){
        Board board = new Board();
        assertNotNull(board.getSquarePieceMap().get("A1"));
    }
    @Test
    public void testBoardgetIsDens(){
        Board board = new Board();
        assertEquals(board.isDens("D1",1),true);
        assertEquals(board.isDens("D9",2),true);
        assertEquals(board.isDens("D3",2),false);
    }
    @Test
    public void testBoardgetIsTrap(){
        Board board = new Board();
        assertEquals(board.isTrap("C1",1),true);
        assertEquals(board.isTrap("C9",2),true);
        assertEquals(board.isTrap("D3",2),false);
    }
    @Test
    public void testBoardgetIsRiver(){
        Board board = new Board();
        assertEquals(board.isRiver("B4"),true);
        assertEquals(board.isRiver("B3"),false);
    }
    @Test
    public void testBoardgetIsLand(){
        Board board = new Board();
        assertEquals(board.isLand("B1"),true);
        assertEquals(board.isLand("C1"),false);
    }
    @Test
    public void testBoardEat(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        game.movePosition("A3","A4");
        game.movePosition("A7","A8");
        game.movePosition("A4","A5");
        game.movePosition("C7","B7");
        game.movePosition("A5","A6");
        game.movePosition("B7","A7");
        game.movePosition("C3","D3");
        game.movePosition("E7","F7");
        game.movePosition("D3","D4");
        game.movePosition("F7","E7");
        game.movePosition("D4","D5");
        game.movePosition("E7","F7");
        game.movePosition("D5","D6");
        game.movePosition("F7","E7");
        game.movePosition("D6","D7");
        game.movePosition("E7","E8");
        game.movePosition("D7","D8");
        board = game.getBoard();

        assertEquals(board.eat("A1","B2"),false);
        assertEquals(board.eat("A1","A9"),false);
        assertEquals(board.eat("A6","A7"),true);
        game.movePosition("G1","F1");

        assertEquals(board.eat("E8","D8"),true);
    }

    @Test
    public void testGetSquarePieceMap(){
        Board board = new Board();
        assertNotNull(board.getSquarePieceMap());
    }

    @Test
    public void testSetSquarePieceMap(){
        Board board = new Board();
        board.setSquarePieceMap(new HashMap<String, Piece>());
        assert true;
    }

}
