package chess;

import java.util.*;

public class Dice {

    Random random;
    Board board;

    public Dice(Board board) {
        random = new Random();
        this.board = board;
    }

    public int getValue() {
        // get valid nums
        int[] options = { 1, 2, 3, 4, 5, 6 };

        return options[random.nextInt(options.length)];
    }
}
