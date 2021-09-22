package chess;

import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class Board {

    private TexturedModel[] squares = new TexturedModel[64];
    Loader loader = new Loader();

    public Board(ModelTexture texture) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                float[] vertices = { j / 4.0f - 1, i / 4.0f - 1, 0, j / 4.0f - 1, (i + 1) / 4.0f - 1, 0,
                        (j + 1) / 4.0f - 1, (i + 1) / 4.0f - 1, 0, (j + 1) / 4.0f - 1, i / 4.0f - 1, 0 };

                int[] indices = { 0, 1, 3, 3, 1, 2 };
                float colorSwitch = 0;
                if ((i + j) % 2 == 0) {
                    colorSwitch = 0.5f;
                }
                float[] textureCoords = { 7.0f / 8, colorSwitch, 7.0f / 8, colorSwitch + 0.5f, 8f / 8,
                        colorSwitch + 0.5f, 8f / 8, colorSwitch };

                RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
                squares[(8 * i) + j] = new TexturedModel(model, texture);
            }
        }
    }

    public TexturedModel[] getSquares() {
        return squares;
    }
}
