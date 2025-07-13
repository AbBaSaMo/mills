package gameClasses;

import interfaces.Subscriber;
import playerClasses.HumanPlayer;
import playerClasses.Player;

import java.awt.*;

/**
 * Class contains the game being played
 */
public class Game implements Subscriber {
    private final String gameTitle;
    private final int displayWidth;
    private final int displayHeight;

    private Player playerOne;
    private Player playerTwo;
    private GameDisplayBuilder gameDisplayBuilder;
    private MenuDisplayBuilder menuDisplayBuilder;

    /**
     * Instantiates a game by setting key GUI parameters and the game title.
     *
     * @param gameTitle The title of the game
     * @param displayWidth The width of the display window
     * @param displayHeight The height of the display window
     */
    public Game(String gameTitle, int displayWidth, int displayHeight) {
        this.gameTitle = gameTitle;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
    }

    public void startMenu() {
        menuDisplayBuilder = new MenuDisplayBuilder(gameTitle, displayWidth, displayHeight);
        menuDisplayBuilder.buildDisplay();
        menuDisplayBuilder.subscribe(this);
    }

    /**
     * Sets up the display and player classes and begins the turn cycle
     */
    public void startGame(Player playerTwo) {
        // init entities
        Board gameBoard = new Board(displayWidth, displayHeight);
        gameDisplayBuilder = new GameDisplayBuilder(gameTitle, displayWidth, displayHeight);
        gameDisplayBuilder.buildDisplay(gameBoard.createBoardComponents());
        playerOne = new HumanPlayer(Color.RED, 1);
        this.playerTwo = playerTwo;

        // playerOne setup
        playerOne.subscribe(this);
        playerOne.subscribe(playerTwo);
        playerOne.subscribe(gameDisplayBuilder);
        playerOne.register(gameBoard);
        // playerTwo setup
        playerTwo.subscribe(this);
        playerTwo.subscribe(playerOne);
        playerTwo.subscribe(gameDisplayBuilder);
        playerTwo.register(gameBoard);

        playerOne.play();
    }

    /**
     * Reset the game, in order to play a new game
     */
    @Override
    public void update() {
        gameDisplayBuilder.reset();
        gameDisplayBuilder = null;
        playerOne = null;
        playerTwo = null;
        startMenu();
    }

    /**
     * Method used by game to update whose turn it is
     * @param player the losing player
     */
    @Override
    public void update(Player player) {
        // if playerTwo has been set, determine the next turn
        if (playerTwo != null) {
            // determine the player that is updating us
            Player thisTurnPlayer = player == playerOne ? playerTwo : playerOne;
            // play the other players turn and let them know what phase they are in
            thisTurnPlayer.play();
        }
        // else set playerTwo and start the game
        else {
            menuDisplayBuilder.reset();
            menuDisplayBuilder = null;
            startGame(player);
        }
    }

    /**
     * Method used by players to get game to update the turn indicator
     * @param color the color the turn indicator must turn to
     */
    @Override
    public void update(Color color) {
        gameDisplayBuilder.updateTurnInfoDisplay(color);
    }

    /**
     * Subscriber interface method
     * @param playerNum The number of the player calling this method
     */
    @Override
    public void update(int playerNum) {

    }

    /**
     * Subscriber interface method
     * @param intersection The intersection calling this method
     */
    @Override
    public boolean update(Intersection intersection) { return false; }
}
