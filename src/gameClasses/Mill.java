package gameClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents each classes.Mill on the board
 */
public class Mill {
    private final List<Intersection> millIntersections = new ArrayList<>();

    /**
     * Adds an intersection to this mill
     * @param intersection the intersection added
     */
    public void addIntersection(Intersection intersection) {
        millIntersections.add(intersection);
        intersection.addMill(this);
    }

    /**
     * Checks if this mill 'formed' -> occupied by 3 tokens of the same team
     * @return whether the mill iss formed
     */
    public boolean millFormed() {
        return millIntersections.get(0).getBackground() == millIntersections.get(1).getBackground()
                && millIntersections.get(0).getBackground() == millIntersections.get(2).getBackground();
    }
}
