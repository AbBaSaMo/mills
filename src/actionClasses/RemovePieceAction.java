package actionClasses;

import gameClasses.Intersection;
import gameClasses.Piece;

import java.util.List;

/**
 * Class facilitates piece removal
 */
public class RemovePieceAction extends Action {
    private final List<Piece> activePiecesList;

    /**
     * Instantiates a removal move with the list of active and dead pieces from that team
     * @param activePiecesList List of alive pieces
     */
    public RemovePieceAction(List<Piece> activePiecesList) {
        this.activePiecesList = activePiecesList;
    }

    /**
     * Attempts to execute the move based on a selected intersection
     * @param clickedIntersection Intersection targeted
     * @return Whether the move executed
     */
    public boolean execute(Intersection clickedIntersection) {
        Piece occupyingPiece = clickedIntersection.piece();
        // is it occupied and not ours
        if(clickedIntersection.isOccupied() && !activePiecesList.contains(occupyingPiece)) {
            System.out.println("RemovePiece: Player removed piece");
            occupyingPiece.removePiece(clickedIntersection);
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
        return "remove piece move";
    }
}
