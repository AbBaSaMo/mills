package playerClasses;

import enums.Phase;
import gameClasses.Board;
import gameClasses.Intersection;
import gameClasses.Piece;
import interfaces.Publisher;
import interfaces.Subscriber;
import actionClasses.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class represents a player, who owns pieces and makes moves
 */
public abstract class Player implements Subscriber, Publisher {
    protected final int playerNum;
    protected Color playerColor;
    protected List<Subscriber> subscribers = new ArrayList<>();
    protected final List<Piece> unplacedPieces = new ArrayList<>();
    protected final List<Piece> placedPieces = new ArrayList<>();
    protected final List<Piece> deadPieces = new ArrayList<>();

    // attributes used in making a turn
    protected final Action placeAction = new PlacePieceAction(unplacedPieces, placedPieces);
    protected final Action slideAction = new SlidePieceAction(placedPieces);
    protected final Action flyAction = new FlyPieceAction(placedPieces);
    protected final Action removePiece = new RemovePieceAction(placedPieces);
    protected final ActionStrategy playerMoveStrat = new ActionStrategy(placeAction);
    protected Phase currentPhase;
    protected boolean isTurn;

    /**
     * Instantiates player by creating its pieces and marking its phase as the first phase of the game
     *
     * @param playerColor color representing this player
     */
    public Player(Color playerColor, int playerNum){
        this.playerNum = playerNum;
        this.playerColor = playerColor;
        initialisePieces(playerColor);
        currentPhase = Phase.MOVE_PIECE;
        isTurn = false;
    }

    /**
     * Creates and gives the player their pieces
     */
    // Used to give the player their pieces
    public void initialisePieces(Color pieceColor){
        // create 9 pieces for this player
        for(int p=1; p<=9; p++) {
            unplacedPieces.add(new Piece(pieceColor));
        }
    }

    /**
     * Subscribed this player to Board intersections
     *
     * @param gameBoard the game board we are playing on
     */
    public void register(Board gameBoard) { gameBoard.registerHumanPlayer(this); }

    /**
     * Allows the player to take a turn
     */
    public void play() {
        // filter dead pieces that might have been removed by opponent
        updatePiecesArray();
        gameOverCheck();
        updateSubscribers(playerColor);
        isTurn = true;
    }

    /**
     * Check if this player has lost the game
     */
    protected void gameOverCheck() {
        if(playerMoveStrat.getStrategy() == slideAction) {
            // for all of our pieces, see if they can be slid somewhere
            for(Piece placedPiece: placedPieces) {
                Intersection intersection = placedPiece.currentPosition();
                for(Intersection adjIntersection: intersection.getAdjIntersections()) {
                    // if there is a single free adj intersection, continue the game
                    if(!adjIntersection.isOccupied()) {
                        return;
                    }
                }
            }
            updateSubscribers();
        } else if (deadPieces.size() >= 7) {
            updateSubscribers();
        }
    }

    protected void updatePiecesArray() {
        for(Piece piece: placedPieces) {
            if(piece.dead()) {
                placedPieces.remove(piece);
                deadPieces.add(piece);
                break; // break out of loop to avoid concurrency issue
            }
        }
    }

    /**
     * Check if this player formed a mill on this turn
     * @param intersection The intersection upon which a turn was just made
     */
    private void checkMill(Intersection intersection) {
        boolean millFormed = intersection.checkMill();

        if(millFormed) {
            // update strategy and continue turn
            currentPhase = Phase.REMOVE_PIECE;
            playerMoveStrat.setStrategy(removePiece);
        } else {
            // tell game our turn is over
            isTurn = false;
            updateSubscribers(this);
        }
    }

    /**
     * Sets the players MoveStrategy to the appropriate strategy for the phase
     */
    protected void checkMoveStrategy() {
        // determine the current strategy based on the number of placed/dead pieces
        if(!unplacedPieces.isEmpty()) {
            playerMoveStrat.setStrategy(placeAction);
        } else if (deadPieces.size() < 6) {
            playerMoveStrat.setStrategy(slideAction);
        } else {
            playerMoveStrat.setStrategy(flyAction);
        }
    }

    /**
     * Method used by an intersection player is subscribed to, to notify that it has been clicked,
     * and takes the required action based on who has been clicked.
     *
     * @param intersection the intersection that was clicked
     */
    @Override
    public boolean update(Intersection intersection) {
        // only care about an intersection being clicked if it's player's turn
        if (isTurn) {
            if (currentPhase == Phase.MOVE_PIECE) {
                checkMoveStrategy(); // determine the kind of move we're making
                if (playerMoveStrat.executeStrategy(intersection)) {
                    // let move know how many pieces we have here
                    checkMill(intersection);
                }
            }
            else if (currentPhase == Phase.REMOVE_PIECE) {
                if (isOpponentPieceRemovable(intersection) && playerMoveStrat.executeStrategy(intersection)) {
                    // revert back to MOVE_PHASE and tell game our turn is over
                    currentPhase = Phase.MOVE_PIECE;
                    isTurn = false;
                    updateSubscribers(this);
                    updateSubscribers(playerNum);
                    return true;
                }
            }
            return !isTurn;
        }
        return false;
    }

    /**
     * Checks if a clicked intersection is viable for removal according to game rules
     *
     * @param clickedIntersection the intersection clicked
     * @return boolean indicating if it can be removed by player
     */
    private boolean isOpponentPieceRemovable(Intersection clickedIntersection) {
        Player opponent;
        for(Subscriber subscriber: subscribers) {
            if(subscriber instanceof Player) {
                opponent = (Player) subscriber;
                return opponent.isOwnPieceRemovable(clickedIntersection);

            }
        }
        return false;
    }

    /**
     * Checks if a clicked intersection is viable for removal according to game rules
     *
     * @param clickedIntersection the intersection clicked
     * @return boolean indicating if it can be removed by player
     */
    public boolean isOwnPieceRemovable(Intersection clickedIntersection) {
        for(Piece piece: placedPieces) {
            // if there is a single piece not in a mill
            // this intersection is removable only if it is not in a mill
            if (!piece.checkMill() && clickedIntersection.isOccupied()) {
                return !clickedIntersection.checkMill();
            }
        } return true;
    }

    @Override
    public void update() {}

    /**
     * Updates subscribers
     * @param player This player
     */
    @Override
    public void update(Player player){  }

    /**
     * Updates subscribers
     * @param color This color
     */
    @Override
    public void update(Color color) {  }

    /**
     * Updates subscribers
     * @param playerNum This player's number
     */
    public void update(int playerNum) {  }

    /**
     * Allows other players to subscribe to this one
     *
     * @param newSubscriber The object subscribing to this publisher
     */
    @Override
    public void subscribe(Subscriber newSubscriber) {
        subscribers.add(newSubscriber);
    }

    /**
     * Updates subscribers when an important change has occurred
     */
    @Override
    public void updateSubscribers() {
        for(Subscriber subscriber: subscribers) {
            subscriber.update();
        }
    }

    @Override
    public void updateSubscribers(Player player) {
        for(Subscriber subscriber: subscribers) {
            subscriber.update(player);
        }
    }

    /**
     * Updates subscribers with this player's color
     * @param thisPlayerColor The player's color
     */
    @Override
    public void updateSubscribers(Color thisPlayerColor) {
        for(Subscriber subscriber: subscribers) {
            subscriber.update(thisPlayerColor);
        }
    }

    /**
     * Updates subscribers with this player's number
     * @param playerNum The number of this player
     */
    @Override
    public void updateSubscribers(int playerNum) {
        for(Subscriber subscriber: subscribers) {
            subscriber.update(playerNum);
        }
    }

    /**
     * Returns a string representation of this player
     * @return The string representation
     */
    @Override
    public String toString() {
        return "Player " + playerNum;
    }
}
