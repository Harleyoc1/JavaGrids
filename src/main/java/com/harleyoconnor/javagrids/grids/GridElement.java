package com.harleyoconnor.javagrids.grids;

import javafx.util.Pair;

import javax.annotation.Nullable;

/**
 * Default implementation of IGridElement.
 *
 * @author Harley O'Connor
 */
public class GridElement implements Cloneable {

    protected Grid grid;
    protected Pair<Integer, Integer> position;
    protected String displayText;

    /**
     * Initiates grid element with a display text. Use this for creating default grid elements, and note that default grid elements cannot take more than one parameter for their constructor.
     *
     * @param displayText The default text to display.
     */
    public GridElement (final String displayText) {
        this.displayText = displayText;
    }

    public GridElement(final Grid grid, final Pair<Integer, Integer> position, final String displayText) {
        this.grid = grid;
        this.position = position;
        this.displayText = displayText;
    }

    public Grid getParentGrid() {
        return this.grid;
    }

    public void setGrid(Grid grid) {
        if (this.grid == null)
            this.grid = grid;
    }

    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }

    public String getDisplayText() {
        return this.displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

}
