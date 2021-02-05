package river;

import river.GameEngine.Location;

public class GameObject {

    private String name;
    private Location location;
    private String sound;

    public GameObject(String name, Location location, String sound) {
        this.name = name;
        this.location = location;
        this.sound = sound;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public String getSound() {
        return sound;
    }

}
