package gameClasses;

import java.awt.*;

/**
 * Class to represent each piece in the game
 */
public class Piece {
    private Intersection currentPosition = null;
    private final Color pieceColor;
    private final Color selectedColor;
    private boolean dead = false;

    /**
     * Instantiates this piece with color from the player it belongs to.
     *
     * @param color The color of the piece, corresponding to which player it belongs to
     */
    public Piece(Color color) {
        pieceColor = color;
        selectedColor = new Color(color.getRed(), 200, color.getBlue());
    }

    /**
     * Returns the current intersection the piece is on if any, else null
     *
     * @return the intersection it is situated un at the moment or null if none
     */
    public Intersection currentPosition() {
        return  currentPosition;
    }

    /**
     * Places the piece on a given intersection
     *
     * @param targetIntersection the intersection on which the piece is to be placed
     */
    public void placePiece(Intersection targetIntersection){
        // if we aren't on a position and the target intersection is valid
        if (currentPosition == null && targetIntersection != null) {
            currentPosition = targetIntersection;
            // update the intersection color as this piece to graphically indicate placement
            currentPosition.setBackground(pieceColor);
            // denote the position as occupied
            currentPosition.occupy(this);
        }
    }

    /**
     * Moves the piece to a target intersection
     *
     * @param targetIntersection the intersection to which the piece is moved
     */
    public void movePiece(Intersection sourceIntersection, Intersection targetIntersection){
        // remove the piece from the sourceIntersection
        sourceIntersection.setBackground(Color.BLACK);
        sourceIntersection.unOccupy();

        // add the piece to the targetIntersection
        currentPosition = targetIntersection;
        currentPosition.setBackground(pieceColor);
        currentPosition.occupy(this);
    }

    /**
     * Removes the piece from this intersection
     * @param targetIntersection The intersection targeted
     */
    public void removePiece(Intersection targetIntersection) {
        // revert the currentPosition's colour to Black
        currentPosition.setBackground(Color.BLACK);
        currentPosition.unOccupy();
        // update this pieces currentPosition as null
        currentPosition = null;
        dead = true;
    }

    /**
     * Returns the color of this piece
     * @return the color of the piece
     */
    public Color getPieceColor() {
        return pieceColor;
    }

    /**
     * Returns the highlighted color of this piece
     * @return the highlighted color of this piece
     */
    public Color getHighlightColor() { return selectedColor; }

    /**
     * Checks the current intersection for a mill formation
     * @return whether a mill is formed
     */
    public boolean checkMill(){
        return currentPosition.checkMill();
    }

    public boolean dead() {
        return dead;
    }
}
