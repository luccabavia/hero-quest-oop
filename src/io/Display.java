package io;

/**
 * Manage output to display operations
 */
public class Display {

    /**
     * Prints strings to standard output.
     * @param string String
     */
    public static void print(String string) {
        System.out.println(string);
    }

    public static void printWarning(String string) {
        System.out.println(String.format("\u001b[33m%s\u001b[0m", string));
    }

}
