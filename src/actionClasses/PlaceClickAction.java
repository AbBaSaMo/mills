package actionClasses;

import gameClasses.Intersection;

import java.util.List;

/**
 * Class facilitates a placing a piece click for a computer player
 */
public class PlaceClickAction extends ClickAction {
    protected List<Intersection> freeIntersections;

    /**
     * Instantiates the place click action with the list of intersections
     * @param freeIntersections all empty intersections
     */
    public PlaceClickAction(List<Intersection> freeIntersections) {
        super();
        this.freeIntersections = freeIntersections;
    }

    /**
     * Responsible for selecting the intersection to place a piece at
     * @return whether it executed properly
     */
    @Override
    public boolean execute() {
        System.out.println("PlaceClickAction: Computer selecting a piece to click, free ints="+freeIntersections.size());
        Intersection selection = freeIntersections.get(
                numberGenerator.nextInt(freeIntersections.size())
        );
        selection.doClick();
        return true;
    }
}
