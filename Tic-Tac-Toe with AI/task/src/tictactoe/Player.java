package tictactoe;

public class Player {
    private final char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public int[] makeMove(int first, int second) {
        first--;
        second--;


        if (first < 3 && first >= 0 && second < 3 && second >= 0) {
            return new int[]{first,second};
        }
        return null;
    }

}
