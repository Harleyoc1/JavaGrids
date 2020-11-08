package com.harleyoconnor.javagrids.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Harley O'Connor
 */
public final class InputUtils {

    private static final Scanner in = new Scanner(System.in);

    /**
     * Gets a string input. Loops until a valid string is input according to the parameters given.
     *
     * @param prompt The message sent to the user informing them of what to enter.
     * @param requireNotEmpty A boolean value stating whether or not the string must be set a value.
     * @return The entered string.
     */
    public static String getInput (final String prompt, final boolean requireNotEmpty) {
        String strInput;

        while (true) {
            System.out.print(prompt + " ");
            strInput = in.next();

            if (!strInput.equals("") || !requireNotEmpty) break;

            invalidInput("string");
        }

        return strInput;
    }

    /**
     * Gets an integer input. Loops until a valid integer is input according to the parameters given.
     *
     * @param prompt The message sent to the user informing them of what to enter
     * @param requirePositive A boolean value stating whether or not to allow negative values.
     * @param requireNotZero A boolean value stating whether or not to allow zero.
     * @return The integer input.
     */
    public static int getIntInput (final String prompt, final boolean requirePositive, final boolean requireNotZero) {
        int intInput = 0;

        while (true) {
            System.out.print(prompt + " ");
            boolean inputMismatch = false;

            try {
                intInput = in.nextInt();
            } catch (InputMismatchException e) {
                inputMismatch = true;
            }

            if (inputMismatch) {
                invalidInput("integer");
                continue;
            }

            if (intInput < 0 && requirePositive) {
                invalidInput("positive integer");
                continue;
            }

            if (intInput == 0 && requireNotZero) {
                invalidInput("non-zero integer");
                continue;
            }

            break;
        }

        return intInput;
    }

    /**
     * Tells the user that what they entered was invalid and prepares the input scanner for new input.
     *
     * @param invalidValue The name of the invalid type.
     */
    private static void invalidInput (final String invalidValue) {
        System.out.println("You must enter a valid " + invalidValue + ".");
        in.next();
    }

}
