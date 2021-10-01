package chess;

import gameLogic.util.Position;
import renderEngine.Loader;
import textures.ModelTexture;

public class Board {

    private Square[][] squares = new Square[8][8];
    Loader loader = new Loader();

    public Board(ModelTexture texture) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                float[] vertices = { (j + 1) / 5.0f - 1, (i + 1) / 4.0f - 1, 0, (j + 1) / 5.0f - 1, i / 4.0f - 1, 0,
                        j / 5.0f - 1, i / 4.0f - 1, 0, j / 5.0f - 1, (i + 1) / 4.0f - 1, 0 };

                int[] indices = { 0, 1, 3, 1, 2, 3 };
                float colorSwitch = 0;
                if ((i + j) % 2 == 0) {
                    colorSwitch = 0.5f;
                }
                float[] textureCoords = { 7.0f / 8, colorSwitch, 7.0f / 8, colorSwitch + 0.5f, 8f / 8,
                        colorSwitch + 0.5f, 8f / 8, colorSwitch };
                squares[i][j] = new Square(vertices, textureCoords, indices, texture, new Position(i, j));
            }
        }
    }

    public Square[][] getSquares() {
        return squares;
    }
}
