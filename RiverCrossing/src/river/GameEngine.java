package river;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameEngine {

    private Map<Item, GameObject> objectMap;
    private Location boatLocation;

    public GameEngine() {
        objectMap = new HashMap<>();
        objectMap.put(Item.ITEM_2, new GameObject("Wolf", Location.START, Color.CYAN));
        objectMap.put(Item.ITEM_1, new GameObject("Goose", Location.START, Color.CYAN));
        objectMap.put(Item.ITEM_0, new GameObject("Beans", Location.START, Color.CYAN));
        objectMap.put(Item.ITEM_3, new GameObject("Farmer", Location.START, Color.MAGENTA));

        boatLocation = Location.START;
    }

    public String getItemLabel(Item id) {
        return objectMap.get(id).getLabel();
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

    public void transport(Item id) {
        loadBoat(id);
        rowBoat();
        unloadBoat(id);
    }

    public void loadBoat(Item id) {
        switch (id) {
            case ITEM_2:
                if (objectMap.get(id).getLocation() == boatLocation && objectMap.get(Item.ITEM_1).getLocation() != Location.BOAT && objectMap.get(Item.ITEM_0).getLocation() != Location.BOAT) {
                    objectMap.get(id).setLocation(Location.BOAT);
                }
                break;
            case ITEM_1:
                if (objectMap.get(id).getLocation() == boatLocation && objectMap.get(Item.ITEM_2).getLocation() != Location.BOAT && objectMap.get(Item.ITEM_0).getLocation() != Location.BOAT) {
                    objectMap.get(id).setLocation(Location.BOAT);
                }
                break;
            case ITEM_0:
                if (objectMap.get(id).getLocation() == boatLocation && objectMap.get(Item.ITEM_2).getLocation() != Location.BOAT && objectMap.get(Item.ITEM_1).getLocation() != Location.BOAT) {
                    objectMap.get(id).setLocation(Location.BOAT);
                }
                break;
            case ITEM_3:
                if (objectMap.get(id).getLocation() == boatLocation) {
                    objectMap.get(id).setLocation(Location.BOAT);
                }
            default: // do nothing
        }
    }

    public void unloadBoat(Item id) {
        if (objectMap.get(id).getLocation() == Location.BOAT) {
            objectMap.get(id).setLocation(boatLocation);
        }
    }

    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
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
    }

}
