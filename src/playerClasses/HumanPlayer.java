package playerClasses;
import gameClasses.Board;

import java.awt.*;

/**
 *  Class represents a player, who makes moves via mouse events
 */
public class HumanPlayer extends Player {
    /**
     * Creates a new Human player
     * @param playerColor The color of this player
     * @param playerNum The number of this player
     */
    public HumanPlayer(Color playerColor, int playerNum){
        super(playerColor, playerNum);
    }

    /**
     * Subscribed this player to Board intersections
     *
     * @param gameBoard the game board we are playing on
     */
    @Override
    public void register(Board gameBoard) {
        gameBoard.registerHumanPlayer(this);
    }
}
