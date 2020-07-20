package io;

import java.util.Scanner;

/**
 * Manage input interactions through the keyboard.
 */
public class Keyboard {

    /**
     * Get user keyboard input.
     * @return a string containing given input.
     */
    public static String getInput() {
        Scanner scannerString = new Scanner(System.in);
        return scannerString.next();
    }

    /**
     *
     * @param message
     * @return
     */
    public static String getInput(String message) {
        Display.print(message);
        Scanner scannerString = new Scanner(System.in);
        return scannerString.next();
    }

}
