package actionClasses;

import gameClasses.Intersection;
import gameClasses.Piece;

import java.util.List;

/**
 * Class facilitates placing a piece
 */
public class PlacePieceAction extends Action {
    private final List<Piece> unplacedPieces;
    private final List<Piece> placedPieces;
    private Intersection firstSelection = null;

    /**
     * Instantiates a place move with the list of currently unplaced and placed pieces from that team
     * @param unplacedPieces List of unplaced pieces
     * @param placedPieces List of placed pieces
     */
    public PlacePieceAction(List<Piece> unplacedPieces, List<Piece> placedPieces) {
        this.unplacedPieces = unplacedPieces;
        this.placedPieces = placedPieces;
    }

    /**
     * Attempts to execute the move based on a selected intersection
     * @param clickedIntersection Intersection targeted
     * @return Whether the move executed
     */
    public boolean execute(Intersection clickedIntersection) {
        // if we haven't selected anything so far and the clicked intersection is empty
        if (firstSelection == null && !clickedIntersection.isOccupied()) {
            firstSelection = clickedIntersection;

            // select a piece to make a move with
            Piece movingPiece = unplacedPieces.get(0);

            // let's make the move
            movingPiece.placePiece(firstSelection);
            placedPieces.add(unplacedPieces.remove(0));
            System.out.println("PlacePieceAction: Player placed a piece");

            // update variables
            firstSelection = null;
            // let the caller know the turn has been made
            return true;
        }
        return false;
    }

    /**
     * String representation of the move
     * @return String representation
     */
    @Override
    public String toString() {
        return "place piece move";
    }
}
