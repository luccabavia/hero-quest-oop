package io;

import java.util.Scanner;

public class Keyboard {

    public static String getInput() {
        System.out.print("Next movement direction (using w, a, s, d keys): ");
        Scanner scannerString = new Scanner(System.in);
        return scannerString.next();
    }

    public static int getInt() {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        return num;
    }

}
