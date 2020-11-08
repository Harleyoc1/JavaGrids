package com.harleyoconnor.javagrids.grids;

import com.harleyoconnor.javagrids.utils.ArrayUtils;
import com.harleyoconnor.javagrids.utils.InputUtils;
import javafx.util.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Harley O'Connor
 */
public class Grid {

    private final int rows;
    private final int columns;
    private final String defaultElement;

    private final List<List<String>> grid = new ArrayList<>();

    public Grid (final int rows, final int columns, final String defaultElement) {
        this.rows = rows;
        this.columns = columns;
        this.defaultElement = defaultElement;

        this.initiateGrid();
    }

    /**
     * Initiates grid with amount of rows and columns specified in the constructor and the default element.
     */
    protected void initiateGrid () {
        for (int i = 0; i < this.rows; i++) {
            this.grid.add(new ArrayList<>());

            for (int j = 0; j < this.columns; j++)
                this.grid.get(i).add(" " + this.defaultElement + " ");
        }
    }

    /**
     * Prints the grid in a readable form with numbers along the top and letters down the side.
     */
    public void printGrid () {
        System.out.println();
        System.out.print(" ");

        for (int i = 1; i <= this.columns; i++) {
            System.out.print((i < 10 ? " " : "") + (i) + " ");
        }

        System.out.println();

        Character currentLetter = 'A';

        for (List<String> list : this.grid) {
            System.out.print(currentLetter);
            list.forEach(System.out::print);
            System.out.println();

            currentLetter++;
        }
    }

    /**
     * Gets element indexes from a readable element ID string in the form of A1.
     *
     * @param elementId The ID of the element, for example, A1 for the first element.
     * @return A pair of integers, the key is the row index and the value is the column index, or null if the element ID was invalid.
     */
    @Nullable
    public Pair<Integer, Integer> getElement (final String elementId) {
        final StringBuilder rowBuilder = new StringBuilder();
        final StringBuilder columnBuilder = new StringBuilder();
        final AtomicBoolean intReached = new AtomicBoolean(false);
        final AtomicBoolean invalid = new AtomicBoolean(false);

        ArrayUtils.toCharList(elementId.toCharArray()).forEach(character -> {
            if (invalid.get()) return;

            try {
                Integer.parseInt(character.toString());
                intReached.set(true);
                columnBuilder.append(character);
            } catch (NumberFormatException e) {
                if (intReached.get()) invalid.set(true);

                rowBuilder.append(character);
            }
        });

        if (invalid.get() || rowBuilder.toString().length() < 1 || columnBuilder.toString().length() < 1) {
            this.invalidElement();
            return null;
        }

        final int row = rowBuilder.toString().hashCode() - 65;
        final int column = Integer.parseInt(columnBuilder.toString()) - 1;

        if (row >= this.rows || column >= this.grid.get(0).size()) {
            this.invalidElement();
            return null;
        }

        return new Pair<>(row, column);
    }

    /**
     * Changes an element in the grid from a readable element ID string in the form of A1.
     *
     * @param elementId The ID of the element, for example, A1 for the first one.
     * @param newValue The new value to put into the element.
     * @return Boolean value of whether or not the operation was successful.
     */
    public boolean changeElement (final String elementId, final String newValue) {
        final Pair<Integer, Integer> indexPair = getElement(elementId);

        if (indexPair == null) return false;

        this.grid.get(indexPair.getKey()).set(indexPair.getValue(), " " + newValue + " ");
        return true;
    }

    private void invalidElement () {
        System.out.println("The element you entered was invalid. Make sure it exists and is in the correct format.");
    }

}
