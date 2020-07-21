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
     * Get user keyboard input as String
     * @param message
     * @return
     */
    public static String getInput(String message) {
        Display.print(message);
        Scanner scannerString = new Scanner(System.in);
        return scannerString.next();
    }

    /**
     * Get user keyboard input as int
     * @param message message displayed for user before input
     * @return
     */
    public static int getIntInput(String message) {
        Display.print(message);
        Scanner scannerString = new Scanner(System.in);
        return scannerString.nextInt();
    }

}
