package actionClasses;

import gameClasses.Intersection;
import gameClasses.Piece;

import java.util.List;

/**
 * Class facilitates a 'sliding' move
 */
public class SlidePieceAction extends Action {
    private final List<Piece> placedPieces;
    private Intersection firstSelection;
    private Piece firstSelectionPiece;

    /**
     * Instantiates a slide move with the list of currently placed pieces from that team
     * @param placedPieces List of placed pieces
     */
    public SlidePieceAction(List<Piece> placedPieces) {
        this.placedPieces = placedPieces;
    }

    /**
     * Attempts to execute the move based on a selected intersection
     * @param clickedIntersection Intersection targeted
     * @return Whether the move executed
     */
    public boolean execute(Intersection clickedIntersection) {

        // if we haven't picked a piece to move and the intersection has a piece on it
        System.out.println("This intersection is " + clickedIntersection);

        if (firstSelection == null && clickedIntersection.isOccupied()) {
            System.out.println("Seeing if this should be the first pick");
            // get the piece that is on that intersection if it is ours
            for (Piece piece : placedPieces) {
                // if this is the intersection of one of our pieces, pick that piece
                if (piece.currentPosition() == clickedIntersection) {
                    firstSelectionPiece = piece;
                    break;
                }
            }

            // if it ends up being a piece of ours on the selected intersection, select the intersection
            if (firstSelectionPiece != null) {
                firstSelection = clickedIntersection;
                firstSelection.setBackground(firstSelectionPiece.getHighlightColor());
                System.out.println("Player selected a piece to move");
            }

            // if we have picked a piece to move and are selecting an ADJACENT, empty target intersection
        } else if (firstSelection != null && !clickedIntersection.isOccupied() && clickedIntersection.adjacentTo(firstSelection)) {
            // move our piece to the selected intersection
            firstSelectionPiece.movePiece(firstSelection, clickedIntersection);
            System.out.println("Player moved a piece");

            // reset selections for next turn
            firstSelection = null;
            firstSelectionPiece = null;
            return true;


            // if we clicked any other intersection
        } else if (firstSelection != null) {
            // deselect current intersection
            firstSelection.setBackground(firstSelectionPiece.getPieceColor());
            firstSelection = null;
            firstSelectionPiece = null;
            System.out.println("Undoing first selection");


            // check if we clicked another one of our pieces, and select that piece instead.
            for (Piece piece : placedPieces) {
                // if this is the intersection of one of our pieces, pick that piece
                if (piece.currentPosition() == clickedIntersection) {
                    firstSelectionPiece = piece;
                    break;
                }
            }
            if (firstSelectionPiece != null) {
                firstSelection = clickedIntersection;
                firstSelection.setBackground(firstSelectionPiece.getHighlightColor());
                System.out.println("Player selected a different piece to move");
            }
        }
        return false;
    }

    /**
     * String representation of the move
     * @return String representation
     */
    @Override
    public String toString() {
        return "slide piece move";
    }
}
