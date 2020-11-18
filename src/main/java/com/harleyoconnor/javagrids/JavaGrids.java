package com.harleyoconnor.javagrids;

import com.harleyoconnor.javagrids.games.GuessingGame;
import com.harleyoconnor.javagrids.grids.Grid;
import com.harleyoconnor.javautilities.InputUtils;

/**
 * @author Harley O'Connor
 */
public final class JavaGrids {

    public static final JavaGrids INSTANCE = new JavaGrids();

    private JavaGrids() {

    }

    public static void main (final String[] args) {
        INSTANCE.startGuessingGame();
    }

    private void createGrid () {
        final Grid grid = new Grid(5, 5, "_");
        grid.printGrid();

        grid.changeElement("A4", "+");
        grid.changeElement("D1", "]");

        grid.printGrid();
    }

    private void startGuessingGame () {
        boolean playAgain;

        do {
            final GuessingGame guessingGame = new GuessingGame(InputUtils.getIntInput("What difficulty level would you like (levels of below 5 are not recommended)? "), InputUtils.getIntInput("How many guesses would you like? "));
            guessingGame.gameLoop();

            playAgain = InputUtils.getInput("Do you want to play again? (y/n) ", true).equals("y");
        } while (playAgain);
    }

}
