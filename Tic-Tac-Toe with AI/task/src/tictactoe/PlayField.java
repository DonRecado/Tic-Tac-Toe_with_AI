package tictactoe;

import java.util.ArrayList;
import java.util.List;

public class PlayField {
    private char[][] playField;
    private char currentPlayer;

    public PlayField() {
        setPlayField();
    }

    public char[][] getPlayField() {
        return playField.clone();
    }

    public boolean addMoveToPlayfield(int[] moveArray,char symbol) {
        if (playField[moveArray[0]][moveArray[1]] == ' ') {
            this.playField[moveArray[0]][moveArray[1]] = symbol;
            return true;
        }

        return false;
    }

    public String getGameStatus() {
        int xTargetSum = 264;
        int oTargetSum = 237;
        boolean hasEmptyFields = false;

        int counter = 0;

        for (int i = 0; i < playField.length; i++) {
            int hRowSum = 0;
            int vRowSum = 0;
            int d1Sum = 0;
            int d2Sum = 0;

            for (int j = 0; j < playField[i].length; j++) {
                hRowSum += playField[i][j];
                vRowSum += playField[j][i];
                d1Sum += playField[j][j];
                d2Sum += playField[j][2 - j];

                if (playField[i][j] == ' ') {
                    hasEmptyFields = true;
                }
                counter++;
            }

            if (hRowSum == xTargetSum || vRowSum == xTargetSum || d1Sum == xTargetSum || d2Sum == xTargetSum) {
                return "X wins";
            }

            if (hRowSum == oTargetSum || vRowSum == oTargetSum || d1Sum == oTargetSum || d2Sum == oTargetSum) {
                return "O wins";
            }

            if (counter == 9 && !hasEmptyFields) {
                return "Draw";
            }
        }

        return "Game not finished";


    }

    public List<Integer> getEmptyCoordinates() {
        List<Integer> emptyFields = new ArrayList<>();
        int counter = 0;
        for(int i= 0; i < this.playField.length; i++) {
            for(int j = 0; j < this.playField[i].length; j++) {
                if(this.playField[i][j] == ' ') {
                    emptyFields.add(counter);
                }
                counter++;
            }
        }
        return emptyFields;
    }

    public boolean gameIsFinished() {
        if(!this.getGameStatus().equals("Game not finished")) {
            return true;
        }
        return false;
    }

    private void setPlayField() {
        char[][] field = new char[3][3];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = ' ';
            }
        }
        this.playField = field;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------\n");
        for (int i = 0; i < playField.length; i++) {
            sb.append("| ");
            for (int j = 0; j < playField[i].length; j++) {
                sb.append(playField[i][j] + " ");
            }
            sb.append("|\n");
        }


        sb.append("---------");
        return sb.toString();
    }
}
