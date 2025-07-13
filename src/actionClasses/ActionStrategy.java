package actionClasses;

import gameClasses.Intersection;

/**
 * Class implements a strategy pattern to allow each type of move to be made
 */
public class ActionStrategy {
    private Action strategy;

    /**
     * Creates a move strategy based on the current phase
     * @param initialActionStrat The move strategy
     */
    public ActionStrategy(Action initialActionStrat) {
        // create a phase with an initial strategy
        strategy = initialActionStrat;
    }

    /**
     * Updates the move strategy based on the current phase
     * @param newActionStrat The new move strategy
     */
    public void setStrategy(Action newActionStrat) {
        strategy = newActionStrat;
    }

    /**
     * Returns the current move strategy
     * @return Current Move strategy
     */
    public Action getStrategy() {
        return strategy;
    }

    /**
     * Attempts to execute the current move
     * @param clickedIntersection The target intersection
     * @return The result of the attempted execution
     */
    public boolean executeStrategy(Intersection clickedIntersection) {
        return strategy.execute(clickedIntersection);
    }

    public boolean executeStrategy() {
        return strategy.execute();
    }
}
