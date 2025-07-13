package actionClasses;


import java.util.Random;

/**
 * Class inherited by each type of click action
 * Used by computer players, such that intersections can be 'clicked' without interacting with the mouse listeners
 */
public abstract class ClickAction extends Action {
    protected final Random numberGenerator = new Random();

    /**
     * Defines the stereotype for a click action
     */
    public ClickAction() { }

}
