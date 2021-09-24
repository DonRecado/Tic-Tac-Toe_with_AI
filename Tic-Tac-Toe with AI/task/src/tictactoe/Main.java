package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        GameController gc = new GameController(scanner);
        gc.init();
        scanner.close();

    }
}
