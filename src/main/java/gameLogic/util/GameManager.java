package gameLogic.util;

public class GameManager
{
    private Board board;
    private static Dice dice;
    private static int diceValue;
    private static int currPlayer;
    private static int gameState = 0;//0 = ongoing, 1= white win,2= black win, 3 = draw
    public GameManager()
    {
        currPlayer = 1;//white always starts
        board = new Board();
        dice = new Dice(board);
        diceValue = dice.getValue(currPlayer);
    }

    public static void main(String[] args)
    {
        GameManager g = new GameManager();
        
    }

    //call this function to advance the gamestate
    public void movePiece(Move move)
    {
        if(gameState == 0)//only make moves while the game is ongoing
            board.movePiece(move, diceValue, currPlayer,false);
    }

    //logic that needs to be handled after a piece has been moved
    public static void pieceMoved()
    {
        //get a new dice value
        diceValue = dice.getValue(currPlayer);

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

    public static int getGameState(){return gameState;}

    public static void setGameState(int newState){gameState = newState;}
}
