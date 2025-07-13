package interfaces;

import playerClasses.Player;

import java.awt.*;

/**
 * Interface that enables publisher component of the observer pattern
 */
public interface Publisher {
    /**
     * Subscribes an object to this publisher
     * @param newSubscriber The object subscribing to this publisher
     */
    void subscribe(Subscriber newSubscriber);

    /**
     * Updates all subscribers
     */
    void updateSubscribers(Player player);

    /**
     * Updates all subscribers
     */
    void updateSubscribers();

    /**
     * Updates all subscribers
     */
    void updateSubscribers(Color color);
    /**
     * Updates all subscribers
     */
    void updateSubscribers(int playerNum);
}
