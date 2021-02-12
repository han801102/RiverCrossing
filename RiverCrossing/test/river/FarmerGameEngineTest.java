package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class FarmerGameEngineTest {

    private GameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new FarmerGameEngine();
    }

    @Test
    public void testObjectCallThroughs() {

        Assert.assertEquals("", engine.getItemLabel(Item.ITEM_3));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_3));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(Item.ITEM_3));

        Assert.assertEquals("W", engine.getItemLabel(Item.ITEM_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_2));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(Item.ITEM_2));

        Assert.assertEquals("G", engine.getItemLabel(Item.ITEM_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_1));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(Item.ITEM_1));

        Assert.assertEquals("B", engine.getItemLabel(Item.ITEM_0));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_0));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(Item.ITEM_0));
    }

    @Test
    public void testGooseTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_1));

        engine.loadBoat(Item.ITEM_3);
        engine.loadBoat(Item.ITEM_1);
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(Item.ITEM_1));

        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(Item.ITEM_1));

        engine.unloadBoat(Item.ITEM_1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(Item.ITEM_1));
    }

    @Test
    public void testWinningGame() {
        // transport the goose
        engine.loadBoat(Item.ITEM_3);
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the beans
        transport(Item.ITEM_0);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back with the goose
        engine.loadBoat(Item.ITEM_1);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        transport(Item.ITEM_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport goose and finish the game
        transport(Item.ITEM_1);
        engine.unloadBoat(Item.ITEM_3);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {
        // transport the goose
        engine.loadBoat(Item.ITEM_3);
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        transport(Item.ITEM_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {
        // transport the goose
        engine.loadBoat(Item.ITEM_3);
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location wolfLoc = engine.getItemLocation(Item.ITEM_2);
        Location gooseLoc = engine.getItemLocation(Item.ITEM_1);
        Location beansLoc = engine.getItemLocation(Item.ITEM_0);
        Location farmerLoc = engine.getItemLocation(Item.ITEM_3);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.ITEM_2);

        // check that the state has not changed
        Assert.assertEquals(wolfLoc, engine.getItemLocation(Item.ITEM_2));
        Assert.assertEquals(gooseLoc, engine.getItemLocation(Item.ITEM_1));
        Assert.assertEquals(beansLoc, engine.getItemLocation(Item.ITEM_0));
        Assert.assertEquals(farmerLoc, engine.getItemLocation(Item.ITEM_3));
    }

    @Test
    public void testRowBoatWithoutFarmer() {
        Location currentBoatLoc = engine.getBoatLocation();

        engine.rowBoat();

        Assert.assertEquals(currentBoatLoc, engine.getBoatLocation());
    }

    @Test
    public void testResetGame() {
        engine.loadBoat(Item.ITEM_3);
        engine.rowBoat();

        engine.resetGame();

        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_0));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_3));
        Assert.assertEquals(Location.START, engine.getBoatLocation());

    }


    private void transport(Item id) {
        engine.loadBoat(id);
        engine.rowBoat();
        engine.unloadBoat(id);
    }
}
