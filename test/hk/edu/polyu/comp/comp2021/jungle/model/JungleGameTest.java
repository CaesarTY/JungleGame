package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.view.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JungleGameTest {

    private JungleGame game;

    @Before
    public void setUp() throws Exception {
        JungleGame game = new JungleGame();
    }

    @Test
    public void testJungleGameConstructor(){
        JungleGame game = new JungleGame();
        assert true;
    }

    @Test
    public void testJungleGameInitializeGame(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        assertEquals(game.getPlayer()[0].getName(), "PlayerOneName");
        assertEquals(game.getPlayer()[1].getName(), "PlayerTwoName");
        assertEquals(game.isSaved(), false);
        assertEquals(game.getNextTurn(), 1);
        assertEquals(game.isWin(), false);
    }

    @Test
    public void testJungleGameSaveGame(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        assertEquals(game.saveGame(), "PlayerOneName/PlayerTwoName/1#A1/1/6#G1/1/7#B2/1/2#F2/1/3#A3/1/8#C3/1/4#E3/1/5#G3/1/1#A7/2/1#C7/2/5#E7/2/4#G7/2/8#B8/2/3#F8/2/2#A9/2/7#G9/2/6#");
    }

    @Test
    public void testJungleGameLoadGame(){
        JungleGame game = new JungleGame();
        game.loadGame("PlayerOneName/PlayerTwoName/1#A1/1/6#G1/1/7#B2/1/2#F2/1/3#A3/1/8#C3/1/4#E3/1/5#G3/1/1#A7/2/1#C7/2/5#E7/2/4#G7/2/8#B8/2/3#F8/2/2#A9/2/7#G9/2/6#");
        assert true;
    }

    @Test
    public void testJungleGameMovePosition(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        game.setTurn(2);
        //if the square is empty
        assertEquals(game.movePosition("A4", "A5"),false); //player1

        //invalid movement
        assertEquals(game.movePosition("G9", "E2"),false); //player1

        //if the animal not belongs to the current player
        assertEquals(game.movePosition("A3", "A4"),false); //player1

        //move a lion one square on land vertically
        assertEquals(game.movePosition("E7", "E8"),true); //player2

        //move a lion one square on land horizontally
        assertEquals(game.movePosition("A1", "B1"), true); //player1

        //A wolf can not move to the river
        assertEquals(game.movePosition("C7", "C6"), false); //player2
        game.movePosition("A7", "B7"); //mouse is on A7, player2

        //a mouse could move to the river
        game.movePosition("B1", "C1");
        assertEquals(game.movePosition("B7", "B6"), true);

        // A piece may not move to its own den
        assertEquals(game.movePosition("C1", "D1"), false);
        game.movePosition("A3", "A4");

        //a rat could not catch an elephant from the river directly
        game.movePosition("B6", "B5");
        game.movePosition("A4", "A5");
        assertEquals(game.movePosition("B5", "A5"), false);
        game.movePosition("B5", "B6");

        //a wolf can not jumping across the river
        assertEquals(game.movePosition("C3", "C7"), false);

        //an elephant can not eat a mouse
        game.movePosition("G3","G4");
        game.movePosition("B6", "A6");
        assertEquals(game.movePosition("A5", "A6"), false);

        //a rat could catch an elephant on land
        game.movePosition("G4", "F4");
        assertEquals(game.movePosition("A6", "A5"), true);

        //a rat could eat another rat on river
        game.movePosition("F4", "E4");
        game.movePosition("A5", "B5");
        game.movePosition("E4", "D4");
        game.movePosition("B5", "C5");
        game.movePosition("D4", "C4");
        assertEquals(game.movePosition("C5", "C4"), true);

        //a lion could jump across the river vertically
        game.movePosition("E3", "D3");
        game.movePosition("A9", "A8");
        game.movePosition("D3", "D4");
        game.movePosition("A8", "A7");
        game.movePosition("D4", "D5");
        game.movePosition("A7", "B7");
        game.movePosition("D5", "D6");
        assertEquals(game.movePosition("B7", "B3"), true);


        // a animal could capture the others in the trap whatever the rank it is
        game.movePosition("D6", "D7");
        game.movePosition("B3", "A3");
        game.movePosition("D7", "D8");
        game.movePosition("F8", "F9");
        game.movePosition("B2", "C2");
        assertEquals(game.movePosition("E8", "D8"), true);

        //a lion could not move horizontally across the river if there is a rat in the river
        game.movePosition("C2", "D2");
        game.movePosition("A3", "A4");
        game.movePosition("D2", "D3");
        assertEquals(game.movePosition("A4", "D4"), false);

        // a lion could move horizontally across the river if there is no rat in the river
        game.movePosition("A4", "A5");
        game.movePosition("D3", "D4");
        assertEquals(game.movePosition("A5", "D5"), true);

        // a lion could move horizontally across the river if there is no rat in the river
        game.movePosition("D4", "D3");
        assertEquals(game.movePosition("D5", "A5"), true);

        //a line move horizontally
        game.movePosition("D3", "D4");
        game.movePosition("A5", "A6");
        game.movePosition("D4", "D5");
        game.movePosition("G9", "G8");
        game.movePosition("D5", "D6");
        game.movePosition("A6", "A7");
        game.movePosition("D6", "D7");
        game.movePosition("A7", "B7");
        game.movePosition("D7", "E7");
        game.movePosition("D8", "C8");
        game.movePosition("E7", "E8");
        game.movePosition("C8", "C9");
        game.movePosition("E8", "E9");
        game.movePosition("C9", "C8");
        assertEquals(game.movePosition("G1", "F1"), true);

        //a lion could not move vertically across the river if there is a rat in the river
        assertEquals(game.movePosition("C7", "C3"), false);

        //winning situation
        game.movePosition("C7", "D7");
        game.movePosition("E9", "D9");
        System.out.println(game.getWinnerID());
        assertEquals(game.getWinnerID(), 1);
    }

    @Test
    public void testJungleGameSwitchTurn(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        game.switchTurn();
        assertEquals(game.getNextTurn(), 2);
    }

    @Test
    public void testJungleGameSetTurn(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        assertEquals(game.setTurn(0),false);
        assertEquals(game.setTurn(3),false);
        assertEquals(game.setTurn(2),true);
        assertEquals(game.getNextTurn(),2);
    }

    @Test
    public void testJungleGameGetPlayer(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        assertNotNull(game.getPlayer());
    }

    @Test
    public void testJungleGameGetBoard(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        assertNotNull(game.getBoard());
    }

    @Test
    public void testJungleGameIsSaved(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        assertEquals(game.isSaved(),false);
    }

    @Test
    public void testJungleGameIsWin(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        assertEquals(game.isWin(),false);
    }

    @Test
    public void testJungleGameSetWinnerID(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        assertEquals(game.setWinnerID(0),false);
        assertEquals(game.setWinnerID(1),true);
    }

    @Test
    public void testJungleGameGetWinnerID(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        game.setWinnerID(1);
        assertEquals(game.getWinnerID(), 1);
    }

    @Test
    public void testJungleGameSetSavedStatus(){
        JungleGame game = new JungleGame();
        game.initializeGame("PlayerOneName", "PlayerTwoName");
        game.setSaveStatus(true);
        assertEquals(game.isSaved(), true);
    }

}