package gameLogic.util;

public class GameManager
{
    private Board board;
    private static Dice dice;
    private static int diceValue;
    private static int currPlayer;

    public GameManager()
    {
        currPlayer = 1;//white always starts
        board = new Board();
        dice = new Dice(board);
        diceValue = dice.getValue();
    }

    public static void main(String[] args) {
        GameManager g = new GameManager();
    }

    //call this function to advance the gamestate
    public void movePiece(Position start, Position end)
    {
        board.movePiece(new Position(1,0),new Position(2,0), diceValue, currPlayer);
    }

    //logic that needs to be handled after a piece has been moved
    public static void pieceMoved()
    {
        //get a new dice value
        diceValue = dice.getValue();

        //swap the player that can move a piece
        if(currPlayer == 1 )
        {
            currPlayer = 2;
        }else
        {
            currPlayer = 1;
        }
    }

    public static int getDiceValue()
    {
        return diceValue;
    }

    public static int getCurrPlayer()
    {
        return currPlayer;
    }
}
