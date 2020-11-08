package com.harleyoconnor.javagrids.games;

import com.harleyoconnor.javagrids.grids.Grid;
import com.harleyoconnor.javagrids.utils.InputUtils;
import com.harleyoconnor.javagrids.utils.IntegerUtils;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple guessing game, where you guess on which squares contain points.
 *
 * @author Harley O'Connor
 */
public final class GuessingGame {

    /**
     * This is the game grid that will be visible to the player. It will show which positions they have currently found.
     */
    private final Grid gameGrid;
    /**
     * The points map is a map of which positions contain points and how many points they contain. Both are selected at random.
     */
    private final Map<Pair<Integer, Integer>, Integer> pointsMap = new HashMap<>();

    /**
     * The amount of guesses the user gets before the game is over.
     */
    private final int guesses;
    /**
     * The difficulty of the game. The higher the number, the more difficult the game is. Corresponds to the number of rows and columns,
     * among being used for other things like a score multiplier.
     */
    private final int difficulty;

    /**
     * The player's current point count.
     */
    private int points = 0;

    public GuessingGame (final int difficulty, final int guesses) {
        this.gameGrid = new Grid(difficulty, difficulty, "?");
        this.guesses = guesses;
        this.difficulty = difficulty;

        this.populatePointsMap();
    }

    /**
     * Populates the points map with a number of points based on the difficulty at random positions with random amounts.
     */
    private void populatePointsMap () {
        int pointsToGenerate = (int) (Math.pow(this.difficulty, 2) / Math.pow(2, this.difficulty / 5.0));

        if (pointsToGenerate == 0) pointsToGenerate = 1;

        for (int i = 0; i < pointsToGenerate; i++) {
            int pointsAtPosition = (int) ((this.difficulty / 2.0) * IntegerUtils.getRandomIntBetween(8, 12));

            if (pointsAtPosition == 0) pointsAtPosition = 1;

            Pair<Integer, Integer> position;

            do {
                position = new Pair<>(IntegerUtils.getRandomIntBetween(0, this.difficulty - 1), IntegerUtils.getRandomIntBetween(0, this.difficulty - 1));
            } while (this.pointsMap.containsKey(position));

            System.out.println(position.toString());

            this.pointsMap.put(position, pointsAtPosition);
        }
    }

    /**
     * Main game loop, call to start a game, it will loop through until the game is over.
     */
    public void gameLoop () {
        System.out.println("Welcome to the guessing game, you will be given " + this.guesses + " guesses to locate as many points as you can.");

        for (int i = 0; i < this.guesses; i++) {
            this.gameGrid.printGrid();

            String guessPositionString;
            Pair<Integer, Integer> guessPosition;

            do {
                guessPositionString = InputUtils.getInput("\nGuess a position (for example, A1 would be the first position). ", true).toUpperCase();
                guessPosition = this.gameGrid.getElementPosition(guessPositionString);
            } while (guessPosition == null);

            if (!this.pointsMap.containsKey(guessPosition)) {
                System.out.println("No points found at this position.");
                this.gameGrid.changeElement(guessPosition, " _ ");
                continue;
            }

            final Integer points = this.pointsMap.get(guessPosition);
            this.points += points;

            System.out.println("You found " + points + " " + this.getPointsString(points) + " at this position, making your total " + this.points + " " + this.getPointsString(this.points) + ".\n");

            this.gameGrid.changeElement(guessPosition, (points.toString().length() > 2 ? "" : " ") + points.toString() + (points.toString().length() > 1 ? "" : " "));
        }

        this.gameGrid.printGrid();

        System.out.println("\nGame over. You earned " + this.points + " " + this.getPointsString(this.points) + " in total.");
    }

    private String getPointsString (final int points) {
        return points == 1 ? "point" : "points";
    }

}
