package gameClasses;

import interfaces.Subscriber;
import playerClasses.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Class builds the display
 */
public abstract class DisplayBuilder implements Subscriber {
    protected JFrame display;
    protected final String gameTitle;
    protected final int displayWidth;
    protected final int displayHeight;

    /**
     * Instantiates the displayBuilder, setting key parameters for display building
     *
     * @param gameTitle the title of the game to be displayed
     * @param displayWidth the width of the GUI
     * @param displayHeight the height of the GUI
     */
    public DisplayBuilder(String gameTitle, int displayWidth, int displayHeight) {
        this.gameTitle = gameTitle;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
    }

    /**
     * Builds the JFrame that will be the display and the components inside it
     *
     * @param components list of JComponents to be included in the display
     */
    public void buildDisplay(List<JPanel> components) {  }

    /**
     * Builds the JFrame that will be the display
     *
     */
    public void buildDisplay() {  }

    public void reset(){
        display.dispose();
    }

    /**
     *
     *
     * @param labelString the text for the label
     * @param fontSize the size of the label
     * @param xPosScalar scalar to position label horizontally in proportion to displayWidth
     * @param yPosScalar scalar to position label vertically in proportion to displayHeight
     * @return a JLabel containing the specified text, positioned as per te position scalars
     */
    protected JLabel createLabel(String labelString, int fontSize, double xPosScalar, double yPosScalar) {
        JLabel label = new JLabel(labelString);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
        label.setBounds(
                (int)((float) xPosScalar * displayWidth),
                (int)((float) yPosScalar * displayWidth),
                labelString.length() * fontSize,
                fontSize
        );

        return label;
    }

    @Override
    public void update() {}

    /**
     * Subscriber interface method
     * @param player player who has sent the update
     */
    @Override
    public void update(Player player) {  }

    /**
     * Subscriber interface method
     * @param intersection the intersection that has been clicked
     */
    @Override
    public boolean update(Intersection intersection) {
        return false;
    }

    /**
     * Subscriber interface method
     * @param color the color of the player sending the update
     */
    @Override
    public void update(Color color) {  }

    /**
     * Subscriber interface method
     * @param playerNum the number of the player whose turn it is
     */
    @Override
    public void update(int playerNum) {  }
}
