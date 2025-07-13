package actionClasses;

import gameClasses.Intersection;

/**
 * Class inherited by each type of move
 */
public class Action {
    public Action() {

    }

    /**
     * Defines the stereotype for executing an action
     * @param clickedIntersection Targeted intersection
     * @return Whether the action executed
     */
    public boolean execute(Intersection clickedIntersection) { return true; }

    /**
     * Defines the stereotype for selecting a piece
     */
    public boolean execute() { return true; }
}