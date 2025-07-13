package gameClasses;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class builds the GUI window on which the game is displayed
 */
public class GameDisplayBuilder extends DisplayBuilder {
    private final JPanel turnIndicator = new JPanel();
    private final List<JLabel> tokenCounts = new ArrayList<>();

    /**
     * Instantiates the displayBuilder, setting key parameters for display building
     *
     * @param gameTitle the title of the game to be displayed
     * @param displayWidth the width of the GUI
     * @param displayHeight the height of the GUI
     */
    public GameDisplayBuilder(String gameTitle, int displayWidth, int displayHeight) {
        super(gameTitle, displayWidth, displayHeight);
    }

    /**
     * Updates the turn indicator on the display
     * @param playerColor of the player whose turn it is
     */
    public void updateTurnInfoDisplay(Color playerColor) {
        turnIndicator.setBackground(playerColor);
    }

    /**
     * Builds the JFrame that will be the display and the components inside it
     *
     * @param components list of JComponents to be included in the display
     */
    @Override
    public void buildDisplay(List<JPanel> components) {
        // create the JFrame window for the GUI
        display = new JFrame(gameTitle);
        display.setLayout(null); // allow us to specify absolute positions of components
        display.setSize(new Dimension(displayWidth, displayHeight));
        display.getContentPane().setBackground(new Color(200, 200, 200));

        // Create text labels for the UI
        JLabel titleLabel = createLabel(gameTitle, 24 , 0.05, 0.02);
        JPanel playerOneTokenCntIcon = new JPanel();
        JPanel playerTwoTokenCntIcon = new JPanel();

        playerOneTokenCntIcon.setBounds(
                (int)(float)(0.73 * displayWidth),
                (int)(float)(0.2 * displayHeight),
                25, 25
        );
        playerOneTokenCntIcon.setBackground(Color.RED);
        playerTwoTokenCntIcon.setBounds(
                (int)(float)(0.73 * displayWidth),
                (int)(float)(0.27 * displayHeight),
                25, 25
        );
        playerTwoTokenCntIcon.setBackground(Color.BLUE);

        turnIndicator.setBounds(
                (int)(float)(0.68 * displayWidth),
                (int)(float)(0.125 * displayHeight),
                30, 30
        );
        turnIndicator.setBackground(Color.BLACK);

        JLabel turnLabel = createLabel(" 's turn", 24 , 0.7, 0.1);
        JLabel playerOneTokenCnt = createLabel("9x", 20 , 0.7, 0.15);
        JLabel playerTwoTokenCnt = createLabel("9x", 20 , 0.7, 0.2);
        tokenCounts.add(playerOneTokenCnt);
        tokenCounts.add(playerTwoTokenCnt);

        display.add(titleLabel);
        display.add(turnIndicator);
        display.add(playerOneTokenCntIcon);
        display.add(playerTwoTokenCntIcon);
        display.add(turnLabel);
        display.add(playerOneTokenCnt);
        display.add(playerTwoTokenCnt);

        // Add the board components to the UI
        for(JPanel component: components) {
            display.add(component);
        }

        // configure JFrame
        display.setResizable(false);
        display.setVisible(true);
    }

    /**
     * Subscriber interface method
     * @param playerNum the player whose turn it is
     */
    @Override
    public void update(int playerNum) {
        // get the right label
        JLabel count = tokenCounts.get(playerNum%2);
        // decrement the number
        int prevCount = Integer.parseInt(count.getText().substring(0, 1));
        int newCount = prevCount - 1;
        // update JLabel
        if (newCount >= 0) {
            count.setText(Integer.toString(newCount) + "x");
            count.revalidate();
        }
    }
}
