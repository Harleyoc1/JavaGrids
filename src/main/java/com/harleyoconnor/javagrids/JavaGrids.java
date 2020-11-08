package com.harleyoconnor.javagrids;

import com.harleyoconnor.javagrids.grids.Grid;
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
public final class JavaGrids {

    private static final JavaGrids INSTANCE = new JavaGrids();

    private JavaGrids() {

    }

    public static void main (final String[] args) {
        INSTANCE.createGrid();
    }

    private void createGrid () {
        final Grid grid = new Grid(5, 5, "_");
        grid.printGrid();

        grid.changeElement("A4", "+");
        grid.changeElement("D1", "]");

        grid.printGrid();
    }

}
