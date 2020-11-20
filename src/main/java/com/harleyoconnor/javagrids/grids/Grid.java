package com.harleyoconnor.javagrids.grids;

import com.harleyoconnor.javautilities.ArrayUtils;
import javafx.util.Pair;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Harley O'Connor
 */
public class Grid {

    private final int rows;
    private final int columns;
    private final GridElement defaultElement;

    private final List<List<GridElement>> grid = new ArrayList<>();

    public Grid (final int rows, final int columns, final GridElement defaultElement) {
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

            for (int j = 0; j < this.columns; j++) {
                GridElement gridElement = null;

                try {
                    // Instantiate the grid element with a string.

                    final Constructor<?> elementConstructor = this.defaultElement.getClass().getDeclaredConstructor(this.defaultElement.getDisplayText().getClass());
                    elementConstructor.setAccessible(true);
                    gridElement = (GridElement) elementConstructor.newInstance(this.defaultElement.getDisplayText());
                } catch (Exception e) {
                    System.err.println("Fatal error creating grid. This has likely arisen from incorrect usage of JavaGrids.");
                    e.printStackTrace();
                }

                if (gridElement == null) return;

                gridElement.setGrid(this);
                gridElement.setPosition(new Pair<>(i, j));

                this.grid.get(i).add(gridElement);
            }
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

        for (List<GridElement> list : this.grid) {
            System.out.print(currentLetter);
            list.forEach(gridElement -> System.out.print(gridElement.getDisplayText()));
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
    public Pair<Integer, Integer> getElementPosition (final String elementId) {
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
     * Gets the string value of an element from a readable element ID string in the form of A1.
     *
     * @param elementId The ID of the element, for example, A1 for the first one.
     * @return The grid element or null if the element ID was invalid.
     */
    @Nullable
    public GridElement getElementAt (final String elementId) {
        final Pair<Integer, Integer> elementPos = this.getElementPosition(elementId);
        return (elementPos == null) ? null : getElementAt(elementPos);
    }

    /**
     * Grabs grid element from position.
     *
     * @param elementPos The position of the element.
     * @return The grid element at that position, or null if it didn't exist.
     */
    @Nullable
    public GridElement getElementAt (final Pair<Integer, Integer> elementPos) {
        return (elementPos.getKey() >= this.rows || elementPos.getValue() >= this.columns) ? null : this.grid.get(elementPos.getKey()).get(elementPos.getValue());
    }

    /**
     * Changes an element in the grid from a readable element ID string in the form of A1.
     *
     * @param elementId The ID of the element, for example, A1 for the first one.
     * @param newElement The new value to put into the element.
     * @return Boolean value of whether or not the operation was successful.
     */
    public boolean changeElement (final String elementId, final GridElement newElement) {
        final Pair<Integer, Integer> indexPair = getElementPosition(elementId);

        if (indexPair == null) return false;

        this.changeElement(indexPair, newElement);
        return true;
    }


    /**
     * Changes an element in the grid from its position.
     *
     * @param position The position of the element, with the row index being the key and the column being the value.
     * @param newElement The new grid element to set.
     */
    public void changeElement (final Pair<Integer, Integer> position, final GridElement newElement) {
        this.grid.get(position.getKey()).set(position.getValue(), newElement);
    }

    /**
     * @return All grid elements.
     */
    public List<GridElement> getGridElements () {
        final List<GridElement> gridElements = new ArrayList<>();

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++)
                gridElements.add(this.getElementAt(new Pair<>(i, j)));
        }

        return gridElements;
    }

    private void invalidElement () {
        System.out.println("The element you entered was invalid. Make sure it exists and is in the correct format.");
    }

    public List<List<GridElement>> getGrid() {
        return this.grid;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

}
