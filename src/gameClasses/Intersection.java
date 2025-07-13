package gameClasses;

import interfaces.Subscriber;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent each intersection (point) on the board on which a piece can be placed
 */
public class Intersection extends ClickableComponent {
    private final List<Intersection> adjIntersections = new ArrayList<>();
    private final List<Mill> intersectionMills = new ArrayList<>();
    private Boolean occupied = false;
    private Piece occupyingPiece;

    /**
     * Instantiates intersection and sets position on JFrame and color
     * and registers as mouseListener
     *
     * @param xPos Absolute horizontal position of intersection on GUI
     * @param yPos Absolute vertical position of intersection on GUI
     */
    public Intersection(int xPos, int yPos, int length, Color color) {
        super(xPos, yPos, length, color);
    }

    /**
     * Add a given intersection to this intersection's adjacent intersections
     *
     * @param intersection the intersection that is adjacent to this one
     */
    public void linkIntersection(Intersection intersection){
        adjIntersections.add(intersection);
    }

    /**
     * Determines if this intersection is adjacent to the input intersection
     *
     * @param clickedIntersection the intersection we are seeing if this is adjacent to
     * @return boolean indicating adjacency
     */
    public boolean adjacentTo(Intersection clickedIntersection) {
        return adjIntersections.contains(clickedIntersection);
    }

    /**
     * Adds a mill to this intersection's list of associated mills
     */
    public void addMill(Mill mill) {
        intersectionMills.add(mill);
    }

    /**
     * Checks whether any mills associated with this intersection have been formed
     * @return Whether any associated mills are formed
     */
    public boolean checkMill() {
        for(Mill mill:intersectionMills) {
            if (mill.millFormed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets adjacent intersections to this intersection
     * @return ArrayList of adjacent intersections
     */
    public List<Intersection> getAdjIntersections(){
        return adjIntersections;
    }

    /**
     * Denotes the intersection as occupied. Used when a piece has been moved onto this intersection
     */
    public void occupy(Piece piece){
        occupied = true;
        occupyingPiece = piece;
    }

    /**
     * Denotes the intersection as unOccupied
     */
    public void unOccupy(){
        occupied = false;
        occupyingPiece = null;
    }

    /**
     * Get the piece occupying this intersection
     * @return the piece occupying
     */
    public Piece piece() {
        return occupyingPiece;
    }

    /**
     * Checks if the intersection is occupied
     *
     * @return boolean indicating if occupied
     */
    public boolean isOccupied(){
        return occupied;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // let all subscribed players know that this has been clicked
        updateSubscribers();
    }

    /**
     * Allows the AI player "click" an intersection
     */
    public void doClick(){
        updateSubscribers();
    }

    /**
     * Allows other objects to subscribe to this one for an Observer pattern.
     *
     * @param newSubscriber the object subscribing
     */
    @Override
    public void subscribe(Subscriber newSubscriber) {
        subscribers.add(newSubscriber);
    }

    /**
     * Updates all subscribing objects
     */
    @Override
    public void updateSubscribers() {
        // called on MouseClick: let all subscribed players know this was clicked
        for(Subscriber player: subscribers) {
            if (player.update(this)){ // if this player made a move
                break;
            }
        }
    }

    /**
     * Returns a string representation
     */
    public String toString() {
        return "Color -> " + this.getBackground();
    }
}
