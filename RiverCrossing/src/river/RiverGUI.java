package river;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Graphical interface for the River application
 *
 * @author Gregory Kulczycki
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================

    private Map<Item, Rectangle> objRects = new HashMap<>();

    private Rectangle boatRect;
    private int passengers = 0;

    private final int leftBaseX = 20;
    private final int leftBaseY = 275;
    private final int[] dx = {0, 60, 0, 60};
    private final int[] dy = {0, 0, -60, -60};
    private final int moveDistance = 650;

    private final int leftBoatX = 140;
    private final int leftBoatY = 275;
    private final int boatWidth = 110;
    private final int boatHeight = 50;
    private final int boatMoveDistance = 410;

    private final Rectangle farmerGameButtonRect = new Rectangle(300, 120, 80, 30);
    private final Rectangle robotGameButtonRect = new Rectangle(420, 120, 80, 30);

    // ==========================================================
    // Private Fields
    // ==========================================================

    private GameEngine engine; // Model
    private boolean restart = false;

    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {
        engine = new FarmerGameEngine();
        addMouseListener(this);
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g) {


        refreshItemRectangles(); // based on model
        refreshBoatRectangle(); // based on model

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        paintItem(g, Item.ITEM_0);
        paintItem(g, Item.ITEM_1);
        paintItem(g, Item.ITEM_2);
        paintItem(g, Item.ITEM_3);
        paintBoat(g);

        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            restart = true;
        }
        if (engine.gameIsWon()) {
            message = "You Won!";
            restart = true;
        }
        paintMessage(message, g);
        if (restart) {
            paintRestartButton(g, "Farmer", farmerGameButtonRect);
            paintRestartButton(g, "Robot", robotGameButtonRect);
        }


    }

    private void refreshItemRectangles() {
        passengers = 0;

        refreshItemRect(Item.ITEM_0);
        refreshItemRect(Item.ITEM_1);
        refreshItemRect(Item.ITEM_2);
        refreshItemRect(Item.ITEM_3);
    }

    private void refreshItemRect(Item id) {
        Location itemLoc = engine.getItemLocation(id);
        int x;
        int y;
        switch (engine.getItemLocation(id)) {
            case BOAT:
                x = leftBoatX + (engine.getBoatLocation() == Location.FINISH ? boatMoveDistance : 0) + (passengers * 60);
                y = leftBoatY - 60;
                passengers++;
                break;
            case START:
            case FINISH:
            default:
                x = leftBaseX + dx[id.ordinal()] + (itemLoc == Location.FINISH ? moveDistance : 0);
                y = leftBaseY + dy[id.ordinal()];
                break;

        }

        objRects.put(id, new Rectangle(x, y, 50, 50));
    }

    private void refreshBoatRectangle() {
        Rectangle rect;
        if (engine.getBoatLocation() == Location.START) {
            rect = new Rectangle(leftBoatX, leftBoatY, boatWidth, boatHeight);
        } else {
            rect = new Rectangle(leftBoatX + boatMoveDistance, leftBoatY, boatWidth, boatHeight);
        }

        boatRect = rect;
    }

    private void paintItem(Graphics g, Item id) {
        paintRectangle(g, engine.getItemColor(id), objRects.get(id));
        paintLabelInRectangle(g, engine.getItemLabel(id), objRects.get(id));
    }

    private void paintBoat(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(boatRect.x, boatRect.y, boatRect.width, boatRect.height);
    }

    private void paintRectangle(Graphics g, Color color, Rectangle rect) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    private void paintLabelInRectangle(Graphics g, String label, Rectangle rect) {
        g.setColor(Color.BLACK);
        int fontSize = (rect.height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = rect.x + rect.width / 2 - fm.stringWidth(label) / 2;
        int strYCoord = rect.y + rect.height / 2 + fontSize / 2 - 4;
        g.drawString(label, strXCoord, strYCoord);
    }

    public void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    public void paintRestartButton(Graphics g, String label, Rectangle rect) {
        g.setColor(Color.PINK);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        paintLabelInRectangle(g, label, rect);
    }

    // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {
        if (restart && this.farmerGameButtonRect.contains(e.getPoint())) {
            engine = new FarmerGameEngine();
            restart = false;
            repaint();
        } else if (restart && this.robotGameButtonRect.contains(e.getPoint())) {
            engine = new RobotGameEngine();
            restart = false;
            repaint();
        } else if (boatRect.contains(e.getPoint())) {
            engine.rowBoat();
            repaint();
        } else {
            for (Item key : objRects.keySet()) {
                if (objRects.get(key).contains(e.getPoint())) {
                    if (engine.getItemLocation(key) == Location.BOAT) {
                        engine.unloadBoat(key);
                    } else {
                        engine.loadBoat(key);
                    }
                    repaint();
                }
            }
        }
    }

    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
