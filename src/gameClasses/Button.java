package gameClasses;

import interfaces.Subscriber;
import playerClasses.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Class represents buttons on the board
 */
public class Button extends ClickableComponent{
    private final Player playerTwo;

    /**
     * Instantiates the button
     * @param xPos X coordinate of the button
     * @param yPos Y coordinate of the button
     * @param length length of the button
     * @param color color of the button
     * @param playerTwo the player associated with the button
     */
    public Button(int xPos, int yPos, int length, Color color, Player playerTwo) {
        super(xPos, yPos, length, color);
        setBorder(BorderFactory.createLineBorder(color, 4, true));
        setBackground(color.darker());
        this.playerTwo = playerTwo;
    }

    /**
     * Update subscribers when the button is clicked
     */
    @Override
    public void updateSubscribers() {
        for(Subscriber subscriber: subscribers) {
            subscriber.update(playerTwo);
        }
    }

    /**
     * Perform required actions when clicked by a mouse
     * @param e the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        setBackground(Color.WHITE);

        updateSubscribers();
    }
}
