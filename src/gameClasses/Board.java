package gameClasses;

import playerClasses.ComputerPlayer;
import playerClasses.Player;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class represents the board, which contains the intersections and mills
 */
public class Board {
    private final List<List<Intersection>> allIntersections = new ArrayList<List<Intersection>>();
    private final List<Mill> allMills = new ArrayList<>();
    private final int displayWidth;
    private final int boardLength;

    /**
     * Instantiates board, using the displayHeight and displayWidth to determine sizing
     *
     * @param displayWidth Width of the display
     * @param displayHeight Height of the display
     */
    public Board(int displayWidth, int displayHeight) {
        this.displayWidth = displayWidth;
        this.boardLength = (int) (2.0/3.0 * (float)displayHeight);
    }

    /**
     * Registers the human player as watching intersections in the board
     *
     * @param newPlayer the new human player subscribing to the board
     */
    public void registerHumanPlayer(Player newPlayer) {
        // subscribe player to all intersections
        for(List<Intersection> boardSquare: allIntersections) {
            for(Intersection intersection: boardSquare) {
                intersection.subscribe(newPlayer);
            }
        }
    }

    /**
     * Registers a computer player as watching intersections in the board
     * @param newCompPlayer the new computer player subscribing to the board
     */
    public void registerComputerPlayer(ComputerPlayer newCompPlayer) {
        // subscribe player to all intersections
        List<Intersection> intersectionsCopy = new ArrayList<>();

        for(List<Intersection> boardSquare: allIntersections) {
            for(Intersection intersection: boardSquare) {
                intersection.subscribe(newCompPlayer);
                intersectionsCopy.add(intersection);
            }
        }

        newCompPlayer.addIntersections(intersectionsCopy);
    }

    /**
     * Creates required JComponents of the board
     *
     * @return List of created JComponents
     */
    public List<JPanel> createBoardComponents() {
        List<JPanel> boardComponents = new ArrayList<>();

        // create the JComponents of the board
        JPanel boardBackground = createBoardBackground();
        List<JPanel> boardSquares = createBoardSquares();
        List<JPanel> linesConnectingSquares = createLinesConnectingSquares(boardSquares.get(2));
        createIntersections(boardSquares);
        createMills();

        // Store in return arrayList in right order to display correctly when adding to a JComponent
        boardComponents.add(0, boardBackground);
        boardComponents.add(0, boardSquares.get(2));
        boardComponents.add(0, boardSquares.get(1));
        boardComponents.add(0, linesConnectingSquares.get(0));
        boardComponents.add(0, linesConnectingSquares.get(1));
        boardComponents.add(0, boardSquares.get(0));

        // Store the intersections last
        for(List<Intersection> square: allIntersections) {
            for(Intersection intersection: square) {
                boardComponents.add(0, intersection);
            }
        }

        return boardComponents;
    }

    /**
     * Creates the background of the board
     *
     * @return JComponent for background of board
     */
    private JPanel createBoardBackground() {
        JPanel board = new JPanel();
        board.setBounds(
                (int)((float) 0.05 * displayWidth),
                (int)((float) 0.1 * displayWidth),
                boardLength,
                boardLength
        );
        board.setBackground(Color.WHITE);
        board.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));

        return board;
    }

    /**
     * Creates the lines of the board that represent the 3 square rings
     *
     * @return ArrayList containing the created square JPanels
     */
    private List<JPanel> createBoardSquares() {
        // stores the JComponents representing the outer. mid, inner squares on the board
        List<JPanel> boardSquares = new ArrayList<>();

        // creates the bordered JPanels representing the outer, mid, inner squares on the board
        for(int sizeScale=1, posScale=3; sizeScale<=3; sizeScale++, posScale--) {
            JPanel square = new JPanel();
            int xPos = (int)((float) 0.05 * displayWidth + posScale * 0.125 * boardLength);
            int yPos = (int)((float) 0.1 * displayWidth + posScale * 0.125 * boardLength);
            int lenDim = (int) (sizeScale * 0.25 * boardLength);

            // set the size and position of the square based on the board dimensions
            square.setBounds(xPos, yPos, lenDim, lenDim);
            square.setBackground(Color.WHITE);
            square.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            boardSquares.add(square);
        }

        return boardSquares;
    }

    /**
     * Creates the lines of the board that connect the 3 rings
     *
     * @return ArrayList containing the created line JPanels
     */
    private List<JPanel> createLinesConnectingSquares(JPanel boardOuterSquare) {
        // create the horizontal line for the board game
        JPanel horLine = new JPanel();
        horLine.setBounds(
                boardOuterSquare.getX(),
                (int) (boardOuterSquare.getY() + (float) boardOuterSquare.getHeight()/2),
                boardOuterSquare.getWidth(),
                2
        );
        horLine.setBackground(Color.BLACK);

        // create the vertical line for the board game
        JPanel vertLine = new JPanel();
        vertLine.setBounds(
                (int) (boardOuterSquare.getX() + (float) boardOuterSquare.getWidth()/2),
                boardOuterSquare.getY(),
                2,
                boardOuterSquare.getHeight()
        );
        vertLine.setBackground(Color.BLACK);

        // store lines in an array for return
        List<JPanel> crossSquareLines = new ArrayList<>();
        crossSquareLines.add(horLine);
        crossSquareLines.add(vertLine);
        return crossSquareLines;
    }

    /**
     * Creates the intersections on the board, linking all intersections
     */
    private void createIntersections(List<JPanel> boardSquares) {
        /* We create an array of 3 arrays
            / Each array contains the intersections for a square: 1 per outer, middle and inner square
            / Indices in these arrays represent intersections in a square as depicted below
            /       0   1   2
            /       7       3
            /       6   5   4
            / Then we loop through the arrays and link intersections that are adjacent
            /
        */
        int len = 20;
        Color black = Color.BLACK;

        // creates all the intersections for all 3 squares
        for(int x=0; x<=2; x++) {
            int xCoord = boardSquares.get(x).getX();
            int yCoord = boardSquares.get(x).getY();
            int squareLength = boardSquares.get(x).getWidth();
            int midOffset = (int)((double)squareLength/2);

            // ArrayList for all intersections for this square being built
            List<Intersection> squareIntersections = new ArrayList<>();

            // top-left, top-middle, top-right intersections of this square
            squareIntersections.add(new Intersection(xCoord, yCoord, len, black));
            squareIntersections.add(new Intersection(xCoord + midOffset, yCoord, len, black));
            squareIntersections.add(new Intersection(xCoord + squareLength, yCoord, len, black));

            // middle-right intersection of this square
            squareIntersections.add(new Intersection(xCoord + squareLength, yCoord + midOffset, len, black));

            // bottom-right, bottom-middle, bottom-left intersections of this square
            squareIntersections.add(new Intersection(xCoord + squareLength, yCoord + squareLength, len, black));
            squareIntersections.add(new Intersection(xCoord + midOffset, yCoord + squareLength, len, black));
            squareIntersections.add(new Intersection(xCoord, yCoord + squareLength, len, black));

            // middle-left intersection of this square
            squareIntersections.add(new Intersection(xCoord, yCoord + midOffset, len, black));

            allIntersections.add(squareIntersections);
        }

        // link adjacent intersections in a square
        for(int x=0; x<=2; x++) {
            // y == 7
            allIntersections.get(x).get(7).linkIntersection(allIntersections.get(x).get(6));
            allIntersections.get(x).get(7).linkIntersection(allIntersections.get(x).get(0));
            // y == 0
            allIntersections.get(x).get(0).linkIntersection(allIntersections.get(x).get(7));
            allIntersections.get(x).get(0).linkIntersection(allIntersections.get(x).get(1));

            for(int y=6; y>=1; y--) {
                allIntersections.get(x).get(y).linkIntersection(allIntersections.get(x).get(y-1));
                allIntersections.get(x).get(y).linkIntersection(allIntersections.get(x).get(y+1));
            }
        }

        // link adjacent intersections across squares
        for(int y=1; y<=7; y+=2) {
            allIntersections.get(0).get(y).linkIntersection(allIntersections.get(1).get(y));
            allIntersections.get(1).get(y).linkIntersection(allIntersections.get(0).get(y));
            allIntersections.get(1).get(y).linkIntersection(allIntersections.get(2).get(y));
            allIntersections.get(2).get(y).linkIntersection(allIntersections.get(1).get(y));
        }
    }

    /**
     * Creates all required mills and assigns the associated intersections
     */
    private void createMills() {
        /* We have an array of 3 arrays
            / Each array contains the intersections for a square: 1 per outer, middle and inner square
            / Indices in these arrays represent intersections in a square as depicted below
            /       0   1   2
            /       7       3
            /       6   5   4
            /
        */

        // form mills that cross between the 3 squares
        for(int i=1; i<=7; i+=2) {
            Mill mill = new Mill();
            mill.addIntersection(allIntersections.get(0).get(i));
            mill.addIntersection(allIntersections.get(1).get(i));
            mill.addIntersection(allIntersections.get(2).get(i));
            allMills.add(mill);
        }

        // form mills inside the squares
        for(List<Intersection> square: allIntersections) {
            for(int i=0; i<square.size(); i+=2) {
                Mill mill = new Mill();
                mill.addIntersection(square.get(i));
                mill.addIntersection(square.get(i+1));
                mill.addIntersection(square.get((i+2)% square.size()));
                allMills.add(mill);
            }
        }
    }
}
