package interfaces;
import gameClasses.Intersection;
import playerClasses.Player;

import java.awt.*;

/**
 * Interface to implement the subscriber in the observer pattern
 */
public interface Subscriber {

    /**
     * Updates subscribers generically
     */
    void update();

    void update(Player player);
    /**
     * Updates the subscriber regarding some state change related to an intersection
     * @param intersection the intersection
     */
    boolean update(Intersection intersection);

    /**
     * Lets the subscriber know the colour of the publisher
     * @param color colour of the publisher
     */
    void update(Color color);

    /**
     * Lets the subscriber know the players number
     *
     * @param playerNum number of the player
     */
    void update(int playerNum);
}
