package actionClasses;

import gameClasses.Intersection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class facilitates a 'flying' click for computer players
 */
public class FlyClickAction extends ClickAction{
    private List<Intersection> freeIntersections;
    private List<Intersection> selfIntersections;
    protected List<Intersection> allIntersections;
    private Intersection ourIntersection = null;

    /**
     * Instantiates the fly click action with the list of intersections
     * @param allIntersections all available intersections
     */
    public FlyClickAction(List<Intersection> allIntersections) {
        super();
        this.allIntersections = allIntersections;
    }

    /**
     * Responsible for selecting the piece to move, as well as where to move it
     * @return whether it executed properly
     */
    @Override
    public boolean execute() {
        if (ourIntersection == null){
            updateSelfIntersections();
            updateFreeIntersections();

            ourIntersection = selfIntersections.get(
                    numberGenerator.nextInt(selfIntersections.size())
            );

            ourIntersection.doClick();
        } else {
            Intersection freeIntersection = freeIntersections.get(
                    numberGenerator.nextInt(freeIntersections.size())
            );

            freeIntersection.doClick();
            ourIntersection = null;
            return true;
        }
        return false;
    }

    /**
     * Fills an arraylist with all the locations where the computer has a piece
     *
     */
    private void updateSelfIntersections() {
        selfIntersections = new ArrayList<>();

        for (Intersection inter : allIntersections){
            if (inter.isOccupied()){
                if (inter.piece().getPieceColor() == Color.BLUE){
                    selfIntersections.add(inter);
                }
            }
        }
    }

    /**
     * Fills an arraylist with all the locations where there is no piece
     *
     */
    private void updateFreeIntersections() {
        freeIntersections = new ArrayList<>();

        for (Intersection inter : allIntersections){
            if (!inter.isOccupied()){
                freeIntersections.add(inter);
            }
        }
    }
}
