package river;

import java.util.HashMap;
import java.util.Map;

public class GameEngine {

    public enum Item {
        WOLF, GOOSE, BEANS, FARMER;
    }

    private Map<Item, GameObject> objectMap;
    private Location boatLocation;

    public GameEngine() {
        objectMap = new HashMap<>();
        objectMap.put(Item.WOLF, new GameObject("Wolf", Location.START, "Howl"));
        objectMap.put(Item.GOOSE, new GameObject("Goose", Location.START, "Honk"));
        objectMap.put(Item.BEANS, new GameObject("Beans", Location.START, ""));
        objectMap.put(Item.FARMER, new GameObject("Farmer", Location.START, ""));

        boatLocation = Location.START;
    }

    public String getItemName(Item id) {
        return objectMap.get(id).getName();
    }

    public Location getItemLocation(Item id) {
        return objectMap.get(id).getLocation();
    }

    public String getItemSound(Item id) {
        return objectMap.get(id).getSound();
    }

    public Location getBoatLocation() {
        return boatLocation;
    }

    public void loadBoat(Item id) {
        switch (id) {
            case WOLF:
                if (objectMap.get(id).getLocation() == boatLocation && objectMap.get(Item.GOOSE).getLocation() != Location.BOAT && objectMap.get(Item.BEANS).getLocation() != Location.BOAT) {
                    objectMap.get(id).setLocation(Location.BOAT);
                }
                break;
            case GOOSE:
                if (objectMap.get(id).getLocation() == boatLocation && objectMap.get(Item.WOLF).getLocation() != Location.BOAT && objectMap.get(Item.BEANS).getLocation() != Location.BOAT) {
                    objectMap.get(id).setLocation(Location.BOAT);
                }
                break;
            case BEANS:
                if (objectMap.get(id).getLocation() == boatLocation && objectMap.get(Item.WOLF).getLocation() != Location.BOAT && objectMap.get(Item.GOOSE).getLocation() != Location.BOAT) {
                    objectMap.get(id).setLocation(Location.BOAT);
                }
                break;
            case FARMER:
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
        if (objectMap.get(Item.GOOSE).getLocation() == Location.BOAT) {
            return false;
        }

        if (objectMap.get(Item.GOOSE).getLocation() == objectMap.get(Item.FARMER).getLocation()) {
            return false;
        }

        if (objectMap.get(Item.GOOSE).getLocation() == boatLocation) {
            return false;
        }

        if (objectMap.get(Item.GOOSE).getLocation() == objectMap.get(Item.WOLF).getLocation()) {
            return true;
        }

        if (objectMap.get(Item.GOOSE).getLocation() == objectMap.get(Item.BEANS).getLocation()) {
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
