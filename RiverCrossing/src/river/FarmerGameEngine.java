package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FarmerGameEngine implements GameEngine {

    private Map<Item, GameObject> objectMap;
    private Location boatLocation;

    private int passengerSeats;

    public FarmerGameEngine() {
        objectMap = new HashMap<>();
        objectMap.put(Item.ITEM_2, new GameObject("Wolf", Location.START, Color.CYAN));
        objectMap.put(Item.ITEM_1, new GameObject("Goose", Location.START, Color.CYAN));
        objectMap.put(Item.ITEM_0, new GameObject("Beans", Location.START, Color.CYAN));
        objectMap.put(Item.ITEM_3, new GameObject("Farmer", Location.START, Color.MAGENTA));

        passengerSeats = 2;

        boatLocation = Location.START;
    }

    public String getItemLabel(Item id) {
        return id == Item.ITEM_3 ? "" : objectMap.get(id).getLabel().substring(0, 1);
    }

    public Location getItemLocation(Item id) {
        return objectMap.get(id).getLocation();
    }

    public Color getItemColor(Item id) {
        return objectMap.get(id).getColor();
    }

    public Location getBoatLocation() {
        return boatLocation;
    }

    public void loadBoat(Item id) {
        if (objectMap.get(id).getLocation() == boatLocation && passengerSeats > 0) {
            objectMap.get(id).setLocation(Location.BOAT);
            passengerSeats--;
        }
    }

    public void unloadBoat(Item id) {
        if (objectMap.get(id).getLocation() == Location.BOAT) {
            objectMap.get(id).setLocation(boatLocation);
            passengerSeats++;
        }
    }

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

    public boolean gameIsWon() {
        for (Item key : objectMap.keySet()) {
            if (objectMap.get(key).getLocation() != Location.FINISH) {
                return false;
            }
        }

        return true;
    }

    public boolean gameIsLost() {
        if (objectMap.get(Item.ITEM_1).getLocation() == Location.BOAT) {
            return false;
        }

        if (objectMap.get(Item.ITEM_1).getLocation() == objectMap.get(Item.ITEM_3).getLocation()) {
            return false;
        }

        if (objectMap.get(Item.ITEM_1).getLocation() == boatLocation) {
            return false;
        }

        if (objectMap.get(Item.ITEM_1).getLocation() == objectMap.get(Item.ITEM_2).getLocation()) {
            return true;
        }

        if (objectMap.get(Item.ITEM_1).getLocation() == objectMap.get(Item.ITEM_0).getLocation()) {
            return true;
        }
        return false;
    }

    public void resetGame() {
        for (Item key : objectMap.keySet()) {
            objectMap.get(key).setLocation(Location.START);
        }
        boatLocation = Location.START;
        passengerSeats = 2;
    }

    private boolean isBoatOperable() {
        return objectMap.get(Item.ITEM_3).getLocation() == Location.BOAT;
    }
}
