package actionClasses;

import gameClasses.Intersection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class facilitates a sliding click for computer players
 */
public class SlideClickAction extends ClickAction{
    private List<Intersection> clickableIntersections;
    protected List<Intersection> allIntersections;
    private Intersection firstClick;

    /**
     * Instantiates the slide click action with the list of intersections
     * @param allIntersections all available intersections
     */
    public SlideClickAction(List<Intersection> allIntersections) {
        super();
        this.allIntersections = allIntersections;
    }

    /**
     * Responsible for selecting the piece to move, as well as where to move it
     * @return whether it executed properly
     */
    @Override
    public boolean execute() {
        // if computer hasn't made an initial selection
        if (firstClick == null){
            updateSelfIntersections();
            firstClick = clickableIntersections.get(
                    numberGenerator.nextInt(clickableIntersections.size())
            );

            firstClick.doClick();
        // if a piece is selected, click a FREE adjacent intersection from the selection we made
        } else {
            List<Intersection> freeAdjIntersection = viableAdjIntersection(firstClick);

            Intersection freeIntersection = freeAdjIntersection.get(
                    numberGenerator.nextInt(freeAdjIntersection.size())
            );

            freeIntersection.doClick();
            firstClick = null;
            return true;
        }
        return false;
    }

    /**
     * Obtains all free adjacent intersections
     *
     * @param potentialIntersection a first selection
     */
    private List<Intersection> viableAdjIntersection(Intersection potentialIntersection) {
        List<Intersection> viableIntersections = new ArrayList<>();

        for (Intersection inter : potentialIntersection.getAdjIntersections()){
            if (!inter.isOccupied()){
                viableIntersections.add(inter);
            }
        }
        return viableIntersections;
    }

    /**
     * Fills an arraylist with all the locations where the computer can make a move with
     *
     */
    private void updateSelfIntersections() {
        clickableIntersections = new ArrayList<>();
        // get all self intersections that have at least one free adjacent intersection
        for (Intersection inter : allIntersections){
            if (inter.isOccupied() && inter.piece().getPieceColor() == Color.BLUE){
                for(Intersection adjInter: inter.getAdjIntersections()) {
                    if(!adjInter.isOccupied()) {
                        clickableIntersections.add(inter);
                        break;
                    }
                }
            }
        }
    }
}
