package hk.edu.polyu.comp.comp2021.jungle.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player player = new Player();

    @Test
    public void checkName(){
        assertEquals(this.player.setName(null),false);
        assertEquals(this.player.setName("test"),true);
        assertEquals(player.getName(),  "test");
    }

    @Test
    public void checkPieceNum(){
        assertEquals(player.getPieceNum(),  8);
    }

    @Test
    public void checkDecreasePieceNum(){
        player.decreasePieceNum();
        assertEquals(player.getPieceNum(),  7);
    }
}
