package tictactoe;

import java.util.Scanner;

public class GameController {
    private final Scanner scanner;
    private PlayField playField;

    public GameController(Scanner scanner) {
        this.scanner = scanner;

    }


    public void init() {

        Player[] selection = printMenu();
        if (selection != null) {
            Player currentPlayer = selection[0];
            this.playField = new PlayField();
            System.out.println(playField);

            while (true) {
                boolean flag = false;
                if (currentPlayer.getClass().equals(AiPlayer.class)) {
                    AiPlayer current = (AiPlayer) currentPlayer;
                    int[] coordinates = null;

                    switch (current.getLevel()) {
                        case EASY:
                            coordinates = current.makeMove(playField.getEmptyCoordinates());
                            break;
                        case MEDIUM:
                            coordinates = current.makeMove(playField.getPlayField(),playField.getEmptyCoordinates(),AiLevels.MEDIUM);
                            break;
                        case HARD:
                            coordinates = current.makeMove(playField.getPlayField(),playField.getEmptyCoordinates(),AiLevels.HARD);
                            break;
                    }


                    if (coordinates != null) {
                        if (playField.addMoveToPlayfield(coordinates, current.getSymbol())) {
                            System.out.println("Making move level \"" +current.getLevel().toString().toLowerCase() +"\"");
                            System.out.println(playField);

                            if (playField.gameIsFinished()) {
                                break;
                            } else {
                                flag = true;
                            }
                        }
                    }
                } else {
//                    player is user
                    System.out.print("Enter the coordinates:");
                    String input = scanner.nextLine();

                    if (input.trim().length() == 3) {
                        String[] inputArr = input.split(" ");
                        try {
                            int first = Integer.parseInt(inputArr[0]);
                            int second = Integer.parseInt(inputArr[1]);

                            int[] move = currentPlayer.makeMove(first, second);
                            try {
                                if (playField.addMoveToPlayfield(move, currentPlayer.getSymbol())) {
                                    System.out.println(playField);
                                    if (playField.gameIsFinished()) {
                                        break;
                                    } else {
                                        flag = true;
                                    }
                                } else {
                                    System.out.println("This cell is occupied! Choose another one!");
                                }

                            } catch (NullPointerException e) {
                                System.out.println("Coordinates should be from 1 to 3!");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("You should enter numbers!");
                        }
                    } else {
                        System.out.println("You should enter numbers!");
                    }
                }

                if (flag) {
                    currentPlayer = currentPlayer.getSymbol() == 'X' ? selection[1] : selection[0];
                }

            }
            System.out.println(playField.getGameStatus());
        }
    }

    private Player[] printMenu() {
        while (true) {
            System.out.print("Input command: ");
            String input = scanner.nextLine();
            String[] inputArr = input.trim().toLowerCase().split(" ");

            if (inputArr.length > 3 || inputArr.length <= 0 || inputArr.length == 2) {
                System.out.println("Bad parameters!");
            } else if (inputArr.length == 3 && inputArr[0].equals("start")) {
                Player[] returnParameter = new Player[2];
                switch (inputArr[1]) {
                    case "user":
                        returnParameter[0] = new Player('X');
                        break;
                    case "easy":
                        returnParameter[0] = new AiPlayer('X',AiLevels.EASY);
                        break;
                    case "medium":
                        returnParameter[0] = new AiPlayer('X',AiLevels.MEDIUM);
                        break;
                    case "hard":
                        returnParameter[0] = new AiPlayer('X',AiLevels.HARD);
                        break;
                    default:
                        System.out.println("Bad parameters!");
                        return null;
                }

                switch (inputArr[2]) {
                    case "user":
                        returnParameter[1] = new Player('O');
                        break;
                    case "easy":
                        returnParameter[1] = new AiPlayer('O',AiLevels.EASY);
                        break;
                    case "medium":
                        returnParameter[1] = new AiPlayer('O',AiLevels.MEDIUM);
                        break;
                    case "hard":
                        returnParameter[1] = new AiPlayer('O',AiLevels.HARD);
                        break;
                    default:
                        System.out.println("Bad parameters!");
                        return null;
                }

                return returnParameter;


            } else {
                if (inputArr[0].equals("exit")) {
                    return null;
                } else {
                    System.out.println("Bad parameters!");
                }
            }


        }
    }


}
