package playerClasses;

import enums.Phase;
import gameClasses.Board;
import gameClasses.Intersection;
import actionClasses.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class represents a player, who makes moves via random programmatic decisions
 */
public class ComputerPlayer extends Player {
    private List<Intersection> allIntersections = new ArrayList<>();
    private List<Intersection> freeIntersections = new ArrayList<>();
    private List<Intersection> opponentIntersections = new ArrayList<>();


    private final Action placeClick = new PlaceClickAction(freeIntersections);
    private final Action slideClick = new SlideClickAction(allIntersections);
    private final Action flyClick = new FlyClickAction(allIntersections);
    private final Action removeClick = new RemoveClickAction(opponentIntersections);
    private final ActionStrategy clickStrategy = new ActionStrategy(placeClick);

    /**
     * Instantiates the computer player
     * @param playerColor The color of this player
     * @param playerNum The number of this player
     */
    public ComputerPlayer(Color playerColor, int playerNum){
        super(playerColor, playerNum);
    }

    /**
     * Adds the intersections within an arrayList to the current list of intersections
     *
     * @param intersections an arrayList of intersections
     */
    public void addIntersections(List<Intersection> intersections) {
        allIntersections.addAll(intersections);
        freeIntersections.addAll(intersections);
    }

    /**
     * Registers this player to the board
     * @param gameBoard the game board we are playing on
     */
    @Override
    public void register(Board gameBoard) {
        gameBoard.registerComputerPlayer(this);
    }

    /**
     * Plays this players turn
     */
    @Override
    public void play() {
        // update computer players register of pieces and intersection
        updatePiecesArray();
        gameOverCheck();
        if(currentPhase != Phase.REMOVE_PIECE) {
            updateSubscribers(playerColor);
            checkClickStrategy();
        } else {
            clickStrategy.setStrategy(removeClick);
        }
        isTurn = true;

        while (isTurn){
            clickStrategy.executeStrategy();
            try { Thread.sleep(100); }
            catch (InterruptedException e) {throw new RuntimeException(e);}
        }
    }

    /**
     * Checks the phase of the game and assigns the associated move strategy
     */
    private void checkClickStrategy() {
        // determine the current strategy based on the number of placed/dead pieces
        if(!unplacedPieces.isEmpty()) {
            clickStrategy.setStrategy(placeClick);
        } else if (deadPieces.size() < 6) {
            clickStrategy.setStrategy(slideClick);
        } else {
            clickStrategy.setStrategy(flyClick);
        }
    }
}

