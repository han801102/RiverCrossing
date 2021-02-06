package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Item;

public class GameEngineTest {

    private GameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new GameEngine();
    }

    @Test
    public void testObjectCallThroughs() {

        Assert.assertEquals("Farmer", engine.getItemName(Item.FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.FARMER));
        Assert.assertEquals("", engine.getItemSound(Item.FARMER));

        Assert.assertEquals("Wolf", engine.getItemName(Item.WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals("Howl", engine.getItemSound(Item.WOLF));

        Assert.assertEquals("Goose", engine.getItemName(Item.GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals("Honk", engine.getItemSound(Item.GOOSE));

        Assert.assertEquals("Beans", engine.getItemName(Item.BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals("", engine.getItemSound(Item.BEANS));
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));

        engine.loadBoat(Item.GOOSE);
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(Item.GOOSE));

        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(Item.GOOSE));

        engine.unloadBoat(Item.GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(Item.GOOSE));
    }

    @Test
    public void testWinningGame() {
        // transport the goose
        engine.loadBoat(Item.FARMER);
        engine.transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the beans
        engine.transport(Item.BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back with the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        engine.transport(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport goose and finish the game
        engine.transport(Item.GOOSE);
        engine.unloadBoat(Item.FARMER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {
        // transport the goose
        engine.transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        engine.transport(Item.WOLF);
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
        engine.transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location wolfLoc = engine.getItemLocation(Item.WOLF);
        Location gooseLoc = engine.getItemLocation(Item.GOOSE);
        Location beansLoc = engine.getItemLocation(Item.BEANS);
        Location farmerLoc = engine.getItemLocation(Item.FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.WOLF);

        // check that the state has not changed
        Assert.assertEquals(wolfLoc, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals(gooseLoc, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals(beansLoc, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals(farmerLoc, engine.getItemLocation(Item.FARMER));
    }
}
