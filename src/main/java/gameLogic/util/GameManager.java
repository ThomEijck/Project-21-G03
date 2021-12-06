package gameLogic.util;

public class GameManager {
    private Board board;
    private static Dice dice;
    private static int diceValue;
    private static int currPlayer;
    private static int gameState = 0;// 0 = ongoing, 1= white win,2= black win, 3 = draw

    public GameManager() {
        currPlayer = 1;// white always starts
        board = new Board();
        dice = new Dice(board);
        diceValue = dice.getValue(currPlayer);
        GameManager.gameState = 0;
    }

    public static void main(String[] args) {
        GameManager g = new GameManager();

    }

    // call this function to advance the gamestate
    public boolean movePiece(Move move, boolean isAi) {
        if (gameState == 0)// only make moves while the game is ongoing
            return board.movePiece(move, diceValue, currPlayer, isAi);
        else if (gameState == 1) {
            System.out.println("White wins!!!");
        } else {
            System.out.println("Black wins!!!");
        }
        return true;
    }

    // logic that needs to be handled after a piece has been moved
    public static void pieceMoved() {
        // swap the player that can move a piece
        if (currPlayer == 1) {
            currPlayer = 2;
        } else {
            currPlayer = 1;
        }

        // get a new dice value
        diceValue = dice.getValue(currPlayer);
    }

    public Board getBoard() {
        return board;
    }

    public static int getDiceValue() {
        return diceValue;
    }

    public static int getCurrPlayer() {
        return currPlayer;
    }

    public static int getGameState() {
        return gameState;
    }

    public static void setGameState(int newState) {
        gameState = newState;
    }
}
