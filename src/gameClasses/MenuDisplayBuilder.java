package gameClasses;

import interfaces.Publisher;
import interfaces.Subscriber;
import playerClasses.ComputerPlayer;
import playerClasses.HumanPlayer;
import playerClasses.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Class builds the GUI window on which the menu is displayed
 */
public class MenuDisplayBuilder extends DisplayBuilder implements Publisher {
    private Publisher onePlayerButton;
    private Publisher twoPlayerButton;

    /**
     * Instantiates the displayBuilder, setting key parameters for display building
     *
     * @param gameTitle the title of the game to be displayed
     * @param displayWidth the width of the GUI
     * @param displayHeight the height of the GUI
     */
    public MenuDisplayBuilder(String gameTitle, int displayWidth, int displayHeight) {
        super(gameTitle, displayWidth, displayHeight);
    }

    /**
     * Builds the JFrame that will be the display and the components inside it
     */
    @Override
    public void buildDisplay() {
        // create the JFrame window for the GUI
        display = new JFrame(gameTitle);
        display.setLayout(null); // allow us to specify absolute positions of components
        display.setSize(new Dimension(displayWidth, displayHeight));
        display.getContentPane().setBackground(new Color(200, 200, 200));

        // Create text labels and components for the UI
        JLabel titleLabel = createLabel(gameTitle, 60 , 0.05, 0.02);
        String prompt = "click a tile to select a game mode";
        JLabel promptLabel = createLabel(prompt, 35 , 0.15, 0.1);
        JLabel onePlayerMode = createLabel("one player mode", 30 , 0.25, 0.2);
        JLabel twoPlayerMode = createLabel("two player mode", 30 , 0.25, 0.3);
        Button onePlayerButton = new Button(200, 200, 75, Color.RED, new ComputerPlayer(Color.BLUE, 2));
        Button twoPlayerButton = new Button(200, 300, 75, Color.BLUE, new HumanPlayer(Color.BLUE, 2));
        this.onePlayerButton = onePlayerButton;
        this.twoPlayerButton = twoPlayerButton;

        // add all components to UI
        display.add(titleLabel);
        display.add(promptLabel);
        display.add(onePlayerMode);
        display.add(twoPlayerMode);
        display.add(onePlayerButton);
        display.add(twoPlayerButton);

        // configure JFrame
        display.setResizable(false);
        display.setVisible(true);
    }

    /**
     * Subscribes an object to the buttons on this menu
     *
     * @param newSubscriber The object subscribing to this publisher
     */
    @Override
    public void subscribe(Subscriber newSubscriber) {
        // subscribe to both menu buttons
        onePlayerButton.subscribe(newSubscriber);
        twoPlayerButton.subscribe(newSubscriber);
    }

    /**
     * Updates all subscribers
     *
     * @param player the player related to this update
     */
    @Override
    public void updateSubscribers(Player player) {

    }

    /**
     * Updates all subscribers
     */
    @Override
    public void updateSubscribers() {

    }

    /**
     * Updates all subscribers
     *
     * @param color color of this publisher
     */
    @Override
    public void updateSubscribers(Color color) {

    }

    /**
     * Updates all subscribers
     *
     * @param playerNum player number of this publisher
     */
    @Override
    public void updateSubscribers(int playerNum) {

    }

    /**
     * Updates the frame
     */
    @Override
    public void update() {

    }
}
