package tictactoe;

import java.util.List;
import java.util.Random;

public class AiPlayer extends Player {

    private final AiLevels level;

    public AiPlayer(char symbol, AiLevels level) {
        super(symbol);
        this.level = level;
    }

    public AiLevels getLevel() {
        return level;
    }

    public int[] makeMove(List<Integer> emptyFields) {
        if (emptyFields.size() > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(emptyFields.size());

            int number = emptyFields.get(randomIndex);
            int first = number < 3 ? 0 : number > 5 ? 2 : 1;
            int second = first == 0 ? number : first == 1 ? number - 3 : number - 6;
            return new int[]{first, second};
        }
        return null;
    }

    public int[] makeMove(char[][] field, List<Integer> emptyFields, AiLevels level) {
        int aiTarget = getSymbol() == 'X' ? 264 : 237;
        int playerTarget = aiTarget == 264 ? 237 : 264;
        int[] aiAlmostWon;
        int[] playerAlmostWon;

        if (level.equals(AiLevels.MEDIUM)) {
            aiAlmostWon = almostWin(field, aiTarget);

            if (aiAlmostWon != null) {
                return aiAlmostWon;
            }

            playerAlmostWon = almostWin(field, playerTarget);

            if (playerAlmostWon != null) {
                return playerAlmostWon;
            }
            return makeMove(emptyFields);
        } else if (level.equals(AiLevels.HARD)) {
            if (emptyFields.size() > 0) {
                if (emptyFields.size() == 9 || (emptyFields.size() == 8 && field[1][1] == ' ')) {
                    return new int[]{1, 1};
                } else {
                    return findBestMove(field);
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private int[] almostWin(char[][] field, int target) {

        target = (target / 3) * 2;

        for (int i = 0; i < field.length; i++) {
            int hRowSum = 0;
            int vRowSum = 0;
            int d1Sum = 0;
            int d2Sum = 0;

            for (int j = 0; j < field[i].length; j++) {
                hRowSum += field[i][j] == 'X' || field[i][j] == 'O' ? field[i][j] : 0;
                vRowSum += field[j][i] == 'X' || field[j][i] == 'O' ? field[j][i] : 0;
                d1Sum += field[j][j] == 'X' || field[j][j] == 'O' ? field[j][j] : 0;
                ;
                d2Sum += field[j][2 - j] == 'X' || field[j][2 - j] == 'O' ? field[j][2 - j] : 0;
            }

            if (hRowSum == target || vRowSum == target || d1Sum == target || d2Sum == target) {
                for (int k = 0; k < field[i].length; k++) {
                    if (field[i][k] == ' ' && hRowSum == target) {
                        return new int[]{i, k};
                    }
                    if (field[k][i] == ' ' && vRowSum == target) {
                        return new int[]{k, i};
                    }
                    if (field[k][k] == ' ' && d1Sum == target) {
                        return new int[]{k, k};
                    }
                    if (field[k][2 - k] == ' ' && d2Sum == target) {
                        return new int[]{k, 2 - k};
                    }
                }
            }
        }
        return null;
    }

    private boolean movesLeft(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    private int[] findBestMove(char[][] field) {
        int bestValue = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                // Check if cell is empty
                if (field[i][j] == ' ') {
                    // Make the move
                    field[i][j] = this.getSymbol();

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(field, 0, false);

                    field[i][j] = ' ';

                    if (moveVal > bestValue) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestValue = moveVal;
                    }

                }
            }
        }
        return bestMove;
    }

    private int evaluate(char field[][]) {

        char player = this.getSymbol();
        char opponent = this.getSymbol() == 'X' ? 'O' : 'X';
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++) {
            if (field[row][0] == field[row][1] &&
                    field[row][1] == field[row][2]) {
                if (field[row][0] == player)
                    return +10;
                else if (field[row][0] == opponent)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++) {
            if (field[0][col] == field[1][col] &&
                    field[1][col] == field[2][col]) {
                if (field[0][col] == player)
                    return +10;

                else if (field[0][col] == opponent)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (field[0][0] == field[1][1] && field[1][1] == field[2][2]) {
            if (field[0][0] == player)
                return +10;
            else if (field[0][0] == opponent)
                return -10;
        }

        if (field[0][2] == field[1][1] && field[1][1] == field[2][0]) {
            if (field[0][2] == player)
                return +10;
            else if (field[0][2] == opponent)
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    private int minimax(char[][] field, int depth, boolean isCurrentPlayer) {

        int score = evaluate(field);
        int best;

        if (Math.abs(score) == 10) {
            return score;
        } else if (!movesLeft(field)) {
            return 0;
        } else {


            if (isCurrentPlayer) {
                best = Integer.MIN_VALUE;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (field[i][j] == ' ') {
                            field[i][j] = this.getSymbol();
                            best = Math.max(best, minimax(field,
                                    depth + 1, false));
                            field[i][j] = ' ';
                        }
                    }

                }
            } else {
                best = Integer.MAX_VALUE;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (field[i][j] == ' ') {
                            field[i][j] = this.getSymbol() == 'X' ? 'O' : 'X';
                            best = Math.min(best, minimax(field,
                                    depth + 1, true));
                            field[i][j] = ' ';
                        }
                    }

                }
            }
        }
        return best;
    }
}
