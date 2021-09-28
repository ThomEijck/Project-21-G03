package gameLogic.util;

public class GameManager
{
    private Board board;
    private Dice dice;


    public GameManager()
    {
        board = new Board();
        dice = new Dice(board);
        dice.getValue();
    }

    public static void main(String[] args) {
        GameManager g = new GameManager();
    }
}
