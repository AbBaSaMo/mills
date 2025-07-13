import gameClasses.Game;

/**
 * Class acts as a driver to start the game
 */
public class Driver {
    public static void main(String[] args) {
        System.out.println("Starting up!");
        Game newGame = new Game("9 Man's Morris", 1000, 750);
        newGame.startMenu();
    }
}