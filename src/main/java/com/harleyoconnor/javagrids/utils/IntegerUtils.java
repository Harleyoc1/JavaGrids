package com.harleyoconnor.javagrids.utils;

import com.harleyoconnor.javagrids.JavaGrids;

import java.util.Random;

/**
 * @author Harley O'Connor
 */
public final class IntegerUtils {

    /**
     * Gets a random number between the two parameters, inclusive.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A random number between the parameters.
     */
    public static int getRandomIntBetween (final int min, final int max) {
        return JavaGrids.INSTANCE.random.nextInt((max - min) + 1) + min;
    }

}
