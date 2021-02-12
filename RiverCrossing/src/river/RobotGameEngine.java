package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RobotGameEngine implements GameEngine {

    private Map<Item, GameObject> objectMap;
    private Location boatLocation;

    private int passengerSeats;

    public RobotGameEngine() {
        objectMap = new HashMap<>();
        objectMap.put(Item.ITEM_2, new GameObject("Tall bot 1", Location.START, Color.GREEN));
        objectMap.put(Item.ITEM_1, new GameObject("Small bot 1", Location.START, Color.CYAN));
        objectMap.put(Item.ITEM_0, new GameObject("Small bot 2", Location.START, Color.CYAN));
        objectMap.put(Item.ITEM_3, new GameObject("Tall bot 1", Location.START, Color.GREEN));

        boatLocation = Location.START;

        passengerSeats = 2;
    }

    @Override
    public String getItemLabel(Item item) {
        return objectMap.get(item).getLabel().substring(0, 1);
    }

    @Override
    public Color getItemColor(Item item) {
        return objectMap.get(item).getColor();
    }

    @Override
    public Location getItemLocation(Item item) {
        return objectMap.get(item).getLocation();
    }

    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }

    @Override
    public void loadBoat(Item item) {
        GameObject obj = objectMap.get(item);

        if (obj.getLocation() != boatLocation) {
            return;
        }

        if ((item == Item.ITEM_0 || item == Item.ITEM_1) && passengerSeats > 0) {
            obj.setLocation(Location.BOAT);
            passengerSeats--;
        } else if ((item == Item.ITEM_2 || item == Item.ITEM_3) && passengerSeats == 2) {
            obj.setLocation(Location.BOAT);
            passengerSeats -= 2;
        }
    }

    @Override
    public void unloadBoat(Item item) {
        GameObject obj = objectMap.get(item);

        if (obj.getLocation() != Location.BOAT) {
            return;
        }

        obj.setLocation(boatLocation);
        passengerSeats += (item == Item.ITEM_0 || item == Item.ITEM_1) ? 1 : 2;
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);

        if (!isBoatOperable()) {
            return;
        }

        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    @Override
    public boolean gameIsWon() {
        for (Item key : objectMap.keySet()) {
            if (objectMap.get(key).getLocation() != Location.FINISH) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean gameIsLost() {
        return false;
    }

    @Override
    public void resetGame() {
        for (Item key : objectMap.keySet()) {
            objectMap.get(key).setLocation(Location.START);
        }
        boatLocation = Location.START;
        passengerSeats = 2;
    }

    private boolean isBoatOperable() {
        for (Item key : objectMap.keySet()) {
            if (objectMap.get(key).getLocation() == Location.BOAT) {
                return true;
            }
        }

        return false;
    }
}
