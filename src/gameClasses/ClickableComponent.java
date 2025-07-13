package gameClasses;

import interfaces.Publisher;
import interfaces.Subscriber;
import playerClasses.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents clickable components of the interface
 */
public abstract class ClickableComponent extends JPanel implements MouseListener, Publisher {
    protected List<Subscriber> subscribers = new ArrayList<>();

    /**
     * Instantiates a clickable component
     * @param xPos X coordinate of the button
     * @param yPos Y coordinate of the button
     * @param length length of the button
     * @param color color of the button
     */
    public ClickableComponent(int xPos, int yPos, int length, Color color) {
        int halfLength = (int)((float) length /2);
        setBounds(xPos - halfLength, yPos-halfLength, length, length);
        setBackground(color);
        addMouseListener(this);
    }

    /**
     * Enables programmatic clicking of button
     */
    public void doClick(){
    }

    /**
     * Adds a new subscriber to this object
     * @param newSubscriber The object subscribing to this publisher
     */
    @Override
    public void subscribe(Subscriber newSubscriber) {
        subscribers.add(newSubscriber);
    }

    /**
     * Defines the stereotype for updating subscribers
     */
    @Override
    public void updateSubscribers() {
    }

    /**
     * Defines the stereotype for updating subscribers
     */
    @Override
    public void updateSubscribers(Player player) {
    }

    /**
     * Defines the stereotype for updating subscribers
     */
    @Override
    public void updateSubscribers(Color color) {

    }

    /**
     * Defines the stereotype for updating subscribers
     */
    @Override
    public void updateSubscribers(int playerNum) {

    }

    /**
     * Defines the stereotype for being clicked
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Defines the stereotype for being pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Defines the stereotype for being released
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Defines the stereotype for a mouse entering the component
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Defines the stereotype for a mouse exiting the component
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

}
