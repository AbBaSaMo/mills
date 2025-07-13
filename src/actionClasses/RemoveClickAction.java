package actionClasses;

import gameClasses.Intersection;

import java.util.List;

/**
 * Class facilitates a removal click for computer players
 */
public class RemoveClickAction extends ClickAction {
    protected List<Intersection> opponentIntersections;

    /**
     * Instantiates the remove click action with the list of intersections
     * @param opponentIntersections all intersections occupied by an opponent piece
     */
    public RemoveClickAction(List<Intersection> opponentIntersections) {
        super();
        this.opponentIntersections = opponentIntersections;
    }

    /**
     * Responsible for selecting the intersection to remove a piece from
     * @return whether it executed properly
     */
    @Override
    public boolean execute() {
        // from opponentIntersections, select a random piece
        System.out.println("RemoveClickAction: Computer selecting a piece to remove, opponent ints="+opponentIntersections.size());
        Intersection selection = opponentIntersections.get(
                numberGenerator.nextInt(opponentIntersections.size())
        );
        selection.doClick();
        return true;
    }
}
