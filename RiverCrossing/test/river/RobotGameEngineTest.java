package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class RobotGameEngineTest {

    private GameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new RobotGameEngine();
    }

    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals("S", engine.getItemLabel(Item.ITEM_0));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_0));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(Item.ITEM_0));

        Assert.assertEquals("S", engine.getItemLabel(Item.ITEM_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_1));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(Item.ITEM_1));

        Assert.assertEquals("T", engine.getItemLabel(Item.ITEM_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_2));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(Item.ITEM_2));

        Assert.assertEquals("T", engine.getItemLabel(Item.ITEM_3));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_3));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(Item.ITEM_3));
    }

    @Test
    public void testWinningGame() {
        engine.loadBoat(Item.ITEM_0);
        engine.loadBoat(Item.ITEM_1);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_0);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.unloadBoat(Item.ITEM_1);
        engine.loadBoat(Item.ITEM_2);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(Item.ITEM_0);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(Item.ITEM_1);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_0);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());


        engine.unloadBoat(Item.ITEM_1);
        engine.loadBoat(Item.ITEM_3);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_3);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(Item.ITEM_0);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.loadBoat(Item.ITEM_1);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_0);
        engine.unloadBoat(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testTransportSmallBot() {
        engine.loadBoat(Item.ITEM_0);
        engine.rowBoat();

        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());
    }

    @Test
    public void testRowBoatWithoutRob() {
        Location currentBoatLoc = engine.getBoatLocation();

        engine.rowBoat();

        Assert.assertEquals(currentBoatLoc, engine.getBoatLocation());
    }

    @Test
    public void testResetGame() {
        engine.loadBoat(Item.ITEM_2);
        engine.rowBoat();

        engine.resetGame();

        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_0));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_3));
        Assert.assertEquals(Location.START, engine.getBoatLocation());

    }
}