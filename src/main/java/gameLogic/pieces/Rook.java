package gameLogic.pieces;

import gameLogic.util.*;

public class Rook extends Piece {

	private static String name = "Rook";

	public Rook(Position pos, int player) {
		super(pos, player);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int getInt() {
		return 4;
	}

	public Position[] findMoves(Piece[][] board) {
		Position[] possibleMoves = new Position[14];
		for (int i = 0; i < possibleMoves.length; i++) {
			possibleMoves[i] = new Position(-1,-1);
		}
		int x = getPos().row;
		int y = getPos().column;
		int index = 0;
		//check above
		for (int i = y - 1; i >= 0; i--) {
			Piece target = board[x][i];
			if (target == null)
			{
				possibleMoves[index++] = new Position(x, i);
				continue;
			}
			if(target.getPlayer() != getPlayer())
			{
				possibleMoves[index++] = new Position(x, i);
			}
			break;
		}

		//check below
		for (int i = y + 1; i <= 7; i++) {
			Piece target = board[x][i];
			if (target == null)
			{
				possibleMoves[index++] = new Position(x, i);
				continue;
			}
			if(target.getPlayer() != getPlayer())
			{
				possibleMoves[index++] = new Position(x, i);
			}
			break;
		}

		//check left
		for (int i = x - 1; i >= 0; i--) {
			Piece target = board[i][y];
			if (target == null)
			{
				possibleMoves[index++] = new Position(i, y);
				continue;
			}
			if(target.getPlayer() != getPlayer())
			{
				possibleMoves[index++] = new Position(i, y);
			}
			break;
		}
		//check right
		for (int i = x + 1; i <= 7; i++) {
			Piece target = board[i][y];
			if (target == null)
			{
				possibleMoves[index++] = new Position(i, y);
				continue;
			}
			if(target.getPlayer() != getPlayer())
			{
				possibleMoves[index++] = new Position(i, y);
			}
			break;
		}

		return possibleMoves;
	}
}